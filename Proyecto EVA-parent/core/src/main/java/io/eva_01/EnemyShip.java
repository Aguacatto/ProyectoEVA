package io.eva_01;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import io.eva_01.Shootable;


public class EnemyShip extends Entity implements Shootable {
    public EnemyShip(float x, float y, float speed, int health, Texture texture) {
        super(x, y, speed, health, texture);
    }

    @Override
    public void update(float delta) {
        // Lógica de movimiento del enemigo (ej: moverse hacia el jugador)
        // Ejemplo: y -= speed * delta;
    }

    @Override
    public void onDestroy() {
        // Código para manejar la destrucción del enemigo (ej: aumentar el puntaje)
    }

    @Override
    public void shoot() {
        // Código para disparar (crear una nueva instancia de Bullet)
    }

	@Override
	public void reload() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean canShoot() {
		// TODO Auto-generated method stub
		return false;
	}
	
	// Getter para health
    public int getHealth() {
        return health;
    }

    // Setter para health
    public void setHealth(int health) {
        if (health >= 0) {
            this.health = health;
        }
    }
}
