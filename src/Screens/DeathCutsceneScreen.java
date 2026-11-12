package Screens;

import Engine.Config;
import Engine.GraphicsHandler;
import Engine.Screen;
import Engine.ScreenManager;
import Engine.SoundEffectPlayer;
import Game.ScreenCoordinator;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// An unskippable death outcome: show the image until the full sound has finished.
public class DeathCutsceneScreen extends Screen implements AutoCloseable {
    private static final String AUDIO_FILE = "iShowSpeed _My Mom Is Kinda Homeless_ Full Clip 😂.wav";
    private final ScreenCoordinator screenCoordinator;
    private final Runnable returnToMenu;
    private BufferedImage image;
    private SoundEffectPlayer sound;
    private long startedAtNanos;
    private long durationNanos = 3_000_000_000L;
    private boolean finished;

    public DeathCutsceneScreen(ScreenCoordinator screenCoordinator, Runnable returnToMenu) {
        this.screenCoordinator = screenCoordinator;
        this.returnToMenu = returnToMenu;
    }

    @Override
    public void initialize() {
        screenCoordinator.stopLevelMusic();
        try {
            image = ImageIO.read(new File(Config.RESOURCES_PATH, "MyMommasKindaHomeless.png"));
        } catch (IOException e) {
            System.err.println("Unable to load death cutscene image: " + e.getMessage());
        }
        try {
            sound = new SoundEffectPlayer(AUDIO_FILE);
            durationNanos = sound.getDurationMicroseconds() * 1_000L;
            updateVolume();
            sound.play();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.err.println("Unable to play death cutscene audio: " + e.getMessage());
        }
        startedAtNanos = System.nanoTime();
    }

    @Override
    public void update() {
        if (finished) {
            return;
        }
        updateVolume();
        long elapsedNanos = System.nanoTime() - startedAtNanos;
        // Muting affects volume only, so it cannot skip the cutscene.
        // The grace period also avoids trapping the player if an audio device stalls.
        if (elapsedNanos >= durationNanos
                && (sound == null || !sound.isPlaying() || elapsedNanos >= durationNanos + 2_000_000_000L)) {
            finished = true;
            close();
            returnToMenu.run();
        }
    }

    private void updateVolume() {
        if (sound != null) {
            sound.setVolume(screenCoordinator.isSoundEffectsMuted() ? 0f : screenCoordinator.getSoundEffectsVolume());
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        int screenWidth = ScreenManager.getScreenWidth();
        int screenHeight = ScreenManager.getScreenHeight();
        graphicsHandler.drawFilledRectangle(0, 0, screenWidth, screenHeight, Color.BLACK);
        if (image != null) {
            double scale = Math.min(screenWidth / (double) image.getWidth(), screenHeight / (double) image.getHeight());
            int width = (int) Math.round(image.getWidth() * scale);
            int height = (int) Math.round(image.getHeight() * scale);
            graphicsHandler.drawImage(image, (screenWidth - width) / 2, (screenHeight - height) / 2, width, height);
        }
    }

    @Override
    public void close() {
        if (sound != null) {
            sound.close();
            sound = null;
        }
    }
}
