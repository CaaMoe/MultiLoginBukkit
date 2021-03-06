/*
 * Copyleft (c) 2021 ksqeib,CaaMoe. All rights reserved.
 * @author  ksqeib <ksqeib@dalao.ink> <https://github.com/ksqeib445>
 * @author  CaaMoe <miaolio@qq.com> <https://github.com/CaaMoe>
 * @github  https://github.com/CaaMoe/MultiLogin
 *
 * moe.caa.multilogin.core.language.LanguageKeys
 *
 * Use of this source code is governed by the GPLv3 license that can be found via the following link.
 * https://github.com/CaaMoe/MultiLogin/blob/master/LICENSE
 */

package moe.caa.multilogin.core.language;

import moe.caa.multilogin.core.main.MultiCore;

public enum LanguageKeys {
    PLUGIN_LOAD_ERROR("plugin_load_error"),
    BSTATS_LOAD_FAIL("bstats_load_fail"),
    PLUGIN_LOADED("plugin_loaded"),
    PLUGIN_UNLOADED("plugin_unloaded"),
    USE_INSIDE_LANGUAGE("use_inside_language"),
    USE_OUTSIDE_LANGUAGE("use_outside_language"),
    REPAIR_LANGUAGE_KEY("repair_language_key", ""),
    DEBUG_ENABLE("debug_enable"),
    LOGGER_FILE_ERROR("logger_file_error"),
    CONFIG_LOAD_ERROR("config_load_error"),
    SERVICES_NOTHING("services_nothing"),
    CONFIGURATION_VALUE_ERROR("configuration_value_error", ""),
    CONFIGURATION_KEY_ERROR("configuration_key_error", ""),
    URL_ILLEGAL_FORMAT("url_illegal_format", ""),
    YGGDRASIL_CONFIGURATION_ERROR("yggdrasil_configuration_error", "", ""),
    APPLY_YGGDRASIL("apply_yggdrasil", "", ""),
    APPLY_YGGDRASIL_NO_ENABLE("apply_yggdrasil_no_enable", "", ""),
    DATABASE_CONNECTED("database_connected", ""),
    DATABASE_CONNECT_ERROR("database_connect_error"),
    REFLECT_INIT_ERROR("reflect_init_error", ""),
    LIBRARY_INIT("library_init"),
    LIBRARY_DOWNLOADING("library_downloading", ""),
    LIBRARY_DOWNLOAD_FAILED("library_download_failed", ""),
    LIBRARY_DOWNLOADED("library_downloaded", ""),
    LIBRARY_LOADED("library_loaded", ""),
    LIBRARY_LOAD_FAILED("library_load_failed", ""),
    VERIFICATION_NO_ADAPTER("verification_no_adapter"),
    VERIFICATION_NO_CHAE("verification_no_chae"),
    VERIFICATION_RUSH_NAME("verification_rush_name"),
    VERIFICATION_RUSH_NAME_ONL("verification_rush_name_Onl"),
    VERIFICATION_NO_WHITELIST("verification_no_whitelist"),
    VERIFICATION_NO_PAT_MATCH("verification_no_pat_match"),
    VERIFICATION_ERROR("verification_error"),
    VERIFICATION_ALLOW("verification_allow", "", "", "", "", ""),


    VERIFICATION_SELF_LOGIN_SQUEEZE("verification_self_login_squeeze"),
    VERIFICATION_ANOTHER_LOGIN_SQUEEZE("verification_another_login_squeeze"),


    NIT_ONLINE("not_online"),
    ERROR_AUTH("error_auth"),
    ERROR_REDIRECT_MODIFY("error_reflect_modify"),

    DEBUG_LOGIN_START("debug_login_start", "", "", ""),
    DEBUG_LOGIN_AUTH_TASK_SERVER_DOWN("debug_login_auth_task_server_down", "", "", ""),
    DEBUG_LOGIN_AUTH_TASK_DISALLOW("debug_login_auth_task_disallow", "", "", ""),
    DEBUG_LOGIN_AUTH_TASK_ALLOW("debug_login_auth_task_allow", "", "", ""),

    DEBUG_LOGIN_END_ALLOW("debug_login_end_allow", "", "", ""),
    DEBUG_LOGIN_END_DISALLOW("debug_login_end_disallow", "", ""),

    DEBUG_VERIFICATION_NO_CHAE("debug_verification_no_chae", "", "", "", "", ""),
    DEBUG_VERIFICATION_RUSH_NAME("debug_verification_rush_name", "", "", "", "", ""),
    DEBUG_VERIFICATION_NO_WHITELIST("debug_verification_no_whitelist", "", ""),
    DEBUG_VERIFICATION_REPEATED_LOGIN("debug_verification_repeated_login", "", ""),
    DEBUG_VERIFICATION_NO_PAT_MATCH("debug_verification_no_pat_match", "", "", ""),
    DEBUG_VERIFICATION_REPEAT_UUID("debug_verification_repeat_uuid", "", "", "", "", "", ""),

    UPDATE_SENDER("update_sender"),
    UPDATE_CONSOLE("update_console", "", ""),

    COMMAND_UNKNOWN("command_unknown"),
    COMMAND_ERROR("command_error"),
    COMMAND_RELOADED("command_reloaded"),

    COMMAND_WHITELIST_ADD("command_whitelist_add", ""),
    COMMAND_WHITELIST_ADD_ALREADY("command_whitelist_add_already", ""),
    COMMAND_WHITELIST_DEL("command_whitelist_del", ""),
    COMMAND_WHITELIST_DEL_ALREADY("command_whitelist_del_already", ""),

    COMMAND_NO_UUID("command_no_uuid", ""),
    COMMAND_UNKNOWN_ONLINE_UUID("command_unknown_online_uuid", ""),
    COMMAND_UNKNOWN_NAME("command_unknown_name", ""),
    COMMAND_UNKNOWN_REDIRECT_UUID("command_unknown_redirect_uuid", ""),
    COMMAND_QUERY_LIST("command_query_list", ""),
    COMMAND_QUERY_ENTRY("command_query_entry", "", "", "", "", "", ""),

    BUKKIT_LOADED_PLACEHOLDER("bukkit_loaded_placeholder"),
    BUKKIT_FAIL_LOAD_PLACEHOLDER("bukkit_fail_load_placeholder");

    public final String key;
    public final Object[] args;

    LanguageKeys(String key, Object... args) {
        this.key = key;
        this.args = args;
    }

    public String getMessage(MultiCore core, Object... args) {
        return core.getLanguageHandler().getMessage(this, args);
    }
}
