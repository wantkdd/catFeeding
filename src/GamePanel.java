import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private GameGroundPanel ground;
    private ScorePanel scorePanel;
    private CatProfile catProfile;
    private TextSource textSource;
    private GameFrame gameFrame;

    public GamePanel(GameFrame gameFrame, ScorePanel scorePanel, CatProfile catProfile, TextSource textSource) {
        this.scorePanel = scorePanel;
        this.catProfile = catProfile;
        this.textSource = textSource;
        this.gameFrame = gameFrame;

        this.setLayout(new BorderLayout());
        this.setSize(1520, 1080);

        ground = new GameGroundPanel(scorePanel, textSource, catProfile);
        splitPanel();
    }

    // 패널을 3개로 분리
    private void splitPanel() {
        JSplitPane hPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT); // 좌우 분리
        hPane.setDividerLocation(920);
        hPane.setDividerSize(1);
        add(hPane);

        JSplitPane vPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT); // 상하 분리
        vPane.setDividerLocation(480);
        vPane.setDividerSize(1);
        vPane.setTopComponent(scorePanel);
        vPane.setBottomComponent(catProfile);
        catProfile.setPeacefulCat();

        hPane.setRightComponent(vPane);
        hPane.setLeftComponent(ground);
    }

    public void startGame() {
        ground.startGame();
    }

    public void stopGame() {
        ground.stopGame();
    }

    public void checkGameOver() {
        if (ground.isGameOver()) {
            stopGame();
            gameFrame.showPanel("FinishPanel");
        }
    }

}
