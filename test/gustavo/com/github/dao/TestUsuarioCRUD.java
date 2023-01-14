package gustavo.com.github.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;

import g5.com.dao.UsuarioDAO;
import g5.com.modelo.Usuario;

public class TestUsuarioCRUD {
	private Usuario usuario_1;
	private Usuario usuario_2;
	private UsuarioDAO usuarioDAO;
	private int usuarioEliminar = 117;

	@Before
	public void setUp() {
		usuario_1 = new Usuario();
		usuario_2 = new Usuario();
		usuarioDAO = new UsuarioDAO();

		usuario_1.setNome("Milton Paucar");
		usuario_1.setEmail("miltonpaucar999@gmail.com	");
		usuario_1.setSenha("1234567890");

		usuario_2.setNome("Roberto");
		usuario_2.setEmail("roberto123@gmail.com	");
		usuario_2.setSenha("122333444455555");
		usuario_2.setId_usuario(1);
	}

	@Test
	@Order(1)
	@DisplayName("El resultado de insertar un usuario es un ID distinto de cero")
	public void testInsertarUsuario() {
		try {
			usuarioEliminar = (int) usuarioDAO.insertarUsuario(usuario_1);
			Assertions.assertNotEquals(0, usuarioEliminar);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Test
	@Order(2)
	@DisplayName("Debe regresar TRUE si los datos del usuario fueron actualizados")
	public void testAlterarUsuario() {
		try {
			boolean actualizado = usuarioDAO.alterarUsuario(usuario_2);
			Assertions.assertTrue(actualizado);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Test
	@Order(4)
	@DisplayName("Debe regresar TRUE si el ususario fue eliminado")
	public void testBorrarUsuario() {
		try {
			boolean borrado = usuarioDAO.borrarUsuario(usuarioEliminar);
			Assertions.assertTrue(borrado);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Test
	@Order(3)
	@DisplayName("Debe retornar una lista con todos los usuarios")
	public void testListarUsuarios() {
		try {
			List<Usuario> arrayUsuarios = new ArrayList<>();

			arrayUsuarios = usuarioDAO.listarUsuarios();

			Assertions.assertTrue(arrayUsuarios.size() > 0, "La lista tiene por lo menos un usuario");
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
