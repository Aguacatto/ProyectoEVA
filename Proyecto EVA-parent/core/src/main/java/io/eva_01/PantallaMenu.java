package io.eva_01;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.audio.Music; // Importar la clase Music


@SuppressWarnings("unused")
public class PantallaMenu implements Screen {

    private MainEva game;	
    private OrthographicCamera camera;
    private Texture backgroundTexture;

    private boolean isFullscreen = false; // Variable para rastrear el estado de pantalla completa
    private Music backgroundMusic; // Variable para almacenar la música de fondo
    // Define el tamaño de la ventana en modo minimizado
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 800;
    
 // Variables para almacenar el tamaño actual de la ventana
    private int currentWidth;
    private int currentHeight;


    public PantallaMenu(MainEva game) {
        this.game = game;

        camera = new OrthographicCamera();
        currentWidth = Gdx.graphics.getWidth();
        currentHeight = Gdx.graphics.getHeight();
        camera.setToOrtho(false, currentWidth, currentHeight);

        // Cargar la imagen de fondo desde la carpeta "assets"
        backgroundTexture = new Texture("Main.PNG"); // Asegúrate de que la imagen esté en "assets/evaPIXEL.jpg"

        // Cargar la música de fondo desde la carpeta "assets"
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("cruelAngel8bit.mp3")); // Reemplaza "tuCancion.mp3" con el nombre de tu archivo de música
        backgroundMusic.setLooping(true); // Configurar para que la música se repita
        backgroundMusic.setVolume(0.5f); // Ajustar el volumen (de 0.0 a 1.0)
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();

        // Dibujar la imagen de fondo en toda la pantalla con el tamaño actual de la ventana
        game.getBatch().draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Obtener el ancho y alto de la pantalla para centrar el texto
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // Título
        String titleText = "Proyecto: EVA";
        float titleWidth = game.getFont().getRegion().getRegionWidth();
        float titleX = (screenWidth - titleWidth) / 2;
        float titleY = screenHeight / 2 + 100;

        // Subtítulo
        String subtitleText = "La guerra por la instrumentalización humana";
        float subtitleWidth = game.getFont().getRegion().getRegionWidth();
        float subtitleX = (screenWidth - subtitleWidth) / 2;
        float subtitleY = screenHeight / 2 + 50;

        // Instrucciones para comenzar
        String startText = "Presiona ENTER o ESPACIO para comenzar";
        float startWidth = game.getFont().getRegion().getRegionWidth();
        float startX = (screenWidth - startWidth) / 2;
        float startY = screenHeight / 2 - 50;

        // Dibujar textos centrados
        game.getFont().draw(game.getBatch(), titleText, titleX, titleY);
        game.getFont().draw(game.getBatch(), subtitleText, subtitleX, subtitleY);
        game.getFont().draw(game.getBatch(), startText, startX, startY);

        game.getBatch().end();

        // Alternar entre ventana y pantalla completa con F11
        if (Gdx.input.isKeyJustPressed(Input.Keys.F11)) {
            toggleFullscreen();
        }

        // Cambiar de pantalla al presionar ENTER o ESPACIO
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) || Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Screen ss = new PantallaJuego(game, 1, 3, 0, 1, 1, 10);
            ss.resize(WINDOW_WIDTH, WINDOW_HEIGHT);
            game.setScreen(ss);
            dispose();
        }
    }


    @Override
    public void resize(int width, int height) {
        // Actualizar el tamaño de la ventana
        currentWidth = width;
        currentHeight = height;
        camera.setToOrtho(false, width, height); // Actualizar la cámara para el nuevo tamaño de ventana
    }

    @Override
    public void show() {
        backgroundMusic.play(); // Reproducir la música cuando la pantalla se muestra
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        backgroundTexture.dispose(); // Libera la textura de fondo
        backgroundMusic.dispose(); // Libera la música de fondo
    }

    private void toggleFullscreen() {
        // Cambiar entre pantalla completa y modo ventana
        if (isFullscreen) {
            Gdx.graphics.setWindowedMode(WINDOW_WIDTH, WINDOW_HEIGHT);
        } else {
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        }
        isFullscreen = !isFullscreen; // Cambiar el estado de pantalla completa
    }
}
