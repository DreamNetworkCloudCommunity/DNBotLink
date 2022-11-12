package fr.benjimania74.dnbotlink.listeners;

import be.alexandre01.dreamnetwork.api.events.EventCatcher;
import be.alexandre01.dreamnetwork.api.events.Listener;
import be.alexandre01.dreamnetwork.api.events.list.services.CoreServiceStopEvent;
import be.alexandre01.dreamnetwork.api.service.IService;
import be.alexandre01.dreamnetwork.core.console.Console;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.dnbotlink.Main;

public class ServiceStopListener implements Listener {
    @EventCatcher
    public void onServiceStop(CoreServiceStopEvent event){
        IService service = event.getService();
        try{
            Main.coreAPI.getClientManager().getClients().forEach((name, client) -> {
                if(client.getJvmType() == service.getClient().getJvmType() && client.getJvmService().getJvmExecutor().getName().equals(service.getJvmExecutor().getName())){
                    Main.coreAPI.getClientManager().getClients().remove(name, client);
                }
            });
        }catch (Exception e){
            Console.print(Colors.RED_BACKGROUND + "Unable to unregister the client");
        }
    }
}
