package Screens;

import Engine.GraphicsHandler;
import Engine.Screen;
import Engine.ScreenManager;
import Game.Game;
import Game.GameState;
import Game.ScreenCoordinator;
import GameObject.GameObject;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.*;
//import Players.Cat;
import Players.Rose;

import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.Graphics2D;

// This class is for when the platformer game is actually being played
public class PlayLevelScreen extends Screen implements PlayerListener {
    private static final int SPAWN_INTRO_FRAMES = 150;
    private static final int SPOTLIGHT_HOLD_FRAMES = 45;
    private static final float SPAWN_START_ZOOM = 2.6f;

    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected int screenTimer;
    protected int currentLevel = 1;
    protected LevelClearedScreen levelClearedScreen;
    protected LevelLoseScreen levelLoseScreen;
    protected boolean levelCompletedStateChangeStart;
    protected int spawnIntroTimer;
    protected GameObject gameObject;
    protected float scaleX = 1f;
    protected float scaleY = 1f;
    // protected float changeScaleX = 0.01f;
    // protected float changeScaleY = 0.01f;
    // protected float prevY;

    public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public void initialize() {
        if (gameObject != null) {
            gameObject.setScaleX(scaleX);
            gameObject.setScaleY(scaleY);
        }
        // gameObject.getCalibratedYLocation();

        loadLevel();
    }

    private void loadLevel() {
        map = createLevelMap();
        // if (gameObject.compareYChange() > 0) {
        //     scaleX = scaleX + changeScaleX;
        //     scaleY = scaleY + changeScaleY;
        // } else if (gameObject.compareYChange() < 0) {
        //     scaleX = scaleX - changeScaleX;
        //     scaleY = scaleY - changeScaleY;
        // } else {
        //     while (scaleX != 1 && scaleY != 1) {
        //         if (gameObject.compareYChange() > 0) {
        //             scaleX -= changeScaleX;
        //             scaleY -= changeScaleY;
        //         }
        //         if (gameObject.compareYChange() < 0) {
        //             scaleX += changeScaleX;
        //             scaleY += changeScaleY;
        //         }
        //     }
        // }

        player = new Rose (map.getPlayerStartPosition().x, map.getPlayerStartPosition().y, scaleX, scaleY);
        player.setMap(map);
        player.addListener(this);
        map.update(player);
        levelClearedScreen = new LevelClearedScreen();
        levelLoseScreen = new LevelLoseScreen(this);
        spawnIntroTimer = 0;
        levelCompletedStateChangeStart = false;
        playLevelScreenState = PlayLevelScreenState.LEVEL_INTRO;
    }

    private Map createLevelMap() {
        switch (currentLevel) {
            case 1:
                return new TutorialMap();
            case 2:
                return new LevelTwo();
            case 3:
                return new Level_Three();
            case 4:
                return new Level_Four();
            // case 5:
            //     return new Level_Five();
            // case 6:
            //     return new Level_Six();
            // case 7:
            //     return new Level_Seven();
            // case 8:
            //     return new Level_Eight();
            // case 9:
            //     return new Level_Nine();
            default:
                throw new IllegalStateException("No map configured for level " + currentLevel);
        }
    }

    public void update() {
        switch (playLevelScreenState) {
            case LEVEL_INTRO:
                spawnIntroTimer++;
                if (spawnIntroTimer >= SPAWN_INTRO_FRAMES) {
                    playLevelScreenState = PlayLevelScreenState.RUNNING;
                }
                break;
            case RUNNING:
                player.update();
                map.update(player);
                break;
            case LEVEL_COMPLETED:
                if (levelCompletedStateChangeStart) {
                    screenTimer = 130;
                    levelCompletedStateChangeStart = false;
                } else {
                    levelClearedScreen.update();
                    screenTimer--;
                    if (screenTimer == 0) {
                        if (currentLevel < 4) {
                            currentLevel++;
                            loadLevel();
                        } else {
                            goBackToMenu();
                        }
                    }
                }
                break;
            case LEVEL_LOSE:
                levelLoseScreen.update();
                break;
        }
    }

    public void draw(GraphicsHandler graphicsHandler) {
        // based on screen state, draw appropriate graphics
        switch (playLevelScreenState) {
            case LEVEL_INTRO:
                drawSpawnIntro(graphicsHandler);
                break;
            case RUNNING:
                graphicsHandler.beginZoom(map.getZoom(), ScreenManager.getScreenWidth() / 2f, ScreenManager.getScreenHeight() / 2f);
                map.draw(graphicsHandler);
                player.draw(graphicsHandler);
                graphicsHandler.endZoom();
                break;
            case LEVEL_COMPLETED:
                levelClearedScreen.draw(graphicsHandler);
                break;
            case LEVEL_LOSE:
                levelLoseScreen.draw(graphicsHandler);
                break;
        }
    }

    private void drawSpawnIntro(GraphicsHandler graphicsHandler) {
        float progress = Math.max(0f, Math.min(1f,
            (spawnIntroTimer - SPOTLIGHT_HOLD_FRAMES) / (float) (SPAWN_INTRO_FRAMES - SPOTLIGHT_HOLD_FRAMES)));
        progress = progress * progress * (3f - 2f * progress);

        float focusX = player.getCalibratedXLocation() + player.getWidth() / 2f;
        float focusY = player.getCalibratedYLocation() + player.getHeight() / 2f;
        float zoom = map.getZoom() * (SPAWN_START_ZOOM - (SPAWN_START_ZOOM - 1f) * progress);

        graphicsHandler.beginZoom(zoom, focusX, focusY);
        map.draw(graphicsHandler);
        player.draw(graphicsHandler);
        graphicsHandler.endZoom();

        int screenWidth = ScreenManager.getScreenWidth();
        int screenHeight = ScreenManager.getScreenHeight();
        float startRadius = Math.max(player.getWidth(), player.getHeight()) * 2.5f;
        float screenRadius = (float) Math.hypot(screenWidth, screenHeight);
        float radius = startRadius + (screenRadius - startRadius) * progress;
        int darkness = Math.round(220f * (1f - progress));

        Graphics2D overlay = (Graphics2D) graphicsHandler.getGraphics().create();
        overlay.setPaint(new RadialGradientPaint(
            new Point2D.Float(focusX, focusY),
            Math.max(radius, 1f),
            new float[] { 0f, 0.35f, 1f },
            new Color[] {
                new Color(0, 0, 0, 0),
                new Color(0, 0, 0, Math.round(darkness * 0.35f)),
                new Color(0, 0, 0, darkness)
            }
        ));
        overlay.fillRect(0, 0, screenWidth, screenHeight);
        overlay.dispose();
    }

    public PlayLevelScreenState getPlayLevelScreenState() {
        return playLevelScreenState;
    }

    public boolean isRunning() {
        return playLevelScreenState == PlayLevelScreenState.RUNNING;
    }

    @Override
    public void onLevelCompleted() {
        if (playLevelScreenState != PlayLevelScreenState.LEVEL_COMPLETED) {
            playLevelScreenState = PlayLevelScreenState.LEVEL_COMPLETED;
            levelCompletedStateChangeStart = true;
        }
    }

    public void onDeath() {
        if (playLevelScreenState != PlayLevelScreenState.LEVEL_LOSE) {
            playLevelScreenState = PlayLevelScreenState.LEVEL_LOSE;
        }
    }

    public void resetLevel() {
        loadLevel();
    }

    public void goBackToMenu() {
        screenCoordinator.setGameState(GameState.MENU);
    }

    // This enum represents the different states this screen can be in
    private enum PlayLevelScreenState {
        LEVEL_INTRO, RUNNING, LEVEL_COMPLETED, LEVEL_LOSE
    }
}
