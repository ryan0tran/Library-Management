/*
 * Copyright (c) 2019 Nghia Tran.
 * Project I - Library Management System
 */

package dao;

import model.Reader;
import util.DbConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReaderDAO {
    private static ReaderDAO instance = new ReaderDAO();
    
    private ReaderDAO() {
    }
    
    public static ReaderDAO getInstance() {
        return instance;
    }
    
    // CREATE
    public boolean createReader(Reader reader) throws SQLException {
        String sql = "INSERT INTO [reader](created, name, dob, gender, idCardNum, address, canBorrow) VALUES (?, ?, ?, ?, ?, ?, ?)";

        Connection con = DbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
        stmt.setNString(2, reader.getName());
        stmt.setDate(3, reader.getDob());
        stmt.setBoolean(4, reader.getGender());
        stmt.setInt(5, reader.getIdCardNum());
        stmt.setNString(6, reader.getAddress());
        stmt.setBoolean(7, reader.isCanBorrow());

        boolean result = (stmt.executeUpdate() > 0);
        con.close();

        return result;
    }
    // READ

    public Reader getReader(String rid) throws SQLException {
        String sql = "SELECT * FROM [reader] WHERE rid=?";
        Reader reader = null;

        Connection con = DbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, rid);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            reader = new Reader(
                    rs.getInt("rid"),
                    rs.getTimestamp("created"),
                    rs.getNString("name"),
                    rs.getDate("dob"),
                    rs.getBoolean("gender"),
                    rs.getInt("idCardNum"),
                    rs.getNString("address"),
                    rs.getBoolean("canBorrow")
            );
        }

        rs.close();
        con.close();

        return reader;
    }

    public List<Reader> getAllReaders() throws SQLException {
        String sql = "SELECT * FROM [reader]";
        Reader reader = null;
        List<Reader> readerList = new ArrayList<>();

        Connection con = DbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(sql);

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            reader = new Reader(
                    rs.getInt("rid"),
                    rs.getTimestamp("created"),
                    rs.getNString("name"),
                    rs.getDate("dob"),
                    rs.getBoolean("gender"),
                    rs.getInt("idCardNum"),
                    rs.getNString("address"),
                    rs.getBoolean("canBorrow")
            );
            readerList.add(reader);
        }

        rs.close();
        con.close();

        return readerList;
    }

    // UPDATE
    public boolean updateReader(Reader reader) throws SQLException {
        String sql = "UPDATE [reader]" +
                "SET name=?, dob=?, gender=?, idCardNum=?, address=?, canBorrow=? " +
                "WHERE rid=?";

        Connection con = DbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(sql);

        stmt.setNString(1, reader.getName());
        stmt.setDate(2, reader.getDob());
        stmt.setBoolean(3, reader.getGender());
        stmt.setInt(4, reader.getIdCardNum());
        stmt.setNString(5, reader.getAddress());
        stmt.setBoolean(6, reader.isCanBorrow());
        stmt.setInt(7, reader.getRid());

        boolean result = (stmt.executeUpdate() > 0);
        con.close();

        return result;
    }

    // DELETE
    public boolean deleteReader(Reader reader) throws SQLException {
        String sql = "DELETE FROM [reader] WHERE rid=?";

        Connection con = DbConnection.getConnection();
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, reader.getRid());
        boolean result = (stmt.executeUpdate() > 0);
        con.close();

        return result;
    }
}
