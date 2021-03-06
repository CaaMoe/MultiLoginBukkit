/*
 * Copyleft (c) 2021 ksqeib,CaaMoe. All rights reserved.
 * @author  ksqeib <ksqeib@dalao.ink> <https://github.com/ksqeib445>
 * @author  CaaMoe <miaolio@qq.com> <https://github.com/CaaMoe>
 * @github  https://github.com/CaaMoe/MultiLogin
 *
 * moe.caa.multilogin.core.auth.AuthCore
 *
 * Use of this source code is governed by the GPLv3 license that can be found via the following link.
 * https://github.com/CaaMoe/MultiLogin/blob/master/LICENSE
 */

package moe.caa.multilogin.core.auth;

import moe.caa.multilogin.core.language.LanguageKeys;
import moe.caa.multilogin.core.logger.LoggerLevel;
import moe.caa.multilogin.core.main.MultiCore;
import moe.caa.multilogin.core.yggdrasil.YggdrasilService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class AuthCore {

    public final MultiCore core;

    public AuthCore(MultiCore core) {
        this.core = core;
    }

    public <T> Object yggAuth(String name, String serverId) throws SQLException, InterruptedException {
        return yggAuth(name, serverId, null);
    }

    public <T> AuthResult<T> yggAuth(String name, String serverId, String ip) throws SQLException, InterruptedException {
        core.getLogger().log(LoggerLevel.DEBUG, LanguageKeys.DEBUG_LOGIN_START.getMessage(core, name, serverId, ip));
//        顺序获取
        List<List<YggdrasilService>> order = core.getVerifier().getVeriOrder(name);
//        服务器关闭
        boolean down = false;
        boolean haveService = false;
//        异常 必须保留
        Throwable throwable = null;

        for (List<YggdrasilService> entries : order) {
//            没有服务直接下一个
            if (entries.size() != 0) {
                haveService = true;
            } else {
                continue;
            }

//            获取验证结果
            AuthResult<T> result = authWithTasks(entries, name, serverId, ip);
            if (result != null && result.isSuccess()) {
                core.getLogger().log(LoggerLevel.DEBUG, LanguageKeys.DEBUG_LOGIN_END_ALLOW.getMessage(core, name, result.service.getName(), result.service.getPath()));
                return result;
            }
            if (result != null && result.err == AuthFailedEnum.SERVER_DOWN) {
                down = true;
            }
//            异常传出 必须留
            if (result.throwable != null) throwable = result.throwable;
        }
//        重构结果信息
        AuthFailedEnum failedEnum = down ? AuthFailedEnum.SERVER_DOWN : haveService ? AuthFailedEnum.VALIDATION_FAILED : AuthFailedEnum.NO_SERVICE;
        core.getLogger().log(LoggerLevel.DEBUG, LanguageKeys.DEBUG_LOGIN_END_DISALLOW.getMessage(core, name, failedEnum.name()));
        return new AuthResult<>(failedEnum, throwable);
    }

    private <T> AuthResult<T> authWithTasks(List<YggdrasilService> services, String name, String serverId, String ip) throws InterruptedException {
        AuthResult<T> getResult = null;
        List<FutureTask<AuthResult<T>>> tasks = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(services.size());
        for (YggdrasilService entry : services) {
            if (entry == null) continue;
//            创建进程
            FutureTask<AuthResult<T>> task = new FutureTask<>(new AuthTask<>(entry, name, serverId, ip, core, countDownLatch));
            core.plugin.getSchedule().runTaskAsync(task);
            tasks.add(task);
        }
//        等待 不使用循环 减少cpu消耗
        countDownLatch.await(core.servicesTimeOut, TimeUnit.MILLISECONDS);
        Iterator<FutureTask<AuthResult<T>>> itr = tasks.iterator();
        while (itr.hasNext()) {
            FutureTask<AuthResult<T>> task = itr.next();
//            由于强制终结的存在 还是会有没完成的
            if (!task.isDone()) continue;
            AuthResult<T> wResult = null;
            try {
                wResult = task.get();
            } catch (Exception ignored) {
            } finally {
                itr.remove();
            }
            if (getResult == null) getResult = wResult;
            if (wResult != null && wResult.isSuccess()) {
                getResult = wResult;
                break;
            }
        }
        for (FutureTask<AuthResult<T>> future : tasks) {
//            统一终结
            future.cancel(true);
        }
        return getResult;
    }
}
