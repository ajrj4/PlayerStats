package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import bean.Summoner;
import model.SummonerDAO;

@WebServlet(urlPatterns = { "/insert-summoner", "/update-partidas" })
public class SummonerController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	String apiKey = "?api_key=RGAPI-667506a1-aa30-4c39-b582-9866b83be204";
	
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

		case "/update-partidas":
			atualizarSummonerPartidas(request, response);
			break;

		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}

	protected void inserirSummoner(HttpServletRequest request, HttpServletResponse response) {
		
		
		HttpsURLConnection connection = null;
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();

		try {
			URL url = new URL("https://br1.api.riotgames.com/lol/summoner/v4/summoners/by-name/"
					+ request.getParameter("summonerName").replaceAll("\\s", "") + apiKey);
			
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
		
		JsonObject summonerResponse = new Gson().fromJson(responseContent.toString(), JsonObject.class);
		
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
			
		dao.insertSummoner(summoner);
		
		request.setAttribute("puuid", summoner.getSummonerPuuid());
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("insert-usuario");
		
		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}

	}

	protected void atualizarSummonerPartidas(HttpServletRequest request, HttpServletResponse response) {
		summoner = dao.selectSummonerById(request.getParameter("puuid"));
		summoner.setPartidas(Arrays.asList(request.getParameter("partidas").split(",")));

		dao.updateSummoner(summoner);

		request.setAttribute("puuid", summoner.getSummonerPuuid());

		RequestDispatcher dispatcher = request.getRequestDispatcher("partidas.jsp");

		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}

}
