package fr.benjimania74.dnbotlink.utils;

import be.alexandre01.dreamnetwork.core.console.Console;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.dnbotlink.Main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FilesManager {
    private static FilesManager instance;
    public static FilesManager getInstance(){return instance;}

    private final String[] configFiles = {"token", "config", "autostartservice"};
    protected String jar = FilesManager.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
    public Path addonPath = Paths.get((System.getProperty("os.name").split(" ")[0].equalsIgnoreCase("Windows") ? "" : "/") + jar.replace("/" + jar.substring(jar.lastIndexOf("/") + 1), "") + "/" + Main.addonName);

    public FilesManager(){
        if(!Files.exists(addonPath)){createDirectory(addonPath.toString());}
        for(String f : configFiles){
            createFile(addonPath + "/" + f);
            Console.print(Colors.YELLOW + "[" + Colors.GREEN + Main.addonName + Colors.YELLOW + "] " + Colors.CYAN + f + " created");
        }

        Console.print(Colors.YELLOW + "[" + Colors.GREEN + Main.addonName + " INFO" + Colors.YELLOW + "] " + Colors.GREEN + "The Bot's Token can be changed in '" + addonPath + "\\token' file");

        Console.print(Colors.YELLOW + "[" + Colors.GREEN + Main.addonName + Colors.YELLOW + "] " + Colors.CYAN + "Addon's Files are loaded");
        instance = this;
    }

    public String read(String fileName){
        String returnedString = "";
        File file = new File(addonPath + "/" + fileName);
        if(!file.exists()){return returnedString;}
        try{
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()){
                return scan.nextLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return returnedString;
    }

    public void write(String fileName, String content) throws IOException {
        FileWriter fw  = new FileWriter(fileName);
        fw.write(content);
        fw.flush();
        fw.close();
    }

    public void createFile(String file){
        try{
            File f = new File(file);
            f.createNewFile();
        }catch (Exception e){e.printStackTrace();}
    }

    public void createDirectory(String path){
        try{
            Files.createDirectory(Paths.get(path));
        }catch (Exception e){e.printStackTrace();}
    }

    public void deleteDirectory(Path path){
        try{
            Files.delete(path);
        }catch (Exception e){e.printStackTrace();}
    }
}
