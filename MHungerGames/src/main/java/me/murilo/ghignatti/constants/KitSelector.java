package me.murilo.ghignatti.constants;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class KitSelector {
    
    public static ItemStack getKitSelector(){
        ItemStack itemStack = new ItemStack(Material.COMPASS, 1);
        return itemStack;
    }
}
