package fr.benjimania74.dnbotlink.addon.bot;

import be.alexandre01.dreamnetwork.core.console.Console;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.dnbotlink.addon.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.commands.Command;
import fr.benjimania74.dnbotlink.addon.bot.commands.CommandManager;
import fr.benjimania74.dnbotlink.addon.bot.listeners.MessageListener;
import fr.benjimania74.dnbotlink.addon.bot.listeners.SlashCommandAutoCompleteListener;
import fr.benjimania74.dnbotlink.addon.bot.listeners.SlashCommandListener;
import fr.benjimania74.dnbotlink.addon.utils.config.BotConfig;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BotMain {
    @Getter private static BotMain instance;

    @Getter private JDA jda = null;
    @Getter private boolean started;

    public BotMain(){
        start();
        instance = this;
    }

    public void start(){
        if(jda != null && isStarted()){
            stop();
        }
        AddonMain main = AddonMain.getInstance();
        BotConfig config = main.getConfigManager().getBotConfig();

        try {
            JDABuilder builder = JDABuilder.createLight(config.getToken(), GatewayIntent.GUILD_MESSAGES, GatewayIntent.GUILD_MEMBERS, GatewayIntent.MESSAGE_CONTENT)
                    .setChunkingFilter(ChunkingFilter.ALL)
                    .setMemberCachePolicy(MemberCachePolicy.ALL)
                    .setStatus(config.getStatus())
                    .setActivity(Activity.playing((config.getActivity().isEmpty() ? "" : config.getActivity() + " | ") + "By benjimania74"));
            jda = builder.build();
            new CommandManager();

            List<SlashCommandData> commands = new ArrayList<>();
            for(Command command : CommandManager.getInstance().getCommands()){
                SlashCommandData cmd = Commands.slash(command.getName(), command.getDescription());
                if(command.getArgumentsCompleter() != null){
                    command.getArgumentsCompleter().forEach((name, completer) -> {
                        cmd.addOption(completer.getType(), name, completer.getDescription(), true, true);
                    });
                }
                cmd.setGuildOnly(true);
                commands.add(cmd);
            }

            jda.updateCommands().addCommands(commands).queue();

            jda.addEventListener(new SlashCommandListener());
            jda.addEventListener(new SlashCommandAutoCompleteListener());
            jda.addEventListener(new MessageListener());

            main.print(Colors.GREEN + "Discord Bot Started");
            started = true;
        }catch (Exception e){
            main.print(Colors.YELLOW_BRIGHT + "You can configure the Discord Bot with the command 'config'");
            started = false;
        }
    }

    public void stop(){
        if(jda != null && isStarted()){
            try {
                if(!jda.awaitShutdown(Duration.ofSeconds(10))){
                    jda.shutdownNow();
                }
                started = false;
            }catch (InterruptedException ie){
                Console.bug(ie);
            }
        }
    }

    public void restart(){
        stop();
        int i = 0;
        while(isStarted()){i++;}
        start();
    }
}
