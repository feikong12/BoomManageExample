package BookManageSystem.controller;

import BookManageSystem.MainApp;
import BookManageSystem.tools.SimpleTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class MainFrameController {

    @FXML
    private MenuItem bookTypeAddMenuItem;

    @FXML
    private MenuItem bookTypeManageMenuItem;

    @FXML
    private MenuItem bookAddMenuItem;

    @FXML
    private MenuItem bookManageMenuItem;

    @FXML
    private MenuItem exitSoftMenuItem;

    @FXML
    private MenuItem aboutSoftMenuItem;

    @FXML
    private AnchorPane mainFrameAnchorPane;

    @FXML
    private ImageView mainBookManageImageView;

    private SimpleTools simpleTools = new SimpleTools();

    /**
     * 初始化启动
     */
    public void initialize() {
        MenuItem[] menuItems = new MenuItem[]{bookTypeAddMenuItem, bookTypeManageMenuItem,
                bookAddMenuItem, bookManageMenuItem,
                exitSoftMenuItem, aboutSoftMenuItem};
        String[] imagePaths = new String[]{"src/BookManageSystem/images/add.png", "src/BookManageSystem/images/edit.png",
                "src/BookManageSystem/images/add.png", "src/BookManageSystem/images/edit.png",
                "src/BookManageSystem/images/exit.png", "src/BookManageSystem/images/about.png"};
        simpleTools.setMenuItemImage(menuItems, imagePaths);
        mainBookManageImageView.setImage(new Image("file:src/BookManageSystem/images/bookmanagesystem.png"));
    }

    /**
     * “关于软件”菜单选项   事件处理
     * @param event 事件
     */
    @FXML
    void aboutSoftMenuItem(ActionEvent event) {
        new MainApp().initAboutSoftFrame();
    }

    /**
     * “图书添加”菜单项按钮  事件处理
     * @param event 事件
     */
    @FXML
    void bookAddMenuItemEvent(ActionEvent event) {
        AnchorPane pane = new MainApp().initBookAddFrame();
        mainFrameAnchorPane.getChildren().clear();
        mainFrameAnchorPane.getChildren().add(pane);
    }

    /**
     * “图书管理”菜单项按钮  事件处理
     * @param event 事件
     */
    @FXML
    void bookManageMenuItemEvent(ActionEvent event) {
        AnchorPane pane = new MainApp().initBookManageFrame();
        mainFrameAnchorPane.getChildren().clear();
        mainFrameAnchorPane.getChildren().add(pane);
    }

    /**
     * “图书类别添加”菜单项按钮    事件处理
     * @param event 事件
     */
    @FXML
    void bookTypeAddMenuItemEvent(ActionEvent event) {
        AnchorPane anchorPane = new MainApp().initBookTypeAddFrame();
        mainFrameAnchorPane.getChildren().clear();
        mainFrameAnchorPane.getChildren().add(anchorPane);
    }

    /**
     * "图书类别管理"菜单项  事件处理
     * @param event 事件
     */
    @FXML
    void bookTypeManageMenuItemEvent(ActionEvent event) {
        AnchorPane anchorPane = new MainApp().initBookTypeManageFrame();
        mainFrameAnchorPane.getChildren().clear();
        mainFrameAnchorPane.getChildren().add(anchorPane);
    }

    /**
     * 菜单项“退出” 事件处理
     * @param event 事件
     */
    @FXML
    void exitSoftMenuItemEvent(ActionEvent event) {
        System.exit(0);
    }

}
