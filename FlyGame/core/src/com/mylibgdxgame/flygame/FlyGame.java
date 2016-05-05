package com.mylibgdxgame.flygame;

import com.badlogic.gdx.Game;
import com.mylibgdxgame.flygame.loader.ResourseLoader;
import com.mylibgdxgame.flygame.screens.SplashScreen;

public class FlyGame extends Game {

	
	@Override
	public void create () {
		ResourseLoader.load();
		setScreen(new SplashScreen(this));

	}

	@Override
	public void dispose() {
		super.dispose();
		ResourseLoader.dispose();
	}
}
