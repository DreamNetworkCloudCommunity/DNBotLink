package fr.benjimania74.dnbotlink.listeners;

import be.alexandre01.dreamnetwork.api.events.EventCatcher;
import be.alexandre01.dreamnetwork.api.events.Listener;
import be.alexandre01.dreamnetwork.api.events.list.screens.CoreScreenCreateEvent;
import fr.benjimania74.dnbotlink.utils.ScreenReader;

public class ServiceScreenStartListener implements Listener {
    @EventCatcher
    public void onServiceScreenStart(CoreScreenCreateEvent event){
        new ScreenReader(event.getScreen().getService().getProcess(), event.getScreen().getService().getJvmExecutor().getName());
    }
}