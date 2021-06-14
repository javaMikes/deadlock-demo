package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @desc
 * @auther huanghua
 * @create 2021-06-14 11:02
 */
public class TestIndexMerge {

    public static final String URL = "jdbc:mysql://127.0.0.1:3306/esop_test?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai";
    public static final String USER = "root";
    public static final String PASSWORD = "123456";

    public static void main(String[] args) throws ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        ExecutorService executorService = Executors.newFixedThreadPool(300);
        for (int i = 0; i <= 100; i++) {
            final int index = i;
            final String wsCode = "ws_" + i;
            final String skuCode = "sku_" + i;
            executorService.submit(() -> {
                try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
                    for (int j = 0; j < 10000; j++) {
                        conn.createStatement().executeUpdate("update store set store = store - 1 WHERE sku_code='" + skuCode + "' and ws_code ='" + wsCode +"';");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
