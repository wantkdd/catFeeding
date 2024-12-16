import javax.swing.*;

public class GenerateThread extends Thread {
    private GameGroundPanel ground;
    private ScorePanel scorePanel;
    private TextSource textSource;
    private CatProfile catProfile;
    private boolean runFlag = true;
    private volatile boolean isPaused = false; // 정지 상태 추적

    public GenerateThread(GameGroundPanel ground, ScorePanel scorePanel, TextSource textSource, CatProfile catProfile) {
        this.ground = ground;
        this.scorePanel = scorePanel;
        this.textSource = textSource;
        this.catProfile = catProfile;
    }

    @Override
    public void run() {
        try {
            while (true) {
                checkStop();
                synchronized (this) {
                    while (isPaused) {
                        wait(); // 정지 상태일 때 대기
                    }
                }

                JLabel label = generateRandomLabel(); // 라벨 생성
                if (label != null) {
                    label.setLocation((int) (Math.random() * (ground.getWidth() - label.getWidth())), 0);
                    ground.addFallingLabel(label); // GameGroundPanel에 추가

                    FallingThread fallingThread = new FallingThread(label, ground, scorePanel, catProfile);
                    ground.addFallingThread(fallingThread);
                    fallingThread.start();
                }

                Thread.sleep(800); // 0.8초마다 새 라벨 생성
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private JLabel generateRandomLabel() {
        double random = Math.random();

        switch (scorePanel.getDifficulty()) {
            case 1: // Easy
                if (random < 0.89) { // 일반 단어
                    JLabel fishLabel = textSource.createFishLabel();
                    fishLabel.setName("normal");
                    return fishLabel;
                } else if (random < 0.94) { // 점수 아이템
                    JLabel scoreItemLabel = textSource.createScoreItemLabel();
                    scoreItemLabel.setName("bigFish");
                    return scoreItemLabel;
                } else if (random < 0.99) { // 하트 아이템
                    JLabel heartItemLabel = textSource.createHeartItemLabel();
                    heartItemLabel.setName("heart");
                    return heartItemLabel;
                } else if (random < 1.0) { // 타임스탑 아이템
                    JLabel timeStopLabel = textSource.createTimeStopItemLabel();
                    timeStopLabel.setName("timeStop");
                    return timeStopLabel;
                }
                break;

            case 2: // Normal
                if (random < 0.77) { // 일반 단어
                    JLabel fishLabel = textSource.createFishLabel();
                    fishLabel.setName("normal");
                    return fishLabel;
                } else if (random < 0.87) { // 점수 아이템
                    JLabel scoreItemLabel = textSource.createScoreItemLabel();
                    scoreItemLabel.setName("bigFish");
                    return scoreItemLabel;
                } else if (random < 0.97) { // 하트 아이템
                    JLabel heartItemLabel = textSource.createHeartItemLabel();
                    heartItemLabel.setName("heart");
                    return heartItemLabel;
                } else if (random < 1.0) { // 타임스탑 아이템
                    JLabel timeStopLabel = textSource.createTimeStopItemLabel();
                    timeStopLabel.setName("timeStop");
                    return timeStopLabel;
                }
                break;

            case 3: // Hard
                if (random < 0.65) { // 일반 단어
                    JLabel fishLabel = textSource.createFishLabel();
                    fishLabel.setName("normal");
                    return fishLabel;
                } else if (random < 0.85) { // 점수 아이템
                    JLabel scoreItemLabel = textSource.createScoreItemLabel();
                    scoreItemLabel.setName("bigFish");
                    return scoreItemLabel;
                } else if (random < 0.95) { // 하트 아이템
                    JLabel heartItemLabel = textSource.createHeartItemLabel();
                    heartItemLabel.setName("heart");
                    return heartItemLabel;
                } else if (random < 1.0) { // 타임스탑 아이템
                    JLabel timeStopLabel = textSource.createTimeStopItemLabel();
                    timeStopLabel.setName("timeStop");
                    return timeStopLabel;
                }
                break;
        }
        return null;
    }


    synchronized public void stopRunning() {
        runFlag = false;
    }

    synchronized public void checkStop() {
        if (!runFlag) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                return;
            }
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
