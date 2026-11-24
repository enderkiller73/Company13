package Screens;

import Engine.Config;
import Engine.GraphicsHandler;
import Engine.Key;
import Engine.KeyLocker;
import Engine.Keyboard;
import Engine.Screen;
import Engine.ScreenManager;
import Engine.SoundEffectPlayer;
import Game.ScreenCoordinator;
import Utils.SpeedrunRank;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// End-of-run screen showing the player's final speedrun time and rank.
public class RankScreen extends Screen implements AutoCloseable {
    private static final Color BACKDROP_COLOR = new Color(10, 8, 18);
    private static final Color CARD_COLOR = new Color(22, 18, 34);
    private static final Color HEADER_COLOR = new Color(245, 233, 213);
    private static final Color MUTED_TEXT_COLOR = new Color(190, 190, 208);
    private static final Color TIER_FILL_DEFAULT = new Color(30, 26, 46);
    private static final Color TIER_BORDER_DEFAULT = new Color(75, 68, 102);

    private static final Font HEADER_FONT = new Font("Georgia", Font.BOLD, 34);
    private static final Font SUBHEADER_FONT = new Font("Georgia", Font.BOLD, 16);
    private static final Font RANK_LETTER_FONT = new Font("Georgia", Font.BOLD, 112);
    private static final Font RANK_TITLE_FONT = new Font("Georgia", Font.BOLD, 22);
    private static final Font TIME_FONT = new Font(Font.MONOSPACED, Font.BOLD, 26);
    private static final Font TIER_LETTER_FONT = new Font("Georgia", Font.BOLD, 20);
    private static final Font TIER_TIME_FONT = new Font(Font.MONOSPACED, Font.PLAIN, 14);
    private static final Font PROMPT_FONT = new Font("Georgia", Font.PLAIN, 18);

    private static final int RANK_IMAGE_MAX_HEIGHT = 128;

    private final ScreenCoordinator screenCoordinator;
    private final Runnable returnToMenu;
    private final SpeedrunRank rank;
    private final String formattedTime;
    private final KeyLocker keyLocker = new KeyLocker();
    private BufferedImage rankImage;
    private SoundEffectPlayer rankSound;
    private boolean returned;

    public RankScreen(Runnable returnToMenu, long elapsedMillis, String formattedTime) {
        this(null, returnToMenu, elapsedMillis, formattedTime);
    }

    public RankScreen(ScreenCoordinator screenCoordinator, Runnable returnToMenu, long elapsedMillis, String formattedTime) {
        this.screenCoordinator = screenCoordinator;
        this.returnToMenu = returnToMenu;
        this.rank = SpeedrunRank.fromElapsedMillis(elapsedMillis);
        this.formattedTime = formattedTime;
    }

    public SpeedrunRank getRank() {
        return rank;
    }

    @Override
    public void initialize() {
        close();
        returned = false;
        keyLocker.lockKey(Key.SPACE);
        keyLocker.lockKey(Key.ENTER);
        keyLocker.lockKey(Key.ESC);
        loadRankImage();
        playRankSound();
    }

    private void loadRankImage() {
        try {
            rankImage = ImageIO.read(new File(Config.IMAGES_PATH, rank.getImageFileName()));
        } catch (IOException e) {
            rankImage = null;
            System.err.println("Unable to load rank image (" + rank.getImageFileName() + "): " + e.getMessage());
        }
    }

    private void playRankSound() {
        if (screenCoordinator != null && screenCoordinator.isSoundEffectsMuted()) {
            return;
        }
        try {
            rankSound = new SoundEffectPlayer(rank.getSoundFileName());
            syncSoundSettings();
            rankSound.play();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException | IllegalArgumentException e) {
            System.err.println("Unable to play rank sound (" + rank.getSoundFileName() + "): " + e.getMessage());
        }
    }

    private void syncSoundSettings() {
        if (rankSound == null || screenCoordinator == null) {
            return;
        }
        rankSound.setMuted(screenCoordinator.isSoundEffectsMuted());
        rankSound.setVolume(screenCoordinator.getSoundEffectsVolume());
    }

    @Override
    public void update() {
        if (returned) {
            return;
        }
        syncSoundSettings();
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        }
        if (Keyboard.isKeyUp(Key.ENTER)) {
            keyLocker.unlockKey(Key.ENTER);
        }
        if (Keyboard.isKeyUp(Key.ESC)) {
            keyLocker.unlockKey(Key.ESC);
        }

        if ((!keyLocker.isKeyLocked(Key.SPACE) && Keyboard.isKeyDown(Key.SPACE))
                || (!keyLocker.isKeyLocked(Key.ENTER) && Keyboard.isKeyDown(Key.ENTER))
                || (!keyLocker.isKeyLocked(Key.ESC) && Keyboard.isKeyDown(Key.ESC))) {
            returned = true;
            close();
            returnToMenu.run();
        }
    }

    @Override
    public void close() {
        if (rankSound != null) {
            rankSound.close();
            rankSound = null;
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        int screenWidth = ScreenManager.getScreenWidth();
        int screenHeight = ScreenManager.getScreenHeight();

        graphicsHandler.drawFilledRectangle(0, 0, screenWidth, screenHeight, BACKDROP_COLOR);

        int cardWidth = Math.min(580, screenWidth - 80);
        int cardHeight = Math.min(510, screenHeight - 70);
        int cardX = (screenWidth - cardWidth) / 2;
        int cardY = (screenHeight - cardHeight) / 2;
        graphicsHandler.drawFilledRectangleWithBorder(
                cardX, cardY, cardWidth, cardHeight, CARD_COLOR, rank.getAccentColor(), 3);

        Graphics2D g = graphicsHandler.getGraphics();
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        drawCenteredOutlined(graphicsHandler, "RUN COMPLETE", HEADER_FONT,
                HEADER_COLOR, Color.BLACK, 2.5f, screenWidth, cardY + 56);
        drawCentered(graphicsHandler, "FINAL RANK", SUBHEADER_FONT,
                MUTED_TEXT_COLOR, screenWidth, cardY + 94);

        drawRankBadge(graphicsHandler, screenWidth, cardY + 104);
        drawCentered(graphicsHandler, rank.getTitle(), RANK_TITLE_FONT,
                rank.getAccentColor(), screenWidth, cardY + 258);

        drawCentered(graphicsHandler, "Final Time: " + formattedTime, TIME_FONT,
                Color.WHITE, screenWidth, cardY + 305);

        drawTierRow(graphicsHandler, screenWidth, cardY + 345);

        drawCentered(graphicsHandler, "Press SPACE or ENTER to return to menu", PROMPT_FONT,
                MUTED_TEXT_COLOR, screenWidth, cardY + cardHeight - 36);
    }

    private void drawRankBadge(GraphicsHandler graphicsHandler, int screenWidth, int topY) {
        if (rankImage == null) {
            drawCenteredOutlined(graphicsHandler, rank.getLetter(), RANK_LETTER_FONT,
                    rank.getAccentColor(), Color.BLACK, 4.0f, screenWidth, topY + 102);
            return;
        }
        double scale = Math.min(1.0, RANK_IMAGE_MAX_HEIGHT / (double) rankImage.getHeight());
        int drawWidth = (int) Math.round(rankImage.getWidth() * scale);
        int drawHeight = (int) Math.round(rankImage.getHeight() * scale);
        int x = (screenWidth - drawWidth) / 2;
        int y = topY + (RANK_IMAGE_MAX_HEIGHT - drawHeight) / 2;

        Graphics2D imageG = (Graphics2D) graphicsHandler.getGraphics().create();
        imageG.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        imageG.drawImage(rankImage, x, y, drawWidth, drawHeight, null);
        imageG.dispose();
    }

    private void drawTierRow(GraphicsHandler graphicsHandler, int screenWidth, int topY) {
        SpeedrunRank[] tiers = SpeedrunRank.values();
        int boxWidth = 114;
        int boxHeight = 58;
        int gap = 14;
        int totalWidth = tiers.length * boxWidth + (tiers.length - 1) * gap;
        int startX = (screenWidth - totalWidth) / 2;

        for (int i = 0; i < tiers.length; i++) {
            SpeedrunRank tier = tiers[i];
            boolean earned = tier == rank;
            int x = startX + i * (boxWidth + gap);
            Color fill = earned ? new Color(45, 36, 68) : TIER_FILL_DEFAULT;
            Color border = earned ? tier.getAccentColor() : TIER_BORDER_DEFAULT;
            int borderThickness = earned ? 3 : 1;

            graphicsHandler.drawFilledRectangleWithBorder(x, topY, boxWidth, boxHeight, fill, border, borderThickness);

            drawCenteredInBox(graphicsHandler, tier.getLetter(), TIER_LETTER_FONT,
                    earned ? tier.getAccentColor() : MUTED_TEXT_COLOR, x, boxWidth, topY + 25);
            drawCenteredInBox(graphicsHandler, tier.getThresholdLabel(), TIER_TIME_FONT,
                    earned ? Color.WHITE : MUTED_TEXT_COLOR, x, boxWidth, topY + 46);
        }
    }

    private void drawCentered(GraphicsHandler graphicsHandler, String text, Font font,
            Color color, int containerWidth, int baselineY) {
        FontMetrics metrics = graphicsHandler.getGraphics().getFontMetrics(font);
        int x = (containerWidth - metrics.stringWidth(text)) / 2;
        graphicsHandler.drawString(text, x, baselineY, font, color);
    }

    private void drawCenteredOutlined(GraphicsHandler graphicsHandler, String text, Font font,
            Color color, Color outlineColor, float outlineThickness, int containerWidth, int baselineY) {
        FontMetrics metrics = graphicsHandler.getGraphics().getFontMetrics(font);
        int x = (containerWidth - metrics.stringWidth(text)) / 2;
        graphicsHandler.drawStringWithOutline(text, x, baselineY, font, color, outlineColor, outlineThickness);
    }

    private void drawCenteredInBox(GraphicsHandler graphicsHandler, String text, Font font,
            Color color, int boxX, int boxWidth, int baselineY) {
        FontMetrics metrics = graphicsHandler.getGraphics().getFontMetrics(font);
        int x = boxX + (boxWidth - metrics.stringWidth(text)) / 2;
        graphicsHandler.drawString(text, x, baselineY, font, color);
    }
}
