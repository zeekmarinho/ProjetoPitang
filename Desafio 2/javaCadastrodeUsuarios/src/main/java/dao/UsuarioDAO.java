package dao;

import java.util.List;

import entidade.Telefones;
import entidade.Usuario;

public interface UsuarioDAO {
	
public boolean inserirUsuario(Usuario usuario);
	
	public List<Usuario> pesquisarUsuario(Usuario usuario, Telefones telefones);
	
	public boolean adicionarTelefoneUsuario(Usuario usuario);
	
	public boolean removerTelefoneUsuario(Usuario usuario);
	
	public Usuario existeUsuario(Usuario usuario);

	public boolean removerTelefoneUsuario(Telefones telefones);

	public boolean removerUsuario(Usuario usuario);

}
