package io.github.fechan.enmerkar.commands;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

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
    public boolean onCommand(CommandSender sender, Command command, String label, String[] searchLemmas) {
        if (!(sender instanceof Player)) return false;
        if (searchLemmas.length == 0) return false;
    
        String nation = this.pluginConfig.getString("nationality." + sender.getName());

        File lexiconFile = new File(dataFolder, nation + ".yml");
        if (!lexiconFile.exists()) {
            sender.sendMessage(ChatColor.RED + nation + "does not currently have any words in its dictionary");
            return true;
        }
        YamlConfiguration lexicon = YamlConfiguration.loadConfiguration(lexiconFile);

        List<String> failedWords = new ArrayList<>();
        for (String lemma : searchLemmas) {
            String definition = lexicon.getString(lemma);
            if (definition == null) {
                failedWords.add(lemma);
            } else {
                sender.sendMessage("Definition of " + lemma + ": " + definition);
            }
        }
        if (failedWords.size() > 0) {
            sender.sendMessage(ChatColor.RED + "No definition found for the following words: " + String.join(", ", failedWords));
        }
        return true;
    }
}
