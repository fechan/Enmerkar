package io.github.fechan.enmerkar;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;

import io.github.fechan.enmerkar.commands.*;

public class Enmerkar extends JavaPlugin
{
    @Override
    public void onEnable(){
        getLogger().info("Enmerkar plugin enabled!");
        this.saveDefaultConfig();
        File dataFolder = this.getDataFolder();
        FileConfiguration config = this.getConfig();

        this.getCommand("addword").setExecutor(new AddWord(dataFolder, config));
        this.getCommand("define").setExecutor(new GetWord(dataFolder, config));
        this.getCommand("searchdefinitions").setExecutor(new SearchDefinitions(dataFolder, config));
        this.getCommand("setnation").setExecutor(new SetNation(this));
        this.getCommand("unsetnation").setExecutor(new UnsetNation(this));
    }

    @Override
    public void onDisable() {
        getLogger().info("Enmerkar plugin disabled!");
    }
}
