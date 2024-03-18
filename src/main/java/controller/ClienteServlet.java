package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Cliente;
import persistence.ClienteDao;
import persistence.GenericDao;

@WebServlet("/cliente")
public class ClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ClienteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//entrada
		
		String cmd = request.getParameter("botao");
		String cpf = request.getParameter("cpf");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String limiteCredito =  request.getParameter("limiteCredito");
		String dtNascimento = request.getParameter("dtNascimento");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		
		//retorno
		String saida = "";
		String erro = "";
		Cliente cl = new Cliente();
		List<Cliente> clientes = new ArrayList<>();
		
		if(!cmd.contains("Listar")) {
			cl.setCpf(cpf);
		}
		if(cmd.contains("Cadastrar") || cmd.contains("Alterar")) {
			cl.setNome(nome);
			cl.setEmail(email);
			cl.setLimiteCredito(Double.parseDouble(limiteCredito));
			LocalDate dataNascimento = LocalDate.parse(dtNascimento, formatter);
			cl.setDtNascimento(dataNascimento);
		}
		try {
			if(cmd.contains("Cadastrar")) {
				cadastrarCliente(cl);
				saida = "Cliente cadastrado com sucesso";
				cl = null;
			}
			if(cmd.contains("Alterar")) {
				alterarCliente(cl);
				saida = "Cliente alterado com sucesso";
				cl = null;
			}
			if(cmd.contains("Excluir")) {
				excluirCliente(cl);
				saida = "Cliente excluido com sucesso";
				cl = null;
			}
			if(cmd.contains("Buscar")) {
				cl = buscarCliente(cl);
			}
			if(cmd.contains("Listar")) {
				clientes = listarClientes();
			}

		}catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		}finally {
			request.setAttribute("saida", saida);
			request.setAttribute("erro", erro);
			request.setAttribute("motorista", cl);
			request.setAttribute("motoristas", clientes);
			
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			rd.forward(request, response);
		}

	}

	private void cadastrarCliente(Cliente c) throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		ClienteDao cDao = new ClienteDao(gDao);
		cDao.inserir(c);
	}

	private void alterarCliente(Cliente c) throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		ClienteDao cDao = new ClienteDao(gDao);
		cDao.atualizar(c);
	}

	private void excluirCliente(Cliente c) throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		ClienteDao cDao = new ClienteDao(gDao);
		cDao.excluir(c);
	}

	private Cliente buscarCliente(Cliente c) throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		ClienteDao cDao = new ClienteDao(gDao);
		c = cDao.consultar(c);
		return c;
	}

	private List<Cliente> listarClientes() throws SQLException, ClassNotFoundException {
		GenericDao gDao = new GenericDao();
		ClienteDao cDao = new ClienteDao(gDao);
		List<Cliente> clientes = cDao.listar();
		return clientes;
	}

}
