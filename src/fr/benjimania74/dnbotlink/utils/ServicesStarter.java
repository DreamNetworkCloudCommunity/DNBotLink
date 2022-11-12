package fr.benjimania74.dnbotlink.utils;

import be.alexandre01.dreamnetwork.api.service.IContainer;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.dnbotlink.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.Objects;

public class ServicesStarter {
    private final IContainer container = Main.coreAPI.getContainer();
    private String serviceName = "";

    public String startD(String @NotNull [] serviceI){
        serviceName = serviceI[0];
        String serviceStarting = Colors.GREEN_BACKGROUND + StatusMessages.NOW_STARTING.getMessage().replace("SERVICE", serviceName);

        if(serviceI.length == 1){
            if(Services.isBoth(serviceName)){return Colors.RED + StatusMessages.DOUBLE_SERVICE.getMessage().replace("SERVICE", serviceName) + " Type '" + serviceName + " server' for server and '" + serviceName + " proxy' for proxy";}
            if(Services.getType(serviceName) == null){return Colors.RED + StatusMessages.NOT_EXISTING.getMessage().replace("SERVICE", serviceName);}
            startService(serviceName);
            return serviceStarting;
        }
        if(serviceI[1].equalsIgnoreCase("server")){
            if(container.getJVMExecutorsServers().containsKey(serviceName)){startService(serviceName, IContainer.JVMType.SERVER);return serviceStarting;}
            return Colors.RED + StatusMessages.NOT_SERVER.getMessage().replace("SERVICE", serviceName);
        }
        if(serviceI[1].equalsIgnoreCase("proxy")){
            if(container.getJVMExecutorsProxy().containsKey(serviceName)){startService(serviceName, IContainer.JVMType.PROXY);return serviceStarting;}
            return Colors.RED + StatusMessages.NOT_PROXY.getMessage().replace("SERVICE", serviceName);
        }
        return Colors.RED + StatusMessages.NOT_EXISTING.getMessage().replace("SERVICE", serviceName);
    }

    public EmbedBuilder startB(String @NotNull [] serviceI){
        serviceName = serviceI[0];
        EmbedBuilder successEmbed = new EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle(StatusMessages.NOW_STARTING.getMessage().replace("SERVICE", serviceName));
        EmbedBuilder alreadyRunning = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle(StatusMessages.ALREADY_RUNNING.getMessage().replace("SERVICE", serviceName));
        EmbedBuilder proxyNotLaunched = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle(StatusMessages.NO_PROXY_STARTED.getMessage())
                .setDescription("To Launch a Server, a Proxy must be Running");
        EmbedBuilder innexistantService = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle(StatusMessages.NOT_EXISTING.getMessage().replace("SERVICE", serviceName));
        EmbedBuilder doubleService = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle(StatusMessages.DOUBLE_SERVICE.getMessage().replace("SERVICE", serviceName))
                .setDescription("Type '" + serviceName + " server' for the server and '" + serviceName + " proxy' for the proxy");
        EmbedBuilder incorrectST = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle(StatusMessages.INCORRECT_TYPE.getMessage());

        if(serviceI.length == 1){
            if(Services.isBoth(serviceName)){return doubleService;}
            IContainer.JVMType type = Services.getType(serviceName);
            if(type == null){return innexistantService;}
            if(!Services.isDynamic(serviceName, type)){
                if(Services.isLaunched(serviceName)){ return alreadyRunning; }
                if(!Services.isProxyLaunched() && type.equals(IContainer.JVMType.SERVER)){return proxyNotLaunched;}
            }
            startService(serviceName, type);
            return successEmbed;
        }
        if(serviceI[1].equalsIgnoreCase("server")){
            if(!Services.isDynamic(serviceName, IContainer.JVMType.SERVER)){
                if(Services.isLaunched(serviceName, IContainer.JVMType.SERVER)){return alreadyRunning; }
                if(!Services.isProxyLaunched()){return proxyNotLaunched;}
            }
            if(container.getJVMExecutorsServers().containsKey(serviceName)){startService(serviceName, IContainer.JVMType.SERVER);return successEmbed;}
            return incorrectST.setDescription(StatusMessages.NOT_SERVER.getMessage().replace("SERVICE", serviceName));
        }
        if(serviceI[1].equalsIgnoreCase("proxy")){
            if(!Services.isDynamic(serviceName, IContainer.JVMType.PROXY)){
                if(Services.isLaunched(serviceName, IContainer.JVMType.PROXY)){return alreadyRunning; }
                if(container.getJVMExecutorsProxy().containsKey(serviceName)){startService(serviceName, IContainer.JVMType.PROXY);return successEmbed;}
            }
            return incorrectST.setDescription(StatusMessages.NOT_PROXY.getMessage().replace("SERVICE", serviceName));
        }
        return innexistantService;
    }

    public void startService(String serviceName){startService(serviceName, Objects.requireNonNull(Services.getType(serviceName)));}
    public void startService(String serviceName, IContainer.JVMType serviceType){container.getJVMExecutor(serviceName, serviceType).startServer();}
}