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

public class MusicPlayer implements AutoCloseable {
    private final Clip clip;
    private long pausedPosition;
    private boolean paused;

    public MusicPlayer(String fileName) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        File audioFile = new File(Config.MUSIC_PATH, fileName);
        if (!audioFile.getName().toLowerCase(Locale.ROOT).endsWith(".wav")) {
            throw new UnsupportedAudioFileException("Only WAV music files are supported: " + fileName);
        }
        if (!audioFile.isFile()) {
            throw new IOException("Music file not found: " + audioFile.getPath());
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
        play(false);
    }

    public void play(boolean loop) {
        clip.stop();
        clip.setMicrosecondPosition(0);
        paused = false;
        if (loop) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } else {
            clip.start();
        }
    }

    public void pause() {
        if (clip.isRunning()) {
            pausedPosition = clip.getMicrosecondPosition();
            clip.stop();
            paused = true;
        }
    }

    public void resume() {
        if (paused) {
            clip.setMicrosecondPosition(pausedPosition);
            clip.start();
            paused = false;
        }
    }

    public void stop() {
        clip.stop();
        clip.setMicrosecondPosition(0);
        paused = false;
    }

    public boolean isPlaying() {
        return clip.isRunning();
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
    public void close() {
        clip.close();
    }
}