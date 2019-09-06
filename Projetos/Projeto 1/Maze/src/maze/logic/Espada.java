package maze.logic;
/**
 * Esta classe visa representar a pe�a Espada e cont�m m�todos que permitem manipular e obter informa��o sobre o mesmo.
 * @author Nuno Silva e Luis Soares
 *
 */
public class Espada extends Peca
{
	private boolean equipada;

	/**
	 * Construtor da classe, que cria uma espada com x=0 e y=0, sendo que ter� de ser mudada posteriormente, visto que a espada est� agragada ao her�i.
	 */
	public Espada()
	{
		caracter = 'E';
		pos_x = 0;//8; //Usada na primeira itera��o do projeto
		pos_y = 0;//1; //Usada na primeira itera��o do projeto
		equipada = false;
	}
/**
 * Retorna o estado da espada.
 * @return true se estiver equipada, false caso n�o esteja equipada.
 */
	public boolean GetEstado()
	{
		return equipada;
	}
/**
 * Equipa a espada.
 */
	public void Equipar()
	{
		equipada = true;
		caracter = ' ';
	}
/**
 * Desequipa a espada.
 */
	public void Desequipar()
	{
		equipada = false;
	}

}
