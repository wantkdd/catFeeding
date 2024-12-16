import javax.swing.*;

public class FallingThread extends Thread {
    private JLabel fallingLabel;
    private GameGroundPanel ground;
    private ScorePanel scorePanel;
    private CatProfile catProfile;
    private static final int BOTTOM_THRESHOLD = 250; // 바닥에서 300 픽셀 위
    private volatile boolean isPaused = false;

    public FallingThread(JLabel fallingLabel, GameGroundPanel ground, ScorePanel scorePanel, CatProfile catProfile) {
        this.fallingLabel = fallingLabel;
        this.ground = ground;
        this.scorePanel = scorePanel;
        this.catProfile = catProfile;
    }

    @Override
    public void run() {
        try {
            while (true) {
                synchronized (this){
                    while(isPaused){
                        wait();
                    }
                }
                fallingLabel.setLocation(fallingLabel.getX(), fallingLabel.getY() + 10);
                if (fallingLabel.getY() > ground.getHeight() - BOTTOM_THRESHOLD) {
                    if (ground.containsLabel(fallingLabel)) {
                        String labelType = fallingLabel.getName();
                        switch (labelType) {
                            case "timeStop": // 타임스탑 아이템
                                //ground.freezeAllThreads(2000); // 2초 동안 멈춤
                                break;
                            case "bigFish": // 점수 아이템
//                                scorePanel.increase(5);
                                break;
                            case "heart": // 하트 아이템
//                                if (scorePanel.getPatience() < 5) {
//                                    scorePanel.increasePatience();
//                                }
                                break;
                            case "normal": // 일반 단어
                            default:
                                catProfile.setHungryCat();
                                scorePanel.decreasePatience();
                                break;
                        }

                        ground.removeFallingLabel(fallingLabel);
                        sleep(30);
                    }

//                        ground.removeFallingLabel(fallingLabel);
//                        catProfile.setHungryCat();
//                        sleep(30);
//                        scorePanel.decreasePatience();
                    //}
                }
                if (fallingLabel.getY() > ground.getHeight()) {
                    fallingLabel.setLocation(fallingLabel.getX(), -fallingLabel.getHeight());
                }
                sleep(scorePanel.getSpeed());
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    // 쓰레드를 일시 정지하는 메서드
    public synchronized void freeze() {
        isPaused = true;
    }

    // 쓰레드를 다시 재개하는 메서드
    public synchronized void resumeThread() {
        isPaused = false;
        notify(); // 대기 중인 쓰레드를 깨움
    }
}