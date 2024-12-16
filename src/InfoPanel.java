import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InfoPanel extends JPanel {
    private GameFrame gameFrame = null;
    private ImageIcon infoIcon = new ImageIcon("gameBackGround.jpg");
    private Image info = infoIcon.getImage();
    private ImageIcon toMainButton = new ImageIcon("toMainButton.png");
    private JButton toMain = new JButton(toMainButton);

    public InfoPanel(GameFrame gameFrame){
        this.gameFrame = gameFrame;
        setSize(1520,1080);
        setLayout(null);

        toMain.setBounds(1300,850,200,100);
        toMain.setBorderPainted(false);
        toMain.setContentAreaFilled(false);
        toMain.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                gameFrame.showPanel("StartPanel");
            }
        });
        add(toMain);

        setVisible(true);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(info, 0, 0, getWidth(), getHeight(), null);
    }
}
