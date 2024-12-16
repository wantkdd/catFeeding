import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

class GameGroundPanel extends JPanel {
    private Vector<JLabel> fallingLabels = new Vector<>();
    private Vector<FallingThread> fallingThreads = new Vector<>(); // FallingThread 추적
    private ScorePanel scorePanel;
    private TextSource textSource;
    private GenerateThread generateThread;
    private JTextField inputField;
    private CatProfile catProfile;
    private ImageIcon gameBackground = new ImageIcon("gameBackground.jpg");
    private Image backgroundIamge = gameBackground.getImage();

    public GameGroundPanel(ScorePanel scorePanel, TextSource textSource, CatProfile catProfile) {
        this.scorePanel = scorePanel;
        this.textSource = textSource;
        this.catProfile = catProfile;

        setLayout(null);

        inputField = new JTextField(20);
        inputField.setBounds(300, 800, 300, 30);
        inputField.setBackground(Color.white);
        add(inputField);

        inputField.setFocusable(true);
        inputField.requestFocus();
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkInput(inputField.getText());
                inputField.setText("");
            }
        });
    }

    public void addFallingLabel(JLabel label) {
        fallingLabels.add(label);
        add(label);
        repaint();
    }

    public void removeFallingLabel(JLabel label) {
        fallingLabels.remove(label);
        remove(label);
        repaint();
    }

    public boolean containsLabel(JLabel label) {
        return fallingLabels.contains(label);
    }

    public Vector<JLabel> getFallingLabels() {
        return fallingLabels;
    }

    public void startGame() {
            generateThread = new GenerateThread(this, scorePanel, textSource, catProfile);
            generateThread.start();
    }

    public void stopGame() {
        if (generateThread != null) {
            generateThread.stopRunning();
        }
        fallingLabels.clear();
        repaint();
    }

    public boolean isGameOver() {
        return scorePanel.getPatience() <= 0;
    }

    public void freezeAllThreads(int duration) {
        // 모든 쓰레드 정지
        for (FallingThread thread : fallingThreads) {
            thread.freeze();
        }
        if (generateThread != null) {
            generateThread.freeze();
        }

        // 일정 시간 후 모든 쓰레드 재개
        Timer resumeTimer = new Timer(duration, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (FallingThread thread : fallingThreads) {
                    thread.resumeThread();
                }
                if (generateThread != null) {
                    generateThread.resumeThread();
                }
            }
        });

        resumeTimer.setRepeats(false); // 한 번만 실행
        resumeTimer.start();
    }

    private synchronized void checkInput(String input) {
        boolean matched = false;
        for (JLabel label : fallingLabels) {
            if (label.getText().equals(input)) {
                if (label.getName() != null) {
                    switch (label.getName()) {
                        case "bigFish":
                            scorePanel.increase(5); // 고정된 점수 추가
                            catProfile.setFullCat();
                            BGMThread coinBgm = new BGMThread("coinBgm.wav");;
                            coinBgm.start();
                            break;
                        case "heart":
                            scorePanel.increasePatience(); // 하트 추가
                            catProfile.setFullCat();
                            break;
                        case "timeStop":
                            freezeAllThreads(2000); // 2초 동안 멈춤
                            catProfile.setFullCat();
                            break;
                        default:
                            scorePanel.increase(); // 난이도에 따른 점수
                            catProfile.setFullCat();
                            break;
                    }

                }
                removeFallingLabel(label);
                matched = true;
                break;
            }
        }

        // 일치한 라벨이 없을 경우 고양이 상태를 "배고픔"으로 설정
        if (!matched) {
            catProfile.setHungryCat();
        }
    }
    public void addFallingThread(FallingThread thread) {
        fallingThreads.add(thread);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundIamge,0,0,getWidth(),getHeight(), null);
    }

}
