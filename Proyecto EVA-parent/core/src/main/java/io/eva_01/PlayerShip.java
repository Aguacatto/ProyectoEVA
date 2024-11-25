package io.eva_01;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import io.eva_01.Shootable;

public class PlayerShip extends Entity implements Shootable {
	
	private int ammo;
	private float shootCooldown;
	private float lastShotTime;	
	private Texture bulletTexture;
	private Sound choque;
	private Sound bala;
	private Texture texture;
	private int vidas;
	private boolean hurt;
	private int hurtTime;
	private EffectManager effectManager;
	private boolean shieldActive = false;
	private float shieldDuration = 0;
	private boolean destroyed;
	private GameScreen juego;
	
    public PlayerShip(float x, float y, float xSpeed, float ySpeed, int vidas, Texture texture, Sound choque,Texture bulletTexture, Sound bala) {
        super(x, y, xSpeed, ySpeed, vidas, texture);
        this.ammo = 10;
        this.shootCooldown = 0.5f;
        this.lastShotTime = 0;
        this.bulletTexture = bulletTexture;
        this.choque = choque;
        this.bala = bala;
        this.vidas = 3;
        this.hurt = false;
        this.hurtTime = 0;
        this.effectManager = new EffectManager();
        this.destroyed = false;
        
    }

    @Override
    public void update(float delta) {
        if (!hurt) {
        	
        	@SuppressWarnings("unused")
			float currentXSpeed = xSpeed;
        	@SuppressWarnings("unused")
			float currentYSpeed = ySpeed;
            if (effectManager.isEffectActive(EffectManager.EffectType.SPEED_BOOST)) {
                currentXSpeed *= 2; // Si el boost está activo, duplicar la velocidad
                currentYSpeed *= 2;
            }
            
            if (effectManager.isEffectActive(EffectManager.EffectType.SPEED_DECREASE)) {
                if(currentXSpeed != 0) {
                	currentXSpeed /= 2; // Si el boost está activo, disminuir la velocidad
                }
                
                if(currentYSpeed != 0) {
                	currentYSpeed /= 2;
                }
            }
            
            // Movimiento del jugador con velocidad constante
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x -= xSpeed * 20 * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x += xSpeed * 20 * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) y -= ySpeed * 20 * delta;
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) y += ySpeed * 20 * delta;

            // Mantener al jugador dentro de los bordes de la pantalla
            if (x < 0) x = 0;
            if (x + spr.getWidth() > Gdx.graphics.getWidth())
                x = Gdx.graphics.getWidth() - spr.getWidth();
            if (y < 0) y = 0;
            if (y + spr.getHeight() > Gdx.graphics.getHeight())
                y = Gdx.graphics.getHeight() - spr.getHeight();

        } else {
            // Comportamiento cuando el jugador está herido (efecto de sacudida)
            x += Math.random() * 4 - 2; // Movimiento aleatorio horizontal
            hurtTime--;

            if (hurtTime <= 0) {
                hurt = false; // El jugador ya no está herido
            }
            
        }
        
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {         
        	BulletEVA bullet = shoot();
        	juego.agregarBala(bullet);
        	bala.play();
          }
        
        
        effectManager.updateEffects(delta);
    }
    
    public void render(Batch batch) {
        // Dibujar la nave del jugador
        batch.draw(texture, x, y);
    }


    @Override
    public BulletEVA shoot() {
    	if(canShoot()) {
    		lastShotTime = 0;
    		ammo--;
    		return new BulletEVA(spr.getX()+(spr.getWidth() - 30),spr.getY()+((spr.getHeight()/2) + 30),3,0,1,bulletTexture);
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
    
    public void activateShield(float duration) {
        effectManager.activateEffect(EffectManager.EffectType.SHIELD, duration);
        shieldActive = true; // Indica que el escudo está activo.
    }
    
    public void increaseSpeed(float duration) {
    	effectManager.activateEffect(EffectManager.EffectType.SPEED_BOOST, duration);
    	
    }
    
    public void decreaseSpeed(float duration) {
    	effectManager.activateEffect(EffectManager.EffectType.SPEED_DECREASE, duration);
    }
    
    public void activateDoubleShot(float duration) {
    	effectManager.activateEffect(EffectManager.EffectType.DOUBLE_SHOT, duration);
    }

	@Override
	public Rectangle getCollisionArea() {
		return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
	}
	
	@Override
	protected void takeDamage(int damage) {
	    if (shieldActive) {
	        damage = 0;
	        super.takeDamage(damage);
	    } else {
	        // Recibir daño normalmente.
	        super.takeDamage(damage);
	    }
	}

	@Override
	protected void onDestroy() {
		destroyed = true; 
	}

	@Override
	protected void move(float delta) {
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) x -= xSpeed * 20 * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) x += xSpeed * 20 * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) y -= ySpeed * 20 * delta;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) y += ySpeed * 20 * delta;

	}
}
