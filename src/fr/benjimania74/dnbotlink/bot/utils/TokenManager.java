package fr.benjimania74.dnbotlink.bot.utils;

import be.alexandre01.dreamnetwork.core.console.Console;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.dnbotlink.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class TokenManager {
    protected static String jar = TokenManager.class.getProtectionDomain().getCodeSource().getLocation().getPath().substring(1);
    protected static File tokenFile = new File((System.getProperty("os.name").split(" ")[0].equalsIgnoreCase("Windows") ? "" : "/") + jar.replace("/" + jar.substring(jar.lastIndexOf("/") + 1), "") + "/" + Main.addonName + "/token");

    public TokenManager(){
        if(!tokenFile.exists()){
            try{
                tokenFile.createNewFile();
                Console.print(Colors.GREEN + "Token file created");
            }catch (IOException ioe){
                ioe.printStackTrace();
            }
        }
    }

    public static String getToken(){
        try {
            Scanner scan = new Scanner(tokenFile);
            if(scan.hasNextLine()){
                return scan.nextLine();
            }
            return null;
        }catch(FileNotFoundException fnfe){
            fnfe.printStackTrace();
            return null;
        }
    }
}
