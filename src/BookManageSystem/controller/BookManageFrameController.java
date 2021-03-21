package BookManageSystem.controller;

import BookManageSystem.beans.BookBeanTableData;
import BookManageSystem.beans.BookTypeBean;
import BookManageSystem.dao.BookDao;
import BookManageSystem.dao.BookTypeDao;
import BookManageSystem.tools.SimpleTools;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class BookManageFrameController {

    @FXML
    private TextField bookNameTextField;

    @FXML
    private TextField bookAuthorTextField;

    @FXML
    private ComboBox<?> bookTypeComboBox;

    @FXML
    private Button checkButton;

    @FXML
    private Button resetButton;

    @FXML
    private TableView<?> bookManageTableView;

    @FXML
    private TableColumn<BookBeanTableData, String> bookIdTableColumn;

    @FXML
    private TableColumn<BookBeanTableData, String> bookNameTableColumn;

    @FXML
    private TableColumn<BookBeanTableData, String> bookAuthorTableColumn;

    @FXML
    private TableColumn<BookBeanTableData, String> bookAuthorSexTableColumn;

    @FXML
    private TableColumn<BookBeanTableData, String> bookPriceTableColumn;

    @FXML
    private TableColumn<BookBeanTableData, String> bookDescriptionTableColumn;

    @FXML
    private TableColumn<BookBeanTableData, String> bookTypeTableColumn;

    @FXML
    private TextField bookIdTextField;

    @FXML
    private TextField bookPriceTextField;

    @FXML
    private TextField bookNameTextField2;

    @FXML
    private TextField bookAuthorTextField2;

    @FXML
    private RadioButton maleRadioButton;

    @FXML
    private ToggleGroup sex;

    @FXML
    private RadioButton femaleRadioButton;

    @FXML
    private ComboBox<?> bookTypeComboBox2;

    @FXML
    private TextArea bookDescriptionTextArea;

    @FXML
    private Button changeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button resetButton2;

    SimpleTools simpleTools = new SimpleTools();
    BookDao bookDao = new BookDao();

    /**
     * 初始化
     */
    public void initialize() {
        //设置按钮图标
        Labeled[] labeleds = new Labeled[]{changeButton, deleteButton, resetButton2};
        String[] imagePaths = new String[]{"src/BookManageSystem/images/edit.png",
                "src/BookManageSystem/images/delete.png", "src/BookManageSystem/images/reset.png"};
        simpleTools.setLabeledImage(labeleds, imagePaths);
        bookIdTextField.setDisable(true);
        //设置图书表格显示内容
        //查询图书信息sql语句
        String sql = "select bId,bBookName,bAuthor,bSex,bPrice,bBookDescription,btName from tb_book,tb_bookType where tb_book.btId=tb_bookType.btId;";
        simpleTools.setBookTableViewDate(bookManageTableView, simpleTools.getBookTableViewData(sql),
                bookIdTableColumn, bookNameTableColumn, bookAuthorTableColumn, bookAuthorSexTableColumn,
                bookPriceTableColumn, bookDescriptionTableColumn, bookTypeTableColumn);
        //设置下拉列表显示内容
        //查询图书类别sql语句
        String getBookTypeSql = "select * from tb_bookType";
        List bookTypeList = new BookTypeDao().getRecordsDataBySql(getBookTypeSql);
        String[] typeNames = new String[bookTypeList.size()];
        for (int i = 0; i < bookTypeList.size(); i++) {
            BookTypeBean bookTypeBean = (BookTypeBean) bookTypeList.get(i);
            typeNames[i] = bookTypeBean.getBookTypeName();
        }
        simpleTools.addComboBoxItems(bookTypeComboBox, typeNames);
        simpleTools.addComboBoxItems(bookTypeComboBox2, typeNames);
        //为表格注册事件监听器
        bookManageTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showBookDetails((BookBeanTableData) newValue));
    }

    /**
     * 选中图书表格某行后，将行内容显示在下面的文本框中
     * @param bookBeanTableData 表格行数据
     */
    public void showBookDetails(BookBeanTableData bookBeanTableData) {
        if (bookManageTableView.getSelectionModel().getSelectedIndex() >= 0) {  //选中表格中的某一行
            bookIdTextField.setText(bookBeanTableData.getBookId());
            bookNameTextField2.setText(bookBeanTableData.getBookName());
            if(bookBeanTableData.getBookAuthorSex().equals("男")) {
                maleRadioButton.setSelected(true);
            } else if (bookBeanTableData.getBookAuthorSex().equals("女")){
                femaleRadioButton.setSelected(true);
            }
            bookPriceTextField.setText(bookBeanTableData.getBookPrice());
            bookAuthorTextField2.setText(bookBeanTableData.getBookAuthor());
            //设置分类
            int index = 0;
            List inputList = FXCollections.observableArrayList(bookTypeComboBox.getItems());
            for (int i = 0; i < inputList.size(); i++) {
                if (bookBeanTableData.getBookType().equals(inputList.get(i))) {
                    index = i;
                    break;
                }
            }
            bookTypeComboBox2.getSelectionModel().select(index);
            bookDescriptionTextArea.setText(bookBeanTableData.getBookDescription());
        }
    }

    /**
     * 修改按钮 事件监听器
     * @param event 事件
     */
    @FXML
    void changeButtonEvent(ActionEvent event) {
        //获取用户输入的数据
        String bookId = bookIdTextField.getText();
        String bookName = bookNameTextField2.getText();
        String bookAuthorSex = "";
        if (maleRadioButton.isSelected()) {
            bookAuthorSex = maleRadioButton.getText();
        } else if (femaleRadioButton.isSelected()) {
            bookAuthorSex = femaleRadioButton.getText();
        }
        String bookPrice = bookPriceTextField.getText();
        String bookAuthor = bookAuthorTextField2.getText();
        String bookType = (String) bookTypeComboBox2.getSelectionModel().selectedItemProperty().getValue();
        String bookDescription = bookDescriptionTextArea.getText();
        //组成sql语句
        String bookTypeSql = "select * from tb_bookType where btName = '" + bookType + "';";
        List bookTypeList = new BookTypeDao().getRecordsDataBySql(bookTypeSql);
        BookTypeBean bookTypeBean = (BookTypeBean) bookTypeList.get(0);
        //获取图书类别id
        int bookTypeId = bookTypeBean.getBookTypeId();
        //组成修改数据库的sql语句
        String changeSql = "update tb_book set bBookName='" + bookName + "',bAuthor='" + bookAuthor + "',bSex='" + bookAuthorSex +
                "',bPrice=" + bookPrice + ",bBookDescription='" + bookDescription + "',btId=" + bookTypeId + " where " +
                "bId=" + bookId + ";";
        boolean isExecute = bookDao.dataChange(changeSql);
        if (isExecute) {
            initialize();
            simpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "修改成功！");
            reset2ButtonEvent(null);
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
        String sql = "select bId,bBookName,bAuthor,bSex,bPrice,bBookDescription,btName from tb_book,tb_bookType where tb_book.btId=tb_bookType.btId ";
        //判断用户是否输入图书名称，模糊查询
        if (!simpleTools.isEmpty(bookNameTextField.getText())) {
            sql += " and bBookName like '%" + bookNameTextField.getText() + "%'";
        }
        //判断用户是否输入作者名称，模糊查询
        if (!simpleTools.isEmpty(bookAuthorTextField.getText())) {
            sql += " and bAuthor like '%" + bookAuthorTextField.getText() + "%'";
        }
        //判断用户是否选择图书类别
        String bookType = (String) bookTypeComboBox.getSelectionModel().selectedItemProperty().getValue();
        if (!simpleTools.isEmpty(bookType)) {
            sql += " and btName = '" + bookType + "';";
        }
        //通过sql语句查询数据，填写到表格中，并刷新显示表格
        ObservableList data = simpleTools.getBookTableViewData(sql);
        simpleTools.setBookTableViewDate(bookManageTableView, data,
                bookIdTableColumn, bookNameTableColumn, bookAuthorTableColumn, bookAuthorSexTableColumn, bookPriceTableColumn, bookDescriptionTableColumn, bookTypeTableColumn);
    }

    /**
     * 删除按钮 事件监听器
     * @param event 事件
     */
    @FXML
    void deleteButtonEvent(ActionEvent event) {
        String bookId = bookIdTextField.getText();
        String deleteSql = "delete from tb_book where bId = " + bookId + ";";
        boolean isExecute = bookDao.dataChange(deleteSql);
        if (isExecute) {
            initialize();
            reset2ButtonEvent(null);
            simpleTools.informationDialog(Alert.AlertType.INFORMATION, "提示", "信息", "删除成功！");
        } else {
            simpleTools.informationDialog(Alert.AlertType.ERROR, "提示", "错误", "删除失败！");
        }
    }

    @FXML
    void reset2ButtonEvent(ActionEvent event) {
        simpleTools.clearTextFiled(bookIdTextField, bookNameTextField2, bookPriceTextField, bookAuthorTextField2, bookDescriptionTextArea);
        simpleTools.clearSelectedRadioButton(maleRadioButton, femaleRadioButton);
        simpleTools.clearSelectedComboBox(bookTypeComboBox2);
    }

    @FXML
    void resetButtonEvent(ActionEvent event) {
        simpleTools.clearTextFiled(bookNameTextField, bookAuthorTextField);
        simpleTools.clearSelectedComboBox(bookTypeComboBox);
        initialize();
    }

}
