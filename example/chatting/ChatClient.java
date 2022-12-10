package com.example.chatting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient extends JFrame implements ActionListener, Runnable {
    private JTextArea output;
    private JTextField input;
    private JButton sendBtn;
    private Socket socket;
    private ObjectInputStream reader = null;
    private ObjectOutputStream writer = null;
    private String nickName;

    public ChatClient() {
        output = new JTextArea();
        output.setFont(new Font("맑은 고딕", Font.BOLD, 15));
        output.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        input = new JTextField();

        sendBtn = new JButton("send");

        bottom.add("Center", input);
        bottom.add("East", sendBtn);

        Container container = this.getContentPane();
        container.add("Center", scrollPane);
        container.add("South", bottom);

        setBounds(300, 300, 300, 300);
        setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                try {
                    InfoDTO dto = new InfoDTO();
                    dto.setNickName(nickName);
                    dto.setCommand(Info.EXIT);
                    writer.writeObject(dto);
                    writer.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            String msg = input.getText();
            InfoDTO dto = new InfoDTO();

            if (msg.equals("exit")) {
                dto.setCommand(Info.EXIT);
                dto.setNickName(nickName);
            } else if (msg.contains("/to ")) {
                dto.setCommand(Info.WHISPER);
                dto.setNickName(nickName);
                dto.setMessage(msg);
            } else {
                dto.setCommand(Info.SEND);
                dto.setMessage(msg);
                dto.setNickName(nickName);
            }
            writer.writeObject(dto);
            writer.flush();
            input.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void service () {
        String serverIP = JOptionPane.showInputDialog(this, "Enter the Server IP", "192.168.?.?");
        if (serverIP == null || serverIP.length() == 0) {
            System.out.println("Didn't enter Server IP");
            System.exit(0);
        }

        nickName = JOptionPane.showInputDialog(this, "Enter your NickName", "nickname", JOptionPane.INFORMATION_MESSAGE);
        if (nickName == null || nickName.length() == 0) {
            nickName = "guest";
        }

        try {
            socket = new Socket(serverIP, 8888);
            reader = new ObjectInputStream(socket.getInputStream());
            writer = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Client is ready");
        } catch (UnknownHostException e) {
            System.out.println("Can't find the server");
            e.printStackTrace();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Can't connect to server");
            e.printStackTrace();
            System.exit(0);
        }

        try {
            InfoDTO dto = new InfoDTO();
            dto.setCommand(Info.JOIN);
            dto.setNickName(nickName);
            writer.writeObject(dto);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread t = new Thread(this);
        t.start();
        input.addActionListener(this);
        sendBtn.addActionListener(this);
    }

    @Override
    public void run() {
        InfoDTO dto = null;
        while (true) {
            try {
                dto = (InfoDTO) reader.readObject();
                if (dto.getCommand() == Info.EXIT) {
                    reader.close();
                    writer.close();
                    socket.close();
                    System.exit(0);
                } else if (dto.getCommand() == Info.SEND) {
                    output.append(dto.getMessage() + "\n");
                    int pos = output.getText().length();
                    output.setCaretPosition(pos);
                }
            } catch(IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new ChatClient().service();
    }
}
