package g5.com.test.cajaBlanca;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import g5.com.dao.UsuarioDAO;
import g5.com.modelo.Usuario;

public class UsuarioInsertar {
	private Usuario usuario_1;
	private UsuarioDAO usuarioDAO;
	private List<Usuario> arrayUsuarios;

	@Before
	@Test
	public void setUp() {
		usuario_1 = new Usuario();
		usuarioDAO = new UsuarioDAO();

		usuario_1.setNome("Milton Paucar");
		usuario_1.setEmail("miltonpaucar999@gmail.com");
		usuario_1.setSenha("1234567890");
	}

	@Test
	@DisplayName("Se ingresa un usuario que sabemos que ya existe en la BDD")
	public void testInsertarUsuarioB() {
		try {
//			Obtenemos la lista de todos los usuario que existen en la BDD
			arrayUsuarios = usuarioDAO.listarUsuarios();

//			Obtenemos la cantidad de usuario registrados
			int cantidadUsarios = arrayUsuarios.size();
			usuario_1.setId_usuario((int) cantidadUsarios / 2);

//			Ingresamos el usuario con un ID repetido
			int idUsuarioCreado = (int) usuarioDAO.insertarUsuario(usuario_1);

//			El metodo retorna 0 cuando el usuario no fue registrado
			Assertions.assertEquals(0, idUsuarioCreado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
