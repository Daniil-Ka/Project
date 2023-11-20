package org.basicprogramming.gui;

import org.apache.commons.math3.exception.NullArgumentException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.FileChooserUI;
import java.awt.*;
import java.io.File;

public class FileChooser {
    private static final String TITLE = "Выберете файл";
    private static final String HOME = "user.home";

    private static final FileFilter[] FILTERS = new FileFilter[] {
            new ExtensionFilter(".xlsx", "файлы с расширением xlsl")
    };

    private final Component component;

    private JFileChooser fileChooser;

    public FileChooser(Component component) {
        this.component = component;
        fileChooser = new JFileChooser();
    }

    public void setupFileChooser() {
        fileChooser.setDialogTitle(TITLE);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        for (var filter : FILTERS) {
            fileChooser.addChoosableFileFilter(filter);
        }
    }

    private void setDefaultPath() {
        String home = System.getProperty(HOME);
        var homeFolder = new File(home);
        fileChooser.setCurrentDirectory(homeFolder);
    }

    public void showChoose() {

        var result = fileChooser.showOpenDialog(component);
        if (result == JFileChooser.APPROVE_OPTION) {
            JOptionPane.showMessageDialog(component, fileChooser.getSelectedFile().toString());
        }
    }
}

class ExtensionFilter extends FileFilter {
    private String extension;
    private String description;

    ExtensionFilter(String extension, String description) {
        if (extension == null) {
            throw new NullArgumentException();
        }

        this.extension = extension;
        this.description = description;
    }

    @Override
    public boolean accept(File f) {
        if (f != null) {
            if (f.isDirectory())
                return true;
            var name = f.getName();
            var t = f.getName().endsWith(extension);
            return f.getName().endsWith(extension);
        }
        return false;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
