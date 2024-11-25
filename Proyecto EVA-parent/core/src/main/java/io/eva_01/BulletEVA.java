package io.eva_01;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import io.eva_01.Shootable;

public class BulletEVA extends Entity {
    private int damage; // Daño que causa la bala
    private boolean destroyed;

    public BulletEVA(float x, float y, float xSpeed, float ySpeed, int damage, Texture texture) {
        super(x, y, xSpeed,	ySpeed, 1, texture); // La bala tiene 1 de vida porque se destruye al impactar
        
        this.damage = damage;
        this.destroyed = false;
    }

    @Override
    public void update(float delta) { 
        spr.setPosition(spr.getX()+xSpeed, spr.getY()+ySpeed);
        if (spr.getX() < 0 || spr.getX()+spr.getWidth() > Gdx.graphics.getWidth()) {
            destroyed = true;
        }
        if (spr.getY() < 0 || spr.getY()+spr.getHeight() > Gdx.graphics.getHeight()) {
        	destroyed = true;
        }
    }
    
    public void draw(SpriteBatch batch) {
    	spr.draw(batch);
    }

    @Override
    protected void onDestroy() {
        destroyed = true;
    }

    public int getDamage() {
        return damage;
    }
    
    public Rectangle getCollisionArea() {
        // Devuelve un Rectangle que representa el área de colisión de la bala
        return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }
    
    public boolean isDestroyed() {return destroyed;}
    
    public boolean checkCollision(Ball2 b2) {
        if(spr.getBoundingRectangle().overlaps(b2.getArea())){
        	// Se destruyen ambos
            this.destroyed = true;
            return true;

        }
        return false;
    }

	@Override
	protected void move(float delta) {
		// TODO Auto-generated method stub
		
	}
}

