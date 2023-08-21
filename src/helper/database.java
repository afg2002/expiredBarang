/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package helper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;


/**
 *
 * @author afgha
 */
public class database {
        private Connection koneksi;
        public Connection connect(){
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                System.out.println("Berhasil Koneksi!");
            }catch(ClassNotFoundException ex){
                System.out.println("Gagal Koneksi ! "+ex);
            }

            String url = "jdbc:mysql://localhost/expiredBarang";

            try{
                koneksi = (Connection) DriverManager.getConnection(url,"root","");
                System.out.println("Berhasil Koneksi Database!");
            }catch(SQLException ex){
                System.out.println("Gagal koneksi Database! "+ex);

            }

            return koneksi;    
    }
    
  
    
    public void insertData(Connection connection, String tableName, String[] columns, Object[] values) throws SQLException {
        StringBuilder sbColumns = new StringBuilder();
        StringBuilder sbValues = new StringBuilder();
        
        for (int i = 0; i < columns.length; i++) {
            if (i > 0) {
                sbColumns.append(", ");
                sbValues.append(", ");
            }
            sbColumns.append(columns[i]);
            sbValues.append("?");
        }
        
        String sql = "INSERT INTO " + tableName + " (" + sbColumns.toString() + ") VALUES (" + sbValues.toString() + ")";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            
            statement.executeUpdate();
        }
    }
    
     public  void updateData(Connection connection, String tableName, String[] columns, Object[] values, String condition) throws SQLException {
        StringBuilder sbColumns = new StringBuilder();
        
        for (int i = 0; i < columns.length; i++) {
            if (i > 0) {
                sbColumns.append(", ");
            }
            sbColumns.append(columns[i]).append(" = ?");
        }
        
        String sql = "UPDATE " + tableName + " SET " + sbColumns.toString() + " WHERE " + condition;
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++) {
                statement.setObject(i + 1, values[i]);
            }
            
            statement.executeUpdate();
        }
    }
     
     public  void deleteData(Connection connection, String tableName, String condition) throws SQLException {
        String sql = "DELETE FROM " + tableName + " WHERE " + condition;
         System.out.println(sql);
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        }
    }
}
