package Game;

import Engine.DefaultScreen;
import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.MusicPlayer;
import Engine.Screen;
import Engine.Keyboard;
import Level.Player;
import Screens.CreditsScreen;
import Screens.MenuScreen;
import Screens.PlayLevelScreen;
import Screens.ControlsScreen;
import Maps.Introbeforemenu;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/*
 * Based on the current game state, this class determines which Screen should be shown
 * There can only be one "currentScreen", although screens can have "nested" screens
 */
public class ScreenCoordinator extends Screen {
	// currently shown Screen
	protected Screen currentScreen = new DefaultScreen();
	private MusicPlayer menuMusic;
	private boolean soundEffectsMuted;
	private final KeyLocker muteKeyLocker = new KeyLocker();

	// keep track of gameState so ScreenCoordinator knows which Screen to show
	protected GameState gameState;
	protected GameState previousGameState;

	private MusicPlayer levelMusic;
	private Introbeforemenu introScreen;

	public GameState getGameState() {
		return gameState;
	}

	// Other Screens can set the gameState of this class to force it to change the currentScreen
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

	@Override
	public void initialize() {
		// play the startup intro before showing the menu
		gameState = GameState.INTRO;
		muteKeyLocker.lockKey(Key.M);
	}

	@Override
	public void update() {
		if (Keyboard.isKeyUp(Key.M)) {
			muteKeyLocker.unlockKey(Key.M);
		} else if (!muteKeyLocker.isKeyLocked(Key.M)) {
			soundEffectsMuted = !soundEffectsMuted;
			muteKeyLocker.lockKey(Key.M);
			Player.setSoundEffectsMuted(soundEffectsMuted);
		}

		do {
			// if previousGameState does not equal gameState, it means there was a change in gameState
			// this triggers ScreenCoordinator to bring up a new Screen based on what the gameState is
			if (previousGameState != gameState) {
				switch(gameState) {
					case INTRO:
						introScreen = new Introbeforemenu(this);
						currentScreen = introScreen;
						break;
					case MENU:
						if (introScreen != null) {
							introScreen.close();
							introScreen = null;
						}
						stopLevelMusic();
						startMenuMusic();
						currentScreen = new MenuScreen(this);
						break;
					case LEVEL:
						if (menuMusic != null) {
							menuMusic.stop();
						}
						startLevelMusic();
						currentScreen = new PlayLevelScreen(this);
						break;
					case CREDITS:
						currentScreen = new CreditsScreen(this);
						break;
					case CONTROLS:
						currentScreen = new ControlsScreen(this);
				}
				currentScreen.initialize();
			}
			previousGameState = gameState;

			// call the update method for the currentScreen
			currentScreen.update();
		} while (previousGameState != gameState);
	}

	@Override
	public void draw(GraphicsHandler graphicsHandler) {
		// call the draw method for the currentScreen
		currentScreen.draw(graphicsHandler);
	}

	private void startMenuMusic() {
		try {
			if (menuMusic == null) {
				menuMusic = new MusicPlayer("Start Menu 0.wav");
			}
			menuMusic.play(true);
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			System.err.println("Unable to play menu music.");
			e.printStackTrace(System.err);
		}
	}

	private void startLevelMusic() {
		try {
			if (levelMusic == null) {
				levelMusic = new MusicPlayer("Castlevania_ Symphony of the Night OST - Abandoned Pit.wav");
			}
			levelMusic.play(true);
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			System.err.println("Unable to play level music.");
			e.printStackTrace(System.err);
		}
	}

	private void stopLevelMusic() {
		if (levelMusic != null) {
			levelMusic.stop();
		}
	}

}
