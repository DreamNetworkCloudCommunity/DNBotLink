package fr.benjimania74.dnbotlink.addon.dreamnetwork.commands;

import be.alexandre01.dreamnetwork.api.commands.Command;
import be.alexandre01.dreamnetwork.api.console.colors.Colors;
import fr.benjimania74.dnbotlink.addon.dreamnetwork.commands.sub.configure.*;

public class Config extends Command {
    public Config(String name) {
        super(name);

        setCompletorValue(name, getBaseColor() + name);
        addSubCommand("token", new Token(this));
        addSubCommand("activity", new Activity(this));
        addSubCommand("permrole", new PermRole(this));
        addSubCommand("status", new Status(this));

        getHelpBuilder().setTitleUsage("Config Command")
                .setCmdUsage("Define the Discord Bot's Token", "token", "[Discord Bot's Token]")
                .setCmdUsage("See the Discord Bot's Activity", "activity")
                .setCmdUsage("Define the Discord Bot's Activity", "activity", "[Discord Bot's Activity]")
                .setCmdUsage("See the Discord Bot's Permrole", "permrole")
                .setCmdUsage("Define the Discord Bot's Permrole", "permrole", "[Discord Permrole's ID]")
                .setCmdUsage("See the Discord Bot's Status", "status")
                .setCmdUsage("Define the Discord Bot's Status", "status", "[Discord Bot's Status]");
    }

    @Override
    public String getBaseColor() {
        return Colors.GREEN;
    }
}
