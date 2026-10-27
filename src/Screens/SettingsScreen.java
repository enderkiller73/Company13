package Screens;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import Engine.Screen;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Maps.TitleScreenMap;

import java.awt.Color;

public class SettingsScreen extends Screen {
    private static final int SLIDER_X = 270;
    private static final int SLIDER_WIDTH = 250;
    private static final int SLIDER_HEIGHT = 18;
    private static final float VOLUME_STEP = 0.05f;

    private final ScreenCoordinator screenCoordinator;
    private final KeyLocker keyLocker = new KeyLocker();
    private Map background;
    private int selectedSlider;
    private int keyPressTimer;

    public SettingsScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        background = new TitleScreenMap();
        background.setAdjustCamera(false);
        selectedSlider = 0;
        keyPressTimer = 0;
        keyLocker.lockKey(Key.SPACE);
        keyLocker.lockKey(Key.ESC);
    }

    @Override
    public void update() {
        background.update(null);

        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (Keyboard.isKeyUp(Key.ESC)) {
            keyLocker.unlockKey(Key.ESC);
        }
        if ((!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE))
            || (!keyLocker.isKeyLocked(Key.ESC) && Keyboard.isKeyDown(Key.ESC))) {
            screenCoordinator.setGameState(GameState.MENU);
            return;
        }

        if (keyPressTimer > 0) {
            keyPressTimer--;
            return;
        }

        if (Keyboard.isKeyDown(Key.W) || Keyboard.isKeyDown(Key.UP)) {
            selectedSlider = Math.max(0, selectedSlider - 1);
            keyPressTimer = 8;
        } else if (Keyboard.isKeyDown(Key.S) || Keyboard.isKeyDown(Key.DOWN)) {
            selectedSlider = Math.min(1, selectedSlider + 1);
            keyPressTimer = 8;
        } else if (Keyboard.isKeyDown(Key.A) || Keyboard.isKeyDown(Key.LEFT)) {
            adjustSelectedVolume(-VOLUME_STEP);
            keyPressTimer = 4;
        } else if (Keyboard.isKeyDown(Key.D) || Keyboard.isKeyDown(Key.RIGHT)) {
            adjustSelectedVolume(VOLUME_STEP);
            keyPressTimer = 4;
        }
    }

    private void adjustSelectedVolume(float amount) {
        if (selectedSlider == 0) {
            screenCoordinator.setMusicVolume(screenCoordinator.getMusicVolume() + amount);
        } else {
            screenCoordinator.setSoundEffectsVolume(screenCoordinator.getSoundEffectsVolume() + amount);
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        background.draw(graphicsHandler);
        drawText(graphicsHandler, "VOLUME SETTINGS", 40, 40, 48, new Color(140, 24, 150));
        drawText(graphicsHandler, "MUSIC", 100, 210, 28, Color.white);
        drawSlider(graphicsHandler, 245, screenCoordinator.getMusicVolume(), selectedSlider == 0);
        drawText(graphicsHandler, "SOUND EFFECTS", 100, 320, 28, Color.white);
        drawSlider(graphicsHandler, 355, screenCoordinator.getSoundEffectsVolume(), selectedSlider == 1);
        drawText(graphicsHandler, "W/S: SELECT    A/D: ADJUST", 100, 455, 20, Color.white);
        drawText(graphicsHandler, "SPACE OR ESC: RETURN TO MENU", 100, 510, 20, Color.white);
    }

    private void drawSlider(GraphicsHandler graphicsHandler, int y, float volume, boolean selected) {
        Color borderColor = selected ? Color.white : Color.darkGray;
        graphicsHandler.drawFilledRectangle(SLIDER_X, y, SLIDER_WIDTH, SLIDER_HEIGHT, Color.darkGray);
        int filledWidth = Math.round((SLIDER_WIDTH - 2) * volume);
        graphicsHandler.drawFilledRectangle(SLIDER_X + 1, y + 1, filledWidth, SLIDER_HEIGHT - 2, new Color(200, 0, 100));
        graphicsHandler.drawRectangle(SLIDER_X, y, SLIDER_WIDTH, SLIDER_HEIGHT, borderColor, selected ? 3 : 1);
        graphicsHandler.drawString(Math.round(volume * 100) + "%", SLIDER_X + SLIDER_WIDTH + 15, y + SLIDER_HEIGHT,
            new java.awt.Font("Georgia", java.awt.Font.BOLD, 18), Color.white);
    }

    private void drawText(GraphicsHandler graphicsHandler, String text, int x, int y, int size, Color color) {
        graphicsHandler.drawString(text, x, y, new java.awt.Font("Georgia", java.awt.Font.BOLD, size), color);
    }
}
