package BookManageSystem.beans;

import javafx.beans.property.SimpleStringProperty;

/**
 * @author lzh
 * @date 2021/3/15 20:46
 * @description JavaFX中表格显示数据的类型不同，需要再创建实体类
 */
public class BookTypeBeanTableData {
    private SimpleStringProperty bookTypeId;
    private SimpleStringProperty bookTypeName;
    private SimpleStringProperty bookTypeDescription;

    public BookTypeBeanTableData() {
    }

    public BookTypeBeanTableData(String bookTypeId, String bookTypeName, String bookTypeDescription) {
        this.bookTypeId = new SimpleStringProperty( bookTypeId);
        this.bookTypeName = new SimpleStringProperty(bookTypeName);
        this.bookTypeDescription = new SimpleStringProperty(bookTypeDescription);
    }

    public String getBookTypeId() {
        return bookTypeId.get();
    }

    public SimpleStringProperty bookTypeIdProperty() {
        return bookTypeId;
    }

    public void setBookTypeId(String bookTypeId) {
        this.bookTypeId.set(bookTypeId);
    }

    public String getBookTypeName() {
        return bookTypeName.get();
    }

    public SimpleStringProperty bookTypeNameProperty() {
        return bookTypeName;
    }

    public void setBookTypeName(String bookTypeName) {
        this.bookTypeName.set(bookTypeName);
    }

    public String getBookTypeDescription() {
        return bookTypeDescription.get();
    }

    public SimpleStringProperty bookTypeDescriptionProperty() {
        return bookTypeDescription;
    }

    public void setBookTypeDescription(String bookTypeDescription) {
        this.bookTypeDescription.set(bookTypeDescription);
    }
}
