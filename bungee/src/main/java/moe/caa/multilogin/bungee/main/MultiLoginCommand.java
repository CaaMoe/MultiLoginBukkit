/*
 * Copyleft (c) 2021 ksqeib,CaaMoe. All rights reserved.
 * @author  ksqeib <ksqeib@dalao.ink> <https://github.com/ksqeib445>
 * @author  CaaMoe <miaolio@qq.com> <https://github.com/CaaMoe>
 * @github  https://github.com/CaaMoe/MultiLogin
 *
 * moe.caa.multilogin.bungee.main.MultiLoginCommand
 *
 * Use of this source code is governed by the GPLv3 license that can be found via the following link.
 * https://github.com/CaaMoe/MultiLogin/blob/master/LICENSE
 */

package moe.caa.multilogin.bungee.main;

import moe.caa.multilogin.core.main.MultiCore;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.TabExecutor;

public class MultiLoginCommand extends Command implements TabExecutor {
    private final MultiCore core;

    public MultiLoginCommand(String name, MultiCore core) {
        super(name);
        this.core = core;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        core.getCommandHandler().execute(new BungeeSender(sender), getName(), args);
    }

    @Override
    public Iterable<String> onTabComplete(CommandSender sender, String[] args) {
        return core.getCommandHandler().tabCompete(new BungeeSender(sender), getName(), args);
    }
}
