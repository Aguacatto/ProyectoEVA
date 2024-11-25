package io.eva_01;

import com.badlogic.gdx.graphics.Texture;

public class PowerUp extends Collectible {
    public enum PowerUpType {
        EXTRA_LIFE, SHIELD, SPEED_BOOST, DOUBLE_SHOT
    }

    private PowerUpType type;
    private float duration;
    private boolean isTemp;

    public PowerUp(float x, float y, Texture texture, PowerUpType type, float duration) {
        super(x, y, texture);
        this.type = type;
        this.duration = duration;
        this.isTemp = duration > 0;
    }

    @Override
    public void applyEffect(PlayerShip player) {
        switch (type) {
            case EXTRA_LIFE:
                player.addLife(1);
                break;
            case SHIELD:
                player.activateShield(10);
                break;
            case SPEED_BOOST:
                player.increaseSpeed(10); 
                break;
            case DOUBLE_SHOT:
                player.activateDoubleShot(20);
                break;
        }
    }
}
