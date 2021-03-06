/*
 * Copyleft (c) 2021 ksqeib,CaaMoe. All rights reserved.
 * @author  ksqeib <ksqeib@dalao.ink> <https://github.com/ksqeib445>
 * @author  CaaMoe <miaolio@qq.com> <https://github.com/CaaMoe>
 * @github  https://github.com/CaaMoe/MultiLogin
 *
 * moe.caa.multilogin.core.yggdrasil.YggdrasilServicesHandler
 *
 * Use of this source code is governed by the GPLv3 license that can be found via the following link.
 * https://github.com/CaaMoe/MultiLogin/blob/master/LICENSE
 */

package moe.caa.multilogin.core.yggdrasil;

import moe.caa.multilogin.core.language.LanguageKeys;
import moe.caa.multilogin.core.logger.LoggerLevel;
import moe.caa.multilogin.core.main.MultiCore;
import moe.caa.multilogin.core.util.YamlConfig;

import java.util.ArrayList;

public class YggdrasilServicesHandler {
    private static final ArrayList<YggdrasilService> SERVICES = new ArrayList<>();
    private final MultiCore core;

    public YggdrasilServicesHandler(MultiCore core) {
        this.core = core;
    }

    public void init() {
        reload();
    }

    /**
     * 读取 Yggdrasil
     */
    public void reload() {
        SERVICES.clear();
        YamlConfig services = core.config.get("services", YamlConfig.class);
        if (services != null) {
            for (String path : services.getKeys()) {
                YamlConfig section = services.get(path, YamlConfig.class);
                if (section == null) continue;
                try {
                    YggdrasilService service = YggdrasilService.fromYamlConfig(path, section, core);
                    SERVICES.add(service);
                    if (service.isEnable()) {
                        core.getLogger().log(LoggerLevel.INFO, LanguageKeys.APPLY_YGGDRASIL.getMessage(core, service.getName(), service.getPath()));
                    } else {
                        core.getLogger().log(LoggerLevel.INFO, LanguageKeys.APPLY_YGGDRASIL_NO_ENABLE.getMessage(core, service.getName(), service.getPath()));
                    }
                } catch (Exception e) {
                    core.getLogger().log(LoggerLevel.ERROR, LanguageKeys.YGGDRASIL_CONFIGURATION_ERROR.getMessage(core, path, e.getMessage()));
                }
            }
        }
        if (SERVICES.stream().noneMatch(YggdrasilService::isEnable)) {
            core.getLogger().log(LoggerLevel.WARN, LanguageKeys.SERVICES_NOTHING.getMessage(core));
        }
    }

    public YggdrasilService getService(String path) {
        for (YggdrasilService service : SERVICES) {
            if (service.getPath().equals(path)) {
                return service;
            }
        }
        return null;
    }

    public ArrayList<YggdrasilService> getServices() {
        return SERVICES;
    }
}
