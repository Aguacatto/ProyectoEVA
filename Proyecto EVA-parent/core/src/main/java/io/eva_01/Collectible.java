package io.eva_01;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Collectible {
    protected float x, y;
    protected Texture texture;
    private boolean collected;

    public Collectible(float x, float y, Texture texture) {
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

    // Método para dibujar el collectible en pantalla
    public void draw(Batch batch) {
        batch.draw(texture, x, y);
    }

    // Método que se implementa en cada tipo de collectible para aplicar su efecto
    public abstract void applyEffect(PlayerShip player);

    // Método para verificar si el jugador ha recogido el collectible
    public boolean isCollected(PlayerShip player) {
    	return player.getCollisionArea().overlaps(this.getCollisionArea());
    }
    
    public Rectangle getCollisionArea() {
        return new Rectangle(x, y, texture.getWidth(), texture.getHeight());
    }
    
    public void collect(PlayerShip player) {
        if (!collected && isCollected(player)) {
            collected = true;
            applyEffect(player);
        }
    }
}
