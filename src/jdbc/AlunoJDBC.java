package jdbc;


import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import entities.Aluno;

public class AlunoJDBC {
	
	String sql;
	PreparedStatement pst;
	
	
	public void salvar(Aluno a, Connection con) throws IOException {
		
		try {
			
			sql = "INSERT INTO aluno (nome, sexo, dt_nasc) VALUES (?,  ?, ?)";
			
			pst = con.prepareStatement(sql);
			pst.setString(1, a.getNome());
			pst.setString(2, a.getSexo());
			
			Date dataSql = new Date(a.getDt_nasc().getTime());
			pst.setDate(3, dataSql);
			
			pst.executeUpdate();
			System.out.println("\nCadastro do aluno realizado com sucesso!");
			
		}
		catch (SQLException e) {
			
			System.out.println(e);
		}
		
	}
	
	public List<Aluno> listar(Connection con) throws IOException, SQLException{
		List<Aluno> lista = new ArrayList<>();
		String sql = "SELECT * FROM aluno";
		Statement st = con.createStatement();
		
		ResultSet rs = st.executeQuery(sql);
		
		while(rs.next()) {
			
			
			Aluno a = new Aluno(
					rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDate("dt_nasc"));
			lista.add(a);
			
		}
		
		rs.close();
		st.close();
		
		return lista;
	
		
		
	}
	
	public void apagar(int id) throws SQLException, IOException{
		Connection con = DB.getConexao();
		
		
		String sql = "DELETE FROM aluno WHERE id = ?";
		pst = con.prepareStatement(sql);
		pst.setInt(1, id );
		pst.executeUpdate();
		System.out.println("\n Remo��o do aluno feita com sucesso! \n");
		
		
		
		
	}
	
	public void alterar(Aluno a) throws IOException, SQLException {
		Connection con = DB.getConexao();
		String sql = "UPDATE aluno SET nome = " + a.getNome() + ", sexo = " + a.getSexo() + ", dt_nasc = " + a.getDt_nasc() + "WHERE id = " + a.getId();
		pst = con.prepareStatement(sql);
		pst.executeUpdate();
		
		System.out.println("\n Altera��o feita com sucesso! \n");		
		
		
		
		
		
	}
}

