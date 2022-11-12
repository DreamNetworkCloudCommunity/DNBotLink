package fr.benjimania74.dnbotlink.bot.utils;

import be.alexandre01.dreamnetwork.api.connection.core.communication.IClient;
import be.alexandre01.dreamnetwork.api.connection.request.RequestType;
import fr.benjimania74.dnbotlink.Main;

public class ExecuteServerCmd {
    public void execute(String cmd, String serviceName){
        IClient client = Main.coreAPI.getContainer().getJVMExecutorsServers().get(serviceName).getService(0).getClient();
        client.getRequestManager().sendRequest(RequestType.SPIGOT_EXECUTE_COMMAND, cmd);
    }
}
