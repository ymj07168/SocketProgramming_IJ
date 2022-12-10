package com.example.chatting;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Logo extends JFrame implements ActionListener{
    JPanel p1, p2;
    JLabel intro;
    JLabel lbImg;


    private Image background=new ImageIcon(Logo.class.getResource("logo_back.png")).getImage();
//    Image logo = background.getScaledInstance(300, 300, Image.SCALE_SMOOTH);


    public Logo() {
        super();
        setTitle("Good Parking");
//        setSize(300, 300);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLUE);
        setBounds(300, 300, 300, 300);
        setLayout(null);
//
//        // 패널1
//        p1 = new JPanel();
//        p1.setBackground(Color.BLUE);
//        ImageIcon img = new ImageIcon("car.png");
//        lbImg = new JLabel(img);
//        p1.add(lbImg);
//        p1.setBounds(50, 100, 100, 100);
//
//
//        // 패널2
//        p2 = new JPanel();
//        p2.setBackground(Color.BLUE);
//        intro = new JLabel("Good Parking");
//        Font font = new Font("맑은 고딕", Font.BOLD, 30);
//        intro.setFont(font);
//        intro.setForeground(Color.white);
//        add(intro);
//        p2.add(intro);
//        p2.setBounds(50, 50, 200, 50);
//
//        add(p1);
//        add(p2);

        setVisible(true);

    }

    public void paint(Graphics g) {//그리는 함수
        g.drawImage(background, 0, 0, null);//background를 그려줌
    }
    public static void main(String[] args){
        new Logo();
    }




    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
