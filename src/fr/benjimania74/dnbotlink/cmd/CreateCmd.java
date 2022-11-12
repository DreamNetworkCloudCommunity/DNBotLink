package fr.benjimania74.dnbotlink.cmd;

import be.alexandre01.dreamnetwork.api.commands.Command;
import be.alexandre01.dreamnetwork.core.console.Console;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.dnbotlink.utils.CreateService;
import fr.benjimania74.dnbotlink.utils.Services;
import fr.benjimania74.dnbotlink.utils.StatusMessages;

public class CreateCmd extends Command {
    public CreateCmd(String name) {
        super(name);

        addSubCommand("server", args -> {
            if(args.length < 4){Console.print(Colors.RED + "Format: create server <server-name> <paper | spigot> <version> [<dynamic | static [default]>]");return true;}

            String serviceName = args[1];
            String serverName = args[2];
            String serverVersion = args[3];

            if(Services.getType(serviceName) != null){Console.print(Colors.RED + StatusMessages.EXIST.getMessage().replace("SERVICE", serviceName));return true;}
            if(!serverName.equalsIgnoreCase("paper") && !serverName.equalsIgnoreCase("spigot")){Console.print(Colors.RED + "The Server must be on Spigot or Paper");return true;}

            if(args.length == 5){
                String serverType = args[4];
                if(!serverType.equalsIgnoreCase("dynamic") && !serverType.equalsIgnoreCase("static")){
                    Console.print(Colors.RED + StatusMessages.INCORRECT_TYPE.getMessage());
                    return true;
                }
                Console.print(CreateService.createServer(serverName, serverName, serverVersion, serverType));
            }

            Console.print(CreateService.createServer(serviceName, serverName, serverVersion));
            return true;
        });

        getHelpBuilder().setTitleUsage("Create Command")
                .setCmdUsage("Create a server","server", "<server-name> <paper | spigot> <version> [<dynamic | static [default]>]")
                .setCmdUsage("Create a proxy","proxy", "<proxy-name> [<dynamic | static [default]>]");
    }
}
