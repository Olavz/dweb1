package com.olavz.demo.fxclient;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ClientForm extends Application {

    private final TextArea serverUpdates = new TextArea();
    private SocketRunner socketRunner;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        TextField txtUrl = new TextField("ws://localhost:8888/endpoint");
        Button btnConnect = new Button("Connect");
        TextField txtMessage = new TextField();

        primaryStage.setTitle("FX Client");
        primaryStage.show();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Scene scene = new Scene(grid, 600, 450);
        primaryStage.setScene(scene);

        grid.add(txtUrl, 0, 0);

        grid.add(btnConnect, 1, 0);
        btnConnect.setOnAction(e -> {
            socketRunner = new SocketRunner(txtUrl.getText(), serverUpdates);
            Thread t = new Thread(socketRunner);
            t.start();
            btnConnect.setDisable(true);
            txtUrl.setDisable(true);
            txtMessage.requestFocus();

        });

        Label lblUpdatesFromServer = new Label("Updates from server:");
        grid.add(lblUpdatesFromServer, 0, 1);

        serverUpdates.setMinWidth(300.0);
        grid.add(serverUpdates, 0, 2);


        grid.add(txtMessage, 0, 4);
        txtMessage.setOnKeyPressed(e -> {
            if(e.getCode().equals(KeyCode.ENTER)) {
                socketRunner.sendMessage(txtMessage.getText());
                txtMessage.setText("");
            }
        });
    }
}
