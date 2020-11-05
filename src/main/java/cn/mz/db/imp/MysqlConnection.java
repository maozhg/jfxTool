package cn.mz.db.imp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * @version 1.0
 * @Description TODD
 * @Author wmazh
 * @Date 2020-11-03 0003 13:53
 */
public class MysqlConnection {
    private String url;
    private String username;
    private String password;
    private String port;
    private String driver = "com.mysql.jdbc.Driver";
    private List<Connection> conList;
    private int min = 0;
    private int max = 5;


    public MysqlConnection(String url, String username, String password, String port) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.port = port;
    }

    public Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection con = DriverManager.getConnection("jdbc:mysql://"+url+":"+port,username,password);
//        conList.add(con);
        return con;
    }

    public void closeConnection() throws SQLException {
        for(Connection con:conList){
            con.close();
        }
    }






}
