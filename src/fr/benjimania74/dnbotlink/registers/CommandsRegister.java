package fr.benjimania74.dnbotlink.registers;

import be.alexandre01.dreamnetwork.api.DNCoreAPI;
import be.alexandre01.dreamnetwork.core.console.Console;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.dnbotlink.cmd.AutoStarterCmd;
import fr.benjimania74.dnbotlink.cmd.ClientsCmd;
import fr.benjimania74.dnbotlink.cmd.CreateCmd;
import fr.benjimania74.dnbotlink.cmd.ServerCmd;

public class CommandsRegister {
    public static boolean register(DNCoreAPI coreAPI){
        try {
            coreAPI.getCommandReader().getCommands().addCommands(new ServerCmd("server"));
            coreAPI.getCommandReader().getCommands().addCommands(new ClientsCmd("clients"));
            coreAPI.getCommandReader().getCommands().addCommands(new AutoStarterCmd("autostart"));
            coreAPI.getCommandReader().getCommands().addCommands(new CreateCmd("create"));
            return true;
        }catch (Exception e){
            Console.print(Colors.RED + "[ERROR] Commands can't be registered");
            return false;
        }
    }
}
