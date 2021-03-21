package BookManageSystem.controller;

import BookManageSystem.MainApp;
import BookManageSystem.tools.SimpleTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginFrameController {

    private SimpleTools simpleTools = new SimpleTools();

    @FXML
    private Label systemLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private TextField userNameTextField;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField passwordTextField;

    @FXML
    private Button loginButton;

    @FXML
    private Button resetButton;

    @FXML
    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initialize() {
        //给组件添加图标
        Labeled[] labeleds = new Labeled[]{systemLabel, userNameLabel, passwordLabel, loginButton, resetButton};
        String[] imagePaths = new String[]{"src/BookManageSystem/images/logo.png", "src/BookManageSystem/images/userName.png", "src/BookManageSystem/images/password.png",
                "src/BookManageSystem/images/login.png", "src/BookManageSystem/images/reset.png"};
        simpleTools.setLabeledImage(labeleds, imagePaths);
    }


    /**
     * “登录”按钮   事件监听方法
     * @param event 事件
     */
    @FXML
    void loginButtonEvent(ActionEvent event) {
        boolean isOkLogin = simpleTools.isLogin(userNameTextField, passwordTextField, "张三", "123456");
        if (isOkLogin) {
            //成功登录，关闭登录界面，跳转到主界面
            stage.close();
            new MainApp().initMainFrame();
        }
    }

    /**
     * “重置”按钮   事件监听方法
     * @param event 事件
     */
    @FXML
    void resetButtonEvent(ActionEvent event) {
        simpleTools.clearTextFiled(userNameTextField, passwordTextField);
    }

}
