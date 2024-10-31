package io.eva_01;

import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class MainEva extends Game {
    @Override
    public void create() {
        setScreen(new FirstScreen());
    }
}