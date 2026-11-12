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

    // Wrap the supplied ASCII banner at word boundaries to fit the game window.
    private static final String[] ENGINE_LOGO = {
        "__________                                      .___ __________",
        "\\______   \\______  _  __ ___________   ____   __| _/ \\______   \\___.__.",
        " |     ___/  _ \\ \\/ \\/ // __ \\_  __ \\_/ __ \\ / __ |   |    |  _<   |  |",
        " |    |  (  <_> )     /\\  ___/|  | \\/\\  ___// /_/ |   |    |   \\\\___  |",
        " |____|   \\____/ \\/\\_/  \\___  >__|    \\___  >____ |   |______  // ____|",
        "                            \\/            \\/     \\/          \\/ \\/",
        "",
        "",
        "___________.__             ___________              .__",
        "\\__    ___/|  |__   ____   \\_   _____/ ____    ____ |__| ____   ____",
        "  |    |   |  |  \\_/ __ \\   |    __)_ /    \\  / ___\\|  |/    \\_/ __ \\",
        "  |    |   |   Y  \\  ___/   |        \\   |  \\/ /_/  >  |   |  \\  ___/",
        "  |____|   |___|  /\\___  > /_______  /___|  /\\___  /|__|___|  /\\___  >",
        "                \\/     \\/          \\/     \\//_____/         \\/     \\/",
        "",
        "",
        "________   _____     _____  .__  .__    ___________.__",
        "\\_____  \\_/ ____\\   /  _  \\ |  | |  |   \\__    ___/|__| _____   ____",
        " /   |   \\   __\\   /  /_\\  \\|  | |  |     |    |   |  |/     \\_/ __ \\",
        "/    |    \\  |    /    |    \\  |_|  |__   |    |   |  |  Y Y  \\  ___/",
        "\\_______  /__|    \\____|__  /____/____/   |____|   |__|__|_|  /\\___  >",
        "        \\/                \\/                                \\/     \\/"
    };

    private static final long COMPANY_SCREEN_NANOS = 4_000_000_000L;
    private static final long MIN_INTRO_NANOS = 7_000_000_000L;
    private static final long PLAYBACK_FALLBACK_NANOS = 11_000_000_000L;

    private static final String INTRO_AUDIO = "2012 New Valve Intro (PORTAL 2) (720p HD).wav";

    private final ScreenCoordinator screenCoordinator;
    private final KeyLocker keyLocker = new KeyLocker();
    private MusicPlayer introMusic;
    private boolean playbackObserved;
    private boolean showingEngineBanner;
    private long startedAtNanos;

    public Introbeforemenu(ScreenCoordinator screenCoordinator) {
        this.screenCoordinator = screenCoordinator;
    }

    @Override
    public void initialize() {
        keyLocker.lockKey(Key.SPACE);
        playbackObserved = false;
        showingEngineBanner = false;
        try {
            introMusic = new MusicPlayer(INTRO_AUDIO);
            introMusic.play();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.err.println("Unable to play startup intro audio.");
            e.printStackTrace(System.err);
        }
        startedAtNanos = System.nanoTime();
    }

    @Override
    public void update() {
        if (Keyboard.isKeyUp(Key.SPACE)) {
            keyLocker.unlockKey(Key.SPACE);
        } else if (!keyLocker.isKeyLocked(Key.SPACE)) {
            screenCoordinator.setGameState(GameState.MENU);
            return;
        }

        long elapsedNanos = System.nanoTime() - startedAtNanos;
        showingEngineBanner = elapsedNanos >= COMPANY_SCREEN_NANOS;

        boolean musicPlaying = introMusic != null && introMusic.isPlaying();
        playbackObserved |= musicPlaying;
        boolean musicFinished = introMusic == null || (playbackObserved && !musicPlaying);
        // If playback never starts, still finish the intro instead of getting stuck.
        boolean playbackTimedOut = !playbackObserved && elapsedNanos >= PLAYBACK_FALLBACK_NANOS;
        if (elapsedNanos >= MIN_INTRO_NANOS && (musicFinished || playbackTimedOut)) {
            screenCoordinator.setGameState(GameState.MENU);
        }
    }

    @Override
    public void draw(GraphicsHandler graphicsHandler) {
        graphicsHandler.drawFilledRectangle(
            0, 0, ScreenManager.getScreenWidth(), ScreenManager.getScreenHeight(), Color.BLACK
        );

        String[] logo = showingEngineBanner ? ENGINE_LOGO : LOGO;
        Font logoFont = new Font(Font.MONOSPACED, Font.BOLD, 13);
        java.awt.FontMetrics fontMetrics = graphicsHandler.getGraphics().getFontMetrics(logoFont);
        int lineHeight = fontMetrics.getHeight();
        int totalHeight = logo.length * lineHeight;
        int startY = (ScreenManager.getScreenHeight() - totalHeight) / 2;
        int maxLineWidth = 0;
        for (String line : logo) {
            maxLineWidth = Math.max(maxLineWidth, fontMetrics.stringWidth(line));
        }
        int startX = (ScreenManager.getScreenWidth() - maxLineWidth) / 2;

        for (int i = 0; i < logo.length; i++) {
            graphicsHandler.drawString(logo[i], startX, startY + i * lineHeight + fontMetrics.getAscent(), logoFont, Color.WHITE);
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
