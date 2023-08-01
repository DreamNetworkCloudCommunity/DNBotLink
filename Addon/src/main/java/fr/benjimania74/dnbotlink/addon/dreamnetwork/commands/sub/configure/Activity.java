package fr.benjimania74.dnbotlink.addon.dreamnetwork.commands.sub.configure;

import be.alexandre01.dreamnetwork.api.commands.Command;
import be.alexandre01.dreamnetwork.api.commands.sub.NodeBuilder;
import be.alexandre01.dreamnetwork.api.commands.sub.SubCommand;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.dnbotlink.addon.dreamnetwork.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.utils.BotConfig;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

public class Activity extends SubCommand {
    public Activity(Command command){
        super(command);
        NodeBuilder nodeBuilder = new NodeBuilder(
                NodeBuilder.create(command.getName(), NodeBuilder.create(Colors.PURPLE_BOLD + "activity")));
    }

    @Override
    public boolean onSubCommand(@NotNull @NonNull String[] args) {
        return when(sArgs -> {
            AddonMain main = AddonMain.getInstance();
            BotConfig config = main.getConfigManager().getBotConfig();

            if(sArgs.length == 1){
                main.print(Colors.GREEN + "The actual Discord Bot's Activity is: " + Colors.YELLOW_BRIGHT + config.getActivity());
                return true;
            }

            StringBuilder sb = new StringBuilder();
            for(int i = 1 ; i != sArgs.length ; i++){
                sb.append(sArgs[i]).append(" ");
            }
            String complete = sb.substring(0, sb.toString().length()-1);
            if(complete.equalsIgnoreCase("none")){
                config.setActivity("");
            }else {
                config.setActivity(complete);
            }
            main.getConfigManager().saveBotConfig();
            main.print(Colors.GREEN + "The Discord Bot's Activity has been changed");
            return true;
        }, args, "activity");
    }
}
