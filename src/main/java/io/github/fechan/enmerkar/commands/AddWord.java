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

public class AddWord implements CommandExecutor {
    private File dataFolder;
    private FileConfiguration pluginConfig;

    public AddWord(File dataFolder, FileConfiguration pluginConfig) {
        this.dataFolder = dataFolder;
        this.pluginConfig = pluginConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command must be run by a player");
            return false;
        }
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "When adding a word, you must have both a word to define and its definition");
            return false;
        }
        
        String lemma = args[0];
        String definition = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        String nation = this.pluginConfig.getString("nationality." + sender.getName());

        if (nation == null) {
            sender.sendMessage(ChatColor.RED + "You must be in a nation to use this command!");
            return true;
        }

        File lexiconFile = new File(dataFolder, nation + ".yml");
        if (!lexiconFile.exists()) {
            try { lexiconFile.createNewFile(); }
            catch (IOException e) {
                sender.sendMessage(ChatColor.RED + "Unable to find or create lexicon for nation " + nation);
                return true;
            }
        }
        YamlConfiguration lexicon = YamlConfiguration.loadConfiguration(lexiconFile);
        
        lexicon.set(lemma, definition);
        try { lexicon.save(lexiconFile); }
        catch (IOException e) {
            sender.sendMessage(ChatColor.RED + "Unable to save the definition of the word " + lemma);
        }
        sender.sendMessage("Set word definition of the word " + lemma);

        return true;
    }
}
