package maze.logic;
/**
 * Classe Peca que tem e representa todas as informações, assim como métodos, comuns entre todas as peças do jogo. 
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
	 * Retorna o caracter da peça.
	 * @return char da peça.
	 */
	public char GetCaracter()
	{
		return caracter;
	}
/**
 * Retorna a posição x da peça.
 * @return coordenada x da peça no labirinto.
 */
	public int GetPosX()
	{
		return pos_x;
	}
	/**
	 * Retorna a posição y da peça.
	 * @return coordenada y da peça no labirinto.
	 */
	public int GetPosY()
	{
		return pos_y;
	}

	/**
	 * Altera a posição da peça para corresponder a uma posição do labirinto acima.
	 */
	public void MoveUp()
	{
		pos_x--;
	}
	/**
	 * Altera a posição da peça para corresponder a uma posição do labirinto abaixo.
	 */
	public void MoveDown()
	{
		pos_x++;
	}
	/**
	 * Altera a posição da peça para corresponder a uma posição do labirinto à direita.
	 */
	public void MoveRight()
	{
		pos_y++;
	}
	/**
	 * Altera a posição da peça para corresponder a uma posição do labirinto à esquerda
	 */
	public void MoveLeft()
	{
		pos_y--;
	}
	/**
	 * Troca as coordenadas da peça pelas coordenadas específicadas.
	 * @param x coordenada x específicada.
	 * @param y coordenada y específicada.
	 */
	public void mudarCoordenadas(int x, int y)
	{
		pos_x=x;
		pos_y=y;
	}
	
		
		
}
