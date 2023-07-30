package fr.benjimania74.dnbotlink.addon.listeners;

import be.alexandre01.dreamnetwork.api.events.EventCatcher;
import be.alexandre01.dreamnetwork.api.events.Listener;
import be.alexandre01.dreamnetwork.api.events.list.screens.CoreScreenCreateEvent;
import be.alexandre01.dreamnetwork.api.service.IService;
import fr.benjimania74.dnbotlink.addon.AddonMain;
import fr.benjimania74.dnbotlink.addon.bot.utils.ScreenReader;

public class ServiceStartListener implements Listener {
    @EventCatcher
    public void onStart(CoreScreenCreateEvent event){
        IService service = event.getScreen().getService();
        AddonMain main = AddonMain.getInstance();

        main.getServiceScreenReaders().put(service, new ScreenReader(service));
    }
}
