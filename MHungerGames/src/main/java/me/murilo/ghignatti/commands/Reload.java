package me.murilo.ghignatti.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.murilo.ghignatti.MHungerGames;

public class Reload implements CommandExecutor{

    private MHungerGames mainInstance;

    public Reload(MHungerGames mainInstance){
        this.mainInstance = mainInstance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        this.mainInstance.reloadConfig();
        sender.sendMessage("Configs Reloaded!");
        return false;
    }
    
}
