package g5.com.test.cajaBlanca;

import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import g5.com.dao.UsuarioDAO;
import g5.com.modelo.Usuario;

public class UsuarioEliminar {
	private UsuarioDAO usuarioDAO;
	private List<Usuario> arrayUsuarios;

	@Before
	@Test
	public void setUp() {
		usuarioDAO = new UsuarioDAO();
	}

	@Test
	@DisplayName("Debe regresar TRUE si el usuario fue eliminado")
	public void testBorrarUsuarioNoExiste() {
		try {
//			obtenemos la lista de todos los usuarios
			arrayUsuarios = usuarioDAO.listarUsuarios();

//          obtenemos el tamanio del array, por enden cuantos usuarios hay
			int cantidadUsuarios = arrayUsuarios.size();

//			tratamos de borrar un usuario que sabemos que no existe
			boolean borrado = usuarioDAO.borrarUsuario(cantidadUsuarios + 100);
			Assertions.assertFalse(borrado);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
