package BookManageSystem.dao;

import BookManageSystem.beans.BookBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lzh
 * @date 2021/3/16 17:54
 * @description 添加记录到数据库的方法
 */
public class BookDao {

    public boolean dataChange(String sql) {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = JDBCUtils.getConnection();
            statement = connection.createStatement();
            int num = statement.executeUpdate(sql);
            return num > 0;
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.release(statement, connection);
        }
        return false;
    }

    /**
     * 根据sql语句，获取数据库记录数据
     * @param sql   sql语句
     * @return  返回包含记录records对象的list
     */
    public List getRecordsDataBySql(String sql) {
        List list = new ArrayList();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = JDBCUtils.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                BookBean bookBean = new BookBean();
                bookBean.setBookId(resultSet.getInt(1));
                bookBean.setBookName(resultSet.getString(2));
                bookBean.setBookAuthor(resultSet.getString(3));
                bookBean.setBookAuthorSex(resultSet.getString(4));
                bookBean.setBookPrice(resultSet.getFloat(5));
                bookBean.setBookDescription(resultSet.getString(6));
                bookBean.setBookTypeId(resultSet.getString(7));
                list.add(bookBean);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }
}
