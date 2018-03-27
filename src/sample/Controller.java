package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

import java.util.Random;

public class Controller {
    @FXML
    private AnchorPane root;

    @FXML
    private ProgressBar progressBar1;

    @FXML
    private ProgressBar progressBar2;

    XYChart.Series progressBar1Series = new XYChart.Series();
    XYChart.Series progressBar2Series = new XYChart.Series();

    private boolean exit = false;

    public void exit() {
        exit = true;
    }

    @FXML
    public void initialize() {
        progressBar1Series.setName("ProgressBar 1");
        progressBar2Series.setName("ProgressBar 2");
        initThread(progressBar1, progressBar1Series).start();
        initThread(progressBar2, progressBar2Series).start();
    }

    private Thread initThread(ProgressBar progressBar, XYChart.Series series) {
        return new Thread(() -> {
            int time = 0;
            while (true) {
                progressBar.setProgress(progressBar.getProgress() + 0.01 * new Random().nextInt(2));
                time++;
                series.getData().add(new XYChart.Data(time, progressBar.getProgress()));
                if (progressBar.getProgress() >= 1.0 || exit) {
                    break;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
