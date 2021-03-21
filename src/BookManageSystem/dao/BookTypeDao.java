package BookManageSystem.dao;

import BookManageSystem.beans.BookTypeBean;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BookTypeDao {

    /**
     * 添加图书类别记录到数据库中，根据sql语句执行数据库的增、删、改操作
     * @param sql   SQL语句
     * @return      执行成功返回true, 否则返回false
     */
    public boolean dataChange(String sql) {
        Connection connection = null;
        Statement statement = null;
        try {
            //获得数据的连接
            connection = JDBCUtils.getConnection();
            //获得Statement对象
            statement = connection.createStatement();
            //发送SQL语句
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
     * 根据SQL语句获取数据库记录数据
     * @param sql   SQL语句
     * @return  返回数据库内包含Record对象的合集
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
                BookTypeBean bookTypeBean = new BookTypeBean();
                bookTypeBean.setBookTypeId(resultSet.getInt(1));
                bookTypeBean.setBookTypeName(resultSet.getString(2));
                bookTypeBean.setBookTypeDescription(resultSet.getString(3));
                list.add(bookTypeBean);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.release(resultSet, statement, connection);
        }
        return list;
    }
}
