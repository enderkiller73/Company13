package Utils;

import java.util.Locale;
import java.util.function.LongSupplier;

// Measures real elapsed time independently of the game's frame rate.
public class SpeedrunTimer {
    private final LongSupplier clock;
    private long elapsedNanos;
    private long segmentStartedAt;
    private boolean started;
    private boolean running;
    private boolean finished;

    public SpeedrunTimer() {
        this(System::nanoTime);
    }

    public SpeedrunTimer(LongSupplier clock) {
        this.clock = clock;
    }

    public void reset() {
        elapsedNanos = 0;
        started = false;
        running = false;
        finished = false;
    }

    public void start() {
        if (!started) {
            started = true;
            resume();
        }
    }

    public void pause() {
        if (running) {
            elapsedNanos += clock.getAsLong() - segmentStartedAt;
            running = false;
        }
    }

    public void resume() {
        if (started && !running && !finished) {
            segmentStartedAt = clock.getAsLong();
            running = true;
        }
    }

    public void finish() {
        if (started) {
            pause();
            finished = true;
        }
    }

    public long getElapsedMillis() {
        long totalNanos = elapsedNanos;
        if (running) {
            totalNanos += clock.getAsLong() - segmentStartedAt;
        }
        return totalNanos / 1_000_000L;
    }

    public String getFormattedTime() {
        long millis = getElapsedMillis();
        return String.format(Locale.ROOT, "%02d:%02d.%03d",
                millis / 60_000, (millis / 1_000) % 60, millis % 1_000);
    }
}
