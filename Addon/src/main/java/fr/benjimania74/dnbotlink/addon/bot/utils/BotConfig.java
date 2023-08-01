package fr.benjimania74.dnbotlink.addon.bot.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.dv8tion.jda.api.OnlineStatus;

@AllArgsConstructor
@Getter
@Setter
public class BotConfig {
    private String token = "";
    private String activity = "DNBotLink for DreamNetwork";
    private String permRole = "everyone";
    private OnlineStatus status = OnlineStatus.ONLINE;
    private String prefix = "DN.";

    public BotConfig(){}
}
