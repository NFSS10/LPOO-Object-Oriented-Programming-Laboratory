package maze.logic;
/**
 * Esta classe visa representar a peça Heroi e contém métodos que permitem manipular e obter informação sobre o mesmo.
 * 
 * @author Nuno Silva e Luis Soares
 *
 */
public class Heroi extends Peca
{
	private boolean saida=false; /** Se true, esta na saida*/
	
	private Espada espada;
	
	
	
	/**
	 * Construtor base, onde x e y são a posição no labirinto da 1ª iteração, TP1
	 */
	public Heroi()
	{
		caracter='H';
		pos_x = 1;
		pos_y = 1;
		espada=new Espada();
	}

	
	/**
	 * Construtor que cria um heroi com cooredenadas x e y especificas.
	 * 
	 * @param x
	 *            coordenada onde se ira colocar, no labirinto, o herói.
	 * @param y
	 *            coordenada onde se ira colocar, no labirinto, o herói.
	 */
	public Heroi(int x, int y)
	{
		caracter = 'H';
		pos_x = x;
		pos_y = y;
		espada = new Espada();
	}

	/**
	 * Retorna a espada.
	 * 
	 * @return espada
	 */
	public Espada GetEspada()
	{
		return espada;
	}
	/**
	 * Retorna informação sobre o posicionamento do herói em relação à saída.
	 * @return true se o herói está posicionado na saída, caso contrário, false.
	 */
	public boolean GetSaida()
	{
		return saida;
	}
	
	
	/**
	 * Equipa a espada.
	 */
	public void EquiparEspada()
	{
		espada.Equipar();
		caracter = 'A';
	}

	/**
	 * Verifica se o herói está armado.
	 * 
	 * @return true se o herói está armado, caso contrário false.
	 */
	public boolean Armado()
	{
		return espada.GetEstado();
	}

	/**
	 * Coloca o estado do herói como, está na saida.
	 */
	public void EstaSaida()
	{
		saida = true;
	}

	/**
	 * Coloca o estado do herói como, não está na saida.
	 */
	public void NaoEstaSaida()
	{
		saida = false;
	}
}
