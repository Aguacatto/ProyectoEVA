package io.eva_01;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import io.eva_01.Shootable;

public class PlayerShip extends Entity implements Shootable {
	
	private int ammo;
	private float shootCooldown;
	private float lastShotTime;	
	private Texture bulletTexture;
	
    public PlayerShip(float x, float y, float speed, int health, Texture texture) {
        super(x, y, speed, health, texture);
        this.ammo = 10;
        this.shootCooldown = 0.5f;
        this.lastShotTime = 0;
        this.bulletTexture = bulletTexture;
    }

    @Override
    public void update(float delta) {
        // Lógica de movimiento del jugador (ej: control con teclas)
        // Ejemplo: y += speed * delta;
    }

    @Override
    public void onDestroy() {
        // Código para manejar la destrucción del jugador (ej: mostrar "Game Over")
    }

    @Override
    public BulletEVA shoot() {
    	if(canShoot()) {
    		lastShotTime = 0;
    		ammo--;
    		return new BulletEVA(x, y, 500, 10, bulletTexture);
    	}
    	return null;
    }
    
    public void reload() {
    	ammo = 10;
    }
    
    public boolean canShoot() {
    	return ammo > 0 && lastShotTime >= shootCooldown;
    }
    
    
}
