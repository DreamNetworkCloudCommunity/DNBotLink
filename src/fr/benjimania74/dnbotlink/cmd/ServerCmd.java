package fr.benjimania74.dnbotlink.cmd;

import be.alexandre01.dreamnetwork.api.commands.Command;
import be.alexandre01.dreamnetwork.core.console.Console;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.dnbotlink.utils.Services;
import fr.benjimania74.dnbotlink.utils.ServicesStarter;
import fr.benjimania74.dnbotlink.utils.ServicesStopper;

public class ServerCmd extends Command {
    public ServerCmd(String name) {
        super(name);

        addSubCommand("start", args -> {
            if(args.length == 1){
                Console.print(Colors.RED + "Format: server start <service> [<proxy | server>]");return true;}

            String[] serviceI;
            if(args.length == 2){ serviceI = new String[]{args[1]}; }else{ serviceI = new String[]{args[1], args[2]}; }
            Console.print(new ServicesStarter().startD(serviceI));
            return true;
        });

        addSubCommand("stop", args -> {
            if(args.length == 1){Console.print(Colors.RED + "Format: server stop <service> [<proxy | server>] \n server stop dynamic <service-ID>");return true;}

            if(!Services.isServicesLaunched()){Console.print(Colors.RED + "There's no Services Running");return true;}

            String[] serviceI;
            if(args.length == 2){ serviceI = new String[]{args[1]}; }else{ serviceI = new String[]{args[1], args[2]}; }
            if (args[1].equals("allservices")){
                new ServicesStopper().stopAllServices();
                Console.print(Colors.GREEN + "All Services have been Stopped");
                return true;
            }
            if(args[1].equals("dynamic")){
                if(serviceI.length != 2){Console.print(Colors.RED + "Format: server stop dynamic <service-ID>");return true;}
                Console.print(new ServicesStopper().stopDynamicD(serviceI[1]));
                return true;
            }
            if(!Services.isLaunched(args[1])){Console.print(Colors.RED + "This Service is not Running");return true;}
            Console.print(new ServicesStopper().stopD(serviceI));
            return true;
        });

        getHelpBuilder().setTitleUsage("Server Command")
                .setCmdUsage("Launch a server","start", "<service> [<proxy | server>]")
                .setCmdUsage("Stop a server","stop", "<service> [<proxy | server>]")
                .setCmdUsage("Stop a dynamic server", "stop", "dynamic <service>");
    }
}
