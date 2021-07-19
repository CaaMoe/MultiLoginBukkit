package moe.caa.multilogin.core.command.commands.multilogin.query;

import moe.caa.multilogin.core.command.Permission;
import moe.caa.multilogin.core.command.SubCommand;
import moe.caa.multilogin.core.data.User;
import moe.caa.multilogin.core.data.database.handler.UserDataHandler;
import moe.caa.multilogin.core.impl.ISender;
import moe.caa.multilogin.core.language.LanguageKeys;
import moe.caa.multilogin.core.main.MultiCore;

import java.util.List;

public class QueryNameCommand extends SubCommand {

    protected QueryNameCommand() {
        super("name", Permission.MULTI_LOGIN_MULTI_LOGIN_QUERY);
    }

    @Override
    public void executeAsync(ISender sender, String[] args) throws Throwable {
        if(args.length == 1){
            List<User> users = UserDataHandler.getUserEntryByCurrentName(args[0]);
            if(users.size() == 0){
                sender.sendMessage(LanguageKeys.COMMAND_UNKNOWN_NAME.getMessage(args[0]));
                return;
            }
            MultiCore.plugin.getSchedule().runTask(() -> {
               sender.sendMessage(LanguageKeys.COMMAND_QUERY_LIST.getMessage(users.size()));
                for (User user : users) {
                    sender.sendMessage(MainQueryCommand.toMessage(user));
                }
            });
        } else {
            sendUnknownCommandMessage(sender);
        }
    }
}