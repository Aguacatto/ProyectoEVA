package io.eva_01;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;

public abstract class Entity {
    protected float x, y; // Posición
    protected float speed; // Velocidad
    protected int health; // Vida
    protected Texture texture; // Textura de la entidad

    public Entity(float x, float y, float speed, int health, Texture texture) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.health = health;
        this.texture = texture;
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

    // Método que debe implementarse en las clases hijas para actualizar el comportamiento
    public abstract void update(float delta);

    // Método que define lo que sucede al destruir la entidad
    protected abstract void onDestroy();
}
