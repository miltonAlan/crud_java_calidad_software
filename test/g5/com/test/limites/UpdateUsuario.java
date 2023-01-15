package g5.com.test.limites;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import g5.com.dao.UsuarioDAO;
import g5.com.modelo.Usuario;

public class UpdateUsuario {
	private Usuario usuario_1;
	private Usuario usuario_2;
	private Usuario usuario_3;
	private Usuario usuario_4;
	private UsuarioDAO usuarioDAO;
//	private int usuarioEliminar = 117;

	@Before
	public void setUp() {
		usuario_1 = new Usuario();
		usuario_2 = new Usuario();
		usuario_3 = new Usuario();
		usuario_4 = new Usuario();
		usuarioDAO = new UsuarioDAO();

//		Valores validos
		usuario_1.setNome("Milton UPDATED");
		usuario_1.setId_usuario(-2147483648);

		usuario_2.setNome("Roberto UPDATED");
		usuario_2.setId_usuario(-1);

//		Valores invalidos
		usuario_3.setNome("Milton UPDATED");
		usuario_3.setId_usuario(2147483647);

		usuario_4.setNome("Roberto UPDATED");
		usuario_4.setId_usuario(1);
	}

	@Test
	@DisplayName("El metodo debe fallar al tratar de actualizar un usuario fuera del limite del valor numerico")
	public void testAlterarUsuario() {
		try {
			boolean actualizado = usuarioDAO.alterarUsuario(usuario_1);
			Assertions.assertFalse(actualizado);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Test
	@DisplayName("El metodo debe fallar al tratar de actualizar un usuario fuera del limite del valor numerico")
	public void testAlterarUsuario2() {
		try {
			boolean actualizado = usuarioDAO.alterarUsuario(usuario_2);
			Assertions.assertFalse(actualizado);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Test
	@DisplayName("El metodo debe ser exitoso al tratar de actualizar un usuario dentro de los limites")
	public void testAlterarUsuario3() {
		try {
			boolean actualizado = usuarioDAO.alterarUsuario(usuario_3);
			Assertions.assertTrue(actualizado);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Test
	@DisplayName("El metodo debe ser exitoso al tratar de actualizar un usuario dentro de los limites")
	public void testAlterarUsuario4() {
		try {
			boolean actualizado = usuarioDAO.alterarUsuario(usuario_4);
			Assertions.assertTrue(actualizado);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
