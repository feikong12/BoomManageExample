package BookManageSystem.tools;

import BookManageSystem.beans.BookBean;
import BookManageSystem.beans.BookBeanTableData;
import BookManageSystem.beans.BookTypeBean;
import BookManageSystem.beans.BookTypeBeanTableData;
import BookManageSystem.dao.BookDao;
import BookManageSystem.dao.BookTypeDao;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.util.List;
import java.util.Optional;

public class SimpleTools {

    /**
     * 设置按钮、标签等组件的图标
     * @param labeleds  要设施的组件
     * @param imagePaths    图标路径
     */
    public void setLabeledImage(Labeled[] labeleds, String[] imagePaths) {
        for (int i = 0; i < labeleds.length; i++) {
            labeleds[i].setGraphic(new ImageView(new Image("file:" + imagePaths[i])));
        }
    }

    /**
     * 为菜单项组件设置图标
     * @param menuItems     菜单项
     * @param imagePaths    图标路径
     */
    public void setMenuItemImage(MenuItem[] menuItems, String[] imagePaths) {
        for (int i = 0; i < menuItems.length; i++) {
            menuItems[i].setGraphic(new ImageView(new Image("file:" + imagePaths[i])));
        }
    }

    /**
     * 判断是否为空
     * @param str   判断文本
     * @return  不为空返回true, 否则返回false
     */
    public boolean isEmpty(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 自定义一个对话框
     * @param alertType 对话框类型
     * @param title     对话框标题
     * @param header    对话框头信息
     * @param massage   对话框显示内容
     * @return  确认返回true, 否则返回false
     */
    public boolean informationDialog(Alert.AlertType alertType, String title, String header, String massage) {
        Alert alert = new Alert(alertType, massage,
                new ButtonType("取消", ButtonBar.ButtonData.CANCEL_CLOSE),
                new ButtonType("确认", ButtonBar.ButtonData.YES));
        //设置对话框的标题
        alert.setTitle(title);
        alert.setHeaderText(header);
        //showAndWait() 将在对话框消失以前不会执行后面的代码
        Optional<ButtonType> buttonType = alert.showAndWait();
        //根据点击结果返回，确认返回true
        return buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES);
    }

    /**
     * 判断是否登录成功
     * @param userNameTextField 用户名文本框
     * @param passwordTextField 密码文本框
     * @param userName          正确用户名
     * @param password          正确密码
     * @return  登录成功返回true, 否则返回false
     */
    public boolean isLogin(TextInputControl userNameTextField, TextInputControl passwordTextField, String userName, String password) {
        SimpleTools simpleTools = new SimpleTools();
        if (simpleTools.isEmpty(userNameTextField.getText())) {
            simpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "用户名不能为空！");
            return false;
        }
        if (simpleTools.isEmpty(passwordTextField.getText())) {
            simpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "密码不能为空！");
            return false;
        }
        if (!userNameTextField.getText().equals(userName) && !passwordTextField.getText().equals(password)) {
            simpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "错误", "用户名和密码均不正确！");
            return false;
        }
        if (!userNameTextField.getText().equals(userName)) {
            simpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "用户名不正确！");
            return false;
        }
        if (!passwordTextField.getText().equals(password)) {
            simpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "警告", "密码不正确！");
            return false;
        }
        if (userNameTextField.getText().equals(userName) && passwordTextField.getText().equals(password)) {
            return simpleTools.informationDialog(Alert.AlertType.WARNING, "提示", "信息", "登录成功！");
        }
        return false;
    }

    /**
     * 清空文本组件内容
     * @param inputControls 文本组件
     */
    public void clearTextFiled(TextInputControl... inputControls) {
        for (TextInputControl inputControl : inputControls) {
            inputControl.setText("");
        }
    }

    /**
     * 取消所有单选按钮选择
     * @param toggleButtons 单选按钮组
     */
    public void clearSelectedRadioButton(ToggleButton... toggleButtons) {
        for (ToggleButton toggleButton : toggleButtons) {
            toggleButton.setSelected(false);
        }
    }

    /**
     * 取消所有下拉列表框选择
     * @param comboBoxes    下拉列表框组
     */
    public void clearSelectedComboBox(ComboBox... comboBoxes) {
        for (ComboBox comboBox : comboBoxes) {
            comboBox.getSelectionModel().select(-1);
        }
    }

    /**
     * 将数据显示在图书类别表格中
     * @param tableView             表格视图控件
     * @param data                  要显示到表格上的数据
     * @param idColumn              ID列控件
     * @param nameColumn            图书类别名称列控件
     * @param descriptionColumn     图书类别描述列控件
     */
    public void setBookTypeTableViewDate(TableView tableView, ObservableList data,
                                         TableColumn<BookTypeBeanTableData, String> idColumn,
                                         TableColumn<BookTypeBeanTableData, String> nameColumn,
                                         TableColumn<BookTypeBeanTableData, String> descriptionColumn) {
        //设置每一列的数据
        idColumn.setCellValueFactory(cellData -> cellData.getValue().bookTypeIdProperty());
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().bookTypeNameProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().bookTypeDescriptionProperty());
        //将数据添加到表格控件中
        tableView.setItems(data);
    }

    /**
     * 通过sql语句，中数据库中查询图书类别数据并进行封装
     * @param sql   sql语句
     * @return      返回ObservableList<BookTypeBeanTableData>类型的数据
     */
    public ObservableList<BookTypeBeanTableData> getBookTypeTableViewData(String sql) {
        BookTypeDao bookTypeDao = new BookTypeDao();
        List list = bookTypeDao.getRecordsDataBySql(sql);
        //创建ObservableList<BookTypeBeanTableData>对象
        ObservableList<BookTypeBeanTableData> data = FXCollections.observableArrayList();
        //遍历集合中的数据
        for (Object o : list) {
            BookTypeBean bookTypeBean = (BookTypeBean) o;
            BookTypeBeanTableData tableData = new BookTypeBeanTableData(
                    String.valueOf(bookTypeBean.getBookTypeId()), bookTypeBean.getBookTypeName(), bookTypeBean.getBookTypeDescription());
            data.add(tableData);
        }
        return data;
    }

    /**
     * 将数据显示在图书表格中
     * @param tableView                 表格视图控件
     * @param data                      要显示在表格上的数据
     * @param bookIdColumn              图书ID列表控件
     * @param bookNameColumn            图书名列表控件
     * @param bookAuthorColumn          图书作者列表控件
     * @param AuthorSexColumn           作者性别列表控件
     * @param bookPriceColumn           图书价格列表控件
     * @param bookDescriptionColumn     图书描述列表控件
     * @param BookTypeColumn            图书类别列表控件
     */
    public void setBookTableViewDate(TableView tableView, ObservableList data,
                                     TableColumn<BookBeanTableData, String> bookIdColumn, TableColumn<BookBeanTableData, String> bookNameColumn,
                                     TableColumn<BookBeanTableData, String> bookAuthorColumn, TableColumn<BookBeanTableData, String> AuthorSexColumn,
                                     TableColumn<BookBeanTableData, String> bookPriceColumn, TableColumn<BookBeanTableData, String> bookDescriptionColumn,
                                     TableColumn<BookBeanTableData, String> BookTypeColumn) {
        // 设置id列的数据
        bookIdColumn.setCellValueFactory(cellData -> cellData.getValue().bookIdProperty());
        // 设置图书名称列的数据
        bookNameColumn.setCellValueFactory(cellData -> cellData.getValue().bookNameProperty());
        // 设置图书作者列的数据
        bookAuthorColumn.setCellValueFactory(cellData -> cellData.getValue().bookAuthorProperty());
        // 设置图书作者性别列的数据
        AuthorSexColumn.setCellValueFactory(cellData -> cellData.getValue().bookAuthorSexProperty());
        // 设置图书价格列的数据
        bookPriceColumn.setCellValueFactory(cellData -> cellData.getValue().bookPriceProperty());
        // 设置图书描述列的数据
        bookDescriptionColumn.setCellValueFactory(cellData -> cellData.getValue().bookDescriptionProperty());
        // 设置图书类别列的数据
        BookTypeColumn.setCellValueFactory(cellData -> cellData.getValue().bookTypeProperty());
        // 将数据设置到表格视图
        tableView.setItems(data);
    }

    /**
     * 通过sql语句从数据空中查询数据，并进行封装
     * @param sql   sql语句
     * @return  返回ObservableList<BookBeanTableData>类型的数据
     */
    public ObservableList<BookBeanTableData> getBookTableViewData(String sql) {
        BookDao bookDao = new BookDao();
        List list = bookDao.getRecordsDataBySql(sql);
        ObservableList<BookBeanTableData> data = FXCollections.observableArrayList();
        for (int i = 0; i < list.size(); i++) {
            BookBean bookBean = (BookBean) list.get(i);
            BookBeanTableData bookBeanTableData = new BookBeanTableData(String.valueOf(bookBean.getBookId()),
                    bookBean.getBookName(), bookBean.getBookAuthor(), bookBean.getBookAuthorSex(), String.valueOf(bookBean.getBookPrice()),
                    bookBean.getBookDescription(), bookBean.getBookTypeId());
            data.add(bookBeanTableData);

        }
        return data;
    }

    /**
     * 向下拉列表框中添加列表项
     * @param comboBox  下拉列表框
     * @param items 列表项
     */
    public void addComboBoxItems(ComboBox comboBox, Object[] items) {
        comboBox.getItems().clear();
        ObservableList observableList = FXCollections.observableArrayList(items);
        comboBox.setItems(observableList);
    }
}

