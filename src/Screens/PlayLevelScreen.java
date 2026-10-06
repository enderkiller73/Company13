package Screens;

import Engine.GraphicsHandler;
import Engine.Screen;
import Engine.ScreenManager;
import Game.GameState;
import Game.ScreenCoordinator;
import Level.Map;
import Level.Player;
import Level.PlayerListener;
import Maps.TestMap;
import Players.Rose;
import SpriteFont.SpriteFont;

import java.awt.Color;

// This class is for when the platformer game is actually being played
public class PlayLevelScreen extends Screen implements PlayerListener {
    protected ScreenCoordinator screenCoordinator;
    protected Map map;
    protected Player player;
    protected PlayLevelScreenState playLevelScreenState;
    protected int screenTimer;
    protected int currentLevel = 1;
    protected LevelClearedScreen levelClearedScreen;
    protected LevelLoseScreen levelLoseScreen;
    protected boolean levelCompletedStateChangeStart;
    protected SpriteFont levelIntro;
    protected int levelIntroTimer;

    public PlayLevelScreen(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    public void initialize() {
        loadLevel();
    }

    private void loadLevel() {
        map = new TestMap();
        player = new Rose(map.getPlayerStartPosition().x, map.getPlayerStartPosition().y);
        player.setMap(map);
        player.addListener(this);
        levelClearedScreen = new LevelClearedScreen();
        levelLoseScreen = new LevelLoseScreen(this);
        levelIntro = new SpriteFont("Level " + currentLevel, 0, 0, "Arial", 36, Color.white);
        levelIntroTimer = 120;
        levelCompletedStateChangeStart = false;
        playLevelScreenState = PlayLevelScreenState.LEVEL_INTRO;
    }

    public void update() {
        switch (playLevelScreenState) {
            case LEVEL_INTRO:
                levelIntroTimer--;
                if (levelIntroTimer <= 0) {
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
                        if (currentLevel == 1) {
                            currentLevel = 2;
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
            graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
            int textWidth = graphicsHandler.getGraphics().getFontMetrics(levelIntro.getFont()).stringWidth(levelIntro.getText());
            int textHeight = graphicsHandler.getGraphics().getFontMetrics(levelIntro.getFont()).getHeight();
            levelIntro.setLocation(
                (ScreenManager.getScreenWidth() - textWidth) / 2f,
                (ScreenManager.getScreenHeight() - textHeight) / 2f
            );
            levelIntro.draw(graphicsHandler);
            break;
            case RUNNING:
                map.draw(graphicsHandler);
                player.draw(graphicsHandler);
                break;
            case LEVEL_COMPLETED:
                levelClearedScreen.draw(graphicsHandler);
                break;
            case LEVEL_LOSE:
                levelLoseScreen.draw(graphicsHandler);
                break;
        }
    }

    public PlayLevelScreenState getPlayLevelScreenState() {
        return playLevelScreenState;
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
