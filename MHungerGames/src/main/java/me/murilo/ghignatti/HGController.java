package me.murilo.ghignatti;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.bukkit.Bukkit;

import me.murilo.ghignatti.enums.GameState;
import me.murilo.ghignatti.events.PreBattle;

public class HGController {
    
    @JsonIgnore
    private HashMap<String, Integer> tasks;

    @JsonIgnore
    private int currentGameTime;
    
    @JsonIgnore
    private GameState currentGameState;

    @JsonIgnore
    private MHungerGames mainInstance;

    @JsonIgnore
    private PreBattle preBattle;

    private int startGame;

    private int invincibilityTime;

    private int minimumPlayers;

    private HGController(@JsonProperty("startGame") int startGame, @JsonProperty("invincibilityTime") int invincibilityTime, @JsonProperty("minimumPlayers") int minimumPlayers){
        this.tasks = new HashMap<>();
        this.currentGameTime = 0;
        this.currentGameState = GameState.WAITING_PLAYERS;
        this.startGame = startGame;
        this.invincibilityTime = invincibilityTime;
        this.minimumPlayers = minimumPlayers;
    }

    public static HGController loadConfigurations(MHungerGames mainInstance){
        String dataFolderPath = mainInstance.getDataFolder().getAbsolutePath();
        File dataFolder = new File(dataFolderPath);
        if(!dataFolder.exists())
            dataFolder.mkdir();
        File configFile = new File(new StringBuilder(dataFolderPath).append("/").append("config.json").toString());
        if(configFile.exists())
            try {
                HGController result = new ObjectMapper().readValue(configFile, HGController.class);
                result.setMainInstance(mainInstance);
                result.preBattle = new PreBattle(mainInstance);
                return result;
            } catch (IOException e) {
                //TODO add a server notification of this error
                e.printStackTrace();
                return null;
            }
        else{
            HGController result = new HGController(300, 120, 5);
            result.setMainInstance(mainInstance);
            result.preBattle = new PreBattle(mainInstance);
            try {
                new ObjectMapper().writeValue(configFile, result);
            } catch (IOException e) {
                //TODO add a server notification of this error
                e.printStackTrace();
                return null;
            }
            return result;
        }
    }

    public void changeGameState(GameState state){
        switch(state){
            case BATTLE:
                Bukkit.getScheduler().cancelTask(tasks.remove("INVINCIBILITY"));
                Bukkit.broadcastMessage("Let the battle begin!");
                preBattle.removeInvincibility();
                break;
            case FINAL_COMBAT:
                break;
            case INVINCIBILITY:
                preBattle.startInvincibleGameState();
                Bukkit.getScheduler().cancelTask(tasks.remove("TIMER"));
                tasks.put("INVINCIBILITY", Bukkit.getScheduler().scheduleSyncRepeatingTask(mainInstance, new Runnable(){
                    @Override
                    public void run() {
                        currentGameTime++;
                        Bukkit.broadcastMessage(new StringBuilder("You Are Invencible! For: ").append(invincibilityTime - (currentGameTime - startGame)).append(" seconds").toString());
                        if((currentGameTime - startGame) == invincibilityTime){
                            changeGameState(GameState.BATTLE);
                        }
                    }
                }, 0L, 20L));
                break;
            case START_COUNTDOWN:
                tasks.put("TIMER", Bukkit.getScheduler().scheduleSyncRepeatingTask(mainInstance, new Runnable(){
                    @Override
                    public void run() {
                        currentGameTime++;
                        Bukkit.broadcastMessage(new StringBuilder("Starting game in: ").append(startGame - currentGameTime).toString());
                        if(currentGameTime >= startGame)
                            changeGameState(GameState.INVINCIBILITY);
                    }
                }, 0L, 20L));
                break;
            case WAITING_PLAYERS:
                break;
            case WINNER:
                break;
            default:
                break;

        }
    }

    public long getStartGame() {
        return startGame;
    }

    public long getInvincibilityTime() {
        return invincibilityTime;
    }

    public int getMinimumPlayers() {
        return minimumPlayers;
    }

    public GameState getCurrentGameState() {
        return currentGameState;
    }

    public void setMainInstance(MHungerGames mainInstance) {
        this.mainInstance = mainInstance;
    }

    public PreBattle getPreBattle() {
        return preBattle;
    }

    public void reloadConfigurations(){
        HGController toCopy = null;
        try {
            toCopy = new ObjectMapper().readValue(new StringBuilder(this.mainInstance.getDataFolder().getAbsolutePath()).append("/").append("config.json").toString(), HGController.class);
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if(toCopy != null){
            this.startGame = toCopy.startGame;
            this.invincibilityTime = toCopy.invincibilityTime;
            this.minimumPlayers = toCopy.minimumPlayers;
        }
    }
}
