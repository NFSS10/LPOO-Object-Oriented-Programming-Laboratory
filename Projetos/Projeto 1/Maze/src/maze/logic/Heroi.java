package maze.logic;
/**
 * Esta classe visa representar a pe�a Heroi e cont�m m�todos que permitem manipular e obter informa��o sobre o mesmo.
 * 
 * @author Nuno Silva e Luis Soares
 *
 */
public class Heroi extends Peca
{
	private boolean saida=false; /** Se true, esta na saida*/
	
	private Espada espada;
	
	
	
	/**
	 * Construtor base, onde x e y s�o a posi��o no labirinto da 1� itera��o, TP1
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
	 *            coordenada onde se ira colocar, no labirinto, o her�i.
	 * @param y
	 *            coordenada onde se ira colocar, no labirinto, o her�i.
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
	 * Retorna informa��o sobre o posicionamento do her�i em rela��o � sa�da.
	 * @return true se o her�i est� posicionado na sa�da, caso contr�rio, false.
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
	 * Verifica se o her�i est� armado.
	 * 
	 * @return true se o her�i est� armado, caso contr�rio false.
	 */
	public boolean Armado()
	{
		return espada.GetEstado();
	}

	/**
	 * Coloca o estado do her�i como, est� na saida.
	 */
	public void EstaSaida()
	{
		saida = true;
	}

	/**
	 * Coloca o estado do her�i como, n�o est� na saida.
	 */
	public void NaoEstaSaida()
	{
		saida = false;
	}
}
