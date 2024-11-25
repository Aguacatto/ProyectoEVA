package io.eva_01;

public class ZigZagMovementStrategy implements MovementStrategy {
    private float zigzagSpeed = 100;

    @Override
    public void move(Entity entity, float delta) {
        entity.setY(entity.getY() - entity.getYSpeed() * delta);
        entity.setX(entity.getX() + (float) Math.sin(entity.getY() / 50) * zigzagSpeed * delta);
    }
}