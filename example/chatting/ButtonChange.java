package com.example.chatting;

import java.awt.*;

import javax.swing.JButton;

public class ButtonChange extends JButton {
    public ButtonChange(String text) {
        super(text);
        decorate();
    }

    protected void decorate() { // 버튼 초기값 초기화
        setBorderPainted(false);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Color fontcolor = new Color(249,247,248);
        Color bc = new Color(0,122,255);

        Graphics2D graphics = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();

        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isArmed()) { // 버튼을 누르면 어둡게
            graphics.setColor(bc.darker());
        } else if (getModel().isRollover()) { // 버튼 위에 올리면 밝게
            graphics.setColor(bc.brighter());
        } else {
            graphics.setColor(bc);
        }

        graphics.fillRoundRect(0, 0, w, h, 10, 10);

        FontMetrics fontMetrics = graphics.getFontMetrics(); // 현재 설정된 폰트의 정보 가짐
        Rectangle stringBounds = fontMetrics.getStringBounds(this.getText(), graphics).getBounds(); // 범위

        int textX = (w - stringBounds.width) / 2; // 텍스트의 x축 위치 설정
        int textY = (h - stringBounds.height) / 2 + fontMetrics.getAscent(); // 텍스트의 y축 위치 설정

        graphics.setColor(fontcolor); // 색 설정
        graphics.drawString(getText(), textX, textY); // 텍스트 배치
        graphics.dispose();

        super.paintComponent(g);
    }
}
