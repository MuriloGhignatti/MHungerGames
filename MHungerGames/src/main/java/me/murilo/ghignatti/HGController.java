package me.murilo.ghignatti;

import java.util.HashMap;

import me.murilo.ghignatti.enums.GameState;

public class HGController {
    
    private static HashMap<String, Integer> tasks = new HashMap<>();

    private static long currentGameTime = 0;
    
    private static GameState currentGameState = GameState.WAITING_PLAYERS;

}
