package com.teamboard.worker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/** Reads the directory straight out of the SQLite file the web app writes. */
public class MemberRepository {

    private final String jdbcUrl;

    public MemberRepository(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    /**
     * Every member, active or not.
     *
     * Deliberately unfiltered: the nightly HR feed is a full snapshot and
     * downstream reconciles leavers by the active flag.
     */
    public List<Member> findAll() throws SQLException {
        String sql = "SELECT id, name, email, role, department, start_date, is_active "
                   + "FROM members ORDER BY name ASC";
        List<Member> members = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(jdbcUrl);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                members.add(new Member(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("role"),
                    rs.getString("department"),
                    rs.getString("start_date"),
                    rs.getInt("is_active") == 1
                ));
            }
        }
        return members;
    }
}
