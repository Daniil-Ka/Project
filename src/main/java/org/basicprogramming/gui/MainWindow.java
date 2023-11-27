package org.basicprogramming.gui;

import org.basicprogramming.db.DB;
import org.basicprogramming.db.models.Student;
import org.basicprogramming.loaders.XLSLLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class ThreadLoader implements Runnable {
    public XLSLLoader loader;

    private JDialog dialog;

    public ThreadLoader(XLSLLoader loader, JDialog dialog) {
        this.loader = loader;
        this.dialog = dialog;
    }

    public void run() {
        try {
            loader.load();
        } catch (IOException exception) {
            JOptionPane.showMessageDialog(null, "Ошибка при чтении: " + exception.getMessage());
            exception.printStackTrace();
        }
        finally {
            dialog.dispose();
        }
    }
}

public class MainWindow extends JFrame {

    public MainWindow() {
        JFrame frame = new JFrame("My First GUI");

        //String path = "../images/icon.png";
        //ImageIcon icon = new ImageIcon(MainWindow.class.getResource(path));
        //frame.setIconImage(icon.getImage());

        JPanel buttonsPanel = new JPanel();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // задание геометрии окна
        frame.setSize(300, 300);
        // запрет изменения размеров
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JButton selectFile = new JButton("Открыть файл");
        JButton stop = new JButton("Стоп");
        buttonsPanel.add(selectFile);
        buttonsPanel.add(stop);

        frame.getContentPane().add(BorderLayout.NORTH, buttonsPanel);
        frame.setVisible(true);

        selectFile.addActionListener(e -> {
            try {
                var fileChooser = new FileChooser(MainWindow.this);
                var file = fileChooser.showChoose();

                JDialog modalDialog = new JDialog(frame, "Думает", Dialog.ModalityType.DOCUMENT_MODAL);
                modalDialog.setSize(300, 70);
                modalDialog.setLocationRelativeTo(frame);

                final JProgressBar progressBar = new JProgressBar();
                progressBar.setStringPainted(true);
                modalDialog.add(progressBar);

                var loader = new XLSLLoader(file.getPath(), (current, max) -> {
                    progressBar.setMaximum(max);
                    progressBar.setValue(current);
                });

                var w = new ThreadLoader(loader, modalDialog);
                Thread t2 = new Thread(w);
                t2.start();

                modalDialog.setVisible(true);
            }
            catch (Exception exception) {
                JOptionPane.showMessageDialog(MainWindow.this, "Ошибка: " + exception.getMessage());
                exception.printStackTrace();
            }
        });

        var label = new JLabel();
        buttonsPanel.add(label);

        //label.setText(String.valueOf(t.size()));
    }
}
