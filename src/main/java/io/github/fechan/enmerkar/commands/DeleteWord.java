package io.github.fechan.enmerkar.commands;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class DeleteWord implements CommandExecutor {
    private File dataFolder;
    private FileConfiguration pluginConfig;

    public DeleteWord(File dataFolder, FileConfiguration pluginConfig) {
        this.dataFolder = dataFolder;
        this.pluginConfig = pluginConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command must be run by a player");
            return false;
        }
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "You must specify a word to delete");
            return false;
        }
        
        String lemma = args[0];
        String nation = this.pluginConfig.getString("nationality." + sender.getName());

        if (nation == null) {
            sender.sendMessage(ChatColor.RED + "You must be in a nation to use this command!");
            return true;
        }

        File lexiconFile = new File(dataFolder, nation + ".yml");
        if (!lexiconFile.exists()) {
            sender.sendMessage(ChatColor.RED + "Unable to find a lexicon for nation " + nation);
            return true;
        }
        YamlConfiguration lexicon = YamlConfiguration.loadConfiguration(lexiconFile);
        
        lexicon.set(lemma, null);
        try { lexicon.save(lexiconFile); }
        catch (IOException e) {
            sender.sendMessage(ChatColor.RED + "Unable to save the deletion of " + lemma + " to disk");
        }
        sender.sendMessage("Deleted the word " + lemma);

        return true;
    }
}
