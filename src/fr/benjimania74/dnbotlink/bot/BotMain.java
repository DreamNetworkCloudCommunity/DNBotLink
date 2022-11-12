package fr.benjimania74.dnbotlink.bot;

import be.alexandre01.dreamnetwork.core.console.Console;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.dnbotlink.bot.cmd.Command;
import fr.benjimania74.dnbotlink.bot.listeners.MessageListener;
import fr.benjimania74.dnbotlink.bot.utils.CommandsRegister;
import fr.benjimania74.dnbotlink.utils.FilesManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.util.HashMap;

public class BotMain {
    public static BotMain instance;
    public JDA jda;
    public static HashMap<String, Command> commandsList;

    public boolean create(){
        instance = this;
        commandsList = new HashMap<>();

        try{
            new BotConfig();

            String token = FilesManager.getInstance().read("token");

            JDABuilder builder = JDABuilder.createLight(token, GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
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
        commandsList.put(command.getName(), command);
        for(Command cmd : commands){commandsList.put(cmd.getName(), cmd);}
    }
    public void unregisterCommand(Command command){commandsList.remove(command.getName());}
    public HashMap<String, Command> getCommands(){return commandsList;}
}
