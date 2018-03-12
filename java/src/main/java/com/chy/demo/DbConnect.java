package com.chy.demo;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by chenhaoyu on 2018/2/26
 */
public class DbConnect {

    private static String url = "jdbc:mysql://localhost:3306/demo_server?useUnicode=true&characterEncoding=utf-8";
    private static String username = "root";
    private static String userpwd = "root";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, userpwd);
            System.out.println(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
