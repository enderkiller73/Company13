package Engine;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.Locale;

public class SoundEffectPlayer implements AutoCloseable {
    private final Clip clip;
    private boolean muted;

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
        if (muted) {
            return;
        }
        clip.stop();
        clip.setMicrosecondPosition(0);
        clip.start();
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
        if (muted) {
            clip.stop();
            clip.setMicrosecondPosition(0);
        }
    }

    @Override
    public void close() {
        clip.close();
    }
}
