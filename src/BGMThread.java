import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class BGMThread extends Thread {
    private Clip clip;
    private String filePath;
    private volatile boolean stopRequested = false;

    public BGMThread(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close(); // 재생 완료 시 Clip 닫기
                }
            });

            while (clip.isRunning()) {
                Thread.sleep(10); // Clip이 실행 중인 동안 대기
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            stopBGM();
        }
    }

    public void stopBGM() {
        stopRequested = true;
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}
