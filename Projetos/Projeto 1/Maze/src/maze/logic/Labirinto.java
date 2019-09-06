package maze.logic;

import java.util.ArrayList;
import java.util.Random;

import org.omg.CORBA.PUBLIC_MEMBER;
/**
 * Classe principal do jogo, onde t�m o conjunto de todas as pe�as do jogo assim como m�todos essencias para a intera��o entre o tabuleiro e todas as pe�as do tabuleiro.
 * OBS: (0,0) canto superior do esquerdo do labirinto. Andar para a direita +1 no y. Andar para baixo +1 no x.
 * 
 * @author Nuno Silva
 */
public class Labirinto
{
	private static char board[][];
	
	private Heroi heroi;
	private ArrayList<Dragao> dragoes;
	private boolean saida;
	private int sX;
	private int sY;
	private String status;
	
	
	
	/////////////////////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Construtor base que cria o labirinto da 1� itera��o, TP1.
	 */
	public Labirinto()
	{
		heroi= new Heroi();
		heroi.GetEspada().mudarCoordenadas(8, 1);
		sX=5;
		sY=9;
		dragoes= new ArrayList<Dragao>();
		Dragao dragao = new Dragao();
		dragoes.add(dragao);
		board=new char[][]{
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', ' ', ' ', ' ', ' ', ' ', 'X', ' ', 'S' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', 'X', ' ', 'X', ' ', 'X' },
				{ 'X', ' ', 'X', 'X', ' ', ' ', ' ', ' ', ' ', 'X' },
				{ 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X' } };

		
	}
	
/**
 * Construtor que cria um tabuleiro aleat�rio com um tamanho espec�fico e com um n�mero espec�fico de drag�es.
 * Tamb�m posiciona aleat�riamente no tabuleiro os drag�es assim como 1 her�i e 1 espada. 
 * @param n tamanho do tabuleiro, (n x n).
 * @param numdragoes n�mero de drag�es.
 */
	public Labirinto(int n, int numdragoes)
	{

		int x, y;
		Random nale=new Random();
		Gerador_de_Labirito geradorLabirinto = new Gerador_de_Labirito(n);
		
		board=geradorLabirinto.Gerar();
		
		
		dragoes= new ArrayList<Dragao>();
		boolean aceite=false;
		
		
		//Heroi
		while (!aceite)
		{
			x = nale.nextInt(n);
			y = nale.nextInt(n);
			if (EspacoLivre(x, y))
			{
				aceite = true;
				heroi = new Heroi(x, y);
			}

		}
		
		//Espada
		aceite = false;
		while (!aceite)
		{
			x = nale.nextInt(n);
			y = nale.nextInt(n);
			if (EspacoLivre(x, y))
			{
				aceite = true;
				heroi.GetEspada().mudarCoordenadas(x, y);
			}

		}
		
		
		//Dragoes
		AtualizarPecas();
		for (int i = 0; i < numdragoes; i++)
		{
			aceite = false;
			while (!aceite)
			{
				x = nale.nextInt(n);
				y = nale.nextInt(n);
				if (EspacoLivre(x, y))
				{
					aceite = true;
					Dragao dragao = new Dragao(x, y);
					dragoes.add(dragao);
				}

			}
		}
		AtualizarPecas();
		atribuirCoordSaida();
		saida=false;
	}
	/**
	 * Verifica se todos os drag�es est�o mortos.
	 * @return true se todos os drag�es est�o mortos.
	 */
	public boolean TodosDragoesMortos()
	{
		for (int i = 0; i < dragoes.size(); i++)
		{
			if (!dragoes.get(i).GetEstado_Morto())
				return false;

		}

		return true;
	}
	/**
	 * Verifica se tem alguma pe�a � volta de uma posi��o espec�fica.
	 * @param x coordenada x no labirinto.
	 * @param y coordenada y no labirinto.
	 * @return true se estiver desocupada e n�o adjacente a outra pe�a.
	 */
	public boolean EspacoLivre(int x, int y)
	{
		if(x<0 || y<0 || x>=board.length||y>=board.length)
			return false;
		
		if ((x + 1 >= board.length) || (y + 1 >= board.length) ||(x-1<0)||(y-1<0)||((board[x+1][y]=='D') ||(board[x-1][y]=='D')||(board[x][y+1]=='D')||(board[x][y-1]=='D'))||((board[x+1][y]=='H') ||(board[x-1][y]=='H')||(board[x][y+1]=='H')||(board[x][y-1]=='H' )))
				return false;
		
		
		else if(board[x][y]==' ')
			return true;
		
		return false;
	}
	
	/**
	 * Apaga um caracter do labir�nto numa posi��o espec�fica.
	 * @param x coordenada x no labirinto.
	 * @param y coordenada y no labirinto.
	 */
	public void ApagaPos(int x, int y)
	{
		board[x][y] = ' ';
	}

	/**
	 * Verifica e atualiza o estado do jogo e as suas pe�as.
	 */
	public void AtualizarPecas()
	{
		
		// Estado_______________________________________

		// Se estiver na posicao da espada e nao a tiver equipada, equipar
		if (heroi.GetEspada().GetPosX() == heroi.GetPosX() && heroi.GetEspada().GetPosY() == heroi.GetPosY()
				&& !heroi.GetEspada().GetEstado())
		{
			heroi.EquiparEspada();
			status = "Est�s armado.";
			System.out.println(status);
		}

		// Se estiver na posicao do dragao e estiver armado, mata-lo e ativar saida
		for (int i = 0; i < dragoes.size(); i++)
		{
			if (heroi.GetPosX() == dragoes.get(i).GetPosX() && heroi.GetPosY() == dragoes.get(i).GetPosY() && heroi.Armado()
					&& !dragoes.get(i).GetEstado_Morto())
			{
				dragoes.get(i).Morrer();
				status = "Matas-te o Dragao !";
				System.out.println(status);
			}
		}
		if(TodosDragoesMortos())
		{
			saida = true;
			if(dragoes.size()==0) //Por causa de um bug
				status="";
			else
				status = "Saida desbloqueada.";
			
			System.out.println(status);
		}
		
		if (heroi.GetPosX() == sX && heroi.GetPosY() ==sY )
			heroi.EstaSaida();
		else
			heroi.NaoEstaSaida();

			// _____________________________________________

			// Posicoes
		board[heroi.GetEspada().GetPosX()][heroi.GetEspada().GetPosY()] = heroi.GetEspada().GetCaracter(); // Espada
		
		for (int i = 0; i < dragoes.size(); i++)
		{
			board[dragoes.get(i).GetPosX()][dragoes.get(i).GetPosY()] = dragoes.get(i).GetCaracter(); // Dragao
			if (dragoes.get(i).GetPosX() == heroi.GetEspada().GetPosX() && dragoes.get(i).GetPosY() == heroi.GetEspada().GetPosY()) //Dragao em Cima da espada
			{
				board[dragoes.get(i).GetPosX()][dragoes.get(i).GetPosY()] = 'F';
				status = "Um Drag�o est� em cima da Espada";
			}
		}
		
		board[heroi.GetPosX()][heroi.GetPosY()] = heroi.GetCaracter(); // Heroi
	}


	/**
	 * 	Mostra na consola o labirinto
	 */
	public void Print()
	{
		AtualizarPecas();
		System.out.print(this.GetBoardString());
		/*for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
			
		}*/

	}


	/**
	 * Retorna o caracter da coordenada do labirinto (ver "OBS" da classe)
	 * @param x coordenada x no labirinto.
	 * @param y coordenada y no labirinto.
	 * @return
	 */
	public char GetBoardChar(int x, int y)
	{
		return board[x][y];
	}
	/**
	 * Retorna o her�i do labirinto.
	 * @return heroi.
	 */
	public Heroi GetHeroi()
	{
		return heroi;
	}
	
	/**
	 * Retorna um drag�o.
	 * @param n indice o arraylist "dragoes" do labirinto.
	 * @return dragao
	 */
	public Dragao GetDragao(int n)
	{
		return dragoes.get(n);
	}
	/**
	 * Retorna o estado da sa�da.
	 * @return true se a sa�da estiver ativada.
	 */
	public boolean GetSaida()
	{
		return saida;
	}
	/**
	 * Muda saida para true
	 */
	public void SetSaida()
	{
		saida=true;
	}
	
	/**
	 * Retorna o labirinto como string. (OBS: Podia ter feito o override do "to_string" em vez desta fun��o)
	 * @return string que representa o tabuleiro.
	 */
	public String GetBoardString()
	{
		String res = new String();
		res = "";
		
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board[i].length; j++)
			{
				res=res+board[i][j] + " ";
			}
			res=res+"\n";
		}
		return res;
	}
	/**
	 * Retorna todos os drag�es.
	 * @return ArrayList
	 */
	public ArrayList<Dragao> GetDragoes()
	{
		return dragoes;
	}
	/**
	 * Retorna o estado do jogo.
	 * @return string que diz o estado do jogo.
	 */
	public String GetStatus()
	{return status;}
	
	

	/**
	 * Movimenta o heroi.
	 * @param movimento n�mero que representa o movimento: 1 = cima / 2 = baixo / 3 = esquerda / 4 = direita
	 * @return 0 = consegue mover / 1 = nao consegue mover / 2 - erro no switch
	 */
	public int MovimentaHeroi(int movimento)
	{
		switch (movimento)
		{
		case 1: // Movimento para cima
			if (GetBoardChar(heroi.GetPosX() - 1, heroi.GetPosY()) == 'X'
				|| (GetBoardChar(heroi.GetPosX() - 1, heroi.GetPosY()) == 'S' && !saida))
				return 1;
			else
			{
				ApagaPos(heroi.GetPosX(), heroi.GetPosY());
				heroi.MoveUp();
			}
			break;
		case 2: // Movimento para baixo
			if (GetBoardChar(heroi.GetPosX() + 1, heroi.GetPosY()) == 'X'
					|| (GetBoardChar(heroi.GetPosX() +1, heroi.GetPosY()) == 'S' && !saida))
				return 1;
			else
			{
				ApagaPos(heroi.GetPosX(), heroi.GetPosY());
				heroi.MoveDown();
			}
			break;
		case 3: // Movimento para a esquerda
			if (GetBoardChar(heroi.GetPosX(), heroi.GetPosY() - 1) == 'X'
					|| (GetBoardChar(heroi.GetPosX(), heroi.GetPosY()-1) == 'S' && !saida))
				return 1;
			else
			{
				ApagaPos(heroi.GetPosX(), heroi.GetPosY());
				heroi.MoveLeft();
			}
			break;
		case 4: // Movimento para a direita
			if (GetBoardChar(heroi.GetPosX(), heroi.GetPosY() + 1) == 'X'
					|| (GetBoardChar(heroi.GetPosX(), heroi.GetPosY()+1) == 'S' && !saida))
				return 1;
			else
			{
				ApagaPos(heroi.GetPosX(), heroi.GetPosY());
				heroi.MoveRight();
			}
			break;

		default:
			return 2;
		}

		return 0;
	}

	/**
	 * Movimenta um drag�o.
	 * 
	 * @param movimento n�mero que representa o movimento: 1 = cima / 2 = baixo / 3 = esquerda / 4 = direita / 5 = n�o se movimenta
	 * @param indice indice do drag�o que se pretende movimentar.
	 * @return 0 = consegue mover / 1 = nao consegue mover / 2 - erro no switch
	 */
	public int MovimentaDragao(int movimento, int indice)
	{
		switch (movimento)
		{
		case 1: // Movimento para cima
			if (GetBoardChar(dragoes.get(indice).GetPosX() - 1, dragoes.get(indice).GetPosY()) == 'X'
					|| (GetBoardChar(dragoes.get(indice).GetPosX() - 1, dragoes.get(indice).GetPosY()) == 'S'))
				return 1;
			else
			{
				ApagaPos(dragoes.get(indice).GetPosX(), dragoes.get(indice).GetPosY());
				dragoes.get(indice).MoveUp();
			}
			break;
		case 2: // Movimento para baixo
			if (GetBoardChar(dragoes.get(indice).GetPosX() + 1, dragoes.get(indice).GetPosY()) == 'X'
					|| (GetBoardChar(dragoes.get(indice).GetPosX() + 1, dragoes.get(indice).GetPosY()) == 'S'))
				return 1;
			else
			{
				ApagaPos(dragoes.get(indice).GetPosX(), dragoes.get(indice).GetPosY());
				dragoes.get(indice).MoveDown();
			}
			break;
		case 3: // Movimento para a esquerda
			if (GetBoardChar(dragoes.get(indice).GetPosX(), dragoes.get(indice).GetPosY() - 1) == 'X'
					|| (GetBoardChar(dragoes.get(indice).GetPosX(), dragoes.get(indice).GetPosY() - 1) == 'S'))
				return 1;
			else
			{
				ApagaPos(dragoes.get(indice).GetPosX(), dragoes.get(indice).GetPosY());
				dragoes.get(indice).MoveLeft();
			}
			break;
		case 4: // Movimento para a direita
			if (GetBoardChar(dragoes.get(indice).GetPosX(), dragoes.get(indice).GetPosY() + 1) == 'X'
					|| (GetBoardChar(dragoes.get(indice).GetPosX(), dragoes.get(indice).GetPosY() + 1) == 'S'))
				return 1;
			else
			{
				ApagaPos(dragoes.get(indice).GetPosX(), dragoes.get(indice).GetPosY());
				dragoes.get(indice).MoveRight();
			}
			break;
		case 5:
			return 1;

		default:
			return 2;
		}

		return 0;
	}
	/**
	 * Verifica se o her�i est� a sa�r.
	 * @return true se o her�i esta na sa�da e a sa�da est� ativa.
	 */
	//Verifica se a saida esta disponivel
	public boolean Saida()
	{
		if(GetSaida() && GetHeroi().GetSaida())
			return true;
		
		return false;
	}

	/**
	 * Verifica se o her�i morreu com um drag�o, ou seja, estar na mesma posicao que o drag�o e o mesmo
	 * n�o estar adormecido ou n�o estar morto.
	 * @return true se o her�i est� morto, caso contr�rio, false
	 */
	public boolean HeroiMorreu()
	{
		for (int i = 0; i < dragoes.size(); i++)
		{
			if (GetHeroi().GetPosX() == GetDragao(i).GetPosX() 
					&& GetHeroi().GetPosY() == GetDragao(i).GetPosY()
					&& GetDragao(i).Ativo() && !GetHeroi().Armado())
			{
				status="O Drag�o matou-te !";
				System.out.println(status);
				return true;
			}
		}
		return false;
	}
	/**
	 * Adormece aleat�riamente os drag�es.
	 * @return n�mero que � usado noutro m�todo para regular a forma como os drag�es adormecem.
	 */
	//Adormecer aleatoriamente o Dragao
	public int AleAdormecer()
	{
		int nAle;
		int res=0;
		for (int i = 0; i < dragoes.size(); i++)
		{

			if (!GetDragao(i).GetEstado_Morto())
			{
				Random geradorAleatorio = new Random();
				nAle = geradorAleatorio.nextInt(6);
				if (nAle < 2)
				{
					GetDragao(i).Adormecer();
					res=3;
				} else
					GetDragao(i).Acordar();
			}
		}
		return res;
	}
	
	
	/**
	 * Verifica se o jogo terminou.
	 * @return true se terminou, ou seja se o her�i ganhou ou morreu.
	 */
	//True, acabou, false n acabou
	public boolean EstadoJogo()
	{
		if (Saida())
		{
			status="Ganhaste !!!";
			System.out.println(status);
			return true;
		}
		
		if(HeroiMorreu())
		{
			status="Morreste !";
			System.out.println(status);
			return true;
			}
				
		return false;
	}
	/**
	 * Obt�m informa��o sobre as coordenadas da sa�da e atualiza as mesmas.
	 */
	//ve pelos limites do board � procura da saida
	public void atribuirCoordSaida()
	{
		// X
		for (int i = 0; i < board.length; i++)
			if (board[i][0] == 'S')
			{
				sX = i;
				sY = 0;
				return;
			}

		for (int j = 0; j < board.length; j++)
			if (board[j][board.length - 1] == 'S')
			{
				sX = j;
				sY = board.length - 1;
				return;
			}

		// y
		for (int k = 0; k < board.length; k++)
			if (board[0][k] == 'S')
			{
				sX = 0;
				sY = k;
				return;

			}

		for (int l = 0; l < board.length; l++)
			if (board[board.length - 1][l] == 'S')
			{
				sX = board.length - 1;
				sY = l;
				return;
			}
	}
	
	/**
	 * Coloca um caracter espec�fico numa posi��o espec�fica do labirinto.
	 * @param x coordenada x no labirinto.
	 * @param y coordenada y no labirinto.
	 * @param car caracter espec�fico que vai ser adicionado.
	 */
	public void setCaracter(int x, int y, char car)
	{
		board[x][y]=car;
	}
	
}
