package fr.benjimania74.dnbotlink.addon.bot.listeners;

import be.alexandre01.dreamnetwork.api.connection.request.RequestType;
import fr.benjimania74.dnbotlink.addon.dreamnetwork.AddonMain;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        AddonMain main = AddonMain.getInstance();
        User sender = event.getAuthor();
        MessageChannelUnion channel = event.getChannel();
        String content = event.getMessage().getContentRaw();

        if(sender.isBot()){return;}

        if(channel.getType().isThread()) {
            main.getServiceScreenReaders().forEach((service, screenReader) -> {
                for (ThreadChannel tc : screenReader.getChannels()) {
                    if (tc.getId().equals(channel.getId())) {
                        service.getClient().getRequestManager().sendRequest(RequestType.SERVER_EXECUTE_COMMAND, content);
                        return;
                    }
                }
            });
        }
    }
}
