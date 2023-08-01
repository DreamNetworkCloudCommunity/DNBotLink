package fr.benjimania74.dnbotlink.addon.dreamnetwork.listeners;

import be.alexandre01.dreamnetwork.api.events.EventsFactory;

public class EventsRegister {
    public static void register(EventsFactory eventsFactory) {
        eventsFactory.registerListener(new ServiceStartListener());
        eventsFactory.registerListener(new ServiceStopListener());
    }
}
