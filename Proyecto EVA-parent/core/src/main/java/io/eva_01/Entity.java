package io.eva_01;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entity {
    protected float x, y; // Posición
    protected float xSpeed; // Velocidad
    protected float ySpeed;
    protected int health; // Vida
    protected Texture texture; // Textura de la entidad
    protected Sprite spr;

    public Entity(float x, float y, float xSpeed, float ySpeed, int health, Texture texture) {
        spr = new Sprite(texture);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.health = health;
        this.texture = texture;
        spr.setPosition(x, y);
    }

    // Método para dibujar la entidad
    public void draw(Batch batch) {
        batch.draw(texture, x, y);
    }

    // Método para reducir la vida de la entidad
    protected void takeDamage(int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            onDestroy();
        }
    }
    
    public final void updateAndRender(SpriteBatch batch, float delta) {
        handleCollisions(); // Método opcional: puede ser común o específico
        render(batch); // Método común para todas las entidades
    }
    
    
    protected void handleCollisions() {
    	
    }
    
    protected void render(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    // Método que debe implementarse en las clases hijas para actualizar el comportamiento
    public abstract void update(float delta);

    // Método que define lo que sucede al destruir la entidad
    protected abstract void onDestroy();
    
    public abstract Rectangle getCollisionArea();
    
    public boolean checkCollision(Entity other) {
        return this.getCollisionArea().overlaps(other.getCollisionArea());
    }
    
    public float getY() {
    	return y;
    }
    
    public float getX() {
    	return x;
    }
    
    public float getXSpeed() {
    	return xSpeed;
    }
    
    public float getYSpeed() {
    	return xSpeed;
    }

	public void setY(float f) {
		this.y = f;
	}
	
	public void setX(float f) {
		this.x = f;
	}

	protected void move(float delta) {
		// TODO Auto-generated method stub
		
	}
}
