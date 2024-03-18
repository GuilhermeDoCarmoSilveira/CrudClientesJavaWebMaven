package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Cliente;


public class ClienteDao implements ICrud<Cliente> {

	private GenericDao gDao;

	public ClienteDao(GenericDao gDao) {
		super();
		this.gDao = gDao;
	}

	@Override
	public String inserir(Cliente cl) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_insertCliente (?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, cl.getCpf());
		cs.setString(2, cl.getNome());
		cs.setString(3, cl.getEmail());
		cs.setDouble(4, cl.getLimiteCredito());
		cs.setString(5, cl.getDtNascimento().toString());
		cs.registerOutParameter(6, Types.VARCHAR);

		cs.execute();

		String saida = cs.getString(6);
		cs.close();
		c.close();

		return saida;
	}

	@Override
	public String atualizar(Cliente cl) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_UpdateCliente (?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, cl.getCpf());
		cs.setString(2, cl.getNome());
		cs.setString(3, cl.getEmail());
		cs.setDouble(4, cl.getLimiteCredito());
		cs.setString(5, cl.getDtNascimento().toString());
		cs.registerOutParameter(6, Types.VARCHAR);

		cs.execute();

		String saida = cs.getString(6);
		cs.close();
		c.close();

		return saida;
	}

	@Override
	public String excluir(Cliente cl) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "{CALL sp_DeleteCliente (?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, cl.getCpf());
		cs.registerOutParameter(2, Types.VARCHAR);

		cs.execute();

		String saida = cs.getString(2);
		cs.close();
		c.close();

		return saida;

	}

	@Override
	public Cliente consultar(Cliente cl) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		String sql = "SELECT * FROM cliente WHERE cpf = (?) ";
		PreparedStatement ps = c.prepareStatement(sql);
		ps.setString(1, cl.getCpf());

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			cl.setCpf(rs.getString("cpf"));
			cl.setNome(rs.getString("nome"));
			cl.setEmail(rs.getString("email"));
			cl.setLimiteCredito(rs.getDouble("limiteDeCredito"));
			Date dataSql = rs.getDate("dtNascimento");
			LocalDate dataJava = dataSql.toLocalDate();
			cl.setDtNascimento(dataJava);
		}
		rs.close();
		ps.close();
		c.close();

		return cl;
	}

	@Override
	public List<Cliente> listar() throws SQLException, ClassNotFoundException {
		List<Cliente> clientes = new ArrayList<>();

		Connection c = gDao.getConnection();
		String sql = "SELECT * FROM cliente";
		PreparedStatement ps = c.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			
			Cliente cl = new Cliente();
			
			cl.setCpf(rs.getString("cpf"));
			cl.setNome(rs.getString("nome"));
			cl.setEmail(rs.getString("email"));
			cl.setLimiteCredito(rs.getDouble("limiteDeCredito"));
			Date dataSql = rs.getDate("dtNascimento");
			LocalDate dataJava = dataSql.toLocalDate();
			cl.setDtNascimento(dataJava);
			
			clientes.add(cl);
		}

		rs.close();
		ps.close();
		c.close();

		return clientes;
	}

}
