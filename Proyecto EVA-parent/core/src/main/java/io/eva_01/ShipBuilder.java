package io.eva_01;

import com.badlogic.gdx.graphics.Texture;

public class ShipBuilder {
    private float x, y, xSpeed, ySpeed;
    private int health;
    private Texture texture, bulletTexture;
    private MovementStrategy movementStrategy;

    public ShipBuilder setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public ShipBuilder setXSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
        return this;
    }
    
    public ShipBuilder setYSpeed(float ySpeed) {
        this.ySpeed = ySpeed;
        return this;
    }

    public ShipBuilder setTexture(Texture texture) {
        this.texture = texture;
        return this;
    }

    public ShipBuilder setBulletTexture(Texture bulletTexture) {
        this.bulletTexture = bulletTexture;
        return this;
    }

    public ShipBuilder setMovementStrategy(MovementStrategy movementStrategy) {
        this.movementStrategy = movementStrategy;
        return this;
    }

    public EnemyShip buildEnemyShip() {
        return new EnemyShip(x, y, xSpeed, ySpeed, health, texture, movementStrategy);
    }
}

