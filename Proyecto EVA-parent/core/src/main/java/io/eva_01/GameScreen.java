package io.eva_01;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.eva_01.PlayerShip;
import io.eva_01.EnemyShip;
import io.eva_01.Bullet;
import io.eva_01.PowerUp;
import io.eva_01.Obstacle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameScreen implements Screen {
	private MainEva game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Texture backgroundTexture;
    private PlayerShip player;
    private Sound explosionSound;
    private int score;
    private Music gameMusic;
    private int velXAngeles;
    private int velYAngeles;
    private int cantAngeles;
    private boolean isPaused;
    private List<Entity> entities;
    private List<Collectible> collectibles;
    private GameManager manager;
    
    private ArrayList<Ball2> angelesMen;
    private ArrayList<Ball2> angelesMaj;
    private ArrayList<EnemyShip> angelesShoot;
    private ArrayList<BulletEVA> balasEVA;
    
    

    public GameScreen(MainEva game, int ronda, int vidas, int score,  
			int velXAngeles, int velYAngeles, int cantAngeles) {
    	this.manager = GameManager.getInstance();
    	this.game = game;
    	manager.addScore(score);
        manager.nextLevel();
        
        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 640);
        initAudio();
        initBackground();
        initPlayer(vidas);
        	
        initEntities(velXAngeles, velYAngeles, cantAngeles);
        
        isPaused = false;
        Texture playerTexture = new Texture("Evangelion_Unit_01_versionTv.png");
        Texture bulletTexture = new Texture(Gdx.files.internal("bullet.png"));
        player = new PlayerShip(30, Gdx.graphics.getHeight()/2, 10, 10, 3, playerTexture, Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")), bulletTexture, Gdx.audio.newSound(Gdx.files.internal("BulletSound.mp3")));

        entities = new ArrayList<>();
        entities.add(player);
    }
    
    private void initAudio() {
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("RamielDeath.mp3"));
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("FightMusic.mp3"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.3f);
        gameMusic.play();
    }
    
    private void initBackground() {
        backgroundTexture = new Texture("1310225.jpeg");
    }
    
    private void initEntities(int velXAsteroides, int velYAsteroides, int asteroidCount) {
        entities = new ArrayList<>();
        angelesMen = new ArrayList<>();
        angelesMaj = new ArrayList<>();
        angelesShoot = new ArrayList<>();

        entities.add(player);

        // Crear ángeles pequeños y grandes
        Random r = new Random();
        
        MovementStrategy movement = new ZigZagMovementStrategy();
        for (int i = 0; i < asteroidCount / 2; i++) {
            Ball2 angel = new Ball2(r.nextInt((int)Gdx.graphics.getWidth()),
	  	            50+r.nextInt((int)Gdx.graphics.getHeight()-50),
	  	            20+r.nextInt(10), velXAsteroides+r.nextInt(4), velYAsteroides+r.nextInt(4), 
	  	            new Texture(Gdx.files.internal("Ramiel_octahedron.png")));
            angelesMen.add(angel);
            angelesMaj.add(angel);
        }
        for (int i = 0; i < asteroidCount / 2; i++) {
            EnemyShip angel = new EnemyShip(r.nextInt((int)Gdx.graphics.getWidth()),
	  	            50+r.nextInt((int)Gdx.graphics.getHeight()-50),
	  	            20+r.nextInt(10), velXAsteroides+r.nextInt(4), velYAsteroides+r.nextInt(4), 
	  	            new Texture(Gdx.files.internal("sahaquiel.png")), movement);
            angelesShoot.add(angel);
        }
    }
    
    private void initPlayer(int lives) {
        player = new PlayerShip(30, Gdx.graphics.getHeight() / 2, 0, 0, 3,
                new Texture("eva01Resize.png"),
                Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),
                new Texture("bullet.png"),
                Gdx.audio.newSound(Gdx.files.internal("BulletSound.mp3")));
    }	

    @Override
    public void render(float delta) {
    	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    	if (Gdx.input.isKeyJustPressed(Input.Keys.P)) {
            manager.setPaused(!manager.isPaused());
        }
        if (manager.isPaused()) {
            renderPauseMenu();
            return;
        }
    	
        batch.begin();
        
        game.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        
        dibujaEncabezado();
	      if (!player.isHerido()) {
		      // colisiones entre balas y asteroides y su destruccion  
	    	  for (int i = 0; i < balasEVA.size(); i++) {
		            BulletEVA b = balasEVA.get(i);
		            b.update(delta);
		            for (int j = 0; j < angelesMen.size(); j++) {    
		              if (b.checkCollision(angelesMen.get(j))) {          
		            	 explosionSound.play();
		            	 angelesMen.remove(j);
		            	 angelesMaj.remove(j);
		            	 j--;
		            	 score +=10;
		              }   	  
		  	        }
		                
		         //   b.draw(batch);
		            if (b.isDestroyed()) {
		                balasEVA.remove(b);
		                i--; //para no saltarse 1 tras eliminar del arraylist
		            }
		      }
		      //actualizar movimiento de asteroides dentro del area
		      for (Ball2 ball : angelesMen) {
		          ball.update();
		      }
		      //colisiones entre asteroides y sus rebotes  
		      for (int i=0;i<angelesMen.size();i++) {
		    	Ball2 ball1 = angelesMen.get(i);   
		        for (int j=0;j<angelesMaj.size();j++) {
		          Ball2 ball2 = angelesMaj.get(j); 
		          if (i<j) {
		        	  ball1.checkCollision(ball2);
		     
		          }
		        }
		      } 
	      }
        
        // Dibujar y actualizar todas las entidades
        for (Entity entity : entities) {
            entity.update(delta);
            entity.draw(batch);
        }

        /* Dibujar y verificar colisiones con los collectibles
        for (Collectible collectible : collectibles) {
            collectible.draw(batch);
            if (collectible.isCollected(player)) {
                collectible.applyEffect(player);
                manager.addScore(10);
            }
        }*/
        
        for (BulletEVA b : balasEVA) {       
	          b.draw(batch);
	      }

        batch.end();
        
        checkLevelCompletion();
    }
    
    public void dibujaEncabezado() {
		CharSequence str = "Vidas: "+player.getVidas()+" Ronda: "+manager.getLevel();
		game.getFont().getData().setScale(2f);		
		game.getFont().draw(batch, str, 10, 30);
		game.getFont().draw(batch, "Score:"+this.score, Gdx.graphics.getWidth()-150, 30);
		game.getFont().draw(batch, "HighScore:"+game.getHighScore(), Gdx.graphics.getWidth()/2-100, 30);
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
	
	private void checkLevelCompletion() {
		if (angelesMen.size()==0 && angelesMaj.size()==0 && angelesShoot.size()==0) {
			Screen ss = new GameScreen(game,manager.getLevel()+1, player.getVidas(), manager.getScore(), 
					velXAngeles + (int)0.25*manager.getLevel(), velYAngeles+ (int)0.25*manager.getLevel(), cantAngeles+2);
			game.setScreen(ss);
			dispose();
		  }
    }
	
	public boolean agregarBala(BulletEVA bb) {
    	return balasEVA.add(bb);
    }

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	private void renderPauseMenu() {
	    // Dibujar elementos del menú de pausa
	    batch.begin();
	    game.getFont().draw(batch, "Paused", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
	    game.getFont().draw(batch, "Press P to Resume", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2 - 20);
	    batch.end();
	}


	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
}
