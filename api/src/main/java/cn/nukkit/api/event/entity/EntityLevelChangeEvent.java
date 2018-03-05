package cn.nukkit.api.event.entity;


import cn.nukkit.api.entity.Entity;
import cn.nukkit.api.event.Cancellable;
import cn.nukkit.api.level.Level;

public class EntityLevelChangeEvent implements EntityEvent, Cancellable {
    private final Entity entity;
    private final Level originLevel;
    private final Level targetLevel;
    private boolean cancelled;

    public EntityLevelChangeEvent(Entity entity, Level originLevel, Level targetLevel) {
        this.entity = entity;
        this.originLevel = originLevel;
        this.targetLevel = targetLevel;
    }

    public Level getOrigin() {
        return originLevel;
    }

    public Level getTarget() {
        return targetLevel;
    }

    @Override
    public Entity getEntity() {
        return entity;
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
