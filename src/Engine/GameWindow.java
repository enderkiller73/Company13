package Engine;

import javax.swing.*;
import java.awt.*;

/*
 * The JFrame that holds the GamePanel
 * Just does some setup and exposes the gamePanel's screenManager to allow an external class to setup their own content and attach it to this engine.
 */
public class GameWindow {
	private JFrame gameWindow;
	private GamePanel gamePanel;
	private GraphicsDevice graphicsDevice;
	private Rectangle windowedBounds;
	private boolean fullscreen;

	public GameWindow() {
		gameWindow = new JFrame("Game");
		graphicsDevice = gameWindow.getGraphicsConfiguration().getDevice();
		gamePanel = new GamePanel(this);
		gamePanel.setFocusable(true);
		gamePanel.requestFocusInWindow();
		gameWindow.setContentPane(gamePanel);
		gameWindow.setResizable(false);
		gameWindow.pack();
		gameWindow.setLocationRelativeTo(null);
		windowedBounds = gameWindow.getBounds();
		gameWindow.setVisible(true);
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // it'd be nice if this actually worked more than 1/3rd of the time
		gamePanel.setupGame();
	}

	public void toggleFullscreen() {
		if (fullscreen) {
			graphicsDevice.setFullScreenWindow(null);
			gameWindow.dispose();
			gameWindow.setUndecorated(false);
			gameWindow.setBounds(windowedBounds);
			gameWindow.setVisible(true);
		} else {
			windowedBounds = gameWindow.getBounds();
			gameWindow.dispose();
			gameWindow.setUndecorated(true);
			graphicsDevice.setFullScreenWindow(gameWindow);
			gameWindow.setVisible(true);
		}
		fullscreen = !fullscreen;
		gamePanel.requestFocusInWindow();
	}

	// triggers the game loop to start as defined in the GamePanel class
	public void startGame() {
		gamePanel.startGame();
	}

	public ScreenManager getScreenManager() {
		return gamePanel.getScreenManager();
	}
}
