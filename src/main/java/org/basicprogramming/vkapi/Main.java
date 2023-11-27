package org.basicprogramming.vkapi;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.swing.*;

class VKWindow extends JFrame {

    public VKWindow() {
        JFrame frame = new JFrame("My First GUI");

        //String path = "../images/icon.png";
        //ImageIcon icon = new ImageIcon(MainWindow.class.getResource(path));
        //frame.setIconImage(icon.getImage());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // задание геометрии окна
        frame.setSize(300, 300);
        // запрет изменения размеров
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        JFXPanel jfxPanel = new JFXPanel();
        frame.add(jfxPanel);

// Creation of scene and future interactions with JFXPanel
// should take place on the JavaFX Application Thread
        Platform.runLater(() -> {
            WebView webView = new WebView();
            jfxPanel.setScene(new Scene(webView));
            webView.getEngine().load("http://www.stackoverflow.com/");
            /*
                  final WebView view = new WebView();
        final WebEngine engine = view.getEngine();
        engine.load(VK_AUTH_URL);


        primaryStage.setScene(new Scene(view));
        primaryStage.show();

        engine.locationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String string, String t1) {
                return;
            }
        });
             */
        });
    }

    public static void main(String[] args){
        new VKWindow();
    }

}


public class Main extends Application{
    public static final String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    public static final String VK_AUTH_URL = ""; //TODO!!!
    public static String tokenUrl;

    public static void main(String[] args){
        System.out.println(Main.getTokenUrl());
    }

    public static String getTokenUrl(){
        launch(Main.class);
        return tokenUrl;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {


    }

}