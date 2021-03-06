/*
 * Copyleft (c) 2021 ksqeib,CaaMoe. All rights reserved.
 * @author  ksqeib <ksqeib@dalao.ink> <https://github.com/ksqeib445>
 * @author  CaaMoe <miaolio@qq.com> <https://github.com/CaaMoe>
 * @github  https://github.com/CaaMoe/MultiLogin
 *
 * moe.caa.multilogin.core.impl.IPlugin
 *
 * Use of this source code is governed by the GPLv3 license that can be found via the following link.
 * https://github.com/CaaMoe/MultiLogin/blob/master/LICENSE
 */

package moe.caa.multilogin.core.impl;

import com.google.gson.Gson;
import moe.caa.multilogin.core.command.Permission;
import moe.caa.multilogin.core.data.User;
import moe.caa.multilogin.core.language.LanguageKeys;
import moe.caa.multilogin.core.main.MultiCore;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * 代表一个插件对象
 */
public interface IPlugin {

    /**
     * 获得插件数据文件夹
     *
     * @return 插件数据文件夹
     */
    File getDataFolder();

    /**
     * 获得插件Jar包文件流
     *
     * @param path Jar包文件路径
     * @return 对应的文件流
     */
    default InputStream getJarResource(String path) {
        try {
            URL url = getClass().getClassLoader().getResource(path);
            if (url != null) {
                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);
                return connection.getInputStream();
            }
        } catch (IOException ignored) {
        }
        return null;
    }

    /**
     * 获得当前所有在线的玩家列表
     *
     * @return 在线玩家列表
     */
    List<ISender> getOnlinePlayers();

    /**
     * 获得插件的日志记录器
     *
     * @return 日志记录器
     */
    Logger getLogger();

    /**
     * 获得插件版本号
     *
     * @return 插件版本号
     */
    String getPluginVersion();

    /**
     * 获得线程调度器对象
     *
     * @return 调度器对象
     */
    AbstractScheduler getSchedule();

    /**
     * 是否开启在线验证
     *
     * @return 在线验证模式
     */
    boolean isOnlineMode();

    /**
     * 获得在线玩家
     *
     * @param uuid uuid
     * @return 玩家
     */
    ISender getPlayer(UUID uuid);

    /**
     * 获得在线玩家
     */
    List<ISender> getPlayer(String name);

    /**
     * 获得原版验证时的 GSON 对象
     */
    Gson getAuthGson();

    /**
     * 得到验证返回的 Type
     */
    Type authResultType();

    /**
     * 加载反射核心服务
     */
    void initCoreService() throws Throwable;

    /**
     * 加载其他服务
     */
    void initOtherService();

    /**
     * 关闭服务端
     */
    void shutdown();

    MultiCore getMultiCore();

    String getServerCoreName();

    String getServerVersion();


    default boolean onAsyncLoginSuccess(UUID uuid, String name) {
        return getMultiCore().getVerifier().CACHE_LOGIN.remove(uuid, name);
    }

    default void onQuit(UUID redirectUuid) {
        getMultiCore().getVerifier().CACHE_USER.removeIf(user -> getPlayer(user.getRedirectUuid()) == null);
    }

    /**
     * 发送更新信息
     */
    default void onJoin(ISender player) {
        if (Permission.MULTI_LOGIN_UPDATE.hasPermission(player)) {
            if (getMultiCore().getUpdater().haveUpdate) {
                player.sendMessage(LanguageKeys.UPDATE_SENDER.getMessage(getMultiCore()));
            }
        }
    }

    /**
     * 获得 User 缓存
     *
     * @param redirectUuid 游戏内 UUID
     */
    default User getCacheUserData(UUID redirectUuid) {
        for (User user : getMultiCore().getVerifier().CACHE_USER) {
            if (user.getRedirectUuid().equals(redirectUuid)) {
                return user;
            }
        }
        return null;
    }
}
