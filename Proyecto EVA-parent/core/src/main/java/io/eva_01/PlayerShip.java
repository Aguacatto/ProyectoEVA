package io.eva_01;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import io.eva_01.Shootable;

public class PlayerShip extends Entity implements Shootable {
    public PlayerShip(float x, float y, float speed, int health, Texture texture) {
        super(x, y, speed, health, texture);
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
    public void shoot() {
        // Código para disparar (crear una nueva instancia de Bullet)
    }
}
