package io.github.fechan.enmerkar.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class UnsetNation implements CommandExecutor {
    private JavaPlugin plugin;
    private FileConfiguration pluginConfig;

    public UnsetNation(JavaPlugin plugin) {
        this.plugin = plugin;
        this.pluginConfig = plugin.getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "You must specify a player to unset the nationality of");
            return false;
        }

        String player = args[0];
        this.pluginConfig.set("nationality." + player, null);
        this.plugin.saveConfig();
        sender.sendMessage("Unset the nationality of " + player);

        return true;
    }
}
