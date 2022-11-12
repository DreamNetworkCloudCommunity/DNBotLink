package fr.benjimania74.dnbotlink.bot;

import be.alexandre01.dreamnetwork.core.console.Console;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.configmanager.Config;
import fr.benjimania74.configmanager.EncodedConfigManager;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.json.simple.JSONObject;

import java.util.HashMap;

public class BotConfig {
    private static BotConfig instance;
    public static BotConfig getInstance() {return instance;}

    private EncodedConfigManager config;

    private String activity = "DreamNetwork Bot";
    public String getActivity() {return activity;}
    public void setActivity(String activity) {this.activity = activity;}

    private String status = "online";
    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}

    private String prefix = "d!";
    public String getPrefix() {return prefix;}
    public void setPrefix(String prefix) {this.prefix = prefix;}

    private String permRole = "everyone";
    public String getPermRole() {return permRole;}
    public void setPermRole(String permRole) {this.permRole = permRole;}

    private HashMap<Object, Object> links = new HashMap<>();
    public HashMap<Object, Object> getLinks() {return links;}
    public void setLinks(HashMap<Object, Object> links) {this.links = links;}
    public void addLink(String serviceName, String discordChannel){links.put(serviceName, discordChannel); save();}

    private HashMap<Object, Object> chatLinks = new HashMap<>();
    public HashMap<Object, Object> getChatLinks() {return chatLinks;}
    public void setChatLinks(HashMap<Object, Object> links) {this.chatLinks = links;}
    public void addChatLink(String serviceName, String discordChannel){chatLinks.put(serviceName, discordChannel); save();}

    public BotConfig(){
        try {
            config = Config.getInstance().getEncodedConfig("config");
            setActivity(config.getString("activity"));
            setStatus(config.getString("status"));
            setPrefix(config.getString("prefix"));
            setLinks(config.getHashMap("link"));
            setChatLinks(config.getHashMap("chatlink"));
            setPermRole(config.getString("permrole"));

            if(BotMain.instance.jda != null){
                BotMain.instance.jda.getPresence().setStatus(OnlineStatus.fromKey(getStatus()));
                BotMain.instance.jda.getPresence().setActivity(Activity.playing(getActivity() + " | Type " + getPrefix() + "help"));
            }
        }catch (Exception e){
            config = Config.getInstance().createEncodedConfig("config");
            Console.print(Colors.GREEN_BACKGROUND + "The Configuration File has been created");
            save();
        }
        instance = this;
    }

    public void save(){
        try {
            config.set("activity", getActivity());
            config.set("status", getStatus());
            config.set("prefix", getPrefix());
            config.set("permrole", getPermRole());

            JSONObject list = new JSONObject();
            list.putAll(getLinks());
            config.set("link", list);

            JSONObject chatList = new JSONObject();
            chatList.putAll(getChatLinks());
            config.set("chatlink", chatList);

            config.save();

            reload();
        }catch (Exception e){
            Console.print(Colors.RED_BACKGROUND + "Can't save the Configuration File");
        }
    }

    public void reload(){new BotConfig();}
}
