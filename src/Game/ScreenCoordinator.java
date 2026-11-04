package Game;

import Engine.DefaultScreen;
import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.MusicPlayer;
import Engine.Screen;
import Engine.Keyboard;
import Engine.ScreenManager;
import Level.Player;
import Screens.CreditsScreen;
import Screens.MenuScreen;
import Screens.PlayLevelScreen;
import Screens.ControlsScreen;
import Screens.SettingsScreen;
import Maps.Introbeforemenu;
import SpriteFont.SpriteFont;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

/*
 * Based on the current game state, this class determines which Screen should be shown
 * There can only be one "currentScreen", although screens can have "nested" screens
 */
public class ScreenCoordinator extends Screen {
	// currently shown Screen
	protected Screen currentScreen = new DefaultScreen();
	private MusicPlayer menuMusic;
	private float musicVolume = 0.5f;
	private float soundEffectsVolume = 0.5f;
	private boolean soundEffectsMuted;
	private final KeyLocker muteKeyLocker = new KeyLocker();
	private final KeyLocker pauseKeyLocker = new KeyLocker();
	private boolean isPaused;
	private SpriteFont pauseLabel;
	private SpriteFont resumeInstruction;
	private SpriteFont mainMenuInstruction;

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
		Player.setSoundEffectsVolume(soundEffectsVolume);
		pauseLabel = createPauseLabel("PAUSED", 32);
		resumeInstruction = createPauseLabel("Press ESC to resume", 20);
		mainMenuInstruction = createPauseLabel("Press ENTER to return to the main menu", 20);
	}

	@Override
	public void update() {
		if (updatePauseState()) {
			return;
		}

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
						break;
					case SETTINGS:
						currentScreen = new SettingsScreen(this);
						break;
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
		if (isPaused) {
			graphicsHandler.drawFilledRectangle(
				0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(),
				new Color(0, 0, 0, 150)
			);
			drawCentered(pauseLabel, graphicsHandler, ScreenManager.getScreenHeight() / 2 - 45);
			drawCentered(resumeInstruction, graphicsHandler, ScreenManager.getScreenHeight() / 2);
			drawCentered(mainMenuInstruction, graphicsHandler, ScreenManager.getScreenHeight() / 2 + 35);
		}
	}

	private boolean updatePauseState() {
		if (Keyboard.isKeyUp(Key.ESC)) {
			pauseKeyLocker.unlockKey(Key.ESC);
		}
		if (Keyboard.isKeyUp(Key.ENTER)) {
			pauseKeyLocker.unlockKey(Key.ENTER);
		}

		if (isPaused) {
			if (Keyboard.isKeyDown(Key.ESC) && !pauseKeyLocker.isKeyLocked(Key.ESC)) {
				isPaused = false;
				pauseKeyLocker.lockKey(Key.ESC);
			} else if (Keyboard.isKeyDown(Key.ENTER) && !pauseKeyLocker.isKeyLocked(Key.ENTER)) {
				isPaused = false;
				pauseKeyLocker.lockKey(Key.ENTER);
				setGameState(GameState.MENU);
			}
			return true;
		}

		if (gameState == GameState.LEVEL
			&& currentScreen instanceof PlayLevelScreen
			&& ((PlayLevelScreen) currentScreen).isRunning()
			&& Keyboard.isKeyDown(Key.ESC)
			&& !pauseKeyLocker.isKeyLocked(Key.ESC)) {
			isPaused = true;
			pauseKeyLocker.lockKey(Key.ESC);
			return true;
		}

		return false;
	}

	private SpriteFont createPauseLabel(String text, int size) {
		SpriteFont label = new SpriteFont(text, 0, 0, "Arial", size, Color.white);
		label.setOutlineColor(Color.black);
		label.setOutlineThickness(2.0f);
		return label;
	}

	private void drawCentered(SpriteFont label, GraphicsHandler graphicsHandler, int y) {
		Font font = label.getFont();
		int textWidth = graphicsHandler.getGraphics().getFontMetrics(font).stringWidth(label.getText());
		label.setLocation((ScreenManager.getScreenWidth() - textWidth) / 2f, y);
		label.draw(graphicsHandler);
	}

	private void startMenuMusic() {
		try {
			if (menuMusic == null) {
				menuMusic = new MusicPlayer("Dark Souls II Soundtrack OST - Main Menu Theme.wav");
			}
			menuMusic.setVolume(musicVolume);
			if (!menuMusic.isPlaying()) {
				menuMusic.play(true);
			}
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
			levelMusic.setVolume(musicVolume);
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

	public float getMusicVolume() {
		return musicVolume;
	}

	public void setMusicVolume(float volume) {
		musicVolume = clampVolume(volume);
		if (menuMusic != null) {
			menuMusic.setVolume(musicVolume);
		}
		if (levelMusic != null) {
			levelMusic.setVolume(musicVolume);
		}
	}

	public float getSoundEffectsVolume() {
		return soundEffectsVolume;
	}

	public void setSoundEffectsVolume(float volume) {
		soundEffectsVolume = clampVolume(volume);
		Player.setSoundEffectsVolume(soundEffectsVolume);
	}

	private float clampVolume(float volume) {
		return Math.max(0.0f, Math.min(volume, 1.0f));
	}

}
