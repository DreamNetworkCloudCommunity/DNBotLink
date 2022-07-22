package fr.benjimania74.dnapitest.bot;

import be.alexandre01.dreamnetwork.client.console.Console;
import be.alexandre01.dreamnetwork.client.console.colors.Colors;
import fr.benjimania74.dnapitest.bot.cmd.Command;
import fr.benjimania74.dnapitest.bot.listeners.MessageListener;
import fr.benjimania74.dnapitest.utils.FilesManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BotMain {
    public static BotMain instance;
    public JDA jda;
    public static List<Command> commandsList;

    public boolean create(){
        instance = this;
        commandsList = new ArrayList<>();

        try{
            new BotConfig();

            String token = FilesManager.getInstance().read("token");

            JDABuilder builder = JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.GUILD_MESSAGES)
                    .setStatus(OnlineStatus.fromKey(BotConfig.getInstance().getStatus()))
                    .setActivity(Activity.playing(BotConfig.getInstance().getActivity() + " | Type " + BotConfig.getInstance().getPrefix() + "help"));

            jda = builder.build();
            new CommandsRegister().register();
            jda.addEventListener(new MessageListener());

            Console.print(Colors.GREEN_BACKGROUND + "The Bot is started");
            return true;
        }catch (Exception e){
            Console.print(Colors.RED_BACKGROUND + "The Bot can't be started");
            e.printStackTrace();
            return false;
        }
    }

    public void registerCommand(Command command, Command... commands){
        commandsList.add(command);
        commandsList.addAll(Arrays.asList(commands));
    }
    public void unregisterCommand(Command command){commandsList.remove(command);}
    public List<Command> getCommands(){return commandsList;}
}
