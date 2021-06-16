package at.kaindorf;

import com.badlogic.gdx.Game;
/*
	Klasse als Singleton implementiert.
*/
/**
 * @author: Daniel Remetan
 * @date: 24.03.2021
 * @project: KingOfTerromia
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
