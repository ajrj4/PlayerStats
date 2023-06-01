package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import bean.Summoner;
import model.SummonerDAO;
import model.UsuarioDAO;

@WebServlet(urlPatterns = { "/insert-summoner", "/delete-summoner", "/update-summoner"})
public class SummonerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Esta chave é resetada todos os dias e precisa ser atualizada manualmente
	//Vou colocar proximo a data de entrega, nao acho interessante deixar publico no github
	String apiKey = "?api_key=";
	
	Summoner summoner = new Summoner();
	SummonerDAO dao = new SummonerDAO();

	public SummonerController() {
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
		case "/insert-summoner":
			inserirSummoner(request, response);
			break;
			
		case "/delete-summoner":
			deletarSummoner(request, response);
			break;
			
		case "/update-summoner":
			atualizarSummoner(request, response);
			break;

		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	protected void inserirSummoner(HttpServletRequest request, HttpServletResponse response) {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		
		if(usuarioDAO.selectUsuarioByName(request.getParameter("nome")).getNome() != null) {
			try {
				response.getWriter().println("<script type='text/javascript'>");
				response.getWriter().println("alert('Usuário já cadastrado!');");
				response.getWriter().println("window.location.replace('cadastrarUsuario.jsp');");
				response.getWriter().println("</script>");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		request.setAttribute("summoner", request.getParameter("summonerName"));
		
		String respostaDeApi = buscarDadosDeSummoner(request);		
		
		JsonObject summonerResponse = new Gson().fromJson(respostaDeApi, JsonObject.class);	
		JsonPrimitive summonerNameResponse = summonerResponse.getAsJsonPrimitive("name");
		
		if (summonerNameResponse == null) {
			try {
				response.getWriter().println("<script type='text/javascript'>");
				response.getWriter().println("alert('Invocador nao encontrado');");
				response.getWriter().println("window.location.replace('cadastrarUsuario.jsp');");
				response.getWriter().println("</script>");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		String puuid = summonerResponse.getAsJsonPrimitive("puuid").getAsString();
		String encryptedId = summonerResponse.getAsJsonPrimitive("id").getAsString();
		String summonerLevel = summonerResponse.getAsJsonPrimitive("summonerLevel").getAsString();
		
		summoner.setSummonerPuuid(puuid);
		summoner.setSummonerEncryptedId(encryptedId);
		summoner.setSummonerName(summonerNameResponse.getAsString());
		summoner.setSummonerLevel(summonerLevel);
			
		try {
			dao.insertSummoner(summoner);
		} catch (SQLIntegrityConstraintViolationException e) {
			try {
				response.getWriter().println("<script type='text/javascript'>");
				response.getWriter().println("alert('Invocador já cadastrado!');");
				response.getWriter().println("window.location.replace('cadastrarUsuario.jsp');");
				response.getWriter().println("</script>");
				return;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("puuid", summoner.getSummonerPuuid());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("insert-usuario");
		
		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	//TODO
	protected void deletarSummoner(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		String puuid = (String) session.getAttribute("puuid");
		
		dao.deleteSummoner(puuid);
		
		try {
			response.getWriter().println("<script type='text/javascript'>");
			response.getWriter().println("alert('Conta excluída com sucesso!');");
			response.getWriter().println("window.location.replace('logout');");
			response.getWriter().println("</script>");
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void atualizarSummoner(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		
		request.setAttribute("summoner", session.getAttribute("summoner"));
		String respostaDeApi = buscarDadosDeSummoner(request);
		
		JsonObject json = new Gson().fromJson(respostaDeApi, JsonObject.class);
		
		String puuid = json.get("puuid").getAsString();
		String encryptedId = json.get("id").getAsString();
		String summonerName = json.get("name").getAsString();
		String summonerLevel = json.get("summonerLevel").getAsString();
		
		summoner.setSummonerPuuid(puuid);
		summoner.setSummonerEncryptedId(encryptedId);
		summoner.setSummonerName(summonerName);
		summoner.setSummonerLevel(summonerLevel);
		
		dao.updateSummoner(summoner);
		
		try {
			response.sendRedirect("update-partidas");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected String buscarDadosDeSummoner(HttpServletRequest request) {
		
		HttpsURLConnection connection = null;
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();

		try {
			URL url = new URL("https://br1.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
					+ ((String) request.getAttribute("summoner")).replaceAll("\\s", "") + apiKey);
			
			connection = (HttpsURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			int status = (connection.getResponseCode());

			if (status > 299) {
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);

				}
				reader.close();
			} else {
				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				while ((line = reader.readLine()) != null) {
					responseContent.append(line);

				}
				reader.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			connection.disconnect();
		}
		
		return responseContent.toString();
	}

}
