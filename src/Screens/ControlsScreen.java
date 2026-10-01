package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;

import java.awt.*;

// This class is for the credits screen
public class ControlsScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected Map background;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont controlsLabel;
    protected SpriteFont baseControlsLabel;
    protected SpriteFont advancedControlsLabel;
    protected SpriteFont returnInstructionsLabel;

    public ControlsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        // setup graphics on screen (background map, spritefont text)
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        controlsLabel = new SpriteFont("Controls", 35, 10, "Georgia", 50, Color.black);
        controlsLabel.setOutlineColor(Color.gray);
        controlsLabel.setOutlineThickness(3);
        baseControlsLabel = new SpriteFont("Basic Movement: A = Left, W = Jump, D = Right, S = Crouch", 130, 121, "Georgia", 20, Color.red);
        baseControlsLabel.setOutlineColor(Color.pink);
        baseControlsLabel.setOutlineThickness(3);
        advancedControlsLabel = new SpriteFont("Advanced Movement: J = Dash, K = Place Platform, L = Float", 130, 151, "Georgia", 20, Color.pink);
        advancedControlsLabel.setOutlineColor(Color.white);
        advancedControlsLabel.setOutlineThickness(3);
        returnInstructionsLabel = new SpriteFont("Press Space to return to the menu", 20, 532, "Times New Roman", 30, Color.white);
        returnInstructionsLabel.setOutlineColor(Color.black);
        returnInstructionsLabel.setOutlineThickness(3);
        keyLocker.lockKey(Key.SPACE);
    }

    public void update() {
        background.update(null);

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }

        // if space is pressed, go back to main menu
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        controlsLabel.draw(graphicsHandler);
        baseControlsLabel.draw(graphicsHandler);
        advancedControlsLabel.draw(graphicsHandler);
        returnInstructionsLabel.draw(graphicsHandler);
    }
}