package fr.benjimania74.dnbotlink.registers;

import be.alexandre01.dreamnetwork.api.DNCoreAPI;
import be.alexandre01.dreamnetwork.core.console.Console;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.dnbotlink.listeners.ServiceScreenStartListener;
import fr.benjimania74.dnbotlink.listeners.ServiceStartListener;
import fr.benjimania74.dnbotlink.listeners.ServiceStopListener;

public class ListenerRegister {
    public static boolean register(DNCoreAPI coreAPI){
        try{
            coreAPI.getEventsFactory().registerListener(new ServiceStartListener());
            coreAPI.getEventsFactory().registerListener(new ServiceStopListener());
            coreAPI.getEventsFactory().registerListener(new ServiceScreenStartListener());
            return true;
        }catch (Exception e){
            Console.print(Colors.RED + "[ERROR] Listeners can't be registered");
            return false;
        }

    }
}
