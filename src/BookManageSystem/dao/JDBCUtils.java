package BookManageSystem.dao;

import java.sql.*;

/**
 * 连接JDBC类
 */
public class JDBCUtils {

    /**
     * 加载驱动，建立数据库链接
     * @return  返回数据库Connection连接对象
     * @throws SQLException     抛出异常
     * @throws ClassNotFoundException   抛出异常
     */
   public static Connection getConnection() throws SQLException, ClassNotFoundException {
       Class.forName("com.mysql.cj.jdbc.Driver");  //加载驱动
       String url = "jdbc:mysql://localhost:3306/db_bookSystem";    //连接数据库
       //数据库登录用户名和密码
       String username = "root";
       String password = "root";
       return DriverManager.getConnection(url, username, password);
   }

    /**
     * 关闭数据库连接，释放资源
     * @param statement     Statement对象
     * @param connection    Connection对象
     */
   public static void release(Statement statement, Connection connection) {
       if (statement != null) {
           try {
               statement.close();
           } catch (SQLException throwable) {
               throwable.printStackTrace();
           }
           statement = null;
       }
       if (connection != null) {
           try {
               connection.close();
           } catch (SQLException throwable) {
               throwable.printStackTrace();
           }
           connection = null;
       }
   }

    /**
     * 关闭数据库连接，释放资源
     * @param resultSet     ResultSet对象
     * @param statement     Statement对象
     * @param connection    Connection对象
     */
   public static void release(ResultSet resultSet, Statement statement, Connection connection) {
       if (resultSet != null) {
           try {
               resultSet.close();
           } catch (SQLException throwable) {
               throwable.printStackTrace();
           }
           resultSet = null;
       }
       release(statement, connection);
   }

}
