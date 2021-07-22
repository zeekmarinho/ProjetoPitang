package entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "TELEFONES")
public class Telefones {
	
	@Id
	@Column(name = "ID", nullable = false)
	@GeneratedValue(generator = "STELEFONES")
	@SequenceGenerator(name = "STELEFONES", sequenceName = "STELEFONES", allocationSize = 1)
	private int id;
	@Column(name = "DDD", nullable = false)
	private String ddd;
	@Column(name = "NUMERO", nullable = false)
	private String numero;
	@Column(name = "TIPO", nullable = false)
	private String tipo;
	
	@ManyToOne
	@JoinColumn(name = "CPF_USUARIO", referencedColumnName = "CPF", nullable = false)
	private Usuario usuario;	
	

	public Telefones() {
		
	}

	public Telefones(int id, String numero, String tipo, Usuario usuario) {
		super();
		this.id = id;
		this.numero = numero;
		this.tipo = tipo;
		this.usuario = usuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getDDD() {
		return ddd;
	}

	public void setDDD(String ddd) {
		this.ddd = ddd;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Telefones telefoneEntrada = (Telefones) obj;

		if (this.numero.equalsIgnoreCase(telefoneEntrada.getNumero())
				|| this.tipo.equals(telefoneEntrada.getTipo())) {
			return true;
		} else {
			return false;
		}
	}
	
	

}
