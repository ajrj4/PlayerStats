package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import bean.Summoner;
import model.SummonerDAO;


@WebServlet(urlPatterns = {"/select-partidas", "/update-partidas"})
public class PartidaController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//Esta chave é resetada todos os dias e precisa ser atualizada manualmente
	String apiKey = "?api_key=RGAPI-0c0ab81d-01fe-4227-b85d-10464d8a4813";
       
    public PartidaController() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		switch(action) {
		
		case "/select-partidas":
			partidasToJson(request, response);
			break;
	
		case "/update-partidas":
			atualizarPartidas(request, response);
			break;
			
		default:
			throw new IllegalArgumentException("Unexpected value: " + action);
		}
	}
	
	protected void partidasToJson(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession(false);
		
		SummonerDAO summonerDAO = new SummonerDAO();
		Summoner summoner = summonerDAO.selectSummonerById(String.valueOf(session.getAttribute("puuid")));
		
		if (summoner.getPartidas() == Collections.<String>emptyList()) {
			try {
				response.sendRedirect("update-partidas");
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		int pagina;
		
		if(request.getParameter("pagina") != null && !request.getParameter("pagina").isEmpty()) {
			pagina = Integer.parseInt(request.getParameter("pagina"));
		} else {
			pagina = 0;
		}

		List<String> partidas = summoner.getPartidas();
		
		request.setAttribute("partida", matchProperties(partidas.get(pagina)));
		request.setAttribute("pagina", pagina);
		request.setAttribute("puuid", summoner.getSummonerPuuid());

		RequestDispatcher dispatcher = request.getRequestDispatcher("partidas.jsp");

		try {
			dispatcher.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}

	}

	protected void atualizarPartidas(HttpServletRequest request, HttpServletResponse response) {
		final int MATCH_COUNT = 20;
		
		HttpSession session = request.getSession(false);
		
		String idString = String.valueOf(session.getAttribute("puuid"));
		HttpsURLConnection connection = null;
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();

		try {
			URL url = new URL("https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/" + idString + "/ids"
					+ apiKey + "&start=0&count=" + MATCH_COUNT);

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
		
		JsonArray jsonArray = new Gson().fromJson(responseContent.toString(), JsonArray.class); 
		
		String partida;	
		
		List<String> listPartidas = new ArrayList<>();
		
		for (int i = 0; i < MATCH_COUNT; i++) {
			partida = jsonArray.get(i).toString().replaceAll("\"", "");
			listPartidas.add(partida);
		}
		
		SummonerDAO summonerDAO = new SummonerDAO();
		Summoner summoner = summonerDAO.selectSummonerById(idString);
		summoner.setPartidas(listPartidas);
		summonerDAO.updateSummoner(summoner);

		try {
			response.sendRedirect("select-partidas?pagina=0");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected String matchProperties(String partida) {
		HttpsURLConnection connection = null;
		BufferedReader reader;
		String line;
		StringBuffer responseContent = new StringBuffer();

		try {
			URL url = new URL("https://americas.api.riotgames.com/lol/match/v5/matches/" + partida + apiKey);

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
