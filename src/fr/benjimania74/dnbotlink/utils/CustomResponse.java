package fr.benjimania74.dnbotlink.utils;

import be.alexandre01.dreamnetwork.api.connection.core.communication.IClient;
import be.alexandre01.dreamnetwork.api.service.IContainer;
import be.alexandre01.dreamnetwork.core.utils.messages.Message;
import fr.benjimania74.dnbotlink.bot.BotConfig;
import fr.benjimania74.dnbotlink.bot.utils.SendMessage;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

public class CustomResponse extends be.alexandre01.dreamnetwork.api.connection.core.communication.CoreResponse {
    public CustomResponse(){}

    @Override
    protected void onResponse(Message message, ChannelHandlerContext ctx, IClient client) {
        if(message.getHeader().equals("DNBotLink-Chat")){
            String msg = message.getString("Chat");
            System.out.println(msg);
            String service = msg.split(" ")[0];

            if(Services.isLaunched(service, IContainer.JVMType.SERVER)){
                HashMap<Object, Object> chatLinks = BotConfig.getInstance().getChatLinks();
                if(!chatLinks.isEmpty()){if(chatLinks.containsKey(service)){SendMessage.send(msg.substring(service.length() + 1), chatLinks.get(service));}}
            }
        }
    }
}
