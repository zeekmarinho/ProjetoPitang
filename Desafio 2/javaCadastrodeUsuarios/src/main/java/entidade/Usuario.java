package entidade;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIO")
public class Usuario {
	
	@Id
	@Column(name = "CPF", nullable = false)
	private String cpf;
	@Column(name = "NOME", nullable = false)
	private String nome;
	@Column(name = "EMAIL", nullable = false)
	private String email;
	@Column(name = "SENHA")
	private String senha;
	
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Telefones> listaTelefones;

	public Usuario() {
		
	}

	public Usuario(String cpf, String nome, String email, String senha, List<Telefones> listaTelefones) {
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.listaTelefones = listaTelefones;
	}	

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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

	public List<Telefones> getListaTelefones() {
		return listaTelefones;
	}

	public void setListaTelefones(List<Telefones> listaTelefones) {
		this.listaTelefones = listaTelefones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		Usuario usuarioEntrada = (Usuario)obj;
		
		if(this.cpf.equals(usuarioEntrada.getCpf())) {
			return true;
		}else {
			return false;
		}
	}
	
	

}
