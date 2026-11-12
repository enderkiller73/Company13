package Screens;

import Engine.GraphicsHandler;
import Engine.Screen;
import Engine.ScreenManager;
import SpriteFont.SpriteFont;

import java.awt.*;

// This class is for the level cleared screen
public class LevelClearedScreen extends Screen {
    protected SpriteFont winMessage;
    private String speedrunTime;
    private static final Font SPEEDRUN_FONT = new Font(Font.MONOSPACED, Font.BOLD, 24);

    public LevelClearedScreen() {
        initialize();
    }

    @Override
    public void initialize() {
        winMessage = new SpriteFont("Level Cleared", 320, 239, "Arial", 30, Color.white);
    }

    @Override
    public void update() {

    }

    public void setSpeedrunTime(String speedrunTime) {
        this.speedrunTime = speedrunTime;
    }

    public void draw(GraphicsHandler graphicsHandler) {
        // paint entire screen black and dislpay level cleared text
        graphicsHandler.drawFilledRectangle(0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.black);
        winMessage.draw(graphicsHandler);
        if (speedrunTime != null) {
            String text = "Speedrun: " + speedrunTime;
            int width = graphicsHandler.getGraphics().getFontMetrics(SPEEDRUN_FONT).stringWidth(text);
            graphicsHandler.drawString(text, (ScreenManager.getScreenWidth() - width) / 2,
                    295, SPEEDRUN_FONT, Color.WHITE);
        }
    }
}
