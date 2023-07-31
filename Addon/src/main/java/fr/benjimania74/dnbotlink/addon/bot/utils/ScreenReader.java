package fr.benjimania74.dnbotlink.addon.bot.utils;

import be.alexandre01.dreamnetwork.api.service.IService;
import be.alexandre01.dreamnetwork.api.service.screen.IScreenStream;
import fr.benjimania74.dnbotlink.addon.bot.BotMain;
import lombok.Getter;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;

import java.util.ArrayList;
import java.util.List;

public class ScreenReader {
    @Getter private List<ThreadChannel> channels = new ArrayList<>();
    private List<String> waitingMessage = new ArrayList<>();
    private int timeLeft = 0;
    private boolean stop = false;

    public ScreenReader(IService service){
        if(!BotMain.getInstance().isStarted() || service.getJvmExecutor().isProxy()){return;}
        timer();

        IScreenStream screenStream = service.getScreen().getScreenStream();
        while (screenStream == null){
            screenStream = service.getScreen().getScreenStream();
        }
        screenStream.getScreenInReader().getReaderLines().add(line -> {
            addLine(line);
            return line;
        });
    }

    private void addLine(String line){
        if(waitingMessage.size() == 20){send();}
        waitingMessage.add(line);
        timeLeft = 1;
    }

    public void send(){
        if(waitingMessage.isEmpty() || !BotMain.getInstance().isStarted()){return;}
        StringBuilder sb = new StringBuilder();
        for(String l : waitingMessage){sb.append(l).append("\n");}
        waitingMessage = new ArrayList<>();

        for(ThreadChannel channel : channels){
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
