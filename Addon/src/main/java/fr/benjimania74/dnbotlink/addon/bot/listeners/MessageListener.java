package fr.benjimania74.dnbotlink.addon.bot.listeners;

import fr.benjimania74.dnbotlink.addon.AddonMain;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        AddonMain main = AddonMain.getInstance();
        User sender = event.getAuthor();
        MessageChannelUnion channel = event.getChannel();
        String content = event.getMessage().getContentRaw();

        if(sender.isBot()){return;}

        main.getServiceScreenReaders().forEach((service, screenReader) -> {
            if(screenReader.getValidChannels().contains(channel.asTextChannel())){
                try {
                    service.getScreen().getScreenStream().getScreenOutWriter().writeOnConsole(content);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
