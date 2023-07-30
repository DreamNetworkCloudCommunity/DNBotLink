package fr.benjimania74.dnbotlink.addon.utils.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Getter
public class LinkConfig {
    private List<String> consoleLinks = new ArrayList<>();
    private HashMap<String, List<String>> chatLinks = new HashMap<>();

    public LinkConfig(){}
}
