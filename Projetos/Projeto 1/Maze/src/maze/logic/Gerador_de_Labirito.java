package maze.logic;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.Stack;

import javax.print.attribute.standard.PrinterLocation;
/**
 * Esta classe tem o objetivo de criar um labirinto aleatório com um tamanho específico.
 * Características:
 * Só funciona com tamanhos ímpares, só tem uma saída e os corredores só tem de 1 caracter de largura de espaço, ou seja, nunca vai existir corredores com 2x2 de espaço.
 * Baseado nos sites fornecidos nas aulas práricas.
 * 
 * @author Nuno Silva
 *
 */
public class Gerador_de_Labirito
{
	private char board[][];
	private boolean visitado[][];
	private Stack<Point2D> pilha;
	private Point2D pontoguia;
	private Point2D pontoguiaStack;

	/**
	 * Imprime o tabuleiro e o tabuleiro de visitado para ver o estado do
	 * labirinto.
	 */
/*
	public void Print()
	{
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board.length; j++)
			{
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}

		for (int i = 0; i < visitado.length; i++)
		{
			for (int j = 0; j < visitado.length; j++)
			{
				if (visitado[i][j] == true)
					System.out.print('+' + " ");
				else
					System.out.print('.' + " ");

			}
			System.out.println();
		}
	  }
*/
	
	/**
	 * Coloca o tabuleiro com espaços rodeado de 'X' e coloca o array visitado
	 * todo a false em preparação para o algoritmo que cria o labirinto.
	 */
	public void Encher()
	{
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				if ((i % 2 == 0) || (j % 2 == 0))
					board[i][j] = 'X';
				else
					board[i][j] = ' ';
			}
		}
		for (int i = 0; i < visitado.length; i++)
		{
			for (int j = 0; j < visitado[i].length; j++)
			{
				visitado[i][j] = false;
			}
		}
	}

	/**
	 * Vê à volta do pontoguia, se tem espaços disponíveis. Usado no algoritmo.
	 * 
	 * @return true se exitirem espaços disponíveis.
	 */
	public boolean VisinhosDisponiveis()
	{
		int x, y;
		x = (int) pontoguiaStack.getX();
		y = (int) pontoguiaStack.getY();

		if (x + 1 < visitado.length)
			if (visitado[x + 1][y] == false)
				return true;

		if (x - 1 >= 0)
			if (visitado[x - 1][y] == false)
				return true;

		if (y + 1 < visitado.length)
			if (visitado[x][y + 1] == false)
				return true;

		if (y - 1 >= 0)
			if (visitado[x][y - 1] == false)
				return true;

		return false;
	}

	/**
	 * Avança para a esquerda.
	 */
	public void goLEFT()
	{
		board[(int) pontoguia.getX()][(int) pontoguia.getY() - 1] = ' ';// Remove
		pontoguia.setLocation((int) pontoguia.getX(), (int) pontoguia.getY() - 2);// Atualiza
																					// no
																					// labirinto

		Point2D ponto = new Point(((int) pontoguiaStack.getX()), (int) pontoguiaStack.getY() - 1);// Atualiza
																									// no
																									// visitado
		visitado[(int) ponto.getX()][(int) ponto.getY()] = true;

		pilha.push(ponto); // coloca na stack

	}

	/**
	 * Avança para a direita.
	 */
	public void goRIGHT()
	{
		board[(int) pontoguia.getX()][(int) pontoguia.getY() + 1] = ' ';// Remove
		pontoguia.setLocation((int) pontoguia.getX(), (int) pontoguia.getY() + 2);// Atualiza
																					// no
																					// labirinto

		Point2D ponto = new Point(((int) pontoguiaStack.getX()), (int) pontoguiaStack.getY() + 1);// Atualiza
																									// no
																									// visitado
		visitado[(int) ponto.getX()][(int) ponto.getY()] = true;

		pilha.push(ponto); // coloca na stack
	}

	/**
	 * Avança para baixo.
	 */
	public void goDOWN()
	{
		board[(int) pontoguia.getX() + 1][(int) pontoguia.getY()] = ' ';// Remove
		pontoguia.setLocation((int) pontoguia.getX() + 2, (int) pontoguia.getY());// Atualiza
																					// no
																					// labirinto

		Point2D ponto = new Point(((int) pontoguiaStack.getX() + 1), (int) pontoguiaStack.getY());// Atualiza
																									// no
																									// visitado
		visitado[(int) ponto.getX()][(int) ponto.getY()] = true;

		pilha.push(ponto); // coloca na stack
	}

	/**
	 * Avança para cima.
	 */
	public void goUP()
	{
		board[(int) pontoguia.getX() - 1][(int) pontoguia.getY()] = ' ';// Remove
		pontoguia.setLocation((int) pontoguia.getX() - 2, (int) pontoguia.getY());// Atualiza
																					// no
																					// labirinto

		Point2D ponto = new Point(((int) pontoguiaStack.getX() - 1), (int) pontoguiaStack.getY());// Atualiza
																									// no
																									// visitado
		visitado[(int) ponto.getX()][(int) ponto.getY()] = true;

		pilha.push(ponto); // coloca na stack
	}

	/**
	 * Verifica se o algoritmo já passou por todo o labirinto
	 * 
	 * @return true se já completou o labirinto.
	 */
	public boolean Completo()
	{
		for (int i = 0; i < visitado.length; i++)
		{
			for (int j = 0; j < visitado[i].length; j++)
			{
				if (visitado[i][j] == false)
					return false;
			}
		}
		return true;
	}

	/**
	 * Cria os caminhos do labirinto.
	 */
	public void Construir()
	{
		int x, y;
		Random nalea = new Random();
		int movimento;
		boolean sair = false;

		while (!Completo() || !sair)
		{
			movimento = nalea.nextInt(4);
			x = (int) pontoguiaStack.getX();
			y = (int) pontoguiaStack.getY();

			if (movimento == 0) // Cima
			{
				if (x - 1 >= 0)
					if (!visitado[x - 1][y])
					{
						goUP();
						pontoguiaStack.setLocation(x - 1, y);
					}
			} else if (movimento == 1) // Baixo
			{
				if (x + 1 < visitado.length)
					if (!visitado[x + 1][y])
					{
						goDOWN();
						pontoguiaStack.setLocation(x + 1, y);
					}
			} else if (movimento == 2) // Esquerda
			{
				if (y - 1 >= 0)
					if (!visitado[x][y - 1])
					{
						goLEFT();
						pontoguiaStack.setLocation(x, y - 1);
					}
			} else if (movimento == 3) // Direita
			{
				if (y + 1 < visitado.length)
					if (!visitado[x][y + 1])
					{
						goRIGHT();
						pontoguiaStack.setLocation(x, y + 1);
					}
			}

			// ver vizinhança e agir conforme
			if (!VisinhosDisponiveis())
			{
				pontoguiaStack = pilha.pop();
				pontoguia.setLocation(pontoguiaStack.getX() * 2 + 1, pontoguiaStack.getY() * 2 + 1);
			}

			if (pilha.isEmpty())
				sair = true;

		}

	}

	/**
	 * Método usado para criar e retornar o labirinto final.
	 * 
	 * @return char[][] que corresponde ao labirinto gerado.
	 */
	public char[][] Gerar()
	{

		Random numeroaleatorio = new Random();
		boolean cond = true;

		int fila, coluna;
		int tentativas = 4;
		do
		{

			fila = numeroaleatorio.nextInt(board.length);

			if ((fila % 2 == 1) || (fila == board.length - 1) || fila == 0)
				cond = false;

		} while (cond);

		cond = true;
		tentativas = 4;
		do
		{
			coluna = numeroaleatorio.nextInt(board.length);
			tentativas--;

			if ((fila == 0) || (fila == board.length - 1))
			{
				if ((coluna % 2 == 1) && (coluna < board.length - 2))
					cond = false;
			} else if (((coluna == 0) && (tentativas < 1)) || (coluna == board.length - 1))
				cond = false;

		} while (cond);

		// Coloca a saida no sitio certo
		board[fila][coluna] = 'S';

		// Coloca o marcador para gerar o
		// labirinto______________________________________________________________________
		if ((fila + 1) < board.length)
		{
			if (board[fila + 1][coluna] != 'X')
			{
				// board[fila+1][coluna] = '+';
				pontoguia = new Point(fila + 1, coluna);
			}
		}

		if ((coluna + 1) < board.length)
		{
			if (board[fila][coluna + 1] != 'X')
			{
				// board[fila][coluna+1] = '+';
				pontoguia = new Point(fila, coluna + 1);
			}
		}

		if ((coluna - 1) >= 0)
		{
			if (board[fila][coluna - 1] != 'X')
			{
				// board[fila][coluna-1] = '+';
				pontoguia = new Point(fila, coluna - 1);
			}
		}

		if ((fila - 1) >= 0)
		{
			if (board[fila - 1][coluna] != 'X')
			{
				// board[fila-1][coluna] = '+';
				pontoguia = new Point(fila - 1, coluna);
			}
		}
		// __________________________________________________________________________________________________________________

		// Marca como visitado
		// visitado[(fila - 1) / 2][(coluna - 1) / 2] = true;
		visitado[(int) pontoguia.getX() / 2][(int) pontoguia.getY() / 2] = true;
		pontoguiaStack = new Point((int) pontoguia.getX() / 2, (int) pontoguia.getY() / 2);

		pilha.push(pontoguiaStack);

		Construir();

		return board;
	}

	/**
	 * Construtor onde cria um labirinto com o tamanho desejado.
	 * 
	 * @param n
	 *            tamanho pretendido do labirinto. ( n linhas por n colunas)
	 */
	public Gerador_de_Labirito(int n)
	{
		board = new char[n][n];
		visitado = new boolean[(n - 1) / 2][(n - 1) / 2];
		pilha = new Stack<Point2D>();
		Encher();
	}
	
	
	/*
	public static void main(String[] args)
	{
		Gerador_de_Labirito gerador = new Gerador_de_Labirito(11);
		gerador.Print();
		
		
	}
	*/
	
}
