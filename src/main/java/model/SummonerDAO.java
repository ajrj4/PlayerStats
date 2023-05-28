package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;

import bean.Summoner;

public class SummonerDAO {
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

	public void insertSummoner(Summoner summoner) {
		Connection connection = getConnection();
		String sql = "insert into summoners (summonerId, encryptedId, summonerName, summonerLevel) values (?, ?, ?, ?)";

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, summoner.getSummonerPuuid());
			ps.setString(2, summoner.getSummonerEncryptedId());
			ps.setString(3, summoner.getSummonerName());
			ps.setString(4, summoner.getSummonerLevel());

			ps.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Summoner selectSummonerById(String id) {
		Summoner summoner = new Summoner();
		Connection connection = getConnection();
		String sql = "select * from summoners where summonerId=?";

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				summoner.setSummonerPuuid(rs.getString(1));
				summoner.setSummonerEncryptedId(rs.getString(2));
				summoner.setSummonerName(rs.getString(3));
				summoner.setSummonerLevel(rs.getString(4));
				summoner.setPartidas(rs.getString(5) != null ? Arrays.asList(rs.getString(5).split(" "))
						: Collections.<String>emptyList());

			}

			connection.close();
			return summoner;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int updateSummoner(Summoner summoner) {
		Connection connection = getConnection();
		String sql = "update summoners set summonerName=?, summonerLevel=?, partidas=? where summonerId=?";
		int status = 0;

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, summoner.getSummonerName());
			ps.setString(2, summoner.getSummonerLevel());
			ps.setString(3, String.join(" ", summoner.getPartidas()));
			ps.setString(4, summoner.getSummonerPuuid());
			status = ps.executeUpdate();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	public int deleteSummoner(String id) {
		Connection connection = getConnection();
		String sql = "delete from summoners where summonerId=?";
		int status = 0;

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, id);
			status = ps.executeUpdate();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return status;
	}
}
