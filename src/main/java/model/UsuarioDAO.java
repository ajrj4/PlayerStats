package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Usuario;

public class UsuarioDAO {
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

	public void insertUsuario(Usuario usuario){
		Connection connection = getConnection();
		String sql = "insert into usuarios (nome, email, senha, summonerId) values (?, ?, ?, ?)";
		
			try {
				PreparedStatement ps = connection.prepareStatement(sql);
				ps.setString(1, usuario.getNome());
				ps.setString(2, usuario.getEmail());
				ps.setString(3, usuario.getSenha());
				ps.setString(4, usuario.getSummonerId());
				
				ps.executeUpdate();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}		
	}

	public Usuario selectUsuarioByName(String name) {
		Usuario usuario = new Usuario();
		Connection connection = getConnection();
		String sql = "select * from usuarios where nome=?";

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				usuario.setIdUsuario(rs.getInt(1));
				usuario.setIdNivelUsuario(rs.getInt(2));
				usuario.setNome(rs.getString(3));
				usuario.setEmail(rs.getString(4));
				usuario.setSenha(rs.getString(5));
				usuario.setSummonerId(rs.getString(6));
			}

			connection.close();
			return usuario;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int updateUsuario(Usuario usuario) {
		Connection connection = getConnection();
		String sql = "update usuarios set nome=?, email=?, senha=?, summonerId=? where id=?";
		int status = 0;

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getEmail());
			ps.setString(3, usuario.getSenha());
			ps.setString(4, usuario.getSummonerId());
			ps.setInt(5, usuario.getIdUsuario());
			status = ps.executeUpdate();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return status;
	}

	public int deleteUsuario(int id) {
		Connection connection = getConnection();
		String sql = "delete from usuarios where idUsuario=?";
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
