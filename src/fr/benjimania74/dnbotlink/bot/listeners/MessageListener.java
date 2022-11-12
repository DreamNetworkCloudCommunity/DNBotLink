package fr.benjimania74.dnbotlink.bot.listeners;


import be.alexandre01.dreamnetwork.api.service.IContainer;
import be.alexandre01.dreamnetwork.core.utils.messages.Message;
import fr.benjimania74.dnbotlink.Main;
import fr.benjimania74.dnbotlink.bot.BotConfig;
import fr.benjimania74.dnbotlink.bot.BotMain;
import fr.benjimania74.dnbotlink.bot.utils.ExecuteServerCmd;
import fr.benjimania74.dnbotlink.utils.Services;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class MessageListener extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if(event.getAuthor().isBot()){return;}

        HashMap<Object, Object> chatLinks = BotConfig.getInstance().getChatLinks();
        if(!chatLinks.isEmpty()){
            if(chatLinks.containsValue(event.getChannel().getId())){
                for(Object name : chatLinks.keySet()){
                    if(chatLinks.get(name).equals(event.getChannel().getId())){
                        if(Services.isLaunched(name, IContainer.JVMType.SERVER)){
                            String msg = "[Discord] " + event.getMessage().getAuthor().getName() + "> " + event.getMessage().getContentRaw();
                            Message message = new Message();
                            message.setHeader("DNBot-Chat");
                            message.set("Chat", msg);
                            Main.coreAPI.getContainer().getJVMExecutorsServers().get(name).getService(0).getClient().writeAndFlush(message);
                        }
                    }
                }
            }
        }

        if(event.getMessage().getContentRaw().equalsIgnoreCase("<@" + event.getJDA().getSelfUser().getId() + ">")){
            event.getChannel().sendMessage("My prefix is " + BotConfig.getInstance().getPrefix()).queue();
            return;
        }

        String permRID = BotConfig.getInstance().getPermRole();
        if(!permRID.equals("everyone")){
            Role r = event.getGuild().getRoleById(permRID);
            assert r != null;
            if(!event.getGuild().getMember(event.getAuthor()).getRoles().contains(r)){return;}
        }

        if(event.getMessage().getContentRaw().startsWith(BotConfig.getInstance().getPrefix())){
            String cmd = event.getMessage().getContentRaw().split(" ")[0].substring(BotConfig.getInstance().getPrefix().length());
            if(BotMain.commandsList.containsKey(cmd)){BotMain.commandsList.get(cmd).execute((TextChannel) event.getChannel(), Main.coreAPI, event.getMessage());}
        }

        BotConfig.getInstance().getLinks().forEach((service, id) -> {
            String name = (String) service;

            if(((String)service).split("<&>").length == 2){
                String[] args = ((String)service).split("<&>");
                name = args[0];
                if(args[1].equalsIgnoreCase("proxy")){ return; }
            }

            if(id.equals(event.getChannel().getId())){new ExecuteServerCmd().execute(event.getMessage().getContentRaw(), name);}
        });
    }
}
