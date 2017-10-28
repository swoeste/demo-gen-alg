package de.swoeste.demo.gen.alg.ui;

import java.util.ArrayList;
import java.util.List;

import de.swoeste.demo.gen.alg.model.world.Tile;
import de.swoeste.demo.gen.alg.model.world.World;
import de.swoeste.demo.gen.alg.ui.model.UITile;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class UIWorldController {

    @FXML
    private SplitPane    rootPane;

    @FXML
    private ScrollPane   controlPane;

    @FXML
    private Label        fpsLabel;

    @FXML
    private ScrollPane   worldPane;

    @FXML
    private Canvas       worldCanvas;

    private final long[] frameTimes     = new long[100];
    private int          frameTimeIndex = 0;
    private boolean      arrayFilled    = false;

    @FXML
    void initialize() {
        Canvas canvas = getWorldCanvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();

        final List<UITile> uiTiles = new ArrayList<>();

        final int tilesize = 40;
        final int gap = 2;

        final World world = new World(25);
        Tile[][] tiles = world.getTiles();
        for (int i = 0; i < tiles.length; i++) {
            Tile[] tiles2 = tiles[i];
            for (int j = 0; j < tiles2.length; j++) {
                final Tile tile = tiles2[j];
                // gc.fillRect((i * 12) + 20, (j * 12) + 20, 10, 10);
                uiTiles.add(new UITile(tile, i * (tilesize + gap), j * (tilesize + gap), tilesize));
            }

        }

        // 0 * 12 + 20 = 20 |

        Circle circle = new Circle();

        Rectangle rectangle = new Rectangle();
        // rectangle.contains(localX, localY)

        // Image earth = new Image("img/earth.png");
        // Image sun = new Image("img/sun.png");
        // Image space = new Image("img/space.png");

        // Label label = new Label();

        // gc.drawImage(space, 0, 0);

        // Sprite sprite = new Sprite();

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(final MouseEvent e) {

                System.out.println("Clicked: " + e.getX() + " " + e.getY());

                // canvas.setScaleX(4);
                // canvas.setScaleY(4);

                // if (rectangle.contains(e.getX(), e.getY())) {
                // double x = 50 + (400 * Math.random());
                // double y = 50 + (400 * Math.random());
                // targetData.setCenter(x, y);
                // points.value++;
                // } else {
                // points.value = 0;
                // }
            }
        });

        new AnimationTimer() {
            @Override
            public void handle(final long currentNanoTime) {
                final double maxWidth = gc.getCanvas().getWidth();
                final double maxHeight = gc.getCanvas().getHeight();

                // clear the whole canvas
                gc.setFill(Color.BLACK);
                gc.fillRect(0, 0, maxWidth, maxHeight);

                // draw tiles
                for (UITile tiles2 : uiTiles) {
                    gc.setFill(Color.BLUE);
                    gc.fillRect(tiles2.getX(), tiles2.getY(), tiles2.getSize(), tiles2.getSize());

                }

                // calculate fps
                // TODO move to extra class
                long oldFrameTime = UIWorldController.this.frameTimes[UIWorldController.this.frameTimeIndex];
                UIWorldController.this.frameTimes[UIWorldController.this.frameTimeIndex] = currentNanoTime;
                UIWorldController.this.frameTimeIndex = (UIWorldController.this.frameTimeIndex + 1) % UIWorldController.this.frameTimes.length;
                if (UIWorldController.this.frameTimeIndex == 0) {
                    UIWorldController.this.arrayFilled = true;
                }
                if (UIWorldController.this.arrayFilled) {
                    long elapsedNanos = currentNanoTime - oldFrameTime;
                    long elapsedNanosPerFrame = elapsedNanos / UIWorldController.this.frameTimes.length;
                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame;
                    // label.setText(String.format("Current frame rate: %.3f", frameRate));
                    // gc.fillText(String.format("Current frame rate: %.1f", frameRate), 10, 10);
                    UIWorldController.this.fpsLabel.setText(String.format("Current frame rate: %.1f", frameRate));
                }
            }
        }.start();
    }

    public Canvas getWorldCanvas() {
        return this.worldCanvas;
    }

}
