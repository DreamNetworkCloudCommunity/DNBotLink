package fr.benjimania74.dnbotlink.utils;

import be.alexandre01.dreamnetwork.core.console.Console;
import fr.benjimania74.dnbotlink.bot.BotConfig;
import fr.benjimania74.dnbotlink.bot.utils.SendMessage;

import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScreenReader extends Thread {
    private final Process process;
    private final String serviceName;
    private final StringBuilder datas = new StringBuilder();
    private boolean isRunning = true;
    private final InputStream reader;

    public ScreenReader(Process process, String serviceName){
        this.process = process;
        this.reader = process.getInputStream();
        this.serviceName = serviceName;
    }

    @Override
    public void run() {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                if(!process.isAlive()){
                    isRunning = false;
                }

                if(!isRunning){
                    try {
                        reader.mark(0);
                        reader.reset();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    service.shutdown();
                    return;
                }

                String data = new String(datas.toString());


                if(datas.length() != 0){
                    while(data.contains("\n\n") || data.contains("\n\r")){
                        data = data.replaceAll("\n\n","").replaceAll("\n\r","");
                    }

                    if(BotConfig.getInstance().getLinks().containsKey(serviceName)){
                        new SendMessage().send("[" + serviceName.toUpperCase() + " SERVICE] ```" + data.replace("\n>", "") + "```", BotConfig.getInstance().getLinks().get(serviceName));
                    }
                    datas.setLength(0);
                }

            }
        },10,10, TimeUnit.MILLISECONDS);
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            ReadableByteChannel channel = Channels.newChannel(reader);
            String data = null;

            int i = 0;
            while (channel.read(buffer) != -1 && this.isRunning ) {
                ((Buffer)buffer).flip();
                if(buffer.remaining() != 0)
                    datas.append(StandardCharsets.UTF_8.decode(buffer));
                ((Buffer)buffer).clear();
            }

            System.out.println(datas.toString());
        } catch (Exception exception) {
            Console.print(exception.getCause().getMessage());
            Console.setActualConsole("m:default");
        }
    }

    public void stopR(){this.isRunning = false;}
}
