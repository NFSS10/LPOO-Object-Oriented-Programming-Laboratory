package maze.logic;
/**
 * Classe Peca que tem e representa todas as informa��es, assim como m�todos, comuns entre todas as pe�as do jogo. 
 * 
 * @author Nuno Silva e Luis Soares
 *
 */
public class Peca
{
	protected char caracter;

	protected int pos_x;
	protected int pos_y;

	////////////////////////////
	////////////////////////////
	
	/**
	 * Retorna o caracter da pe�a.
	 * @return char da pe�a.
	 */
	public char GetCaracter()
	{
		return caracter;
	}
/**
 * Retorna a posi��o x da pe�a.
 * @return coordenada x da pe�a no labirinto.
 */
	public int GetPosX()
	{
		return pos_x;
	}
	/**
	 * Retorna a posi��o y da pe�a.
	 * @return coordenada y da pe�a no labirinto.
	 */
	public int GetPosY()
	{
		return pos_y;
	}

	/**
	 * Altera a posi��o da pe�a para corresponder a uma posi��o do labirinto acima.
	 */
	public void MoveUp()
	{
		pos_x--;
	}
	/**
	 * Altera a posi��o da pe�a para corresponder a uma posi��o do labirinto abaixo.
	 */
	public void MoveDown()
	{
		pos_x++;
	}
	/**
	 * Altera a posi��o da pe�a para corresponder a uma posi��o do labirinto � direita.
	 */
	public void MoveRight()
	{
		pos_y++;
	}
	/**
	 * Altera a posi��o da pe�a para corresponder a uma posi��o do labirinto � esquerda
	 */
	public void MoveLeft()
	{
		pos_y--;
	}
	/**
	 * Troca as coordenadas da pe�a pelas coordenadas espec�ficadas.
	 * @param x coordenada x espec�ficada.
	 * @param y coordenada y espec�ficada.
	 */
	public void mudarCoordenadas(int x, int y)
	{
		pos_x=x;
		pos_y=y;
	}
	
		
		
}
