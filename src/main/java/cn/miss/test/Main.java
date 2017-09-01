package cn.miss.test;

import javax.swing.*;

/**
 * @Author MissNull
 * @Description:
 * @Date: Created in 2017/8/3.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("linux");
        JFrame jFrame = new JFrame();
        jFrame.setSize(300,400);
        jFrame.pack();
        jFrame.setVisible(true);
        CellRendererPane cellRendererPane = new CellRendererPane();
        jFrame.setContentPane(cellRendererPane);
        cellRendererPane.add(new JButton("sss"));
    }
}
