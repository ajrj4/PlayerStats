package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Summoner;
import bean.Usuario;
import model.SummonerDAO;
import model.UsuarioDAO;

@WebServlet(urlPatterns = { "/insert-usuario", "/select-usuario", "/update-usuario", "/delete-usuario", "/main",
		"/login", "/logout" })
public class UsuarioController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Usuario usuario = new Usuario();
	UsuarioDAO dao = new UsuarioDAO();

	public UsuarioController() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		switch (action) {
		case "/insert-usuario":
			inserirUsuario(request, response);
			break;

		case "/select-usuario":
			selecionarUsuario(request, response);
			break;

		case "/update-usuario":
			atualizarUsuario(request, response);
			break;

		case "/delete-usuario":
			excluirUsuario(request, response);
			break;

		case "/main":
			paginaInicial(request, response);
			break;

		case "/login":
			login(request, response);
			break;
			
		case "/logout":
			logout(request, response);
			break;

		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}


	protected void inserirUsuario(HttpServletRequest request, HttpServletResponse response) {
		usuario.setNome(request.getParameter("nome"));
		usuario.setEmail(request.getParameter("email"));
		usuario.setSenha(request.getParameter("senha"));
		usuario.setSummonerId((String) request.getAttribute("puuid"));

		dao.insertUsuario(usuario);
		
		request.removeAttribute("puuid");
		
		try {
			response.getWriter().println("<script type='text/javascript'>");
			response.getWriter().println("alert('Cadastro concluído!');");
			response.getWriter().println("window.location.replace('login.jsp');");
			response.getWriter().println("</script>");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void selecionarUsuario(HttpServletRequest request, HttpServletResponse response) {
		String nome = request.getParameter("nome");

		usuario = dao.selectUsuarioByName(nome);

		request.setAttribute("id", usuario.getIdUsuario());
		request.setAttribute("nome", usuario.getNome());
		request.setAttribute("email", usuario.getEmail());
		request.setAttribute("senha", usuario.getSenha());
		request.setAttribute("summonerId", usuario.getSummonerId());

		RequestDispatcher dispatcher = request.getRequestDispatcher("atualizarUsuario.jsp");

		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}

	}

	protected void atualizarUsuario(HttpServletRequest request, HttpServletResponse response) {
		usuario.setIdUsuario(Integer.parseInt(request.getParameter("id")));
		usuario.setNome(request.getParameter("nome"));
		usuario.setEmail(request.getParameter("email"));
		usuario.setSenha(request.getParameter("senha"));
		usuario.setSummonerId(request.getParameter("summonerId"));

		dao.updateUsuario(usuario);
	}

	protected void excluirUsuario(HttpServletRequest request, HttpServletResponse response) {
		int id = Integer.parseInt(request.getParameter("id"));

		dao.deleteUsuario(id);

		try {
			response.sendRedirect("login");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void paginaInicial(HttpServletRequest request, HttpServletResponse response) {
		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
	}

	protected void login(HttpServletRequest request, HttpServletResponse response) {
		String nomeUsuario = request.getParameter("nome");
		String senhaUsuario = request.getParameter("senha");

		usuario = dao.selectUsuarioByName(nomeUsuario);
		
		SummonerDAO summonerDAO = new SummonerDAO();
		Summoner summoner = summonerDAO.selectSummonerById(usuario.getSummonerId());


		if (usuario == null || !senhaUsuario.equals(usuario.getSenha())) {
			try {
				response.getWriter().println("<script type='text/javascript'>");
				response.getWriter().println("alert('Usuario ou senha incorretos!');");
				response.getWriter().println("window.location.replace('login.jsp');");
				response.getWriter().println("</script>");
				return;
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		
		HttpSession session = request.getSession(true);

		session.setAttribute("usuario", usuario.getNome());
		session.setAttribute("puuid", usuario.getSummonerId());
		session.setAttribute("summoner", summoner.getSummonerName());
		session.setAttribute("encryptedId", summoner.getSummonerEncryptedId());

		try {
			response.sendRedirect("main");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		
		session.invalidate();
		
		try {
			response.sendRedirect("login.jsp");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
