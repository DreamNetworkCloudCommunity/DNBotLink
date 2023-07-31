package fr.benjimania74.dnbotlink.addon.listeners;

import be.alexandre01.dreamnetwork.api.events.EventCatcher;
import be.alexandre01.dreamnetwork.api.events.Listener;
import be.alexandre01.dreamnetwork.api.events.list.services.CoreServiceStopEvent;
import be.alexandre01.dreamnetwork.api.service.IService;
import fr.benjimania74.dnbotlink.addon.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.utils.ScreenReader;

public class ServiceStopListener implements Listener {
    @EventCatcher
    public void onStop(CoreServiceStopEvent event){
        IService service = event.getService();
        AddonMain main = AddonMain.getInstance();
        ScreenReader sc = main.getServiceScreenReaders().get(service);
        sc.stop();
        main.getServiceScreenReaders().remove(service);
    }
}
