package fr.benjimania74.dnbotlink.addon.utils.service;

import be.alexandre01.dreamnetwork.api.service.IJVMExecutor;
import fr.benjimania74.dnbotlink.addon.AddonMain;

public class StartService {
    public static States start(String service){
        AddonMain main = AddonMain.getInstance();
        IJVMExecutor jvm = main.getCoreAPI().getContainer().tryToGetJVMExecutor(service);

        if(jvm == null){return States.NOT_FOUND;}
        if(!jvm.isConfig()){return States.NOT_CONFIG;}
        jvm.startServer();
        return States.STARTED;
    }

    public enum States {
        NOT_CONFIG,
        NOT_FOUND,
        STARTED
    }
}
