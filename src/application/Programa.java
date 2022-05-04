package application;


import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import entities.Aluno;
import jdbc.AlunoJDBC;
import jdbc.DB;

public class Programa {

	public static void main(String[] args) throws IOException, SQLException {

		Connection con = DB.getConexao();
		System.out.println("Conexão realizada com sucesso !");
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
		
		Scanner console = new Scanner(System.in);
		
		int opcao = 0;
		
		try {

			do {
				System.out.print("######## Menu ########" + 
								"\n1- Cadastrar" + 
								"\n2- Listar"    + 
								"\n3- Alterar"   +
								"\n4- Excluir"   + 
								"\n5- Sair"      +
								"\n\tOpção: ");
				opcao = Integer.parseInt(console.nextLine());

				if (opcao == 1) {

					Aluno a = new Aluno();
					AlunoJDBC acao = new AlunoJDBC();

					System.out.print("\n*** Cadastrar Aluno ***\n\r");
					System.out.print("Nome: ");
					a.setNome(console.nextLine());
					System.out.print("Sexo: ");
					a.setSexo(console.nextLine());
		
					System.out.print("Data de nascimento (dd/MM/yyyy): ");
					a.setDt_nasc( Date.valueOf( LocalDate.parse( console.nextLine(), formato) ) ) ;
					
					acao.salvar(a, con);
					console.nextLine();
				}else if(opcao == 2) {
			
					System.out.println("LISTA");
					
					List<Aluno> l = new ArrayList<>();
					AlunoJDBC acao = new AlunoJDBC();
					l = acao.listar(con);
					
				
					for(Aluno a: l) {
						System.out.println("Aluno: " + a.getId() + " " + a.getNome() + " " +  a.getSexo() + " " + new SimpleDateFormat("dd-MM-yyyy").format(a.getDt_nasc()));
					}
					
					
					
					
				}else if(opcao == 3){
					
					Aluno a = new Aluno();
					AlunoJDBC acao = new AlunoJDBC();
					System.out.println("\n Digite o id do aluno que vc quer alterar: ");
					a.setId(console.nextInt());
					System.out.println("\nDigite o novo nome: ");
					a.setNome(console.nextLine());
					System.out.println("\nDigite o novo sexo: ");
					a.setSexo(console.nextLine());
					System.out.print("Digite a nova Data de nascimento (dd/MM/yyyy): ");
					a.setDt_nasc( Date.valueOf( LocalDate.parse( console.nextLine(), formato) ) ) ;
					
					acao.alterar(a);
				}
				else if(opcao == 4) {
					
					int id;
					AlunoJDBC acao = new AlunoJDBC();
					System.out.println("\n Remover aluno \n ");
					System.out.println("\n Digite o ID que você deseja remover: ");
					id = console.nextInt();
					acao.apagar(id);
					
				}
				
				
			} while (opcao != 5);

		} catch (Exception e) {
			System.out.println("Erro: " + e);
		}
		
		DB.fechaConexao();
		System.out.println("Conexão fechada com sucesso !");
	}
}
