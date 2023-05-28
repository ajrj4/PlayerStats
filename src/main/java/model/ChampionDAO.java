package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Champion;

public class ChampionDAO {
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost:3306/playerstats?useSSL=false&useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "root";

	private Connection getConnection() {
		Connection connection = null;

		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println(e);
		}

		return connection;
	}

	public void insertChampion(Champion champion) {
		String sql = "insert into champions (championId, championName, championIcon) values (?, ?, ?)";

		Connection connection = getConnection();
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, champion.getChampionId());
			ps.setString(2, champion.getChampionName());
			ps.setString(3, champion.getChampionIcon());
			ps.executeUpdate();

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Champion> selectChampions() {
		String sql = "select * from champions order by championName";
		List<Champion> championList = new ArrayList<>();

		Connection connection = getConnection();

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Champion champion = new Champion();
				champion.setChampionId(rs.getInt(1));
				champion.setChampionName(rs.getString(2));
				champion.setChampionIcon(rs.getString(3));
				championList.add(champion);
			}
			
			connection.close();
			return championList;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Champion selectChampionById(int id) {
		Connection connection = getConnection();
		Champion champion = new Champion();
		String sql = "select * from champions where championId=?";

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				champion.setChampionId(rs.getInt(1));
				champion.setChampionName(rs.getString(2));
				champion.setChampionIcon(rs.getString(3));
			}
			connection.close();
			return champion;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public int deleteChampion(int id) {
		Connection connection = getConnection();
		String sql = "delete from champions where championId=?";
		int status = 0;

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			status = ps.executeUpdate();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return status;
	}
}
