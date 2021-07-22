package controle;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import dao.UsuarioDAO;
import dao.UsuarioDAOImpl;
import entidade.Usuario;
import entidade.Telefones;

@ManagedBean(name = "UsuarioBean")
@SessionScoped
public class UsuarioBean {
	
	private Usuario usuario; // Utilizado no cadastrogeral
	private Telefones telefones; // Utilizado no add contato
	private List<Usuario> listaUsuarios;

	private UsuarioDAO usuarioDAO;
	
		// Valores utilizados para regra de remover telefone
		private String dddSel, numeroSel, tipoSel;
		
		// Atributo utilizado para limpar campos quando veem da tela de pesquisa
		private boolean origemPesquisa = false;
		
		//Atributo utilizado para saber se esta em modo de edição ou inclusão
		private boolean modoEdicao = false;
		
		//Valores para pesquisar e remover
		private String cpfSelecionado;
		
		/**
		 * Construtor UsuarioBean, onde inicializa o usuarioDAO, dando acesso ao banco
		 * de dados
		 */
		
		public UsuarioBean() {
			this.usuarioDAO = new UsuarioDAOImpl();
			this.inicializarCampos();
		}
		
		/**
		 * Metodo utilizado para zera e inicializar todos os campos e atributos
		 */
		
		public void inicializarCampos() {
			System.out.println("++++++++ entrou +++++++");
			if(!origemPesquisa) { //Vai entrar aqui quando vier a primeira vez da pesquisa
				this.usuario = new Usuario();
				this.usuario.setListaTelefones(new ArrayList<Telefones>());
				modoEdicao = false;
			}
			
			origemPesquisa = false;

			// this.contato é o objeto referenciado para adicionar os contatos em tela
			this.telefones = new Telefones();

			this.listaUsuarios = new ArrayList<Usuario>();
		}
		
		/**
		 * Metodo utilizado para salvar o usuario com seus telefones
		 */
		
		public void salvar() {
			
			if(!modoEdicao) {
				if (usuarioDAO.existeUsuario(usuario) == null) {
					this.usuarioDAO.inserirUsuario(usuario);
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuario inserido com sucesso"));
				} else {
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Usuario já existe!"));
				}			
			}else {
				this.usuarioDAO.inserirUsuario(usuario);
				this.usuario = this.usuarioDAO.existeUsuario(usuario);
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Usuario alterado com sucesso"));
			}
		}
		
		/**
		 * Metodo utilizado para adicionar o telefone
		 */
		
		public void adicionarTelefone() {

			telefones.setUsuario(usuario);
			this.usuario.getListaTelefones().add(telefones);

			// Necessario dar uma nova instancia, senão a mesma é afetado em uma nova
			// inserção
			
			this.telefones = new Telefones();
		}
		
		/**
		 * Metodo utilizado para remover telefofone
		 */
		
		public void removerTelefone() {
			
			Telefones achou = null;
			for (Telefones cont : usuario.getListaTelefones()) {
				if(dddSel != null && !dddSel.isEmpty() && numeroSel != null && !numeroSel.isEmpty() && tipoSel != null && !tipoSel.isEmpty()) {
					
					if(cont.getDDD().equals(dddSel) && 
						cont.getNumero().equals(numeroSel) && 
						cont.getTipo().contentEquals(tipoSel)) {
						achou = cont;
					}
					
				}else if(dddSel != null && !dddSel.isEmpty()) {
					if(cont.getDDD().equals(dddSel)) {
						achou = cont;
					}
				}else {
					if(cont.getNumero().equals(numeroSel)) {
						achou = cont;
					}
				}
			}
			
			if(achou != null) {
				if(achou.getId() > 0) {
					this.usuarioDAO.removerTelefoneUsuario(achou);
				}
				
				usuario.getListaTelefones().remove(achou);
				
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Telefone removido"));
			}
			
		}
		
		public void pesquisarUsuario() {
			this.listaUsuarios = usuarioDAO.pesquisarUsuario(usuario, telefones);
		}
		
		/**
		 * Metodo utilizado para recuperar o usuario pelo cpf e abrir a tela de 
		 * Cadastro de usuario com o mesmo preenchido
		 * @return
		 */
		
		public String editarUsuario() {
			
			this.origemPesquisa = true;
			this.modoEdicao = true;
			
			Usuario usuarioPesquisa = new Usuario();
			usuarioPesquisa.setCpf(cpfSelecionado);
			
			this.usuario = this.usuarioDAO.existeUsuario(usuarioPesquisa);
			
			return "/cadastroUsuario.xhtml?faces-redirect=true&amp;includeViewParams=true";
		}
		
		/**
		 * Metodo utilizado para recuperar o usuario pelo cpf e depois remover da base,
		 * atualizando a lista de pesquisa
		 */
		
		public void removerUsuario() {
			
			Usuario usuarioPesquisa = new Usuario();
			usuarioPesquisa.setCpf(cpfSelecionado);
			
			this.usuarioDAO.removerUsuario(usuarioPesquisa);
			
			this.pesquisarUsuario();
		}

		public Usuario getUsuario() {
			return usuario;
		}

		public void setUsuario(Usuario usuario) {
			this.usuario = usuario;
		}

		public Telefones getTelefones() {
			return telefones;
		}

		public void setTelefones(Telefones telefones) {
			this.telefones = telefones;
		}

		public List<Usuario> getListaUsuarios() {
			return listaUsuarios;
		}

		public void setListaUsuarios(List<Usuario> listaUsuarios) {
			this.listaUsuarios = listaUsuarios;
		}

		public UsuarioDAO getUsuarioDAO() {
			return usuarioDAO;
		}

		public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
			this.usuarioDAO = usuarioDAO;
		}

		public String getDddSel() {
			return dddSel;
		}

		public void setDddSel(String dddSel) {
			this.dddSel = dddSel;
		}

		public String getNumeroSel() {
			return numeroSel;
		}

		public void setNumeroSel(String numeroSel) {
			this.numeroSel = numeroSel;
		}

		public String getTipoSel() {
			return tipoSel;
		}

		public void setTipoSel(String tipoSel) {
			this.tipoSel = tipoSel;
		}

		public String getCpfSelecionado() {
			return cpfSelecionado;
		}

		public void setCpfSelecionado(String cpfSelecionado) {
			this.cpfSelecionado = cpfSelecionado;
		}		

}
