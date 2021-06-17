package me.murilo.ghignatti.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import me.murilo.ghignatti.MHungerGames;
import me.murilo.ghignatti.enums.GameState;

public class PreBattle implements Listener{

    private final MHungerGames mainInstance;

    public PreBattle(MHungerGames mainInstace){
        this.mainInstance = mainInstace;
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(mainInstance.getHgController().getCurrentGameState() == GameState.WAITING_PLAYERS && mainInstance.getServer().getOnlinePlayers().size() >= mainInstance.getHgController().getMinimumPlayers()){
            PlayerJoinEvent.getHandlerList().unregister(this);
            mainInstance.getHgController().changeGameState(GameState.START_COUNTDOWN);
        }
        else{
            Bukkit.broadcastMessage(new StringBuilder("We need at least ").append(mainInstance.getHgController().getMinimumPlayers() - mainInstance.getServer().getOnlinePlayers().size()).append(" more players").toString());
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        GameState currentGameState = mainInstance.getHgController().getCurrentGameState();
        if(currentGameState != GameState.BATTLE || currentGameState != GameState.FINAL_COMBAT){
            event.setCancelled(true);
        }
        else{
            EntityDamageEvent.getHandlerList().unregister(this);
        }
    }

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event){
        GameState currentGameState = mainInstance.getHgController().getCurrentGameState();
        if(currentGameState != GameState.BATTLE || currentGameState != GameState.FINAL_COMBAT){
            event.setCancelled(true);
        }
        else{
            FoodLevelChangeEvent.getHandlerList().unregister(this);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        GameState currentGameState = mainInstance.getHgController().getCurrentGameState();
        if(currentGameState != GameState.BATTLE || currentGameState != GameState.FINAL_COMBAT){
            event.setCancelled(true);
        }
        else{
            BlockBreakEvent.getHandlerList().unregister(this);
        }
    }

    @EventHandler
    public void onBlockBurn(BlockBurnEvent event){
        GameState currentGameState = mainInstance.getHgController().getCurrentGameState();
        if(currentGameState != GameState.BATTLE || currentGameState != GameState.FINAL_COMBAT){
            event.setCancelled(true);
        }
        else{
            BlockBurnEvent.getHandlerList().unregister(this);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        GameState currentGameState = mainInstance.getHgController().getCurrentGameState();
        if(currentGameState != GameState.BATTLE || currentGameState != GameState.FINAL_COMBAT && !event.getPlayer().hasPermission("mhg.bypass.blockplace")){
            event.setCancelled(true);
        }
        else{
            BlockPlaceEvent.getHandlerList().unregister(this);
        }
    }

    @EventHandler
    public void onChestOpen(PlayerInteractEvent event){
        GameState currentGameState = mainInstance.getHgController().getCurrentGameState();
        if(currentGameState != GameState.BATTLE || currentGameState != GameState.FINAL_COMBAT && (event.getClickedBlock().getType().equals(Material.CHEST) || event.getClickedBlock().getType().equals(Material.FURNACE) || event.getClickedBlock().getType().equals(Material.ENDER_CHEST) || event.getClickedBlock().getType().equals(Material.SHULKER_BOX))){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event){
        GameState currentGameState = mainInstance.getHgController().getCurrentGameState();
        if(currentGameState != GameState.BATTLE || currentGameState != GameState.FINAL_COMBAT && !event.getPlayer().hasPermission("mhg.bypass.itemdrop")){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event){
        GameState currentGameState = mainInstance.getHgController().getCurrentGameState();
        if(currentGameState != GameState.BATTLE || currentGameState != GameState.FINAL_COMBAT){
            LivingEntity entity = event.getEntity();
            if(entity instanceof Player && !((Player) entity).hasPermission("mhg.bypass.itempickup")){
                event.setCancelled(true);
            }
            else if(entity instanceof Player == false)
                event.setCancelled(true);
        }
    }

    public void removeInvincibility(){
        EntityDamageEvent.getHandlerList().unregister(this);
        FoodLevelChangeEvent.getHandlerList().unregister(this);
    }

    public void startInvincibleGameState(){
        Bukkit.getOnlinePlayers().forEach(x -> x.teleport(Bukkit.getWorld("world").getSpawnLocation()));
        PlayerInteractEvent.getHandlerList().unregister(this);
        BlockPlaceEvent.getHandlerList().unregister(this);
        BlockBurnEvent.getHandlerList().unregister(this);
        BlockBreakEvent.getHandlerList().unregister(this);
        PlayerDropItemEvent.getHandlerList().unregister(this);
        EntityPickupItemEvent.getHandlerList().unregister(this);
    }
}
