package BookManageSystem.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class SoftInformationFrameController {

    private Stage dialogStage;

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private ImageView imageView;

    @FXML
    private Hyperlink hyperlink;

    @FXML
    private Button closeButton;

    @FXML
    public void initialize() {
        hyperlink.setText("相关GitHub链接");
        imageView.setImage(new Image("file:src/BookManageSystem/images/panda.png"));
    }

    /**
     * 关闭按钮 事件监听器
     * @param event 事件
     */
    @FXML
    void closeButtonEvent(ActionEvent event) {
        dialogStage.close();
    }

    /**
     * 超链接  事件监听器
     * @param event 事件
     */
    @FXML
    void hyperlinkEvent(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("https://www.hao123.com/"));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
