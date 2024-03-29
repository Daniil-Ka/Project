package org.basicprogramming.gui;

import org.apache.commons.math3.exception.NullArgumentException;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
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
        setupFileChooser();
    }

    public void setupFileChooser() {
        fileChooser.setDialogTitle(TITLE);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        for (var filter : FILTERS) {
            fileChooser.addChoosableFileFilter(filter);
        }
        fileChooser.setFileFilter(FILTERS[0]);
    }

    private void setDefaultPath() {
        String home = System.getProperty(HOME);
        var homeFolder = new File(home);
        fileChooser.setCurrentDirectory(homeFolder);
    }

    public File showChoose() {
        var result = fileChooser.showOpenDialog(component);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
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
            return f.getName().endsWith(extension);
        }
        return false;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
