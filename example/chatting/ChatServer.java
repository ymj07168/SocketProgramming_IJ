package com.example.chatting;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ChatServer {

    private ServerSocket serverSocket;
    private List<ChatHandler> list;
    private List<String> nameList;
    public ChatServer() {
        try {
            serverSocket = new ServerSocket(8888);
            System.out.println("Chatting Server is ready");
            list = new ArrayList<ChatHandler>();
            nameList = new ArrayList<>();
            while (true) {
                Socket socket = serverSocket.accept();
                ChatHandler handler = new ChatHandler(socket, list);
                handler.start();
                list.add(handler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ChatServer();

    }
}
