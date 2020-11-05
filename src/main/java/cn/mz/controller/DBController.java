package cn.mz.controller;

import cn.mz.db.dto.Cate;
import cn.mz.db.imp.MysqlConnection;
import cn.mz.util.TooUtil;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @version 1.0
 * @Description TODD
 * @Author wmazh
 * @Date 2020-11-03 0003 11:32
 */
public class DBController {

    @FXML
    private Pane pane;
    @FXML
    private TextField url;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField port;
    @FXML
    private TextArea display;

    @FXML
    private TextArea sqltext;

    @FXML
    private AnchorPane tablerest;

    @FXML
    private Button connect;
    @FXML
    private Button disconnect;

    private Connection con;

    ObservableList<Cate> teamMembers = null;

    @FXML
    void connectDb(MouseEvent event){

        String urls = url.getText();
        String usernames = username.getText();
        String psd = password.getText();
        String pot = port.getText();
        if(TooUtil.isOneEmpty(urls,usernames,psd,pot)){
            showtext("信息为空");
            return;
        }
        MysqlConnection mycon = new MysqlConnection(urls,usernames,psd,pot);
        try {
            con = mycon.getConnection();
            if(TooUtil.isEmpty(con)){
                showtext("连接失败");
                return;
            }
            showtext("连接成功");
        } catch (Exception e) {
            showtext("连接失败");
            showtext(exceptonToString(e));
        }
    }

    @FXML
    void disConnectDb(MouseEvent event){

        try {
            con.close();
            showtext("断开连接");
        } catch (SQLException e) {
            e.printStackTrace();
            showtext(exceptonToString(e));
            con = null;
        }finally {
            con=null;
        }

    }

    @FXML
    void cleanText(MouseEvent event){
        cleanLog();
    }

    @FXML
    void runSql(MouseEvent event){
        String sql  = sqltext.getText();
        if(TooUtil.isEmpty(sql)){
            showtext("请输入执行的sql");
            return;
        }
        if(TooUtil.isEmpty(con)){
            showtext("请先连接数据库");
            return;
        }
        String r = null;
        try {
            r = runsqlResult(sql);
        } catch (Exception e) {
            e.printStackTrace();
            showtext(exceptonToString(e));
            return;
        }
        showtext("执行成功");
        showtext(r);
    }

    private String runsqlResult(String sql) throws Exception{
        long start = System.currentTimeMillis();
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        ResultSetMetaData md = rs.getMetaData();
        int column = md.getColumnCount();

        TableView<Cate> table = new TableView();
        table.setPrefWidth(912.0);
        TableColumn<Cate,String> idc = new TableColumn<>("id");
        idc.setCellValueFactory(param -> param.getValue().idProperty());

        TableColumn<Cate,String> urlc = new TableColumn<>("url");
        urlc.setCellValueFactory(param -> param.getValue().urlProperty());

        TableColumn<Cate,String> shop_parent_skuc = new TableColumn<>("shop_parent_sku");
        shop_parent_skuc.setCellValueFactory(param -> param.getValue().shop_parent_skuProperty());

        table.getColumns().setAll(idc,urlc,shop_parent_skuc);

        List<Cate> cates = new ArrayList<>();
        while(rs.next()){
            Cate cate = new Cate(rs.getString("id"),rs.getString("url"),rs.getString("shop_parent_sku"));
            cates.add(cate);
        }
        teamMembers = FXCollections.observableArrayList(cates);
        table.setItems(teamMembers);

        tablerest.getChildren().add(table);

        long end = System.currentTimeMillis();

        return "总计耗时："+(end-start) + "ms";


    }

    private void cleanLog() {
        display.clear();
    }

    private void showtext(String test){
        display.appendText(test);
        display.appendText("\n");
    }

    private String exceptonToString(Exception e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }



}
