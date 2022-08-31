package fr.benjimania74.dnbotlink.utils;

import be.alexandre01.dreamnetwork.client.console.Console;
import be.alexandre01.dreamnetwork.client.console.colors.Colors;
import fr.benjimania74.dnbotlink.Main;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CreateService {
    public static String[] files = {"network.yml", "eula.txt", "server.jar", "plugins/DreamNetwork-Plugin.jar", "plugins/dnbotlink.jar"};

    public static String createServer(String name, String server, String version){return createServer(name, server, version, "static");}

    public static String createServer(String name, String server, String version, String type){
        Path p = Paths.get("template/server/" + name);

        FilesManager.getInstance().createDirectory(p.toString());
        FilesManager.getInstance().createDirectory(p + "/plugins");

        for(String s : files){FilesManager.getInstance().createFile(p + "/" + s);}

        try {
            FilesManager.getInstance().write(
                    p + "/network.yml",
                    "# " + name + "'s configuration of the startup -|- DreamNetwork™\n" +
                    "type: " + type + "\n" +
                    "xms: 1G\n" +
                    "xmx: 2G\n" +
                    "exec: server.jar\n" +
                    "proxy: false"
            );

            InstallFile.installDNPlugin(p + "/" + files[3]);

            FilesManager.getInstance().write(p + "/eula.txt", "eula=true");
            InstallFile.copyLocalFile(
                    Main.class.getProtectionDomain()
                            .getCodeSource()
                            .getLocation()
                            .toURI()
                            .getPath(),
                    p + "/" + files[4]
            );

            if(!InstallFile.installPaper(p + "/server.jar", version)){
                FilesManager.getInstance().deleteDirectory(p);
                return Colors.RED + "INNEXISTANT VERSION";
            }
            return Colors.GREEN + "The Server has been created";
        }catch (Exception e){
            e.printStackTrace();
            return Colors.RED + "ERROR";
        }
    }
}
