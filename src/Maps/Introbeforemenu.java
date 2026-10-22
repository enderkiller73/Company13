package Maps;

import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.MusicPlayer;
import Engine.Screen;
import Engine.ScreenManager;
import Engine.Keyboard;
import Game.GameState;
import Game.ScreenCoordinator;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;

public class Introbeforemenu extends Screen {
    private static final String[] LOGO = {
        "  ____                                          _ _____ ",
        " / ___|___  _ __ ___  _ __   __ _ _ __  _   _  / |___ / ",
        "| |   / _ \\| '_ ` _ \\| '_ \\ / _` | '_ \\| | | | | | |_ \\ ",
        "| |__| (_) | | | | | | |_) | (_| | | | | |_| | | |___) |",
        " \\____\\___/|_| |_| |_| .__/ \\__,_|_| |_|\\__, | |_|____/ ",
        "|  _ \\ _ __ ___  ___ |_|_ _ __ | |_ ___ |___/           ",
        "| |_) | '__/ _ \\/ __|/ _ \\ '_ \\| __/ __|                ",
        "|  __/| | |  __/\\__ \\  __/ | | | |_\\__ \\                ",
        "|_|   |_|  \\___||___/\\___|_| |_|\\__|___/                "
    };

    private static final String INTRO_AUDIO = "2012 New Valve Intro (PORTAL 2) (720p HD).wav";

    private final ScreenCoordinator screenCoordinator;
    private final KeyLocker keyLocker = new KeyLocker();
    private MusicPlayer introMusic;
    private boolean playbackObserved;

    public Introbeforemenu(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        keyLocker.lockKey(Key.SPACE);
        try {
            introMusic = new MusicPlayer(INTRO_AUDIO);
            introMusic.setVolume(screenCoordinator.isMusicMuted() ? 0 : 1);
            introMusic.play();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.err.println("Unable to play startup intro audio.");
            e.printStackTrace(System.err);
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    @Override
    public void update() {
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        } else if (!keyLocker.isKeyLocked(Key.SPACE)) {
            screenCoordinator.setGameState(GameState.MENU);
            return;
        }

        if (introMusic == null) {
            return;
        }

        if (introMusic.isPlaying()) {
            playbackObserved = true;
        } else if (playbackObserved) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(
            0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.BLACK
        );

        Font logoFont = new Font(Font.MONOSPACED, Font.BOLD, 13);
        java.awt.FontMetrics fontMetrics = graphicsHandler.getGraphics().getFontMetrics(logoFont);
        int lineHeight = fontMetrics.getHeight();
        int totalHeight = LOGO.length * lineHeight;
        int startY = (ScreenManager.getScreenHeight() - totalHeight) / 2;
        int maxLineWidth = 0;
        for (String line : LOGO) {
            maxLineWidth = Math.max(maxLineWidth, fontMetrics.stringWidth(line));
        }
        int startX = (ScreenManager.getScreenWidth() - maxLineWidth) / 2;

        for (int i = 0; i < LOGO.length; i++) {
            graphicsHandler.drawString(LOGO[i], startX, startY + (i + 1) * lineHeight, logoFont, Color.WHITE);
        }
    }

    public void setMuted(boolean muted) {
        if (introMusic != null) {
            introMusic.setVolume(muted ? 0 : 1);
        }
    }

    public void close() {
        if (introMusic != null) {
            introMusic.stop();
            introMusic.close();
            introMusic = null;
        }
    }
}
