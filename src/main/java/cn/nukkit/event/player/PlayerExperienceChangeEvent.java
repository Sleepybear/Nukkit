package cn.nukkit.event.player;

import cn.nukkit.Player;
import cn.nukkit.event.HandlerList;

public class PlayerExperienceChangeEvent extends PlayerEvent {
    private static final HandlerList handlers = new HandlerList();

    private int oldLevel;
    private int oldProgress;
    private int newLevel;
    private int newProgress;

    public static HandlerList getHandlers() {
        return handlers;
    }

    public PlayerExperienceChangeEvent(Player player, int oldLevel, int oldProgress, int newProgress, int newLevel){
        this.player = player;
        this.oldLevel = oldLevel;
        this.oldProgress = oldProgress;
        this.newLevel = newLevel;
        this.newProgress = newProgress;
    }

    public int getOldLevel(){
        return this.oldLevel;
    }

    public int getOldProgress(){
        return this.oldProgress;
    }

    public int getNewLevel(){
        return this.newLevel;
    }

    public void setNewLevel(int newLevel){
        this.newLevel = newLevel;
    }

    public int getNewProgress(){
        return newProgress;
    }

    public void setNewProgress(int newProgress){
        this.newProgress = newProgress;
    }
}
