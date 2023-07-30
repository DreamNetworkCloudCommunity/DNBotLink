package fr.benjimania74.dnbotlink.addon.utils.config;

import net.dv8tion.jda.api.OnlineStatus;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import lombok.Getter;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigManager {
    private final Path folder = Paths.get((System.getProperty("os.name").split(" ")[0].equalsIgnoreCase("Windows") ? "" : "/") + "addons/DNBotLink");

    @Getter private BotConfig botConfig;
    @Getter private LinkConfig linkConfig;

    @Getter private HashMap<String, List<String>> autostart;

    public ConfigManager(){
        try {
            File f = folder.toFile();
            if (!f.exists()) {
                f.mkdir();
            }

            JSONParser parser = new JSONParser();
            JSONObject object;

            f = new File(folder + "/bot-config.json");
            if (!f.exists()) {
                f.createNewFile();
                botConfig = new BotConfig();
                saveBotConfig();
            }else {
                try {
                    object = (JSONObject) parser.parse(new FileReader(f));

                    String token = (String) object.getOrDefault("token", "");
                    String activity = (String) object.getOrDefault("activity", "DNBotLink for DreamNetwork");
                    String permRole = (String) object.getOrDefault("permRole", "everyone");
                    OnlineStatus status = OnlineStatus.fromKey((String) object.getOrDefault("status", "online"));
                    String prefix = (String) object.getOrDefault("prefix", "DN.");

                    botConfig = new BotConfig(token, activity, permRole, status, prefix);
                }catch (ParseException e){
                    botConfig = new BotConfig();
                    saveBotConfig();
                }
            }

            f = new File(folder + "/link-config.json");
            if(!f.exists()){
                f.createNewFile();
                linkConfig = new LinkConfig();
                saveLinkConfig();
            }else {
                try{
                    object = (JSONObject) parser.parse(new FileReader(f));

                    List<String> consoleLink = (List<String>) object.getOrDefault("console", new ArrayList<>());
                    HashMap<String, List<String>> chatLink = (HashMap<String, List<String>>) object.getOrDefault("chat", new HashMap<>());

                    linkConfig = new LinkConfig(consoleLink, chatLink);
                }catch (ParseException e){
                    linkConfig = new LinkConfig();
                    saveLinkConfig();
                }
            }

            f = new File(folder + "/autostart.json");
            autostart = new HashMap<>();
            if(!f.exists()){
                f.createNewFile();
                autostart.put("servers", new ArrayList<>());
                autostart.put("proxies", new ArrayList<>());
                saveAutostart();
            }else{
                try{
                    object = (JSONObject) parser.parse(new FileReader(f));

                    List<String> servers = (List<String>) object.getOrDefault("servers", new ArrayList<>());
                    List<String> proxies = (List<String>) object.getOrDefault("proxies", new ArrayList<>());

                    autostart.put("servers", servers);
                    autostart.put("proxies", proxies);
                }catch (ParseException e){
                    linkConfig = new LinkConfig();
                    saveLinkConfig();
                }
            }
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void saveBotConfig(){
        JSONObject toSave = new JSONObject();
        toSave.put("token", botConfig.getToken());
        toSave.put("activity", botConfig.getActivity());
        toSave.put("permRole", botConfig.getPermRole());
        toSave.put("status", botConfig.getStatus().getKey());
        toSave.put("prefix", botConfig.getPrefix());

        try {
            FileWriter fw = new FileWriter(folder + "/bot-config.json");
            fw.write(toSave.toJSONString());
            fw.flush();
            fw.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void saveLinkConfig(){
        JSONObject toSave = new JSONObject();
        toSave.put("console", linkConfig.getConsoleLinks());
        toSave.put("chat", linkConfig.getChatLinks());

        try {
            FileWriter fw = new FileWriter(folder + "/link-config.json");
            fw.write(toSave.toJSONString());
            fw.flush();
            fw.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

    public void saveAutostart(){
        JSONObject toSave = new JSONObject();
        toSave.put("servers", autostart.get("servers"));
        toSave.put("proxies", autostart.get("proxies"));

        try {
            FileWriter fw = new FileWriter(folder + "/autostart.json");
            fw.write(toSave.toJSONString());
            fw.flush();
            fw.close();
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }
}
