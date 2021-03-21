package BookManageSystem;

import BookManageSystem.controller.LoginFrameController;
import BookManageSystem.controller.SoftInformationFrameController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application{
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        this.primaryStage.setTitle("图书馆管理系统");
        initLoginFrame();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * 登录界面
     */
    private void initLoginFrame(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/loginFrame.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setTitle("登录");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);   //不可调整窗口大小

            LoginFrameController controller = loader.getController();
            controller.setStage(primaryStage);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 主界面
     */
    public void initMainFrame() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/mainFrame.fxml"));
            AnchorPane root = loader.load();
            //设置stage舞台属性
            Stage mainFrameStage = new Stage();
            mainFrameStage.setTitle("图书馆管理系统主界面");
            mainFrameStage.setResizable(true);  //可自由改变窗口大小
            mainFrameStage.setAlwaysOnTop(false);   //窗口不会永远置于前端
            mainFrameStage.initModality(Modality.APPLICATION_MODAL);    //阻止输入事件从同一应用程序传递到所有窗口的阶段，除了其子层次结构中的事件
            mainFrameStage.initOwner(primaryStage); //指定父窗口

            Scene scene = new Scene(root);
            mainFrameStage.setScene(scene);
            mainFrameStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 图书类别添加界面
     * @return  返回一个AnchorPane, 便于其他空间调用
     */
    public AnchorPane initBookTypeAddFrame() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("view/bookTypeAddFrame.fxml"));
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图书类别管理界面
     * @return  返回AnchorPane, 便于其他控件调用
     */
    public AnchorPane initBookTypeManageFrame() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/bookTypeManageFrame.fxml"));
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图书添加界面
     * @return  AnchorPane, 便于其他控件调用
     */
    public AnchorPane initBookAddFrame() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/bookAddFrame.fxml"));
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 图书维护界面
     * @return  AnchorPane布局，便于其他控件调用
     */
    public AnchorPane initBookManageFrame() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/bookManageFrame.fxml"));
        try {
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 关于软件 弹出框界面
     */
    public void initAboutSoftFrame() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/softInformationFrame.fxml"));
            AnchorPane page = loader.load();

            Stage mainFrameStage = new Stage();
            mainFrameStage.setTitle("关于软件");
            mainFrameStage.setResizable(true);
            mainFrameStage.setAlwaysOnTop(false);
            mainFrameStage.initModality(Modality.APPLICATION_MODAL);
            mainFrameStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            mainFrameStage.setScene(scene);

            SoftInformationFrameController controller = loader.getController();
            controller.setDialogStage(mainFrameStage);

            mainFrameStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
