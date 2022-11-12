package fr.benjimania74.dnbotlink.utils;

import fr.benjimania74.dnbotlink.bot.BotConfig;
import fr.benjimania74.dnbotlink.bot.utils.SendMessage;

import java.io.*;

public class ScreenReader {

    public ScreenReader(Process process, String serviceName){
        InputStream inputStream = process.getInputStream();

        new Thread(() -> {
            while (process.isAlive()){
                try {
                    ByteArrayOutputStream result = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    for (int length ; (length = inputStream.read(buffer)) != -1 ;) {
                        result.write(buffer, 0, length);
                        if(BotConfig.getInstance().getLinks().containsKey(serviceName)){
                            SendMessage.send("[" + serviceName.toUpperCase() + " SERVICE] ```" + result.toString("UTF-8") + "```", BotConfig.getInstance().getLinks().get(serviceName));
                        }
                        result.reset();
                    }
                }catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }).start();
    }
}