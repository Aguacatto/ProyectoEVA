package io.eva_01;

public class LinearMovementStrategy implements MovementStrategy {
    @Override
    public void move(Entity entity, float delta) {
        entity.setY(entity.getY() - entity.getYSpeed() * delta);
    }
}