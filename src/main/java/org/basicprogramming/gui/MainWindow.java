package org.basicprogramming.gui;

import org.basicprogramming.Main;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

    public MainWindow() {
        JFrame frame = new JFrame("My First GUI");
        // задаём путь к иконке (т.е. где находится файл)
        String path = "../images/icon.png";
        // создание через обращение к главному классу приложения и его ресурсам

        //ImageIcon icon = new ImageIcon(MainWindow.class.getResource(path));
        // помещение иконки на frame
        //frame.setIconImage(icon.getImage());
        JPanel buttonsPanel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // задание геометрии окна
        frame.setSize(300, 300);
        // запрет изменения размеров
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        JButton start = new JButton("Старт");
        JButton stop = new JButton("Стоп");
        buttonsPanel.add(start);
        buttonsPanel.add(stop);
        frame.getContentPane().add(BorderLayout.NORTH, buttonsPanel);
        frame.setVisible(true);

        var fileChooser = new FileChooser(this);
        fileChooser.setupFileChooser();
        fileChooser.showChoose();
    }

    public static void main(String[] args) {
        new MainWindow();
    }
}
