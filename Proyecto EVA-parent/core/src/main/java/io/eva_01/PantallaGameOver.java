package io.eva_01;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;


public class PantallaGameOver implements Screen {

	private MainEva game;
	private OrthographicCamera camera;
	private Texture gameOver;
	private Music gameOverMusic;
	private int currentWidth;
    private int currentHeight;

	public PantallaGameOver(MainEva game) {
		this.game = game;
        
		camera = new OrthographicCamera();
		currentWidth = Gdx.graphics.getWidth();
        currentHeight = Gdx.graphics.getHeight();
        camera.setToOrtho(false, currentWidth, currentHeight);
		
		gameOver = new Texture("evaPIXEL.jpg");
		gameOverMusic = Gdx.audio.newMusic(Gdx.files.internal("Fly_me_to_the_Moon_Claire_version.mp3"));
		gameOverMusic.setLooping(false); // La canción no se repite
		gameOverMusic.setVolume(0.5f);   // Ajusta el volumen
		gameOverMusic.play(); 
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();
		game.getBatch().setProjectionMatrix(camera.combined);

		game.getBatch().begin();
		
		game.getBatch().draw(gameOver, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		float screenWidth = Gdx.graphics.getWidth();
	    float screenHeight = Gdx.graphics.getHeight();
	    
	    String gameOverText = "Game Over !!!";
	    String restartText = "Pincha en cualquier lado para reiniciar ...";
	    
	    float gameOverWidth = game.getFont().getData().scaleX * gameOverText.length() * 10; // Ajustar el cálculo según el tamaño de fuente
	    float restartWidth = game.getFont().getData().scaleX * restartText.length() * 10;
		
	 // Calcular posiciones centradas
	    float gameOverX = (screenWidth - gameOverWidth) / 2;
	    float gameOverY = screenHeight / 2 + 50; // Un poco más arriba del centro

	    float restartX = (screenWidth - restartWidth) / 2;
	    float restartY = screenHeight / 2 - 50; // Un poco más abajo del centro

	    // Dibujar los textos centrados
	    game.getFont().draw(game.getBatch(), gameOverText, gameOverX, gameOverY);
	    game.getFont().draw(game.getBatch(), restartText, restartX, restartY);
	
		game.getBatch().end();

		if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
			Screen ss = new PantallaJuego(game,1,3,0,1,1,10);
			game.setScreen(ss);
			dispose();
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

	@Override
	public void dispose() {
		if (gameOver != null) {
			gameOver.dispose();
		}
		if (gameOverMusic != null) {
			gameOverMusic.dispose();
		}
	}
   
}