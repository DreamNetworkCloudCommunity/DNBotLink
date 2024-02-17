package fr.benjimania74.dnbotlink.addon.dreamnetwork.utils.service;

import be.alexandre01.dreamnetwork.api.service.IService;
import fr.benjimania74.dnbotlink.addon.dreamnetwork.AddonMain;

import java.util.Optional;

public class StopService {
    public static States stop(String serviceName){
        IService service = AddonMain.getInstance().getCoreAPI().getContainer().findService(serviceName).orElse(null);
        if(service == null){return States.NOT_STARTED;}
        service.stop();
        return States.STOPPED;
    }

    public enum States {
        NOT_STARTED,
        STOPPED
    }
}
