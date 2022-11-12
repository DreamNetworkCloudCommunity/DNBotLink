package fr.benjimania74.dnbotlink.utils;

import be.alexandre01.dreamnetwork.api.connection.core.communication.IClient;
import be.alexandre01.dreamnetwork.api.service.IContainer;
import fr.benjimania74.dnbotlink.Main;

import java.util.HashMap;

public class Services {
    private static final IContainer container = Main.coreAPI.getContainer();
    private static final HashMap<String, IClient> clientManager = Main.coreAPI.getClientManager().getClients();

    public static boolean isBoth(String serviceName){
        boolean isServ = false, isProxy = false;

        if(container.getJVMExecutorsServers().containsKey(serviceName)){isServ = true;}
        if(container.getJVMExecutorsProxy().containsKey(serviceName)){isProxy = true;}

        return isProxy && isServ;
    }

    public static IContainer.JVMType getType(String serviceName){
        if(container.getJVMExecutorsServers().containsKey(serviceName)){return IContainer.JVMType.SERVER;}
        if(container.getJVMExecutorsProxy().containsKey(serviceName)){return IContainer.JVMType.PROXY;}
        return null;
    }

    public static boolean isLaunched(String serviceName){
        if(isClientManagerEmpty()){return false;}
        for(IClient client : clientManager.values()){if(client.getJvmService().getJvmExecutor().getName().equals(serviceName)){return true;}}
        return false;
    }

    public static boolean isLaunched(Object serviceName, IContainer.JVMType serviceType){
        if(isClientManagerEmpty()){return false;}
        for(IClient client : clientManager.values()){if(client.getJvmService().getJvmExecutor().getName().equals(serviceName) && client.getJvmType().equals(serviceType)){return true;}}
        return false;
    }

    public static boolean isProxyLaunched(){
        if(isClientManagerEmpty()){return false;}
        for(IClient client : clientManager.values()){ if(client.getJvmType().equals(IContainer.JVMType.PROXY)){return true;}}
        return false;
    }

    public static boolean isServicesLaunched(){return !Main.coreAPI.getClientManager().getClients().isEmpty();}
    public static boolean isClientManagerEmpty(){return Main.coreAPI.getClientManager().getClients().isEmpty();}

    public static boolean isDynamic(String serviceName, IContainer.JVMType type) {return Main.coreAPI.getContainer().getJVMExecutor(serviceName, type).getType().getPath().equals("/tmp/");}

    public static boolean containMultiple(String serviceName, IContainer.JVMType type){
        int i = 0;
        for(IClient client : Main.coreAPI.getClientManager().getClients().values()){
            if(client.getJvmService().getJvmExecutor().getName().equals(serviceName) && client.getJvmType().equals(type)){i++;}
        }
        return i > 1;
    }
}
