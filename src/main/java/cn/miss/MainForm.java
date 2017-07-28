package cn.miss;

import cn.miss.client.HttpClientManager;
import cn.miss.utils.CallBack;

import javax.swing.*;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/7/28.
 */
public class MainForm implements CallBack {
    private final HttpClientManager manager;
    private final JFrame frame;
    private JPanel panel1;
    private JTextField url;
    private JButton startBtn;
    private JTextPane textArea;
    private JComboBox selectMode;
    private JComboBox browser;
    private JComboBox method;
    private JComboBox comboBox3;
    private JButton cookieBtn;
    private String cookie = "";

    public MainForm(JFrame frame) {
        this.frame = frame;
        manager = new HttpClientManager();
        manager.setCallback(this);
        startBtn.addActionListener(e -> {
            startBtn.setEnabled(false);
            manager.setMethod((String) method.getSelectedItem()).
                    setBrowser((String) browser.getSelectedItem()).
                    setUrlType((String) selectMode.getSelectedItem()).
                    setCookies(cookie);
            manager.start();
        });
        cookieBtn.addActionListener(e -> {
            cookie = JOptionPane.showInputDialog(frame, "", "请输入Cookie", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainForm");
        frame.setContentPane(new MainForm(frame).panel1);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500, 400);//设置窗体大小
        frame.setLocationRelativeTo(null);//设置居中
    }

    @Override
    public void callback() {
        JOptionPane.showMessageDialog(panel1, "抓取完成", "提示", JOptionPane.INFORMATION_MESSAGE);
        startBtn.setEnabled(true);
    }
}
