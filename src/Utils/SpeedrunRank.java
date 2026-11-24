package Utils;

import java.awt.Color;

public enum SpeedrunRank {
    S("S", "Savage!", "< 3:00", new Color(255, 215, 0), "Savage.wav", "S.png"),
    A("A", "Apocolyptic!", "< 4:00", new Color(216, 115, 255), "A rank.wav", "A.png"),
    B("B", "Bad Ass!", "< 5:00", new Color(96, 180, 255), "BadAss.wav", "B.png"),
    C("C", "Crazy!", "5:00+", new Color(180, 190, 205), "Crazy.wav", "C.png");

    public static final long S_MAX_EXCLUSIVE_MILLIS = 180_000L;
    public static final long A_MAX_EXCLUSIVE_MILLIS = 240_000L;
    public static final long B_MAX_EXCLUSIVE_MILLIS = 300_000L;

    private final String letter;
    private final String title;
    private final String thresholdLabel;
    private final Color accentColor;
    private final String soundFileName;
    private final String imageFileName;

    SpeedrunRank(String letter, String title, String thresholdLabel, Color accentColor,
            String soundFileName, String imageFileName) {
        this.letter = letter;
        this.title = title;
        this.thresholdLabel = thresholdLabel;
        this.accentColor = accentColor;
        this.soundFileName = soundFileName;
        this.imageFileName = imageFileName;
    }

    public static SpeedrunRank fromElapsedMillis(long elapsedMillis) {
        long clamped = Math.max(0L, elapsedMillis);
        if (clamped < S_MAX_EXCLUSIVE_MILLIS) {
            return S;
        }
        if (clamped < A_MAX_EXCLUSIVE_MILLIS) {
            return A;
        }
        if (clamped < B_MAX_EXCLUSIVE_MILLIS) {
            return B;
        }
        return C;
    }

    public String getLetter() {
        return letter;
    }

    public String getTitle() {
        return title;
    }

    public String getThresholdLabel() {
        return thresholdLabel;
    }

    public Color getAccentColor() {
        return accentColor;
    }

    public String getSoundFileName() {
        return soundFileName;
    }

    public String getImageFileName() {
        return imageFileName;
    }
}
