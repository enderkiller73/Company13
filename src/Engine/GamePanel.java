package Engine;

import GameObject.Rectangle;
import SpriteFont.SpriteFont;
import javax.swing.*;
import java.awt.*;

/*
 * This is where the game loop process and render back buffer is setup
 */
public class GamePanel extends JPanel {
	private final GameWindow gameWindow;
	// loads Screens on to the JPanel
	// each screen has its own update and draw methods defined to handle a "section" of the game.
	private ScreenManager screenManager;

	// used to draw graphics to the panel
	private GraphicsHandler graphicsHandler;

	private KeyLocker keyLocker = new KeyLocker();
	private final Key fullscreenKey = Key.EQUALS;
	private Thread gameLoopProcess;

	private Key showFPSKey = Key.G;
	private SpriteFont fpsDisplayLabel;
	private boolean showFPS = false;
	private int currentFPS;
	private boolean doPaint;

	// The JPanel and various important class instances are setup here
	public GamePanel(GameWindow gameWindow) {
		super();
		this.gameWindow = gameWindow;
		this.setDoubleBuffered(true);
		this.setPreferredSize(new Dimension(Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT));

		// attaches Keyboard class's keyListener to this JPanel
		this.addKeyListener(Keyboard.getKeyListener());

		graphicsHandler = new GraphicsHandler();

		screenManager = new ScreenManager();

		fpsDisplayLabel = new SpriteFont("FPS", 4, 3, "Arial", 12, Color.black);

		currentFPS = Config.TARGET_FPS;

		// this game loop code will run in a separate thread from the rest of the program
		// will continually update the game's logic and repaint the game's graphics
		GameLoop gameLoop = new GameLoop(this);
		gameLoopProcess = new Thread(gameLoop.getGameLoopProcess());
	}

	// this is called later after instantiation, and will initialize screenManager
	// this had to be done outside of the constructor because it needed to know the JPanel's width and height, which aren't available in the constructor
	public void setupGame() {
		setBackground(Color.BLACK);
		screenManager.initialize(new Rectangle(0, 0, Config.GAME_WINDOW_WIDTH, Config.GAME_WINDOW_HEIGHT));
	}

	// this starts the timer (the game loop is started here)
	public void startGame() {
		gameLoopProcess.start();
	}

	public ScreenManager getScreenManager() {
		return screenManager;
	}

	public void setCurrentFPS(int currentFPS) {
		this.currentFPS = currentFPS;
	}

	public void setDoPaint(boolean doPaint) {
		this.doPaint = doPaint;
	}

	public void update() {
		updateFullscreenState();
		updateShowFPSState();
		screenManager.update();
	}

	private void updateFullscreenState() {
		if (Keyboard.isKeyDown(fullscreenKey) && !keyLocker.isKeyLocked(fullscreenKey)) {
			keyLocker.lockKey(fullscreenKey);
			SwingUtilities.invokeLater(gameWindow::toggleFullscreen);
		}

		if (Keyboard.isKeyUp(fullscreenKey)) {
			keyLocker.unlockKey(fullscreenKey);
		}
	}

	private void updateShowFPSState() {
		if (Keyboard.isKeyDown(showFPSKey) && !keyLocker.isKeyLocked(showFPSKey)) {
			showFPS = !showFPS;
			keyLocker.lockKey(showFPSKey);
		}

		if (Keyboard.isKeyUp(showFPSKey)) {
			keyLocker.unlockKey(showFPSKey);
		}

		fpsDisplayLabel.setText("FPS: " + currentFPS);
	}

	public void draw() {
		screenManager.draw(graphicsHandler);

		if (showFPS) {
			fpsDisplayLabel.draw(graphicsHandler);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (doPaint) {
			// every repaint call will schedule this method to be called
			// when called, it will setup the graphics handler and then call this class's draw method
			Graphics2D viewportGraphics = (Graphics2D) g.create();
			double scale = Math.min(
				(double) getWidth() / Config.GAME_WINDOW_WIDTH,
				(double) getHeight() / Config.GAME_WINDOW_HEIGHT
			);
			int offsetX = (int) ((getWidth() - Config.GAME_WINDOW_WIDTH * scale) / 2);
			int offsetY = (int) ((getHeight() - Config.GAME_WINDOW_HEIGHT * scale) / 2);
			viewportGraphics.translate(offsetX, offsetY);
			viewportGraphics.scale(scale, scale);
			graphicsHandler.setGraphics(viewportGraphics);
			draw();
			viewportGraphics.dispose();
		}
	}
}
