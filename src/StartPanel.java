import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class StartPanel extends JPanel{

    private ImageIcon startBackgroungImage = new ImageIcon("introBackground.jpg");
    private Image backgroundIamge = startBackgroungImage.getImage();
    private TextSource textSource = new TextSource();
    private ScorePanel scorePanel = new ScorePanel();
    private CatProfile catProfile = new CatProfile();
    private GameFrame gameFrame = null;
    private GamePanel gamePanel = new GamePanel(gameFrame, scorePanel, catProfile, textSource);

    private ImageIcon startButton = new ImageIcon("startButton.png");
    private JButton start = new JButton(startButton);
    private ImageIcon addWordButton = new ImageIcon("addWordButton.png");
    private JButton addWord = new JButton(addWordButton);
    private ImageIcon mealIcon = new ImageIcon("meal.png"); // meal.png 추가
    private JLabel meal = new JLabel(mealIcon); // 이미지 라벨 생성
    private JTextField addWordTf = new JTextField(20);
    private ImageIcon hardButton = new ImageIcon("hardButton.png");
    private JButton hard = new JButton(hardButton);
    private ImageIcon normalButton = new ImageIcon("normalButton.png");
    private JButton normal = new JButton(normalButton);
    private ImageIcon easyButton = new ImageIcon("easyButton.png");
    private JButton easy = new JButton(easyButton);
    private ImageIcon titleButton = new ImageIcon("title.png");
    private JLabel title = new JLabel(titleButton);
    private ImageIcon infoButton = new ImageIcon("infoButton.png");
    private JButton info = new JButton(infoButton);

    public StartPanel(GameFrame gameFrame, GamePanel gamePanel, ScorePanel scorePanel){

        this.gameFrame = gameFrame;
        this.scorePanel = scorePanel;

        setLayout(null);

        title.setSize(1100,300);
        title.setLocation(200,50);
        add(title);

        start.setSize(330,140);
        start.setLocation(600, 500);
        addHoverEffect(start);
        start.setBorderPainted(false);
        start.setContentAreaFilled(false);
        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gamePanel.startGame();
                gameFrame.showPanel("GamePanel");
            }
        });
        add(start);


        meal.setSize(100, 100);
        meal.setLocation(1390, 482); // addWordButton 바로 위 위치
        add(meal);

        addWord.setSize(200,100);
        addWord.setLocation(1340,501);
        addHoverEffect(addWord);
        addWord.setBorderPainted(false);
        addWord.setContentAreaFilled(false);
        addWord.addMouseListener(new MouseAdapter() {
            @Override //버튼 클릭하면 텍스트필드 읽어와서 addWord(input)을 단어 추가
            public void mouseClicked(MouseEvent e) {
                JTextField tf = addWordTf;
                String inputWord = tf.getText();
                if(!inputWord.isEmpty())
                    textSource.addWord(inputWord);
                animateMealLabel();
                tf.setText("");
            }
        });
        add(addWord);

        addWordTf.setSize(200,30);
        addWordTf.setLocation(1200,550);
        add(addWordTf);

        hard.setSize(220,120);
        hard.setLocation(1000,370);
        addHoverEffect(hard);
        hard.setBorderPainted(false);
        hard.setContentAreaFilled(false);
        hard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                hard.setSize(hard.getWidth()+10, hard.getHeight()+5);
                scorePanel.hard();
            }
        });
        add(hard);

        normal.setSize(220,120);
        normal.setLocation(650,370);
        addHoverEffect(normal);
        normal.setBorderPainted(false);
        normal.setContentAreaFilled(false);
        normal.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                normal.setSize(normal.getWidth()+10, normal.getHeight()+5);
                scorePanel.normal();
            }
        });
        add(normal);

        easy.setSize(220,120);
        easy.setLocation(300,370);
        easy.setBorderPainted(false);
        easy.setContentAreaFilled(false);
        addHoverEffect(easy);
        easy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                easy.setSize(easy.getWidth()+10, easy.getHeight()+5);
                scorePanel.easy();
            }
        });
        add(easy);

        info.setSize(220,120);
        info.setLocation(1300,850);
        info.setBorderPainted(false);
        info.setContentAreaFilled(false);
        addHoverEffect(info);
        info.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameFrame.showPanel("InfoPanel");
            }
        });
        add(info);



        setVisible(true);
    } //생성자 종료

    private void addHoverEffect(JButton button) {
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // 마우스가 버튼에 들어갈 때 크기 살짝 키우기
                button.setSize(new Dimension(button.getWidth() + 15, button.getHeight() + 15));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                // 마우스가 버튼에서 나갈 때 다시 크기 원래대로 돌리기
                button.setSize(new Dimension(button.getWidth() - 15, button.getHeight() - 15));
            }
        });
    }
    private void animateMealLabel() {
        JTextField tf = addWordTf;
        String inputWord = tf.getText();
        if (inputWord.isEmpty()) return;

        Runnable animationTask = new Runnable() {
            @Override
            public void run() {
                int originalY = meal.getY();
                try {
                    // 사료를 위로 이동
                    meal.setLocation(meal.getX(), originalY - 30);
                    Thread.sleep(300); // 0.3초 대기

                    // 사료를 원래 위치로 복원
                    meal.setLocation(meal.getX(), originalY);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // 쓰레드 중단 처리
                }
            }
        };

        // Runnable을 사용하여 Thread 생성
        Thread animationThread = new Thread(animationTask);
        animationThread.start();
    }


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundIamge,0,0, getWidth(), getHeight(), null);
    }

}
