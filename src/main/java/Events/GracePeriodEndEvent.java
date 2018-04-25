package Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class GracePeriodEndEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    public GracePeriodEndEvent() {
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }





}
