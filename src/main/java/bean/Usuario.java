package bean;

public class Usuario {
	private int idUsuario;
	private int idNivelUsuario;
	private String nome;
	private String email;
	private String senha;
	private String summonerId;

	public Usuario() {
		super();
	}

	public Usuario(int idUsuario, int idNivelUsuario, String nome, String email, String senha, String summonerId) {
		super();
		this.idUsuario = idUsuario;
		this.idNivelUsuario = idNivelUsuario;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.summonerId = summonerId;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public int getIdNivelUsuario() {
		return idNivelUsuario;
	}

	public void setIdNivelUsuario(int idNivelUsuario) {
		this.idNivelUsuario = idNivelUsuario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSummonerId() {
		return summonerId;
	}

	public void setSummonerId(String summonerId) {
		this.summonerId = summonerId;
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", idNivelUsuario=" + idNivelUsuario + ", nome=" + nome + ", email="
				+ email + ", senha=" + senha + ", summonerId=" + summonerId + "]";
	}
	

}
