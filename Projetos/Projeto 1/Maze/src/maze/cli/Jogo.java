package maze.cli;
import java.util.Random;
import java.util.Scanner;

import maze.logic.Labirinto;

/**
 * Classe que torna o jogo jogável na consola.
 * 
 * @author Nuno Silva e Luis Soares
 *
 */
public class Jogo
{
	private static Labirinto lab1 = new Labirinto();

	
	/**
	 * Recebe e processa o input do utilizador para o movimentar.
	 * @return número correspondente ao movimento pretendido.
	 */
	public int UtilizadorInput()
	{
		Scanner sc = new Scanner(System.in);
		char input;
		
		input =sc.next().charAt(0);
		if(input=='0')
		{
			System.out.println("Terminado pelo utilizador !");

			return 0;
		}

		// Processar o input para o movimento

		if (input == 'w')
			return 1;
		else if (input == 's')
			return 2;
		else if (input == 'a')
			return 3;
		else if (input == 'd')
			return 4;

		return -1;
	}

	
	/**
	 * Modo de jogo com dragões parados.
	 */
	public void MJ_Parado()
	{
		Boolean sair = false;

		int movimento;

		// Ciclo do jogo
		while ((!sair))
		{
			// Mostrar o labirinto
			lab1.Print();

			// Verificar estado do jogo
			sair = lab1.EstadoJogo();
			if (sair)
				return;

			movimento = 0;
			movimento = UtilizadorInput();

			// O movimento do heroi
			lab1.MovimentaHeroi(movimento);

		}

	}
	
	/**
	 * Modo de jogo com dragões que se movimentam.
	 */
	public void MJ_Movimento()
	{
		Boolean sair = false;
		Random geradorAleatorio = new Random();

		int movimentoDragao;
		int nmov;
		int movimento;

		// Ciclo do jogo
		while ((!sair))
		{
			// Mostrar o labirinto
			lab1.Print();

			// Verificar estado do jogo
			sair = lab1.EstadoJogo();
			if (sair)
				return;

			movimento = 0;
			movimento = UtilizadorInput();

			// O movimento do heroi
			lab1.MovimentaHeroi(movimento);
			for (int i = 0; i < lab1.GetDragoes().size(); i++)
			{
				if (lab1.GetDragao(0).Ativo())
				{
					movimentoDragao = geradorAleatorio.nextInt(5) + 1;
					nmov = lab1.MovimentaDragao(movimentoDragao, 0);
					// para o Dragao mexer-se mais
					if (nmov == 1)
						lab1.MovimentaDragao(movimentoDragao, 0);
				}
			}
		}

	}

	/**
	 * Modo de jogo com dragões que se movimentam e adormecem aleatoriamente.
	 */
	public void MJ_Movimento_e_Adormecer()
	{
		Boolean sair = false;
		Random geradorAleatorio = new Random();

		int movimentoDragao;
		int nmov;
		int movimento;

		int tempoAdormecido = 2; // Para manter minimamente 2 jogadas o dragao
									// adormecido quando o mesmo adormece
		// Ciclo do jogo
		while ((!sair))
		{
			//Mostrar o labirinto
			lab1.Print();
			
			//Verificar estado do jogo
			sair = lab1.EstadoJogo();
			if(sair)
				return;
			
			
			movimento=0;
			movimento=UtilizadorInput();
					
			//O movimento do heroi
			lab1.MovimentaHeroi(movimento);
			
			//Adormece ou nao o dragao
			if(tempoAdormecido>0)
				tempoAdormecido--;
			
			if (tempoAdormecido == 0)
				tempoAdormecido = lab1.AleAdormecer();
			
			
			for (int i = 0; i < lab1.GetDragoes().size(); i++)
			{
				if (lab1.GetDragao(i).Ativo())
				{
					movimentoDragao = geradorAleatorio.nextInt(5) + 1;
					nmov = lab1.MovimentaDragao(movimentoDragao, i);
					// para o Dragao mexer-se mais
					if (nmov == 1)
						lab1.MovimentaDragao(movimentoDragao, i);
				}
			}
			//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
		}
	}
	
	/**
	 * Método principal, que inicia o jogo na consola.
	 */
	public void Jogar()
	{
		Scanner sc = new Scanner(System.in);
		char input;
		
		while(true)
		{
		System.out.println("1: Dragao parado.");
		System.out.println("2: Dragao movimenta-se.");
		System.out.println("3: Dragao movimenta-se e adormece ocasionalmente.");
		System.out.println("0: Sair");
		System.out.println();
		System.out.println("Insira o numero para escolher o modo de Jogo: ");

			input = sc.next().charAt(0);
			if(input=='0')
				return;
			if (input == '1')
			{
				MJ_Parado();
				return;
			}
			if (input == '2')
			{
				MJ_Movimento();
				return;
			}
			if (input == '3')
			{
				MJ_Movimento_e_Adormecer();
				return;
			}
			
			//Se chegou aqui é porque nao inseriu um numero correto
			System.out.println();
			System.out.println("NUMERO INCORRETO tente de novo");

		}
	}
	
	
	public static void main(String[] args)
	{
		(new Jogo()).Jogar();
	}

}
