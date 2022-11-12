package fr.benjimania74.dnbotlink.utils;

import be.alexandre01.dreamnetwork.api.service.IContainer;
import be.alexandre01.dreamnetwork.api.service.IJVMExecutor;
import be.alexandre01.dreamnetwork.core.console.colors.Colors;
import fr.benjimania74.dnbotlink.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ServicesStopper {
    private final IContainer container = Main.coreAPI.getContainer();
    private String serviceName = "";

    public String stopD(String @NotNull [] serviceI){
        serviceName = serviceI[0];
        String notAService = Colors.RED + StatusMessages.NOT_EXISTING.getMessage().replace("SERVICE", serviceName);

        if(serviceI.length == 1){
            if(Services.isBoth(serviceName)){
                return Colors.RED + serviceName + " is a Server and a Proxy ! Type '" + serviceName + " server' for server and '" + serviceName + " proxy' for proxy";
            }
            IContainer.JVMType type = Services.getType(serviceName);
            if(type == null){return notAService;}
            if(Services.containMultiple(serviceName, type)){return Colors.RED + StatusMessages.DYNAMIC_SERVICE.getMessage().replace("SERVICE", serviceName).replace("COMMAND", "stop");}
            stopServer(container.getJVMExecutor(serviceName, Services.getType(serviceName)));
            return Colors.GREEN + StatusMessages.NOW_STOPPED.getMessage();
        }

        if(serviceI[1].equalsIgnoreCase("server")){
            if(!container.getJVMExecutorsServers().containsKey(serviceName)){return Colors.RED + StatusMessages.NOT_SERVER.getMessage().replace("SERVICE", serviceName);}
            if(Services.containMultiple(serviceName, IContainer.JVMType.SERVER)){return Colors.RED + StatusMessages.DYNAMIC_SERVICE.getMessage().replace("SERVICE", serviceName).replace("COMMAND", "stop");}
            stopServer(container.getJVMExecutor(serviceName, Services.getType(serviceName)));
            return Colors.GREEN + StatusMessages.NOW_STOPPED.getMessage().replace("SERVICE", serviceName);
        }
        if(serviceI[1].equalsIgnoreCase("proxy")){
            if(!container.getJVMExecutorsProxy().containsKey(serviceI[0])){return Colors.RED + StatusMessages.NOT_PROXY.getMessage().replace("SERVICE", serviceName);}
            if(Services.containMultiple(serviceName, IContainer.JVMType.PROXY)){return Colors.RED + StatusMessages.DYNAMIC_SERVICE.getMessage().replace("SERVICE", serviceName).replace("COMMAND", "stop");}
            stopServer(container.getJVMExecutor(serviceName, IContainer.JVMType.PROXY));
            return Colors.GREEN + StatusMessages.NOW_STOPPED.getMessage().replace("SERVICE", serviceName);
        }
        return notAService;
    }

    public EmbedBuilder stopB(String @NotNull [] serviceI){
        serviceName = serviceI[0];

        EmbedBuilder successEmbed = new EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle(StatusMessages.NOW_STOPPING.getMessage().replace("SERVICE", serviceName));
        EmbedBuilder notRunning = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle(StatusMessages.NOT_RUNNING.getMessage().replace("SERVICE", serviceName));
        EmbedBuilder doubleService = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle("Double Service")
                .setDescription(serviceName + " is a Server and a Proxy ! Type '" + serviceName + " server' for server and '" + serviceName + " proxy' for proxy");
        EmbedBuilder innexistantService = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle("Innexistant Service")
                .setDescription(serviceName + " is not a Service");
        EmbedBuilder incorrectST = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle("Incorrect Service's Type");
        EmbedBuilder dynamicS = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle("Dynamic Server")
                .setDescription("This service is DYNAMIC started multiple time, please use 'stop dynamic' command");

        if(serviceI.length == 1){
            IContainer.JVMType type = Services.getType(serviceName);
            if(Services.isBoth(serviceName)){return doubleService;}
            if(type == null){return innexistantService;}
            if(!Services.isLaunched(serviceName)){return notRunning;}
            if(Services.containMultiple(serviceName, type)){return dynamicS;}
            stopServer(container.getJVMExecutor(serviceName, Services.getType(serviceName)));
            return successEmbed;
        }

        if(serviceI[1].equalsIgnoreCase("server")){
            if(container.getJVMExecutorsServers().containsKey(serviceName)){
                if(!Services.isLaunched(serviceName, IContainer.JVMType.SERVER)){return notRunning;}
                if(Services.containMultiple(serviceName, IContainer.JVMType.SERVER)){return dynamicS;}
                stopServer(container.getJVMExecutor(serviceName, IContainer.JVMType.SERVER));
                return successEmbed;
            }
            return incorrectST.setDescription(serviceName + " is not a Server");
        }

        if(serviceI[1].equalsIgnoreCase("proxy")){
            if(container.getJVMExecutorsProxy().containsKey(serviceName)){
                if(!Services.isLaunched(serviceName, IContainer.JVMType.PROXY)){return notRunning;}
                if(Services.containMultiple(serviceName, IContainer.JVMType.PROXY)){return dynamicS;}
                stopServer(container.getJVMExecutor(serviceName, IContainer.JVMType.PROXY));
                return successEmbed;
            }
            return incorrectST.setDescription(serviceName + " is not a Proxy");
        }

        return incorrectST.setDescription(serviceI[1] + " is not a Service Type");
    }

    private void stopServer(@NotNull IJVMExecutor executor){executor.getService(0).stop();}
    public void stopAllServices(){ Main.coreAPI.getClientManager().getClients().values().forEach(iClient -> iClient.getJvmService().stop()); }

    public String stopDynamicD(String s) {
        if(Main.coreAPI.getClientManager().getClients().containsKey(s)){
            stopServer(Main.coreAPI.getClientManager().getClient(s).getJvmService().getJvmExecutor());
            return StatusMessages.NOW_STOPPED.getMessage().replace("SERVICE", s);
        }
        return Colors.RED + StatusMessages.NOT_RUNNING.getMessage().replace("SERVICE", s);
    }

    public EmbedBuilder stopDynamicB(String s){
        EmbedBuilder success = new EmbedBuilder()
                .setColor(Color.GREEN)
                .setTitle(StatusMessages.NOW_STOPPED.getMessage().replace("SERVICE", s));
        EmbedBuilder notRunning = new EmbedBuilder()
                .setColor(Color.RED)
                .setTitle(StatusMessages.NOT_RUNNING.getMessage().replace("SERVICE", s));
        if(Main.coreAPI.getClientManager().getClients().containsKey(s)){
            stopServer(Main.coreAPI.getClientManager().getClient(s).getJvmService().getJvmExecutor());
            return success;
        }
        return notRunning;
    }
}