package io.eva_01;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public class Obstacle extends Entity {
    public enum ObstacleType {
        DAMAGE, SLOW_DOWN, PUSH_BACK
    }

    private ObstacleType type;

    public Obstacle(float x, float y, float xSpeed, float ySpeed, int health, Texture texture, ObstacleType type) {
        super(x, y, xSpeed, ySpeed, health, texture);
        this.type = type;
    }

    @Override
    public void update(float delta) {
        y -= ySpeed * delta;
        
        if (y + texture.getHeight() < 0) {
            onDestroy(); // Lógica para destruir o remover el obstáculo
        }
    }

    @Override
    public void onDestroy() {
        // Código para destruir el obstáculo o removerlo del juego
    }

    // Método que aplica el efecto del obstáculo en el jugador
    public void applyEffect(PlayerShip player) {
        switch (type) {
            case DAMAGE:
                player.takeDamage(1); // Ejemplo: le quita 1 de vida al jugador
                break;
            case SLOW_DOWN:
                player.decreaseSpeed(20); // Ejemplo: disminuye la velocidad del jugador
                break;
            case PUSH_BACK:
                spr.setPosition(spr.getX(), spr.getY() - 50); // Retrocede al jugador
                break;
        }
    }

	@Override
	public Rectangle getCollisionArea() {
		return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
	}

	@Override
	protected void move(float delta) {
		// TODO Auto-generated method stub
		
	}
}
