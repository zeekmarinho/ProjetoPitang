package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import entidade.Telefones;
import entidade.Usuario;
import util.JpaUtil;

public class UsuarioDAOImpl implements UsuarioDAO {

	@Override
	public boolean inserirUsuario(Usuario usuario) {

		EntityManager ent = JpaUtil.getEntityManager();
		EntityTransaction tx = ent.getTransaction();
		
		tx.begin();

		ent.merge(usuario);

		tx.commit();
		ent.close();
		
		return true;
	}
	
	private String montarWhere(Usuario usuario, Telefones telefones) {

		String where = "";

		if (usuario.getCpf() != null && !usuario.getCpf().isEmpty()) {
			where += " and cl.cpf = '" + usuario.getCpf() + "'";
		} else {
			if (usuario.getNome() != null && !usuario.getNome().isEmpty()) {
				where += " and upper(cl.nome) like '%" + usuario.getNome().toUpperCase() + "%'";
			}
			
			if (usuario.getEmail() != null && !usuario.getEmail().isEmpty()) {
				where += " and upper(co.email) like '%" + usuario.getEmail().toUpperCase() + "%'";
			}
			
			if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
				where += " and upper(co.senha) like '%" + usuario.getSenha().toUpperCase() + "%'";
			}
			
			if (telefones.getDDD() != null && !telefones.getDDD().isEmpty()) {
				where += " and co.ddd like '%" + telefones.getDDD() + "%'";
			}
			
			if (telefones.getNumero() != null && !telefones.getNumero().isEmpty()) {
				where += " and co.numero like '%" + telefones.getNumero() + "%'";
			}
			
			if (telefones.getTipo() != null && !telefones.getTipo().isEmpty()) {
				where += " and co.tipo like '%" + telefones.getTipo() + "%'";
			}
		}
		return where;
	}

	@Override
	public List<Usuario> pesquisarUsuario(Usuario usuario, Telefones telefones) {

		String sql = "Select distinct cl from Usuario us, Telefones tl " + " where us.cpf = tl.usuario.cpf "
				+ montarWhere(usuario, telefones);

		EntityManager ent = JpaUtil.getEntityManager();

		Query query = ent.createQuery(sql);

		List<Usuario> listaUsuarios = query.getResultList();

		ent.close();
		
		return listaUsuarios;
	}

	@Override
	public boolean adicionarTelefoneUsuario(Usuario usuario) {
		
		EntityManager ent = JpaUtil.getEntityManager();
		EntityTransaction tx = ent.getTransaction();

		tx.begin();

		for (Telefones telefones : usuario.getListaTelefones()) {
			ent.merge(telefones);
		}

		tx.commit();
		ent.close();

		return true;
	}

	@Override
	public boolean removerTelefoneUsuario(Usuario usuario) {
		
		EntityManager ent = JpaUtil.getEntityManager();
		EntityTransaction tx = ent.getTransaction();

		tx.begin();

		for (Telefones telefones : usuario.getListaTelefones()) {
			Telefones existe = ent.find(Telefones.class, telefones.getId());
			if (existe != null) {
				ent.remove(existe);
			}
		}

		tx.commit();
		ent.close();
		return true;
	}

	@Override
	public Usuario existeUsuario(Usuario usuario) {
		
		EntityManager ent = JpaUtil.getEntityManager();

		Usuario existe = ent.find(Usuario.class, usuario.getCpf());

		ent.close();
		return existe;
	}

	@Override
	public boolean removerTelefoneUsuario(Telefones telefones) {
		
		EntityManager ent = JpaUtil.getEntityManager();
		Telefones existe = ent.find(Telefones.class, telefones.getId());

		EntityTransaction tx = ent.getTransaction();

		tx.begin();

		
		ent.remove(existe);

		tx.commit();
		ent.close();
		return true;
	}

	@Override
	public boolean removerUsuario(Usuario usuario) {
		
		EntityManager ent = JpaUtil.getEntityManager();
		EntityTransaction tx = ent.getTransaction();
		
		Usuario existe = ent.find(Usuario.class, usuario.getCpf());

		tx.begin();
		
		ent.remove(existe);

		tx.commit();
		ent.close();
		return true;
	}

}
