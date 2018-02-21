package mpl.backend.gesps;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * Created by Marilia Portela on 20/03/2017.
 */
public class BrowserSample extends Application {
//    public static void main(String[] args) {
//        Browser browser = new Browser();
//        BrowserView browserView = new BrowserView(browser);
//        JFrame frame = new JFrame("JxBrowser");
//        frame.add(browserView, BorderLayout.CENTER);
//        frame.setSize(700, 500);
//        frame.setVisible(true);
//        browser.loadURL("http://www.google.com");
//    }


        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage stage) throws Exception {
            stage.setTitle("JavaFX: WebView");

            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();

            String url = "http://www.google.com.au";
            webEngine.load(url);

            StackPane sp = new StackPane();
            sp.getChildren().add(webView);

            Scene root = new Scene(sp);

            stage.setScene(root);
            stage.show();
        }

}
