package bean;

import java.util.List;

public class Summoner {
	private String summonerPuuid;
	private String summonerEncryptedId;
	private String summonerName;
	private String summonerLevel;
	private List<String> partidas;

	public Summoner() {
		super();
	}

	public Summoner(String summonerPuuid, String summonerEncryptedId, String summonerName, String summonerLevel,
			List<String> partidas) {
		super();
		this.summonerPuuid = summonerPuuid;
		this.summonerEncryptedId = summonerEncryptedId;
		this.summonerName = summonerName;
		this.summonerLevel = summonerLevel;
		this.partidas = partidas;
	}

	public String getSummonerPuuid() {
		return summonerPuuid;
	}

	public void setSummonerPuuid(String summonerPuuid) {
		this.summonerPuuid = summonerPuuid;
	}

	public String getSummonerEncryptedId() {
		return summonerEncryptedId;
	}

	public void setSummonerEncryptedId(String summonerEncryptedId) {
		this.summonerEncryptedId = summonerEncryptedId;
	}

	public String getSummonerName() {
		return summonerName;
	}

	public void setSummonerName(String summonerName) {
		this.summonerName = summonerName;
	}

	public String getSummonerLevel() {
		return summonerLevel;
	}

	public void setSummonerLevel(String summonerLevel) {
		this.summonerLevel = summonerLevel;
	}

	public List<String> getPartidas() {
		return partidas;
	}

	public void setPartidas(List<String> partidas) {
		this.partidas = partidas;
	}

}	