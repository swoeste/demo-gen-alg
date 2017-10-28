package de.swoeste.demo.gen.alg;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author swoeste
 */
public class Main extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    private Stage               primaryStage;

    @Override
    public void start(final Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Infinitum - Genetic Algorithm & Neural Network Demo"); //$NON-NLS-1$
        this.primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("ui/img/infinitum.png"))); //$NON-NLS-1$

        initRoot();
    }

    /**
     * Initializes the root layout.
     */
    public void initRoot() {
        try {

            // Load root layout from fxml file.
            final FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("ui/view/root.fxml")); //$NON-NLS-1$

            final Control root = loader.load();
            final Scene scene = new Scene(root);
            scene.getStylesheets().add(Main.class.getResource("ui/css/styles.css").toExternalForm()); //$NON-NLS-1$

            // Show the scene containing the root layout.
            this.primaryStage.setScene(scene);
            this.primaryStage.show();
        } catch (IOException ex) {
            final String msg = "Unable to initialize user interface"; //$NON-NLS-1$
            LOG.error(msg, ex);
            throw new IllegalStateException(msg, ex);
        }
    }

    public static void main(final String[] args) {
        launch(args);
    }

}