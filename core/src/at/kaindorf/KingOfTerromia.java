package at.kaindorf;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
/*
	Klasse als Singleton implementiert.
*/
public class KingOfTerromia extends Game {

	public static KingOfTerromia INSTANCE;

	public KingOfTerromia() {
		INSTANCE = this;
	}

	@Override
	public void create () {
		setScreen(new MenuScreen());
	}
}
