package cn.nukkit.api.event.level;

import cn.nukkit.api.event.Cancellable;
import cn.nukkit.api.level.Level;

public class ThunderChangeEvent extends WeatherEvent implements Cancellable {
    private final boolean to;
    private boolean cancelled;

    public ThunderChangeEvent(Level level, boolean to) {
        super(level);
        this.to = to;
    }

    /**
     * Gets the state of thunder that the world is being set to
     *
     * @return true if the thunder is being set to start, false otherwise
     */
    public boolean toThunderState() {
        return to;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
