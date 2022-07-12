package fr.benjimania74.dnapitest.cmd;

import be.alexandre01.dreamnetwork.api.commands.Command;
import be.alexandre01.dreamnetwork.api.connection.core.communication.IClient;
import fr.benjimania74.dnapitest.Main;

import java.util.Collection;

public class ClientsCmd extends Command {
    public ClientsCmd(String name) {
        super(name);

        addSubCommand("see", args -> {
            Collection<IClient> clients = Main.clientAPI.getClientManager().getClients().values();
            if(clients.isEmpty()){
                System.out.println("There's no Servers and Proxy Running on the Network");
                return true;
            }
            for(IClient client : Main.clientAPI.getClientManager().getClients().values()){
                System.out.println(client.getJvmType() + " - " + client.getJvmService().getJvmExecutor().getName());
            }
            return true;
        });

        getHelpBuilder().setTitleUsage("Clients Command Help");
        getHelpBuilder().setCmdUsage("See All Servers and Proxys actually Running on the Network", "see");
    }
}
