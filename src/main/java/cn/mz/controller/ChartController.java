package cn.mz.controller;

import cn.mz.db.dto.Cate;
import cn.mz.db.imp.MysqlConnection;
import cn.mz.util.TooUtil;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @version 1.0
 * @Description TODD
 * @Author wmazh
 * @Date 2020-11-03 0003 11:32
 */
public class ChartController {

    Task worker;

    @FXML
    private AnchorPane pane;

    @FXML
    private Button startButton;

    @FXML
    private ProgressBar progressbar;

    @FXML
    private ProgressIndicator proindicator;

    @FXML
    void start(MouseEvent event) throws InterruptedException {

        worker = createTask();
        progressbar.progressProperty().bind(worker.progressProperty());
        proindicator.progressProperty().bind(worker.progressProperty());
        new Thread(worker).start();



    }

    private Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for(int i=0;i<=10;i++){
                    TimeUnit.SECONDS.sleep(1);
                    updateProgress(i,10);
                    updateMessage(i*10+"%");
                }
                return true;
            }
        };
    }




}
