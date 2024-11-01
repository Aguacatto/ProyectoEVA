package io.eva_01;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import io.eva_01.Shootable;

public class BulletEVA extends Entity {
    private float damage; // Daño que causa la bala

    public BulletEVA(float x, float y, float speed, float damage, Texture texture) {
        super(x, y, speed, 1, texture); // La bala tiene 1 de vida porque se destruye al impactar
        this.damage = damage;
    }

    @Override
    public void update(float delta) {
        // Movimiento de la bala (ej: avanzar en el eje Y)
        y += speed * delta;
    }

    @Override
    protected void onDestroy() {
        // Código para manejar la destrucción de la bala (ej: eliminarla del juego)
    }

    public float getDamage() {
        return damage;
    }
}

