package fr.benjimania74.dnbotlink.addon.commands.sub.configure;

import be.alexandre01.dreamnetwork.api.commands.Command;
import be.alexandre01.dreamnetwork.api.commands.sub.NodeBuilder;
import be.alexandre01.dreamnetwork.api.commands.sub.SubCommand;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.dnbotlink.addon.AddonMain;
import fr.benjimania74.dnbotlink.addon.utils.config.BotConfig;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

public class Token extends SubCommand {
    public Token(Command command){
        super(command);

        NodeBuilder nodeBuilder = new NodeBuilder(
                NodeBuilder.create(command.getName(),
                        NodeBuilder.create(Colors.YELLOW_BOLD + "token")));
    }

    @Override
    public boolean onSubCommand(@NotNull @NonNull String[] args) {
        return when(sArgs -> {
            AddonMain main = AddonMain.getInstance();
            BotConfig config = main.getConfigManager().getBotConfig();

            if(sArgs.length < 2){
                main.print(Colors.RED + "Invalid Command's Argument");
                return false;
            }
            config.setToken(sArgs[1]);
            main.getConfigManager().saveBotConfig();
            main.print(Colors.GREEN + "The Discord Bot's Token has been changed");
            return true;
        }, args, "token", "[Discord Bot's Token]");
    }
}