import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.*;

public enum SoundEffect {
    EAT_FOOD("audio/quack.wav"),
    DIE("audio/huh.wav"),
    WIN("audio/win.wav");

    public static enum Volume {
        MUTE, LOW, MEDIUM, HIGH
    }

    public static Volume volume = Volume.LOW;
    private Clip clip;

    private SoundEffect(String soundFileName) {
        try {
            URL url = this.getClass().getClassLoader().getResource(soundFileName);
            if (url == null) {
                System.err.println("Sound file not found: " + soundFileName);
                return;
            }
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (volume != Volume.MUTE && clip != null) {
            if (clip.isRunning()) clip.stop();
            clip.setFramePosition(0);
            clip.start();
        }
    }

    static void initGame() {
        values();
    }
}
