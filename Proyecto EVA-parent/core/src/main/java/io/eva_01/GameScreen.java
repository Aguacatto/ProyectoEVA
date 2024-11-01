package io.eva_01;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.eva_01.PlayerShip;
import io.eva_01.EnemyShip;
import io.eva_01.Bullet;
import io.eva_01.PowerUp;
import io.eva_01.Obstacle;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    private SpriteBatch batch;
    private PlayerShip player;
    private static GameScreen instance;
    private List<Entity> entities;
    private List<Collectible> collectibles;

    public GameScreen() {
        batch = new SpriteBatch();
        Texture playerTexture = new Texture("Evangelion_Unit_01_versionTv.png");
        player = new PlayerShip(100, 100, 200, 3, playerTexture);

        entities = new ArrayList<>();
        entities.add(player);

        // Crear enemigos
        Texture enemyTexture = new Texture("Ramiel_octahedron.png");
        entities.add(new EnemyShip(300, 500, 100, 1, enemyTexture));

        // Crear collectibles (PowerUps)
        collectibles = new ArrayList<>();
        collectibles.add(new PowerUp(200, 600, new Texture("powerup.png"), PowerUp.PowerUpType.EXTRA_LIFE));

        // Crear obstáculos
        entities.add(new Obstacle(150, 700, 50, 1, new Texture("obstacle.png"), Obstacle.ObstacleType.DAMAGE));
    }
    
    public static GameScreen getInstance() {
        if (instance == null) {
            instance = new GameScreen();
        }
        return instance;
    }

    @Override
    public void render(float delta) {
        batch.begin();
        
        // Dibujar y actualizar todas las entidades
        for (Entity entity : entities) {
            entity.update(delta);
            entity.draw(batch);
        }

        // Dibujar y verificar colisiones con los collectibles
        for (Collectible collectible : collectibles) {
            collectible.draw(batch);
            if (collectible.isCollected(player)) {
                collectible.applyEffect(player);
            }
        }

        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        for (Entity entity : entities) {
            entity.texture.dispose();
        }
        for (Collectible collectible : collectibles) {
            collectible.texture.dispose();
        }
    }

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
