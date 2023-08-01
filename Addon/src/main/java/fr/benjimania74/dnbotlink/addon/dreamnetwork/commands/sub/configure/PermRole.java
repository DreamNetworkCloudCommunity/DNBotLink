package fr.benjimania74.dnbotlink.addon.dreamnetwork.commands.sub.configure;

import be.alexandre01.dreamnetwork.api.commands.Command;
import be.alexandre01.dreamnetwork.api.commands.sub.NodeBuilder;
import be.alexandre01.dreamnetwork.api.commands.sub.SubCommand;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.dnbotlink.addon.dreamnetwork.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.utils.BotConfig;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

public class PermRole extends SubCommand {
    public PermRole(Command command){
        super(command);

        NodeBuilder nodeBuilder = new NodeBuilder(
                NodeBuilder.create(command.getName(),
                        NodeBuilder.create(Colors.BLUE_BOLD + "permrole",
                                NodeBuilder.create("everyone"))));
    }

    @Override
    public boolean onSubCommand(@NotNull @NonNull String[] args) {
        return when(sArgs -> {
            AddonMain main = AddonMain.getInstance();
            BotConfig config = main.getConfigManager().getBotConfig();

            if(sArgs.length == 1){
                main.print(Colors.GREEN + "The actual Discord Bot's Permrole is: " + Colors.YELLOW_BRIGHT + config.getPermRole());
                return true;
            }

            if(sArgs[1].equalsIgnoreCase("everyone")){
                config.setPermRole("everyone");
                main.getConfigManager().saveBotConfig();
                main.print(Colors.GREEN + "The Discord Bot's Permrole has been changed");
                return true;
            }

            if(sArgs[1].length() != 18){
                main.print(Colors.RED + "The Discord Permrole's ID must be of 18 lengths");
                return true;
            }

            boolean valid = true;
            char[] v = sArgs[1].toCharArray();
            for(char c : v){
                try{
                    int i = Integer.parseInt(String.valueOf(c));
                }catch (NumberFormatException nfe){
                    valid = false;
                    break;
                }
            }
            if(!valid){
                main.print(Colors.RED + "The Discord Permrole's ID is only composed by Numbers");
                return true;
            }

            config.setPermRole(sArgs[1]);
            main.getConfigManager().saveBotConfig();
            main.print(Colors.GREEN + "The Discord Bot's Permrole has been changed");
            return true;
        }, args, "permrole");
    }
}
