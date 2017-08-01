package cn.miss;

import cn.miss.client.HttpClientManager;
import cn.miss.utils.CallBack;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.util.HashMap;
import java.util.Map;

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
    private JTextPane textPanel;
    private JComboBox selectMode;
    private JComboBox browser;
    private JComboBox method;
    private JButton cookieBtn;
    private JButton stopBtn;
    private JButton headerBtn;
    private String cookie = "";
    private JProgressBar progress;
    private JComboBox speed;
    private Map<String, String> header = new HashMap<>();

    public MainForm(JFrame frame) {
        this.frame = frame;
        manager = new HttpClientManager();
        manager.setCallback(this);
        startBtn.addActionListener(e -> {
            startBtn.setEnabled(false);
            progress.setValue(0);
            System.out.println(speed.getSelectedItem());
            manager.setMethod((String) method.getSelectedItem()).
                    setBrowser((String) browser.getSelectedItem()).
                    setUrlType((String) selectMode.getSelectedItem()).
                    setUrl(url.getText()).
                    setSpeed(Long.parseLong((String) speed.getSelectedItem())).
                    setCookies(cookie);
            manager.start();
        });
        cookieBtn.addActionListener(e -> cookie = JOptionPane.showInputDialog(frame, "", "请输入Cookie", JOptionPane.INFORMATION_MESSAGE));
        stopBtn.addActionListener(e -> {
            manager.stopRunnable();
            startBtn.setEnabled(true);
        });
        headerBtn.addActionListener(e -> {
            String he = JOptionPane.showInputDialog(frame, "", "请输入Header", JOptionPane.INFORMATION_MESSAGE);
            if (he != null && !he.isEmpty()) {
                String[] split = he.split("=");
                if (split.length == 2)
                    header.put(split[0], split[1]);
            }
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

    @Override
    public void append(String msg) {
        try {
            Document document = textPanel.getDocument();
            document.insertString(document.getLength(), msg + "\n", null);
        } catch (BadLocationException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void progressChange(int i) {
        progress.setValue(i);
    }
}
