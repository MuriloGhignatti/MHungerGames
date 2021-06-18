package me.murilo.ghignatti.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.murilo.ghignatti.MHungerGames;

public class KitCommand implements CommandExecutor{

    private final MHungerGames mainInstance;

    public KitCommand(MHungerGames mainInstance){
        this.mainInstance = mainInstance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(this.mainInstance.getHgController().giveKit(((Player) sender), args[0])){
            sender.sendMessage("Seu Kit Agora É: " + args[0]);
            return true;
        }
        return false;
    }
    
}
