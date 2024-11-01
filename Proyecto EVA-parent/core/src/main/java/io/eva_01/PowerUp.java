package io.eva_01;

import com.badlogic.gdx.graphics.Texture;

public class PowerUp extends Collectible {
    public enum PowerUpType {
        EXTRA_LIFE, SHIELD, SPEED_BOOST, DOUBLE_SHOT
    }

    private PowerUpType type;

    public PowerUp(float x, float y, Texture texture, PowerUpType type) {
        super(x, y, texture);
        this.type = type;
    }

    @Override
    public void applyEffect(PlayerShip player) {
        switch (type) {
            case EXTRA_LIFE:
                player.addLife(1);
                break;
            case SHIELD:
                player.activateShield();
                break;
            case SPEED_BOOST:
                player.increaseSpeed(50); // Ejemplo: aumenta la velocidad en 50 unidades
                break;
            case DOUBLE_SHOT:
                player.activateDoubleShot();
                break;
        }
    }
}
