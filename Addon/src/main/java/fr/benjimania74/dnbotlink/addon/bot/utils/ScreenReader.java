package fr.benjimania74.dnbotlink.addon.bot.utils;

import be.alexandre01.dreamnetwork.api.service.IService;
import fr.benjimania74.dnbotlink.addon.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.BotMain;
import lombok.Getter;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.middleman.GuildChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

import java.util.ArrayList;
import java.util.List;

public class ScreenReader {
    private List<String> channels;
    @Getter private final List<TextChannel> validChannels = new ArrayList<>();
    private List<String> waitingMessage = new ArrayList<>();
    private int timeLeft = 0;
    private boolean stop = false;

    public ScreenReader(IService service, List<String> channels){
        this.channels = channels;
        getChannels();
        timer();

        service.getScreen().getScreenStream().getScreenInReader().getReaderLines().add(line -> {
            List<String> c = AddonMain.getInstance().getConfigManager().getLinkConfig().getConsoleLinks().get(service.getFullName().substring(0, service.getFullName().length()-2));
            if(c != this.channels){
                this.channels = c;
                getChannels();
            }
            addLine(line);
            return line;
        });
    }

    private void getChannels(){
        JDA jda = BotMain.getInstance().getJda();
        for(String channelID : channels){
            GuildChannel guildChannel = jda.getGuildChannelById(channelID);
            if(guildChannel instanceof TextChannel){validChannels.add((TextChannel)guildChannel);}
        }
    }

    private void addLine(String line){
        if(waitingMessage.size() == 20){send();}
        waitingMessage.add(line);
        timeLeft = 1;
    }

    public void send(){
        if(waitingMessage.size() == 0){return;}
        StringBuilder sb = new StringBuilder();
        for(String l : waitingMessage){sb.append(l).append("\n");}
        waitingMessage = new ArrayList<>();

        for(TextChannel channel : validChannels){
            MessageCreateBuilder mcb = new MessageCreateBuilder();
            mcb.setSuppressedNotifications(true);
            mcb.setContent("```ansi\n" + sb + "\n```");
            channel.sendMessage(mcb.build()).queue();
        }
    }

    private void timer(){
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if(stop){send();return;}
                if(timeLeft == 0){send();}
                if(timeLeft > 0){
                    timeLeft--;
                }
                try {
                    Thread.sleep(1000);
                    run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        t.start();
    }

    public void stop(){stop = true;}
}
