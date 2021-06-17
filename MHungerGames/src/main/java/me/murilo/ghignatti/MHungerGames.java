package me.murilo.ghignatti;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class MHungerGames extends JavaPlugin{


    private HGController hgController;

    @Override
    public void onEnable() {

        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&6MHungerGames&7] &aLoaded"));
        this.hgController = HGController.loadConfigurations(this);
        this.getServer().getPluginManager().registerEvents(this.hgController.getPreBattle(), this);
        Bukkit.getServer().getConsoleSender().sendMessage("Test StartGame: " + hgController.getStartGame());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    public HGController getHgController() {
        return hgController;
    }

    public void reloadConfigurations(){
        this.hgController.reloadConfigurations();
    }
}