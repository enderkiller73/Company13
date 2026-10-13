package Screens;

import Engine.*;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;
import SpriteFont.SpriteFont;

import java.awt.*;
import java.io.File;
import java.io.IOException;

// This is the class for the main menu screen
public class MenuScreen extends Screen {
    protected ScreenCoordinator screenCoordinator;
    protected int currentMenuItemHovered = 0; // current menu item being "hovered" over
    protected int menuItemSelected = -1;
    protected SpriteFont playGame;
    protected SpriteFont credits;
    protected Map background;
    protected int keyPressTimer;
    protected int pointerLocationX, pointerLocationY;
    protected KeyLocker keyLocker = new KeyLocker();
    protected SpriteFont title;
    protected SpriteFont controls;

    public MenuScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        playGame = new SpriteFont("PLAY GAME", 100, 190, "Georgia", 30, new Color(200, 0, 100));
        playGame.setOutlineColor(Color.black);
        playGame.setOutlineThickness(3);
        controls = new SpriteFont("CREDITS", 100,240,"Georgia",30, new Color(220,20,100));
        controls.setOutlineColor(Color.black);
        controls.setOutlineThickness(3);
        
        credits = new SpriteFont("CONTROLS", 100, 290, "Georgia", 30, new Color(240, 40, 100));
        credits.setOutlineColor(Color.black);
        credits.setOutlineThickness(3);
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        keyPressTimer = 0;
        menuItemSelected = -1;
        keyLocker.lockKey(Key.SPACE);

        Font customFont = SpriteFont.loadCustomFont(75f);

        title = new SpriteFont("Grim Rose", 50, 50, customFont, new Color(140, 24, 150));
        title.setOutlineColor(Color.black);
        title.setOutlineThickness(3);
        //272 - 208
    }

    public void update() {
        // update background map (to play tile animations)
        background.update(null);

        // if down or up is pressed, change menu item "hovered" over (blue square in front of text will move along with currentMenuItemHovered changing)
        if (Keyboard.isKeyDown(Key.S) &&  keyPressTimer == 0) {
            keyPressTimer = 14;
            currentMenuItemHovered++;
        } else if (Keyboard.isKeyDown(Key.W) &&  keyPressTimer == 0) {
            keyPressTimer = 14;
            currentMenuItemHovered--;
        } else {
            if (keyPressTimer > 0) {
                keyPressTimer--;
            }
        }

        // if down is pressed on last menu item or up is pressed on first menu item, "loop" the selection back around to the beginning/end
        if (currentMenuItemHovered > 2) {
            currentMenuItemHovered = 0;
        } else if (currentMenuItemHovered < 0) {
            currentMenuItemHovered = 2;
        }

        // sets location for blue square in front of text (pointerLocation) and also sets color of spritefont text based on which menu item is being hovered
        if (currentMenuItemHovered == 0) {
            playGame.setColor(new Color(100, 0, 100));
            playGame.setOutlineColor(Color.lightGray);
            controls.setColor(new Color (220,20,100));
            controls.setOutlineColor(Color.black);
            credits.setColor(new Color(240,40, 100));
            credits.setOutlineColor(Color.black);
            pointerLocationX = 70;
            pointerLocationY = 200;
        } else if (currentMenuItemHovered == 1) {
            playGame.setColor(new Color(200, 0, 100));
            playGame.setOutlineColor(Color.black);
            controls.setColor(new Color (120,20,100));
            controls.setOutlineColor(Color.lightGray);
            credits.setColor(new Color(240, 40, 100));
            credits.setOutlineColor(Color.black);
            pointerLocationX = 70;
            pointerLocationY = 250;
        } else if (currentMenuItemHovered == 2) {
            playGame.setColor(new Color(200, 0, 100));
            playGame.setOutlineColor(Color.black);
            controls.setColor(new Color (220,20,100));
            controls.setOutlineColor(Color.black);
            credits.setColor(new Color(140, 40, 100));
            credits.setOutlineColor(Color.lightGray);
            pointerLocationX = 70;
            pointerLocationY = 300;
        }

        // if space is pressed on menu item, change to appropriate screen based on which menu item was chosen
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE)) {
            menuItemSelected = currentMenuItemHovered;
            if (menuItemSelected == 0) {
                screenCoordinator.setGameState(GameState.LEVEL);
            } else if (menuItemSelected == 1) {
                screenCoordinator.setGameState(GameState.CREDITS);
            }
            else if (menuItemSelected == 2) {
                screenCoordinator.setGameState(GameState.CONTROLS);
            }
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        title.draw(graphicsHandler);
        playGame.draw(graphicsHandler);
        credits.draw(graphicsHandler);
        controls.draw(graphicsHandler);
        graphicsHandler.drawFilledRectangleWithBorder(pointerLocationX, pointerLocationY, 20, 20, new Color(255, 255, 255), Color.black, 2);
    }

}
