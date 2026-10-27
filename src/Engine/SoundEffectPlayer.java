package Engine;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SoundEffectPlayer implements AutoCloseable {
    private static final ScheduledExecutorService STOP_SCHEDULER = Executors.newSingleThreadScheduledExecutor(task -> {
        Thread thread = new Thread(task, "sound-effect-stop");
        thread.setDaemon(true);
        return thread;
    });

    private final Clip clip;
    private boolean muted;
    private long playbackGeneration;
    private ScheduledFuture<?> scheduledStop;

    public SoundEffectPlayer(String fileName) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File audioFile = new File(Config.SOUND_EFFECTS_PATH, fileName);
        if (!audioFile.getName().toLowerCase(Locale.ROOT).endsWith(".wav")) {
            throw new UnsupportedAudioFileException("Only WAV sound effects are supported: " + fileName);
        }
        if (!audioFile.isFile()) {
            throw new IOException("Sound effect file not found: " + audioFile.getPath());
        }

        try (AudioInputStream source = AudioSystem.getAudioInputStream(audioFile)) {
            AudioFormat sourceFormat = source.getFormat();
            int channels = Math.max(sourceFormat.getChannels(), 1);
            float sampleRate = sourceFormat.getSampleRate() > 0 ? sourceFormat.getSampleRate() : 44100;
            AudioFormat decodedFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                sampleRate,
                16,
                channels,
                channels * 2,
                sampleRate,
                false
            );

            try (AudioInputStream decoded = AudioSystem.getAudioInputStream(decodedFormat, source)) {
                clip = AudioSystem.getClip();
                clip.open(decoded);
            }
        }
    }

    public void play() {
        playForDuration(0);
    }

    public void playFirstHalf() {
        playForDuration(Math.max(1, clip.getMicrosecondLength() / 2));
    }

    private synchronized void playForDuration(long durationMicroseconds) {
        if (muted) {
            return;
        }
        cancelScheduledStop();
        clip.stop();
        clip.setMicrosecondPosition(0);
        clip.start();
        if (durationMicroseconds > 0) {
            long generation = playbackGeneration;
            scheduledStop = STOP_SCHEDULER.schedule(
                () -> stopPlayback(generation), durationMicroseconds, TimeUnit.MICROSECONDS
            );
        }
    }

    private synchronized void stopPlayback(long generation) {
        if (generation == playbackGeneration) {
            clip.stop();
            scheduledStop = null;
        }
    }

    private void cancelScheduledStop() {
        playbackGeneration++;
        if (scheduledStop != null) {
            scheduledStop.cancel(false);
            scheduledStop = null;
        }
    }

    public synchronized void setMuted(boolean muted) {
        this.muted = muted;
        if (muted) {
            cancelScheduledStop();
            clip.stop();
            clip.setMicrosecondPosition(0);
        }
    }

    public void setVolume(float volume) {
        if (!clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            return;
        }

        FloatControl gain = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float boundedVolume = Math.max(0, Math.min(volume, 1));
        float decibels = boundedVolume == 0 ? gain.getMinimum() : (float) (20 * Math.log10(boundedVolume));
        gain.setValue(Math.max(gain.getMinimum(), Math.min(decibels, gain.getMaximum())));
    }

    @Override
    public synchronized void close() {
        cancelScheduledStop();
        clip.close();
    }
}
