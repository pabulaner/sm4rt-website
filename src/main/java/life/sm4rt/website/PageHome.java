package life.sm4rt.website;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class PageHome extends StackPane {

    private static final LocalDateTime DATE = LocalDateTime.of(2025, 6, 1, 0, 0);

    private static final Font FONT = Font.loadFont(PageHome.class.getResourceAsStream("/fonts/Cairo-SemiBold.ttf"), 50);

    private static final String MAIN_COLOR = "#151515FF";

    private static final String DETAIL_COLOR = "#B300ADFF";

    public PageHome() {
        getStylesheets().add("/css/timer.css");

        ImageView image = new ImageView("images/sm4rt.png") {{
            setFitWidth(512);
            setPreserveRatio(true);
        }};

        HBox timer = new HBox() {{
            setAlignment(Pos.CENTER);
            setSpacing(8);

            createTimer(this);
        }};

        getChildren().addAll(new VBox(image, timer) {{
            setAlignment(Pos.CENTER);
            setSpacing(32);
        }});

        StackPane.setAlignment(image, Pos.CENTER);
        StackPane.setAlignment(timer, Pos.BOTTOM_CENTER);

        sceneProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                prefWidthProperty().bind(newValue.widthProperty());
                prefHeightProperty().bind(newValue.heightProperty());
            }
        }));
    }

    private void createTimer(HBox timer) {
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> updateTimer(timer));
            }
        }, 0, 1000);
    }

    private void updateTimer(HBox timer) {
        Duration duration = Duration.between(LocalDateTime.now(), DATE);

        String[] units = { "day", "hour", "min", "sec" };
        long[] values = {
                duration.toDays(),
                duration.toHoursPart(),
                duration.toMinutesPart(),
                duration.toSecondsPart()
        };

        timer.getChildren().clear();

        for (int i = 0; i < units.length; i++) {
            Label leftValueLabel = new Label(String.valueOf(values[i] / 10));
            Label rightValueLabel = new Label(String.valueOf(values[i] % 10));
            Label unitLabel = new Label(units[i]);

            leftValueLabel.getStyleClass().add("timer-value");
            rightValueLabel.getStyleClass().add("timer-value");
            unitLabel.getStyleClass().add("timer-unit");

            timer.getChildren().add(new VBox() {{
                setAlignment(Pos.BOTTOM_CENTER);

                getChildren().add(new HBox(leftValueLabel, rightValueLabel) {{
                    setSpacing(4);
                }});

                // getChildren().add(unitLabel);
            }});

            if (i + 1 != units.length) {
                timer.getChildren().add(new Label(":") {{
                    getStyleClass().add("timer-separator");
                }});
            }
        }
    }
}
