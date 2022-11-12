package fr.benjimania74.dnbotlink.utils;

import be.alexandre01.dreamnetwork.api.service.IJVMExecutor;
import be.alexandre01.dreamnetwork.core.console.Console;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.configmanager.Config;
import fr.benjimania74.configmanager.EncodedConfigManager;
import fr.benjimania74.dnbotlink.Main;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class ServiceAutoStarter {
    public ServiceAutoStarter(){
        List<Object> services;
        EncodedConfigManager eConfigManager;
        try {
            eConfigManager = Config.getInstance().getEncodedConfig("autostartservice");
            services = eConfigManager.getList("services");
        }catch (Exception e){
            eConfigManager = Config.getInstance().createEncodedConfig("services");
            eConfigManager.set("services", new JSONArray());
            eConfigManager.save();
            Console.print(Colors.YELLOW + "No Service to Auto-Start");
            return;
        }

        List<String> executed = new ArrayList<>();
        if(services == null || services.isEmpty()){Console.print(Colors.YELLOW + "No Service to Auto-Start");return;}
        Console.print(Colors.YELLOW + "Auto-Starting of Proxys");

        for(IJVMExecutor executor : Main.coreAPI.getContainer().getJVMExecutorsProxy().values()){
            if(Services.isBoth(executor.getName())){
                if(services.contains(executor.getName() + "<&>proxy")){
                    executor.startServer();
                    Console.print(Colors.GREEN + executor.getName() + " auto-starting");
                    executed.add(executor.getName()+"<&>proxy");
                }
            }else if(services.contains(executor.getName())){
                executor.startServer();
                Console.print(Colors.GREEN + executor.getName() + " auto-starting");
                executed.add(executor.getName());
            }
        }

        executed.forEach(services::remove);
        if(services.isEmpty()){return;}

        int i = 0;
        while (!Services.isProxyLaunched()){System.out.println(i++);}

        Console.print(Colors.YELLOW + "Auto-Starting of Servers");
        for(IJVMExecutor executor : Main.coreAPI.getContainer().getJVMExecutorsServers().values()){
            if(Services.isBoth(executor.getName())){
                if(services.contains(executor.getName() + "<&>server")){
                    executor.startServer();
                    Console.print(Colors.GREEN + executor.getName() + " auto-starting");
                }
            }else if(services.contains(executor.getName())){
                executor.startServer();
                Console.print(Colors.GREEN + executor.getName() + " auto-starting");
            }
        }
        Console.print(Colors.GREEN + "Auto-Starting finished");
    }
}
