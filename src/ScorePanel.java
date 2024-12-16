import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class ScorePanel extends JPanel {
    private int score = 0;
    private JLabel scoreLabel = new JLabel("점수 : 0");
    private int patience = 5; // 기본값 설정
    private int speed = 0; // 기본 속도 설정
    private int difficulty = 0;

    private ArrayList<JLabel> hearts = new ArrayList<>();
    private ImageIcon heart = new ImageIcon(new ImageIcon("heart.png").getImage().getScaledInstance(90, 90, Image.SCALE_SMOOTH));


    public ScorePanel() {
        this.setBackground(new Color(255,193,204));
        this.setLayout(null);

        scoreLabel.setBounds(50,40,500,100);
        scoreLabel.setFont(new Font("MalgunGothic", Font.BOLD, 60)); // 폰트 크기 키우기
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER); // 텍스트 가운데 정렬
        add(scoreLabel);

        for(int i =0 ;i<patience;i++){
            JLabel heartImg = new JLabel(heart);
            heartImg.setBounds(50 + (i * 100), 250, 90, 90);
            hearts.add(heartImg);
            add(heartImg);
        }

    }

    public void increase() {
        switch (difficulty){
            case 1:
                score ++;
                break;
            case 2:
                score += 2;
                break;
            case 3:
                score += 3;
                break;
        }
        scoreLabel.setText("점수: " + score);
    }
    public void increase(int amount) {
        score += amount;
        scoreLabel.setText("점수: " + score);
    }

    public int getScore() {
        return score;
    }

    public void resetScore(){
        score = 0;
        scoreLabel.setText("점수: 0");
    }

    public void easy() {
        patience = 5;
        speed = 300;
        difficulty = 1;
        resetHearts();
    }

    public void normal() {
        patience = 5;
        speed = 200;
        difficulty = 2;
        resetHearts();
    }

    public void hard() {
        patience = 5;
        speed = 100;
        difficulty = 3;
        resetHearts();
    }

    public void resetDifficulty(){
        difficulty = 0;
    }

    public int getPatience() {
        return patience;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDifficulty(){return difficulty;}

    public void decreasePatience() {
        if(patience > 0) {
            patience--;
            removeHeart();
        }
        else if (patience <= 0) {
            GamePanel gamePanel = findGamePanel(); // GamePanel 찾기
            if (gamePanel != null) {
                gamePanel.checkGameOver();
            }
        }
    }
    public void increasePatience() {
        if (patience < 5) {
            patience++;
            addHeart(); // 하트 추가
        }
    }
    private void resetHearts() {
        // 하트만 제거하고 갱신
        for (JLabel heart : hearts) {
            remove(heart);
        }
        hearts.clear();

        for (int i = 0; i < patience; i++) {
            JLabel heartLabel = new JLabel(heart);
            heartLabel.setBounds(50 + (i * 100), 250, 90, 90);
            hearts.add(heartLabel);
            add(heartLabel);
        }

        revalidate();
        repaint();
    }
    private void removeHeart() {
        if (!hearts.isEmpty()) {
            JLabel heartToRemove = hearts.get(hearts.size() - 1);
            hearts.remove(heartToRemove);
            remove(heartToRemove);
            revalidate();
            repaint();
        }
    }
    private void addHeart() {
        JLabel newHeart = new JLabel(heart);
        newHeart.setBounds(50 + (hearts.size() * 100), 250, 90, 90);
        hearts.add(newHeart);
        add(newHeart);
        revalidate();
        repaint();
    }

    private GamePanel findGamePanel() {
        Container parent = getParent();
        while (parent != null) {
            if (parent instanceof GamePanel) {
                return (GamePanel) parent;
            }
            parent = parent.getParent(); // 부모를 계속 탐색
        }
        return null; // GamePanel을 찾지 못한 경우
    }

}