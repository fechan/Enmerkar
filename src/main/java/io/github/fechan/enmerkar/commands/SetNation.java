package io.github.fechan.enmerkar.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class SetNation implements CommandExecutor {
    private JavaPlugin plugin;
    private FileConfiguration pluginConfig;

    public SetNation(JavaPlugin plugin) {
        this.plugin = plugin;
        this.pluginConfig = plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "To set a player, you must specify both a player and a nation to assign them to");
            return false;
        }

        String player = args[0];
        String nation = args[1];

        if (!nation.matches("^[a-zA-Z0-9_]*$")) {
            sender.sendMessage(ChatColor.RED + "Nation names must be alphanumeric and contain no spaces (underscores are allowed)");
        }

        this.pluginConfig.set("nationality." + player, nation);
        this.plugin.saveConfig();
        sender.sendMessage("Set the nationality of " + player + " to " + nation);

        return true;
    }
}
