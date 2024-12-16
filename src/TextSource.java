import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Vector;

public class TextSource {
    private Vector<String> words = new Vector<String>();
    Vector<String> names = new Vector<String>();
    private ImageIcon fishIcon = new ImageIcon("fish.png");
    private ImageIcon bigFishIcon = new ImageIcon("bigFish.png");
    private ImageIcon heartIcon = new ImageIcon("heart.png");
    private ImageIcon timeStopIcon = new ImageIcon("timeStop.png");
    public TextSource(){
        try (BufferedReader br = new BufferedReader(new FileReader("words.txt"))) {
            String word;
            while ((word = br.readLine()) != null) {
                this.words.add(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedReader br = new BufferedReader(new FileReader("rank.txt"))) {
            String name;
            while ((name = br.readLine()) != null) {
                names.add(name);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        fishIcon = new ImageIcon(fishIcon.getImage().getScaledInstance(40,40,Image.SCALE_SMOOTH));
        bigFishIcon = new ImageIcon(bigFishIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        heartIcon = new ImageIcon(heartIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
        timeStopIcon = new ImageIcon(timeStopIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH));

    }

    public JLabel createFishLabel() {
        String word = getWord(); // 랜덤 단어 가져오기
        JLabel fishLabel = new JLabel(word, fishIcon, JLabel.CENTER); // 이미지와 단어를 결합
        fishLabel.setHorizontalTextPosition(JLabel.CENTER); // 텍스트를 중앙에 배치
        fishLabel.setVerticalTextPosition(JLabel.BOTTOM);
        fishLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14)); // 텍스트 스타일 설정
        fishLabel.setForeground(Color.BLACK); // 텍스트 색상 설정
        fishLabel.setSize(80, 60); // 라벨 크기 설정
        return fishLabel;
    }
    // 점수 아이템 라벨 생성
    public JLabel createScoreItemLabel() {
        String word = getWord();
        JLabel scoreLabel = new JLabel(word, bigFishIcon, JLabel.CENTER);
        scoreLabel.setHorizontalTextPosition(JLabel.CENTER);
        scoreLabel.setVerticalTextPosition(JLabel.BOTTOM);
        scoreLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setSize(80, 60); // 라벨 크기 설정
        scoreLabel.setName("bigFish"); // 라벨 타입 설정
        return scoreLabel;
    }

    // 하트 아이템 라벨 생성
    public JLabel createHeartItemLabel() {
        String word = getWord();
        JLabel heartLabel = new JLabel(word, heartIcon, JLabel.CENTER);
        heartLabel.setHorizontalTextPosition(JLabel.CENTER);
        heartLabel.setVerticalTextPosition(JLabel.BOTTOM);
        heartLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        heartLabel.setForeground(Color.BLACK);
        heartLabel.setSize(80, 60); // 라벨 크기 설정
        heartLabel.setName("heart"); // 라벨 타입 설정
        return heartLabel;
    }
    public JLabel createTimeStopItemLabel() {
        String word = getWord(); // 랜덤 단어 가져오기
        JLabel timeStopLabel = new JLabel(word, timeStopIcon, JLabel.CENTER);
        timeStopLabel.setHorizontalTextPosition(JLabel.CENTER);
        timeStopLabel.setVerticalTextPosition(JLabel.BOTTOM);
        timeStopLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        timeStopLabel.setForeground(Color.BLACK);
        timeStopLabel.setSize(80, 60);
        timeStopLabel.setName("timeStop"); // 라벨 타입 설정
        return timeStopLabel;
    }


    public String getWord(){
        int index = (int)(Math.random()*words.size());
        return words.get(index);
    }
    public void addWord(String word){
        if (words.contains(word)){
            System.out.println("중복 단어 : "+word);
            return;
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("words.txt",true))) {
            bw.write(word);
            bw.newLine();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addName(String name){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("rank.txt", true))) {
            bw.newLine();
            bw.write(name);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
