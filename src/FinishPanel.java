import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class FinishPanel extends JPanel{
    private ScorePanel scorePanel = null;
    private TextSource textSource = null;
    private GameFrame gameFrame = null;
    private ImageIcon finishBackground = new ImageIcon("introBackground.jpg");
    private Image backgroundImage = finishBackground.getImage();

    private JTextField nameField = new JTextField(20);
    private ImageIcon nameIcon = new ImageIcon("nameInput.png");
    private JLabel name = new JLabel(nameIcon);

    private ImageIcon restartButton = new ImageIcon("restartButton.png");
    private JButton restart = new JButton(restartButton);

    public FinishPanel(GameFrame gameFrame, ScorePanel scorePanel){
        this.gameFrame = gameFrame;
        this.scorePanel = scorePanel;

        setSize(1520,1080);
        setLayout(null);

        name.setBounds(930, 200, 300, 60); // 크기 및 위치 조정
        name.setOpaque(true);
        add(name);

        nameField.setBounds(930, 260, 300, 50);
        nameField.setFont(new Font("Malgun Gothic", Font.PLAIN, 30));
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField tf = (JTextField)e.getSource();
                String name = tf.getText();
                rank(name, scorePanel.getScore());
                printRank();
                tf.setText("");
            }
        });
        add(nameField);

        restart.setBounds(950, 460, 300, 100);
        restart.setBorderPainted(false);
        restart.setContentAreaFilled(false);
        restart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameFrame.showPanel("StartPanel");
            }
        });
        add(restart);

        printRank();

        setVisible(true);

    }


    public void rank(String name, int score){
        try{
            Vector<String> ranks = readRanks();
            String inputRank = name + "의 급식 성공 횟수 : " +score;
            ranks.add(inputRank);

            Collections.sort(ranks, Comparator.comparingInt(s->Integer.parseInt(s.split("의 급식 성공 횟수 : ")[1])));
            Collections.reverse(ranks);
            writeRanks(ranks);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static Vector<String> readRanks() throws IOException{
        Vector<String> ranks = new Vector<>();
        try (BufferedReader br = new BufferedReader(new FileReader("rank.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                ranks.add(line);
            }
        }
        return ranks;
    }
    private static void writeRanks(Vector<String> ranks) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("rank.txt"))) {
            for (String record : ranks) {
                bw.write(record);
                bw.newLine();
            }
        }
    }
    private void printRank(){
        try {

            Component[] components = getComponents();
            for (Component component : components) {
                if (component instanceof JLabel && component != name) {
                    remove(component);
                }
            }

            Vector<String> records = readRanks();

            JLabel[] rankLabels = new JLabel[records.size()];

            for (int i = 0; i < records.size() && i < 10; i++) { //10명까지 읽어와 출력
                String record = records.get(i);
                rankLabels[i] = new JLabel((i + 1) + "등 : " + record + "회");
                rankLabels[i].setSize(1000, 50);
                rankLabels[i].setLocation(50, 20+ i*55);
                rankLabels[i].setForeground(Color.black);
                rankLabels[i].setFont(new Font("Malgun Gothic",Font.BOLD, 35));
                add(rankLabels[i]);
            }



            // 다시 그리기를 통해 변경된 내용을 화면에 반영
            revalidate();
            this.repaint();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        printRank();//id 입력하면 랭킹 다시 그리기
    }

}
