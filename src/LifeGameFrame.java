import javax.swing.*;
import java.awt.*;
import java.io.File;

public class LifeGameFrame extends JFrame {
    private JTextArea text;
    private JButton buttonStart, buttonRead;
    private JTextField textLifeCircles;
    private LifeGame mLifeGame;
    private int lifeCicles;

    private LifeGameFrame() {
        setLayout(new FlowLayout());
        setTitle("LifeGame");
        setBounds(50, 50, 1600, 900);
        text = new JTextArea(100, 100);
        text.setText("在此输入初始生命\n用“空格”和“x”分别表示网格中每一个死的单元和活的单元\n如\n   \nxxx\n   ");
        buttonStart = new JButton("start");
        buttonRead = new JButton("readfromfile");
        textLifeCircles = new JTextField(10);
        textLifeCircles.setText("在此输入最大周期,然后点击start开始游戏");
        add(textLifeCircles);
        add(text);
        add(buttonStart);
        add(buttonRead);
        buttonRead.addActionListener(e -> {
            File fp = readFile();
            if (fp != null) {
                try {
                    mLifeGame = new LifeGame(fp);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(this, "请选择格式正确的文本文件!", "错误", JOptionPane.WARNING_MESSAGE);
                }
                text.setText(mLifeGame.toString());
            }
        });
        buttonStart.addActionListener(e -> {
            try {
                mLifeGame = new LifeGame(text.getText());
            } catch (Exception e1) {
                JOptionPane.showMessageDialog(this, "请输入格式正确的文本!", "错误", JOptionPane.WARNING_MESSAGE);
            }

            try {
                lifeCicles = Integer.valueOf(textLifeCircles.getText());
            } catch (java.lang.NumberFormatException e0) {
                JOptionPane.showMessageDialog(this, "请输入合法的循环次数!", "错误", JOptionPane.WARNING_MESSAGE);
                lifeCicles = 0;
            }
            new Thread(() -> {
                {
                    while (mLifeGame.getLifeCircles() < lifeCicles) {
                        mLifeGame.shift();
                        text.setText(mLifeGame.toString());
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e2) {
                            System.out.println(e2);
                        }
                    }
                }
            }).start();
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new LifeGameFrame();
    }

    private File readFile() {
        JFileChooser fc = new JFileChooser();
        int returnval = fc.showOpenDialog(this);
        if ((returnval == JFileChooser.APPROVE_OPTION)) {
            return fc.getSelectedFile();
        } else {
            return null;
        }
    }
}

