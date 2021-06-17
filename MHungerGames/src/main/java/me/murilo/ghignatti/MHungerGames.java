package me.murilo.ghignatti;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MHungerGames extends JavaPlugin{

    @Override
    public void onEnable() {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&6MHungerGames&7] &aLoaded"));
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}