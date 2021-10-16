package io.github.fechan.enmerkar.commands;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class GetWord implements CommandExecutor {
    private File dataFolder;
    private FileConfiguration pluginConfig;

    public GetWord(File dataFolder, FileConfiguration pluginConfig) {
        this.dataFolder = dataFolder;
        this.pluginConfig = pluginConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        String nation = this.pluginConfig.getString("nationality." + sender.getName());
        String lemma = args[0];

        File lexiconFile = new File(dataFolder, nation + ".yml");
        if (!lexiconFile.exists()) {
            sender.sendMessage(ChatColor.RED + nation + "does not currently have any words in its dictionary");
            return true;
        }
        YamlConfiguration lexicon = YamlConfiguration.loadConfiguration(lexiconFile);
        
        String definition = lexicon.getString(lemma);
        if (definition == null) {
            sender.sendMessage(ChatColor.RED + lemma + " is not in " + nation + "'s dictionary");
        } else {
            sender.sendMessage("Definition of " + lemma + ": " + definition);
        }
        return true;
    }
}
