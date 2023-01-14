package g5.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import g5.com.coneccion.Coneccion;
import g5.com.modelo.UsuarioTelefono;

public class UsuarioTelefonoDAO {
	private Connection connection;
	private PreparedStatement statement;
	private boolean estadoOperacao;

	public boolean inserirUsuarioTelefone(UsuarioTelefono usuarioTelefone) throws SQLException {
		String sql = null;
		estadoOperacao = false;
		connection = obterConexao();

		try {
			connection.setAutoCommit(false);
			sql = "INSERT INTO usuario_telefone(id_usuario, ddd, numero_telefone, id_telefone_tipo) VALUES(?, ?, ?, ?)";

			statement = connection.prepareStatement(sql);

			statement.setInt(1, usuarioTelefone.getId_usuario());
			statement.setInt(2, usuarioTelefone.getDdd());
			statement.setString(3, usuarioTelefone.getNumero_telefone());
			statement.setInt(4, usuarioTelefone.getId_telefone_tipo());

			estadoOperacao = statement.executeUpdate() > 0;

			connection.commit();
			statement.close();
		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			System.out.println("fechou");
			connection.close();
		}

		return estadoOperacao;
	}

	public List<UsuarioTelefono> listarUsuarioTelefone(int id_usuario) throws SQLException {
		ResultSet resultSet = null;
		List<UsuarioTelefono> arrayUsuarioTelefone = new ArrayList<>();

		String sql = null;
		estadoOperacao = false;
		connection = obterConexao();

		try {
			sql = "SELECT * FROM usuario_telefone WHERE id_usuario=?";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id_usuario);

			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				UsuarioTelefono ut = new UsuarioTelefono();
				ut.setId_usuario(resultSet.getInt(1));
				ut.setDdd(resultSet.getInt(2));
				ut.setNumero_telefone(resultSet.getString(3));
				ut.setId_telefone_tipo(resultSet.getInt(4));
				arrayUsuarioTelefone.add(ut);

			}

			statement.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			System.out.println("fechou");
			connection.close();
		}

		return arrayUsuarioTelefone;
	}

	public boolean deletarTodosOsNumerosDoUsuario(int id_usuario) throws SQLException {
		String sql = null;
		estadoOperacao = false;
		connection = obterConexao();

		try {
			connection.setAutoCommit(false);
			sql = "DELETE FROM usuario_telefone WHERE id_usuario = ?";

			statement = connection.prepareStatement(sql);

			statement.setInt(1, id_usuario);

			estadoOperacao = statement.executeUpdate() > 0;
			connection.commit();
			statement.close();

		} catch (SQLException e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			System.out.println("fechou");
			connection.close();
		}

		return estadoOperacao;
	}

	private Connection obterConexao() throws SQLException {
		return Coneccion.getConnection();
	}

}
