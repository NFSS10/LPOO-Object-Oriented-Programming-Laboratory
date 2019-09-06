package maze.testes;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import maze.logic.Dragao;
import maze.logic.Gerador_de_Labirito;
import maze.logic.Heroi;
import maze.logic.Labirinto;
/**
 * Classe com os testes unitários do projeto
 * @author Nuno Silva e Luis Soares
 *
 */
public class LabirintoTeste
{

/**
 * Testes do labirinto.
 */
	@Test
	public void TesteLabirinto()
	{
		Labirinto labteste = new Labirinto();
		Labirinto labteste1=new Labirinto(11,0);
		Labirinto labteste2=new Labirinto(11,1);
		String status = null;
		assertEquals(false, labteste.EspacoLivre(-1, -1));
		assertEquals(false, labteste.EspacoLivre(0, 0));
		
		
		labteste.Print();
		labteste1.Print();
		labteste1.ApagaPos(0, 0);
		labteste.ApagaPos(0, 0);
		labteste.ApagaPos(0, 1);
		labteste.ApagaPos(1, 0);
		labteste.ApagaPos(1, 1);
		labteste.ApagaPos(2, 1);
		labteste.ApagaPos(1, 2);
		
		assertEquals(true, labteste.EspacoLivre(1, 1));
		assertEquals(false, labteste.EstadoJogo());
		assertEquals(' ', labteste.GetBoardChar(0, 0));
		assertEquals(status, labteste.GetStatus());
		
	    int x = labteste.AleAdormecer();
		if(x==0) 
			assertEquals(0,x);
		else
			assertEquals(3,x);
		
		
		 
			
		assertEquals(false, labteste.HeroiMorreu());
		assertEquals(false, labteste.EstadoJogo());
		
		assertEquals(true, labteste1.TodosDragoesMortos());
		assertEquals(false, labteste2.TodosDragoesMortos());
		
		labteste.setCaracter(0,0,' ');
		assertEquals(' ', labteste.GetBoardChar(0, 0));
		
		assertEquals(false,labteste.Saida());
		labteste.SetSaida();
		labteste.GetHeroi().EstaSaida();
		assertEquals(true, labteste.EstadoJogo());		
		assertEquals(true, labteste.Saida());
		
	
		
		
	/*String status1= labteste.GetBoardString();
		assertEquals(status1,labteste.GetBoardString());
		*/
		
		
	   
		
	}
	
/**
 * Testes do movimento do heroi
 */
	@Test
	public void Testmovimentoheroi()
	{
		Labirinto l = new Labirinto();


		assertEquals('X', l.GetBoardChar(1, 0));

		// movimentacao; 1 - cima, 2 - baixo, 3 - esquerda, 4 - direita, 5 - nao
		assertEquals(1, l.MovimentaHeroi(1));

		assertEquals(0, l.MovimentaHeroi(2));
		assertEquals(0, l.MovimentaHeroi(1));
		
		assertEquals(0, l.MovimentaHeroi(2));
		assertEquals(1, l.MovimentaHeroi(3));
		assertEquals(1, l.MovimentaHeroi(4));
		
		assertEquals(0, l.MovimentaHeroi(1));
		assertEquals(0, l.MovimentaHeroi(4));
		assertEquals(0, l.MovimentaHeroi(3));
		
		assertEquals(0, l.MovimentaHeroi(4));
		assertEquals(1, l.MovimentaHeroi(2));
		
		assertEquals(2, l.MovimentaHeroi(5));
	}
	/**
	 *Testes de movimento de dragões 
	 */
	@Test
	public void TestmovimentoDraao()
	{
		Labirinto l = new Labirinto();

		// movimentacao; 1 - cima, 2 - baixo, 3 - esquerda, 4 - direita, 5 - nao
		// move
		assertEquals(0, l.MovimentaDragao(1,0));
		assertEquals(0, l.MovimentaDragao(2,0));
		
		assertEquals(1, l.MovimentaDragao(3,0));
		assertEquals(1, l.MovimentaDragao(4,0));
		
		assertEquals(0, l.MovimentaDragao(1,0));
		assertEquals(0, l.MovimentaDragao(1,0));
		assertEquals(0, l.MovimentaDragao(4,0));
		assertEquals(0, l.MovimentaDragao(3,0));
		
		assertEquals(0, l.MovimentaDragao(4,0));
		assertEquals(1, l.MovimentaDragao(1,0));
		assertEquals(1, l.MovimentaDragao(2,0));
		
		assertEquals(1, l.MovimentaDragao(5,0));
		
		assertEquals(2, l.MovimentaDragao(6,0));
	
	}
	
	/**
	 * Testes de dragões
	 */
	@Test
	public void TesteDragao()
	{
		Dragao dragao=new Dragao(1, 2);
		
		assertEquals(false,dragao.GetEstado_Morto());
		assertEquals(true,dragao.Ativo());
	
		dragao.Adormecer();
		assertEquals(false,dragao.Ativo());
		dragao.Acordar();
		
		dragao.Morrer();
		assertEquals(false,dragao.Ativo());	
	}
	
	/**
	 * Testes do herói
	 */
	@Test
	public void TesteHeroi()
	{

		Heroi heroi = new Heroi(1, 1);

		assertEquals('H', heroi.GetCaracter());
		assertEquals(false, heroi.Armado());

		heroi.EquiparEspada();
		assertEquals('A', heroi.GetCaracter());
		assertEquals(true, heroi.Armado());

		heroi.GetEspada().Desequipar();
		assertEquals(false, heroi.Armado());

		assertEquals(false, heroi.GetSaida());

		heroi.EstaSaida();
		assertEquals(true, heroi.GetSaida());

		heroi.NaoEstaSaida();
		assertEquals(false, heroi.GetSaida());

	}

	@Test

	/**
	 * Teste do gerador de labirinto
	 */
	public void TestaGeraLabirinto()
	{
		Gerador_de_Labirito labrinto = new Gerador_de_Labirito(11);
		Labirinto labteste10 = new Labirinto(11, 0);
		ArrayList<Dragao> dra = new ArrayList<Dragao>();
		Dragao drag = new Dragao();
		assertEquals(dra, labteste10.GetDragoes());

	}
	
}
