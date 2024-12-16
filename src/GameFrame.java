import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private BGMThread bgm;
    private ScorePanel scorePanel;

    public GameFrame(){
        setTitle("고양이 밥 주기!");
        setSize(1520,1000);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        TextSource textSource = new TextSource();
        scorePanel = new ScorePanel();
        CatProfile catProfile = new CatProfile();

        GamePanel gamePanel = new GamePanel(this, scorePanel, catProfile, textSource);
        StartPanel startPanel = new StartPanel(this, gamePanel, scorePanel);
        FinishPanel finishPanel = new FinishPanel(this, scorePanel);
        InfoPanel infoPanel = new InfoPanel(this);

        cardPanel.add(startPanel, "StartPanel");
        cardPanel.add(gamePanel, "GamePanel");
        cardPanel.add(finishPanel, "FinishPanel");
        cardPanel.add(infoPanel, "InfoPanel");

        add(cardPanel);

        bgm = new BGMThread("lobbyBgm.wav");
        bgm.start();

        setVisible(true);
        cardLayout.show(cardPanel,"StartPanel");
        
    }

    public void showPanel(String panelName) {
        if (bgm != null) {
            bgm.stopBGM();
        }

        switch (panelName) {
            case "StartPanel":
                bgm = new BGMThread("lobbyBgm.wav");
                break;
            case "GamePanel":
                int difficulty = scorePanel.getDifficulty();
                if (difficulty == 1) {
                    bgm = new BGMThread("easyBgm.wav");
                } else if (difficulty == 2) {
                    bgm = new BGMThread("normalBgm.wav");
                } else if (difficulty == 3) {
                    bgm = new BGMThread("hardBgm.wav");
                }
                break;
            case "FinishPanel":
                bgm = new BGMThread("lobbyBgm.wav");
                break;
            case "InfoPanel":
                bgm = null; // InfoPanel에서는 배경음악 없음
                break;
        }

        if (bgm != null) {
            bgm.start();
        }

        cardLayout.show(cardPanel, panelName);
    }

    public static void main(String[] args) {
        new GameFrame();
    }
}