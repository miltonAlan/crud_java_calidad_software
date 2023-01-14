package g5.com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import g5.com.coneccion.Coneccion;
import g5.com.modelo.TelefonoTipo;


public class TelefonoTipoDAO {
	private Connection connection;
	private PreparedStatement statement;
//	private boolean estadoOperacao;
	
	public List<TelefonoTipo> listarTelefoneTipo() throws SQLException {
		ResultSet resultSet = null;
		List<TelefonoTipo> listaProdutos = new ArrayList<>();
		
		String sql = null;
		//estadoOperacao = false;
		connection = obterConexao();

		try {
			sql = "SELECT * FROM telefone_tipo";
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery(sql);
			
			while(resultSet.next()) {
				TelefonoTipo tt = new TelefonoTipo();
				tt.setId_telefone_tipo(resultSet.getInt(1));
				tt.setName(resultSet.getString(2));
				
				listaProdutos.add(tt);
				
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally{
			System.out.println("fechou");
			connection.close();
		}
		
		return listaProdutos;
	}
	public List<TelefonoTipo> listarTelefoneTipoPorID(int id_telefone_tipo) throws SQLException {
		ResultSet resultSet = null;
		List<TelefonoTipo> arrayTelefoneTipo = new ArrayList<>();
		
		String sql = null;
		//estadoOperacao = false;
		connection = obterConexao();

		try {
			sql = "SELECT * FROM telefone_tipo WHERE id_telefone_tipo =?";
			statement=connection.prepareStatement(sql);
			statement.setInt(1, id_telefone_tipo);
			
			resultSet = statement.executeQuery();
			
			if(resultSet.next()) {
				TelefonoTipo tt=new TelefonoTipo();
				
				tt.setId_telefone_tipo(resultSet.getInt(1));
				tt.setName(resultSet.getString(2));
				
				arrayTelefoneTipo.add(tt);

			}
			statement.close();
			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			System.out.println("fechou");
			connection.close();
		}

		return arrayTelefoneTipo;
	}
	
	private Connection obterConexao() throws SQLException {
		return Coneccion.getConnection();
	}
	
}
