package org.basicprogramming;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoadWebPage extends Application{
    public static final String REDIRECT_URL = "https://oauth.vk.com/blank.html";
    public static final String APP_ID = "51802885";
    public static final String TOKEN_NAME = "";
    public static final String VK_AUTH_URL = "https://oauth.vk.com/authorize?client_id=" +
            APP_ID +
            "&response_type=token";
    public static String tokenUrl;

    public static void main(String[] args){
        System.out.println(LoadWebPage.getTokenUrl());
    }

    public static String getTokenUrl(){
        launch(LoadWebPage.class);
        return tokenUrl;
    }

    public static Map<String, List<String>> getQueryParams(String url) {
        Map<String, List<String>> params = new HashMap<>();
        String[] urlParts = url.split("#");
        if (urlParts.length > 1) {
            String query = urlParts[1];
            for (String param : query.split("&")) {
                String[] pair = param.split("=");
                String key = URLDecoder.decode(pair[0], StandardCharsets.UTF_8);
                String value = "";
                if (pair.length > 1) {
                    value = URLDecoder.decode(pair[1], StandardCharsets.UTF_8);
                }

                List<String> values = params.computeIfAbsent(key, k -> new ArrayList<>());
                values.add(value);
            }
        }

        return params;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final WebView view = new WebView();
        final WebEngine engine = view.getEngine();
        engine.load(VK_AUTH_URL);


        primaryStage.setScene(new Scene(view));
        primaryStage.show();

        engine.locationProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                if (newValue instanceof String url) {
                    if(url.startsWith(REDIRECT_URL)){
                        var params = getQueryParams(url);
                        tokenUrl = params.get("a").get(0);
                        primaryStage.close();
                    }
                }

            }


        });

    }

}