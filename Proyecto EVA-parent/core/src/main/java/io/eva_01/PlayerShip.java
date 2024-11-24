package io.eva_01;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import io.eva_01.Shootable;

public class PlayerShip extends Entity implements Shootable {
	
	private int ammo;
	private float shootCooldown;
	private float lastShotTime;	
	private Texture bulletTexture;
	private int vidas;
	private boolean hurt;
	private int hurtTime;
	
    public PlayerShip(float x, float y, float speed, int vidas, Texture texture, Texture bulletTexture) {
        super(x, y, speed, vidas, texture);
        this.ammo = 10;
        this.shootCooldown = 0.5f;
        this.lastShotTime = 0;
        this.bulletTexture = bulletTexture;
        this.vidas = 3;
        this.hurt = false;
        this.hurtTime = 0;
    }

    @Override
    public void update(float delta) {
        if (!hurt) {
            // Movimiento del jugador con velocidad constante
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x -= speed * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x += speed * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) y -= speed * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) y += speed * delta;

            // Mantener al jugador dentro de los bordes de la pantalla
            if (x < 0) x = 0;
            if (x + texture.getWidth() > Gdx.graphics.getWidth())
                x = Gdx.graphics.getWidth() - texture.getWidth();
            if (y < 0) y = 0;
            if (y + texture.getHeight() > Gdx.graphics.getHeight())
                y = Gdx.graphics.getHeight() - texture.getHeight();

        } else {
            // Comportamiento cuando el jugador está herido (efecto de sacudida)
            x += Math.random() * 4 - 2; // Movimiento aleatorio horizontal
            hurtTime--;

            if (hurtTime <= 0) {
                hurt = false; // El jugador ya no está herido
            }
        }
    }
    
    public void render(Batch batch) {
        // Dibujar la nave del jugador
        batch.draw(texture, x, y);
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
    
    // Getter para health
    public int getVidas() {
        return vidas;
    }
    
    // Setter para health
    public void setVidas(int vidas) {
        if (vidas >= 0) {
            this.vidas = vidas;
        }
    }
    
    public void setHerido(int duracion) {
        hurt = true;
        hurtTime = duracion;
    }

    public boolean isHerido() {
        return hurt;
    }
    
    public void addLife(int extraVida) {
    	this.vidas += 1;
    }
    
    public void increaseSpeed(float celeridad) {
    	this.speed += celeridad;
    }
    
    
}
