package BookManageSystem.controller;

import BookManageSystem.beans.BookTypeBean;
import BookManageSystem.dao.BookDao;
import BookManageSystem.dao.BookTypeDao;
import BookManageSystem.tools.SimpleTools;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.Struct;
import java.util.List;

public class BookAddFrameController {

    @FXML
    private TextField bookNameTextField;

    @FXML
    private TextField bookAuthorTextField;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private ToggleGroup sex;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private TextField bookPriceTextField;

    @FXML
    private ComboBox<?> bookTypeComboBox;

    @FXML
    private TextArea bookDescriptionTextArea;

    @FXML
    private Button addBookButton;

    @FXML
    private Button resetBookButton;

    SimpleTools simpleTools = new SimpleTools();

    /**
     * 初始化界面控件数据
     */
    public void initialize() {
        //批量为按钮添加图标
        Labeled[] labeleds = new Labeled[]{addBookButton, resetBookButton};
        String[] imagePaths = new String[]{"src/BookManageSystem/images/add.png", "src/BookManageSystem/images/reset.png"};
        simpleTools.setLabeledImage(labeleds, imagePaths);
        //查询所有图书类别的SQL语句
        String getBookTypeSql = "select * from tb_bookType";
        //获取所有图书类别的数据
        List bookTypeList = new BookTypeDao().getRecordsDataBySql(getBookTypeSql);
        //获取所有图书类别名称
        String[] typeNames = new String[bookTypeList.size()];
        for (int i = 0; i < bookTypeList.size(); i++) {
            BookTypeBean bookTypeBean = (BookTypeBean) bookTypeList.get(i);
            typeNames[i] = bookTypeBean.getBookTypeName();
        }
        //初始化下拉列表框选项
        simpleTools.addComboBoxItems(bookTypeComboBox, typeNames);
    }

    /**
     * 添加按钮 事件监听器
     * @param event 事件
     */
    @FXML
    void addBookButtonEvent(ActionEvent event) {
        //获取用户输入的图书名称、图书作者
        String bookName = bookNameTextField.getText().trim();
        String bookAuthor = bookAuthorTextField.getText().trim();
        //获取用户选中的图书作者性别
        String authorSex = "";
        if (maleRadioButton.isSelected()) {
            authorSex = maleRadioButton.getText();
        } else if(femaleRadioButton.isSelected()) {
            authorSex = femaleRadioButton.getText();
        }
        //获取用户输入的图书价格
        String bookPrice = bookPriceTextField.getText().trim();
        //获取用户选择的图书类别
        String bookType = (String) bookTypeComboBox.getSelectionModel().selectedItemProperty().getValue();
        //获取用户输入的图书描述
        String bookDescription = bookDescriptionTextArea.getText().trim();

        //条件查询图书类别
        String bookTypeSql = "select * from tb_bookType where btName = '" + bookType + "';";
        List bookTypeList = new BookTypeDao().getRecordsDataBySql(bookTypeSql);
        BookTypeBean bookTypeBean = (BookTypeBean) bookTypeList.get(0);
        //获取图书类别id号
        int bookTypeId = bookTypeBean.getBookTypeId();
        //组装添加数据的sql语句
        String addBookSql = "insert into tb_book(bBookName, bAuthor, bSex, bPrice, bBookDescription, btId) values('"
                + bookName + "', '" + bookAuthor + "', '" + authorSex + "', " + bookPrice + ", '" + bookDescription + "', " + bookTypeId + ");";
        boolean isExecute = new BookDao().dataChange(addBookSql);
        if(isExecute) { //成功，重置用户输入，弹出提示框
            resetContent();
            simpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "添加成功！");
        } else {    //失败，弹出提示框
            simpleTools.informationDialog(Alert.AlertType.ERROR, "提示", "错误", "添加失败！");
        }
    }

    /**
     * 重置按钮 事件监听器
     * @param event 事件
     */
    @FXML
    void resetBookButtonEvent(ActionEvent event) {
        resetContent();
    }

    /**
     * 重置文本框、单选按钮和下拉列表框
     */
    private void resetContent() {
        simpleTools.clearTextFiled(bookNameTextField, bookAuthorTextField, bookPriceTextField, bookDescriptionTextArea);
        simpleTools.clearSelectedRadioButton(maleRadioButton, femaleRadioButton);
        simpleTools.clearSelectedComboBox(bookTypeComboBox);
    }

}
