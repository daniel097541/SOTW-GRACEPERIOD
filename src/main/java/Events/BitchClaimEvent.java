package Events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class BitchClaimEvent extends Event implements Cancellable{
    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled = false;
    private String playerName;
    private String factionName;

    public BitchClaimEvent(String player, String faction) {
        this.playerName = player;
        this.factionName = faction;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getFactionName() {
        return factionName;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
