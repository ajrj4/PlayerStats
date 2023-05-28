package bean;

import java.io.IOException;
import java.util.Base64;

import javax.servlet.http.Part;

public class Champion {
	private int championId;
	private String championName;
	private String championIcon;

	public Champion() {
		super();
	}

	public Champion(int championId, String championName, String championIcon) {
		this.championId = championId;
		this.championName = championName;
		this.championIcon = championIcon;
	}

	public String base64Encoder(Part part) {
		try {
			String base64String = Base64.getEncoder().encodeToString(part.getInputStream().readAllBytes());
			return base64String;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int getChampionId() {
		return championId;
	}

	public void setChampionId(int championId) {
		this.championId = championId;
	}

	public String getChampionName() {
		return championName;
	}

	public void setChampionName(String championName) {
		this.championName = championName;
	}

	public String getChampionIcon() {
		return championIcon;
	}

	public void setChampionIcon(String championIcon) {
		this.championIcon = championIcon;
	}

}
