package fr.benjimania74.dnapitest.registers;

import be.alexandre01.dreamnetwork.api.DNClientAPI;
import be.alexandre01.dreamnetwork.client.console.Console;
import be.alexandre01.dreamnetwork.client.console.colors.Colors;
import fr.benjimania74.dnapitest.listeners.ServiceScreenStartListener;
import fr.benjimania74.dnapitest.listeners.ServiceStartListener;
import fr.benjimania74.dnapitest.listeners.ServiceStopListener;

public class ListenerRegister {
    public static boolean register(DNClientAPI clientAPI){
        try{
            clientAPI.getEventsFactory().registerListener(new ServiceStartListener());
            clientAPI.getEventsFactory().registerListener(new ServiceStopListener());
            clientAPI.getEventsFactory().registerListener(new ServiceScreenStartListener());
            return true;
        }catch (Exception e){
            Console.print(Colors.RED + "[ERROR] Listeners can't be registered");
            return false;
        }

    }
}
