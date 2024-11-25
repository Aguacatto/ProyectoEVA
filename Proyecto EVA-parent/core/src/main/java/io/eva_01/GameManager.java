package io.eva_01;

import java.util.ArrayList;

public class GameManager {
    private static GameManager instance;

    private int score;
    private int level;
    private boolean isPaused;
    private ArrayList<Collectible> collectibles;
    

    private GameManager() {
        // Inicialización
        score = 0;
        level = 1;
        isPaused = false;
        collectibles = new ArrayList<>();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        score += points;
    }

    public int getLevel() {
        return level;
    }

    public void nextLevel() {
        level++;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }
    
    public void addCollectible(Collectible collectible) {
        collectibles.add(collectible);
    }

    public ArrayList<Collectible> getCollectibles() {
        return collectibles;
    }
}
