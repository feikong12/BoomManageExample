package BookManageSystem.controller;

import BookManageSystem.beans.BookTypeBeanTableData;
import BookManageSystem.dao.BookTypeDao;
import BookManageSystem.tools.SimpleTools;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

/**
 * @author lzh
 * @date 2021/3/15 21:28
 * @description 图书类别维修  controller
 */
public class BookTypeManageFrameController {

    @FXML
    private TextField bookTypeNameTextField;

    @FXML
    private Button checkButton;

    @FXML
    private TableView<?> bookTypeManageTableView;

    @FXML
    private TableColumn<BookTypeBeanTableData, String> idTableColumn;

    @FXML
    private TableColumn<BookTypeBeanTableData, String> bookTypeNameTableColumn;

    @FXML
    private TableColumn<BookTypeBeanTableData, String> bookTypeDescriptionTableColumn;

    @FXML
    private VBox formVBox;

    @FXML
    private TextField bookTypeIdTextField;

    @FXML
    private TextField bookTypeNameTextField2;

    @FXML
    private TextArea bookTypeDescriptionTextArea;

    @FXML
    private Button changeButton;

    @FXML
    private Button deleteButton;

    private SimpleTools simpleTools = new SimpleTools();
    private BookTypeDao bookTypeDao = new BookTypeDao();

    public void initialize() {
        //批量为按钮添加图标
        simpleTools.setLabeledImage(
                new Labeled[]{changeButton, deleteButton},
                new String[]{"src/BookManageSystem/images/edit.png", "src/BookManageSystem/images/delete.png"});
        //图书类别编号文本框不可被编辑
        bookTypeIdTextField.setEditable(false);
        //插损所有图书类别
        String sql = "select * from tb_bookType";
        ObservableList<BookTypeBeanTableData> data = simpleTools.getBookTypeTableViewData(sql);
        //将数据添加到表格控件中
        simpleTools.setBookTypeTableViewDate(bookTypeManageTableView, data,
                idTableColumn, bookTypeNameTableColumn, bookTypeDescriptionTableColumn);
        bookTypeManageTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showBookTypeDetails((BookTypeBeanTableData) newValue));
    }

    /**
     * 选中行后将选中行内容显示在下方发文本框中
     * @param bookTypeBeanTableData 选中的图书类别数据
     */
    public void showBookTypeDetails(BookTypeBeanTableData bookTypeBeanTableData) {
        if (bookTypeBeanTableData != null) {
            bookTypeIdTextField.setText(bookTypeBeanTableData.getBookTypeId());
            bookTypeNameTextField2.setText(bookTypeBeanTableData.getBookTypeName());
            bookTypeDescriptionTextArea.setText(bookTypeBeanTableData.getBookTypeDescription());
        }
    }

    /**
     * 修改按钮 事件监听器
     * @param event 事件
     */
    @FXML
    void changeButtonEvent(ActionEvent event) {
        //获取用户输入内容
        String id = bookTypeIdTextField.getText();
        String name = bookTypeNameTextField2.getText();
        String description = bookTypeDescriptionTextArea.getText();
        String sql = "update tb_bookType set btName = '" + name + "', btDescription = '" + description
                + "' where btId = " + id + ";";
        boolean isExecute = bookTypeDao.dataChange(sql);
        if (isExecute) {
            initialize();
            simpleTools.clearTextFiled(bookTypeIdTextField, bookTypeNameTextField2, bookTypeDescriptionTextArea);
            simpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "修改成功！");
        } else {
            simpleTools.informationDialog(Alert.AlertType.ERROR, "提示", "错误", "修改失败！");
        }
    }

    /**
     * 查询按钮 事件监听器
     * @param event 事件
     */
    @FXML
    void checkButtonEvent(ActionEvent event) {
        String bookTypeName = bookTypeNameTextField.getText();
        String checkSql = "";
        if (simpleTools.isEmpty(bookTypeName)) {
            checkSql = "select * from tb_bookType";
        }else {
            checkSql = "select * from tb_bookType where btName = " + bookTypeName + ";";
        }
        ObservableList data = simpleTools.getBookTypeTableViewData(checkSql);
        //重新绘制表格数据
        simpleTools.setBookTypeTableViewDate(bookTypeManageTableView, data,
                idTableColumn, bookTypeNameTableColumn, bookTypeDescriptionTableColumn);
    }

    /**
     * 删除按钮 事件监听器
     * @param event 事件
     */
    @FXML
    void deleteButtonEvent(ActionEvent event) {
        //获取要删除的id
        String id = bookTypeIdTextField.getText();
        //组装执行删除的sql语句
        String sql1 = "set FOREIGN_KEY_CHECKS = 0"; //关闭当前页的外键
        String deleteSql = "delete from tb_bookType where btId = " + id + ";";
        String sql2 = "set FOREIGN_KEY_CHECKS = 1"; //启动当前页的外键
        //弹出确认删除
        boolean isConfirmDelete = simpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "是否删除");
        if (isConfirmDelete) {  //执行删除操作
            bookTypeDao.dataChange(sql1);
            boolean isExecute = bookTypeDao.dataChange(deleteSql);
            bookTypeDao.dataChange(sql2);
            if (isExecute) {
                //删除成功，初始化表格
                initialize();
                //清空用户输入
                simpleTools.clearTextFiled(bookTypeIdTextField, bookTypeNameTextField2, bookTypeDescriptionTextArea);
                //弹出删除失败提示框
                simpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "删除成功");
            } else {
                //弹出删除失败提示框
                simpleTools.informationDialog(Alert.AlertType.ERROR, "提示", "错误", "删除失败！");
            }
        }
    }

}
