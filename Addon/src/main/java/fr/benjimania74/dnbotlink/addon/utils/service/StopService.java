package fr.benjimania74.dnbotlink.addon.utils.service;

import be.alexandre01.dreamnetwork.api.service.IService;
import fr.benjimania74.dnbotlink.addon.AddonMain;

public class StopService {
    public static States stop(String service){
        IService s = AddonMain.getInstance().getCoreAPI().getContainer().tryToGetService(service);
        if(s == null){return States.NOT_STARTED;}
        s.stop();
        return States.STOPPED;
    }

    public enum States {
        NOT_STARTED,
        STOPPED
    }
}
