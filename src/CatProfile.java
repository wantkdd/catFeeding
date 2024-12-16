import javax.swing.*;
import java.awt.*;

public class CatProfile extends JPanel {
    private ImageIcon peacefulCat = new ImageIcon("peacefulCat.jpg");
    private ImageIcon hungryCat = new ImageIcon("hungryCat.jpg");
    private ImageIcon fullCat = new ImageIcon("fullCat.jpg");
    public Image profile = null;
    private JLabel text = new JLabel();

    public CatProfile(){
        //setLayout(null);
        setBackground(Color.white);
        profile = peacefulCat.getImage();
        text.setText("밥 달라냥");

    }

    public void setFullCat() {
        text.setText("맛있다냥");
        profile = fullCat.getImage();
        repaint();
        revalidate();
    }
    public void setHungryCat() {
        text.setText("밥 안 주냥");
        profile = hungryCat.getImage();
        repaint();
        revalidate();
    }
    public void setPeacefulCat() {
        text.setText("밥 달라냥");
        profile = peacefulCat.getImage();
        repaint();
        revalidate();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(profile, 0, 0, getWidth(), getHeight(), this);
    }
}
