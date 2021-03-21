package BookManageSystem.controller;

import BookManageSystem.beans.BookTypeBean;
import BookManageSystem.dao.BookTypeDao;
import BookManageSystem.tools.SimpleTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * @author lzh
 * @date 2021/3/15 18:38
 * @description 图书类别添加 controller
 */
public class BookTypeAddFrameController {
    @FXML
    private TextField bookTypeNameTextField;

    @FXML
    private TextArea bookTypeDescriptionTextArea;

    @FXML
    private Button addButton;

    @FXML
    private Button resetButton;

    private SimpleTools simpleTools = new SimpleTools();

    public void initialize() {
        Labeled[] labeleds = new Labeled[]{addButton, resetButton};
        String[] imagePaths = new String[]{"src/BookManageSystem/images/add.png", "src/BookManageSystem/images/reset.png"};
        simpleTools.setLabeledImage(labeleds, imagePaths);
    }

    /**
     * "添加"按钮   事件监听器方法
     * @param event 事件
     */
    @FXML
    void addButtonEvent(ActionEvent event) {
        //获取图书类别名称
        String bookTypeName = bookTypeNameTextField.getText().trim();
        //获取图书类别描述
        String bookTypeDescription = bookTypeDescriptionTextArea.getText().trim();
        //SQL语句
        String sql = "insert into tb_bookType (btName, btDescription) values ('" + bookTypeName + "', '" + bookTypeDescription + "');";
        boolean isExecute = new BookTypeDao().dataChange(sql);
        if (isExecute) {
            simpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "添加成功！");
            simpleTools.clearTextFiled(bookTypeNameTextField, bookTypeDescriptionTextArea);
        } else {
            simpleTools.informationDialog(Alert.AlertType.ERROR, "提示", "错误", "添加失败！");
        }
    }

    /**
     * “重置”按钮   事件监听器方法
     * @param event 事件
     */
    @FXML
    void resetButtonEvent(ActionEvent event) {
        simpleTools.clearTextFiled(bookTypeNameTextField, bookTypeDescriptionTextArea);
    }
}

