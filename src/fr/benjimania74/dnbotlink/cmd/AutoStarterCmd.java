package fr.benjimania74.dnbotlink.cmd;

import be.alexandre01.dreamnetwork.api.commands.Command;
import be.alexandre01.dreamnetwork.core.console.Console;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.configmanager.Config;
import fr.benjimania74.configmanager.EncodedConfigManager;
import fr.benjimania74.dnbotlink.Main;
import fr.benjimania74.dnbotlink.utils.Services;
import org.json.simple.JSONArray;
import java.util.List;

public class AutoStarterCmd extends Command {
    public AutoStarterCmd(String name) {
        super(name);
        EncodedConfigManager autoStartConfig = Config.getInstance().createEncodedConfig("autostartservice");

        addSubCommand("add", args -> {
            List<Object> autoStartedService;
            try {
                autoStartedService = autoStartConfig.getList("services");
            }catch (Exception e){
                autoStartConfig.set("services", new JSONArray());
                autoStartConfig.save();
                autoStartedService = autoStartConfig.getList("services");
            }

            if(args.length < 2){Console.print(Colors.RED + name + " add <service> [<server|proxy>]");return true;}

            if(Services.getType(args[1]) == null){Console.print(Colors.RED + args[1] + " is not a Service");return true;}

            if(args.length == 2){
                if(Services.isBoth(args[1])){Console.print(Colors.RED + args[1] + " is a Server and a Proxy");return true;}
                autoStartedService.add(args[1]);
                autoStartConfig.set("services", autoStartedService);
                autoStartConfig.save();
                Console.print(Colors.GREEN + args[1] + " will now auto start at the start of the Network");
                return true;
            }

            if(args[2].equalsIgnoreCase("server")){
                if(!Main.coreAPI.getContainer().getJVMExecutorsServers().containsKey(args[1])) {Console.print(Colors.RED + args[1] + " is not a Server");return true;}
                autoStartedService.add(args[1] + "<&>server");
                autoStartConfig.set("services", autoStartedService);
                autoStartConfig.save();
                Console.print(Colors.GREEN + args[1] + "(server) will now auto start at the start of th Network");
                return true;
            }
            if(args[2].equalsIgnoreCase("proxy")){
                if(!Main.coreAPI.getContainer().getJVMExecutorsProxy().containsKey(args[1])) {Console.print(Colors.RED + args[1] + " is not a Proxy");return true;}
                autoStartedService.add(args[1] + "<&>proxy");
                autoStartConfig.set("services", autoStartedService);
                autoStartConfig.save();
                Console.print(Colors.GREEN + args[1] + "(proxy) will now auto start at the start of th Network");
                return true;
            }

            Console.print(Colors.RED + args[1] + " is not a Service");
            return true;
        });

        addSubCommand("remove", args -> {
            List<Object> autoStartedService;
            try {
                autoStartedService = autoStartConfig.getList("services");
            }catch (Exception e){
                autoStartConfig.set("services", new JSONArray());
                autoStartConfig.save();
                autoStartedService = autoStartConfig.getList("services");
            }

            if(args.length < 2){Console.print(Colors.RED + name + " remove <service> [<server|proxy>]");return true;}

            if(Services.isBoth(args[1])){Console.print(Colors.RED + args[1] + " is a Server and a Proxy");return true;}

            if(args.length == 3){
                if(args[2].equalsIgnoreCase("server")){
                    if(autoStartedService.contains(args[1] + "<&>server")){
                        autoStartedService.remove(args[1] + "<&>server");
                        autoStartConfig.set("services", autoStartedService);
                        autoStartConfig.save();
                        Console.print(Colors.GREEN + args[1] + " has been removed of the auto-starter");
                        return true;
                    }
                    Console.print(Colors.RED + args[1] + " (server) is not auto-started service");
                    return true;
                }
                if(args[2].equalsIgnoreCase("proxy")){
                    if(autoStartedService.contains(args[1] + "<&>proxy")){
                        autoStartedService.remove(args[1] + "<&>proxy");
                        autoStartConfig.set("services", autoStartedService);
                        autoStartConfig.save();
                        Console.print(Colors.GREEN + args[1] + " (proxy) has been removed of the auto-starter");
                        return true;
                    }
                    Console.print(Colors.RED + args[1] + " is not auto-started service");
                    return true;
                }
                Console.print(Colors.RED + name + " remove <service> [<server|proxy>]");
                return true;
            }

            if(autoStartedService.contains(args[1])){
                autoStartedService.remove(args[1]);
                autoStartConfig.set("services", autoStartedService);
                autoStartConfig.save();
                Console.print(Colors.GREEN + args[1] + " has been removed of the auto-starter");
                return true;
            }

            Console.print(Colors.RED + args[1] + " is not auto-started service");
            return true;
        });

        getHelpBuilder().setTitleUsage("Auto-Starter command usage")
                .setCmdUsage("Add a service to the auto-start", "add", "<service> [<server|proxy>]")
                .setCmdUsage("Remove a service to the auto-start", "remove", "<service> [<server|proxy>]");
    }
}
