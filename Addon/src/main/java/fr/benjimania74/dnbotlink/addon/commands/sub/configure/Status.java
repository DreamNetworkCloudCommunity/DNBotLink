package fr.benjimania74.dnbotlink.addon.commands.sub.configure;

import be.alexandre01.dreamnetwork.api.commands.Command;
import be.alexandre01.dreamnetwork.api.commands.sub.NodeBuilder;
import be.alexandre01.dreamnetwork.api.commands.sub.SubCommand;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.dnbotlink.addon.AddonMain;
import fr.benjimania74.dnbotlink.addon.utils.config.BotConfig;
import lombok.NonNull;
import net.dv8tion.jda.api.OnlineStatus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Status extends SubCommand {
    private final List<String> onlineStatuses = new ArrayList<>();

    public Status(Command command){
        super(command);
        OnlineStatus[] onlineS = OnlineStatus.values();
        for(OnlineStatus os : onlineS){
            if(!os.getKey().equalsIgnoreCase("")){
                onlineStatuses.add(os.getKey());
            }
        }

        NodeBuilder nodeBuilder = new NodeBuilder(
                NodeBuilder.create(command.getName(),
                        NodeBuilder.create(Colors.GREEN_BOLD + "status",
                                NodeBuilder.create(onlineStatuses.toArray()))));
    }

    @Override
    public boolean onSubCommand(@NotNull @NonNull String[] args) {
        return when(sArgs -> {
            AddonMain main = AddonMain.getInstance();
            BotConfig config = main.getConfigManager().getBotConfig();

            if(sArgs.length == 1){
                main.print(Colors.GREEN + "The actual Discord Bot' Status is: " + Colors.YELLOW_BRIGHT + config.getStatus().getKey());
                return true;
            }

            if(!onlineStatuses.contains(sArgs[1])){
                main.print(Colors.RED + sArgs[1] + " is not a valid argument");
                return true;
            }

            config.setStatus(OnlineStatus.fromKey(sArgs[1]));
            main.getConfigManager().saveBotConfig();
            main.print(Colors.GREEN + "The Discord Bot's Status has been changed");
            return true;
        }, args, "status");
    }
}
