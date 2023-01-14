package g5.com.controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import g5.com.dao.TelefonoTipoDAO;
import g5.com.dao.UsuarioDAO;
import g5.com.dao.UsuarioTelefonoDAO;
import g5.com.modelo.TelefonoTipo;
import g5.com.modelo.Usuario;
import g5.com.modelo.UsuarioTelefono;

/**
 * Servlet implementation class ProdutoControlador
 */
@WebServlet(description = "Administra as partiçoes para a tabela usuario", urlPatterns = { "/usuario" })
public class UsuarioControlador extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsuarioControlador() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String opcao = request.getParameter("opcao");
		
		if (opcao.equals("criar")) {
			TelefonoTipoDAO telefoneTipoDAO = new TelefonoTipoDAO();
			List<TelefonoTipo> lista = new ArrayList<>();

			try {
				lista = telefoneTipoDAO.listarTelefoneTipo();

				for (TelefonoTipo telefoneTipo : lista) {
					System.out.println(telefoneTipo);
				}
				
				request.setAttribute("lista", lista);
				System.out.println("Pressionou a opção de criar");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/criar.jsp");
				requestDispatcher.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} else if (opcao.equals("listar")) {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			List<Usuario> arrayUsuario = new ArrayList<>();
			UsuarioTelefonoDAO usuarioTelefoneDAO = new UsuarioTelefonoDAO();
			List<ArrayList<UsuarioTelefono>> arrayUsuarioTelefone = new ArrayList<>();
			TelefonoTipoDAO telefoneTipoDAO = new TelefonoTipoDAO();
			List<ArrayList<TelefonoTipo>> arrayTelefoneTipo = new ArrayList<>();
			
			try {

				arrayUsuario = usuarioDAO.listarUsuarios();

				for (Usuario usuario : arrayUsuario) {
					arrayUsuarioTelefone.add((ArrayList<UsuarioTelefono>) usuarioTelefoneDAO.listarUsuarioTelefone(usuario.getId_usuario()));
				}
				for(int i = 0; i < arrayUsuarioTelefone.size(); i++) {
					if (arrayUsuarioTelefone.get(i).isEmpty()) {
						arrayTelefoneTipo.add(null);
						System.out.println("é vazio");
						continue;
					}
					ArrayList<TelefonoTipo> listTelefoneTipo = new ArrayList<>();
					for (UsuarioTelefono usuarioTelefone : arrayUsuarioTelefone.get(i)) {
						listTelefoneTipo.addAll((ArrayList<TelefonoTipo>) telefoneTipoDAO.listarTelefoneTipoPorID(usuarioTelefone.getId_telefone_tipo()));
					}

					arrayTelefoneTipo.add(new ArrayList<>(listTelefoneTipo));
				}

				request.setAttribute("arrayUsuario", arrayUsuario);
				request.setAttribute("arrayUsuarioTelefone", arrayUsuarioTelefone);
				request.setAttribute("arrayTelefoneTipo", arrayTelefoneTipo);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/listar.jsp");
				requestDispatcher.forward(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			System.out.println("Pressionou a opção de listar");
		} else if(opcao.equals("meditar")) {
			int id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
			System.out.println("Editar id: "+ id_usuario);

			UsuarioDAO usuarioDAO = new UsuarioDAO();
			Usuario u = new Usuario();
			UsuarioTelefonoDAO usuarioTelefoneDAO = new UsuarioTelefonoDAO();
			List<UsuarioTelefono> arrayUsuarioTelefone = new ArrayList<>();
			TelefonoTipoDAO telefoneTipoDAO = new TelefonoTipoDAO();
			List<TelefonoTipo> listaTiposTelefone = new ArrayList<>();
			//List<TelefoneTipo> arrayTelefoneTipo = new ArrayList<>();

			
			try {
				u = usuarioDAO.listarUsuario(id_usuario);
				arrayUsuarioTelefone = usuarioTelefoneDAO.listarUsuarioTelefone(u.getId_usuario());
				listaTiposTelefone = telefoneTipoDAO.listarTelefoneTipo();
				System.out.println(listaTiposTelefone);
				request.setAttribute("usuario", u);
				request.setAttribute("arrayUsuarioTelefone", arrayUsuarioTelefone);
				request.setAttribute("listaTiposTelefone", listaTiposTelefone);
				
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/editar.jsp");
				requestDispatcher.forward(request, response);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (opcao.equals("deletar")) {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			UsuarioTelefonoDAO usuarioTelefoneDAO = new UsuarioTelefonoDAO();

			int id_usuario = Integer.parseInt(request.getParameter("id_usuario"));
			try {
				usuarioDAO.borrarUsuario(id_usuario);
				System.out.println("Exclusão do id " + request.getParameter("id_usuario") + " realizado com sucesso!");
				usuarioTelefoneDAO.deletarTodosOsNumerosDoUsuario(id_usuario);
				System.out.println("Exclusão de todos os números do id " + request.getParameter("id_usuario") + " realizado com sucesso!");

				HttpSession session=request.getSession();  
				session.setAttribute("msgAviso", "Exclusão realizada com sucesso!");
				session.setAttribute("msgAvisoCor", "green");
				RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/principal.jsp");
				requestDispatcher.forward(request, response);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if(opcao.equals("voltar")) {
			String url = "";
			HttpSession session = request.getSession();
			if(session.getAttribute("usuario") == null || session.getAttribute("usuario") == "") {
				url = "index.jsp";
			}
			else {
				String view = request.getParameter("view");

				url = "views/"+view;
			}
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
			requestDispatcher.forward(request, response);
		}

		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String opcao = request.getParameter("opcao");
		
		if (opcao.equals("guardar")) {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			UsuarioTelefonoDAO usuarioTelefoneDAO = new UsuarioTelefonoDAO();
			Usuario usuario = new Usuario();
			usuario.setNome(request.getParameter("nome"));
			usuario.setEmail(request.getParameter("email"));
			usuario.setSenha(request.getParameter("senha"));

			try {
				int id_usuario = (int) usuarioDAO.insertarUsuario(usuario);


				if (request.getParameter("id_telefone_tipo01") != "") {
					UsuarioTelefono usuarioTelefone = new UsuarioTelefono();

					usuarioTelefone.setId_telefone_tipo(Integer.parseInt(request.getParameter("id_telefone_tipo01")));
					usuarioTelefone.setNumero_telefone(request.getParameter("telefone01"));
					usuarioTelefone.setDdd(Integer.parseInt(request.getParameter("ddd01")));
					usuarioTelefone.setId_usuario(id_usuario);
	
					usuarioTelefoneDAO.inserirUsuarioTelefone(usuarioTelefone);
				}
				
				if (request.getParameter("id_telefone_tipo02") != "") {
					UsuarioTelefono usuarioTelefone = new UsuarioTelefono();

					usuarioTelefone.setId_telefone_tipo(Integer.parseInt(request.getParameter("id_telefone_tipo02")));
					usuarioTelefone.setNumero_telefone(request.getParameter("telefone02"));
					usuarioTelefone.setDdd(Integer.parseInt(request.getParameter("ddd02")));
					usuarioTelefone.setId_usuario(id_usuario);
	
					usuarioTelefoneDAO.inserirUsuarioTelefone(usuarioTelefone);
			
				}
				
				System.out.println("Cadastro realizado com sucesso!");
				HttpSession session=request.getSession();  
				session.setAttribute("msgAviso", "Cadastro realizado com sucesso!");
				session.setAttribute("msgAvisoCor", "green");
				if(session.getAttribute("usuario") == null || session.getAttribute("usuario") == ""){
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");					
					requestDispatcher.forward(request, response);
				}
				else {
					RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/principal.jsp");					
					requestDispatcher.forward(request, response);					
				}
			}catch (SQLException e) {
				e.printStackTrace();
			}
			
		}else if (opcao.equals("editar")) {
			UsuarioDAO usuarioDAO = new UsuarioDAO();
			Usuario usuario = new Usuario();
			UsuarioTelefonoDAO usuarioTelefoneDAO = new UsuarioTelefonoDAO();
			
			
			usuario.setId_usuario(Integer.parseInt(request.getParameter("id_usuario")));
			usuario.setNome(request.getParameter("nome"));
			usuario.setEmail(request.getParameter("email"));
			usuario.setSenha(request.getParameter("senha"));

			
			try {
				usuarioDAO.alterarUsuario(usuario);
				System.out.println("Edição do usuario id " + request.getParameter("id_usuario") + " realizado com sucesso!");
				usuarioTelefoneDAO.deletarTodosOsNumerosDoUsuario(Integer.parseInt(request.getParameter("id_usuario")));
				System.out.println("Exclusão de todos os números do id " + request.getParameter("id_usuario") + " realizado com sucesso!");
				
				if (request.getParameter("id_telefone_tipo01") != "") {
					UsuarioTelefono usuarioTelefone = new UsuarioTelefono();

					usuarioTelefone.setId_telefone_tipo(Integer.parseInt(request.getParameter("id_telefone_tipo01")));
					usuarioTelefone.setNumero_telefone(request.getParameter("telefone01"));
					usuarioTelefone.setDdd(Integer.parseInt(request.getParameter("ddd01")));
					usuarioTelefone.setId_usuario(Integer.parseInt(request.getParameter("id_usuario")));
	
					usuarioTelefoneDAO.inserirUsuarioTelefone(usuarioTelefone);
					System.out.println("Criação do 1º numero do usuario id " + request.getParameter("id_usuario") + " realizado com sucesso!");
				}
				
				if (request.getParameter("id_telefone_tipo02") != "") {
					UsuarioTelefono usuarioTelefone = new UsuarioTelefono();

					usuarioTelefone.setId_telefone_tipo(Integer.parseInt(request.getParameter("id_telefone_tipo02")));
					usuarioTelefone.setNumero_telefone(request.getParameter("telefone02"));
					usuarioTelefone.setDdd(Integer.parseInt(request.getParameter("ddd02")));
					usuarioTelefone.setId_usuario(Integer.parseInt(request.getParameter("id_usuario")));
	
					usuarioTelefoneDAO.inserirUsuarioTelefone(usuarioTelefone);
					System.out.println("Criação do 2º numero do usuario ido usuario id " + request.getParameter("id_usuario") + " realizado com sucesso!");
			
				}

				HttpSession session=request.getSession();  
				session.setAttribute("msgAviso", "Edição realizada com sucesso!");
				session.setAttribute("msgAvisoCor", "green");

				RequestDispatcher requestDispatcher = request.getRequestDispatcher("views/principal.jsp");
				requestDispatcher.forward(request, response);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		//doGet(request, response);
	}

}
