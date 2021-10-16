package io.github.fechan.enmerkar.commands;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class SearchDefinitions implements CommandExecutor {
    private FileConfiguration pluginConfig;
    private File dataFolder;

    public SearchDefinitions(File dataFolder, FileConfiguration pluginConfig) {
        this.pluginConfig = pluginConfig;
        this.dataFolder = dataFolder;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        if (args.length == 0) return false;

        String nation = this.pluginConfig.getString("nationality." + sender.getName());
        String searchTerm = String.join(" ", args).toLowerCase();

        File lexiconFile = new File(dataFolder, nation + ".yml");
        if (!lexiconFile.exists()) {
            sender.sendMessage(ChatColor.RED + nation + "does not currently have any words in its dictionary");
            return true;
        }
        YamlConfiguration lexicon = YamlConfiguration.loadConfiguration(lexiconFile);

        boolean found = false;
        for (Map.Entry<String, Object> entry : lexicon.getValues(false).entrySet()) {
            String definition = (String) entry.getValue();
            if (definition.toLowerCase().contains(searchTerm)) {
                sender.sendMessage("Definition of " + entry.getKey() + ": "+ definition);
                found = true;
            }
        }
        if (!found) sender.sendMessage(ChatColor.RED + "Couldn't find the search term in any word definitions: " + searchTerm);
        return true;
    }
    
}
