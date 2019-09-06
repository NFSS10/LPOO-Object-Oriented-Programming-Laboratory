package maze.logic;
/**
 * Esta classe visa representar a peça dragão e contém métodos que permitem manipular e obter informação sobre o mesmo.
 * 
 * @author Nuno Silva e Luis Soares
 *
 */
public class Dragao extends Peca
{
	private boolean adormecido;
	private boolean morto;
	
	/**
	 * Construtor base que cria um dragão com as especificações da 1ª iteração do projeto.
	 */
	public Dragao()
	{
		caracter='D';
		pos_x = 3;
		pos_y = 1;
		adormecido =false;
		morto=false;
	}
	/**
	 * Construtor que cria um dragão em coordenadas específicas.
	 * @param x coordenada x do labirinto onde o dragão irá ser colocado
	 * @param y coordenada x do labirinto onde o dragão irá ser colocado
	 */
	public Dragao(int x, int y)
	{
		caracter='D';
		pos_x = x;
		pos_y = y;
		adormecido =false;
		morto=false;
	}
	
/**
 * Retorna o estado do dragão
 * @return true se estiver morto, false se estiver vivo
 */
	public boolean GetEstado_Morto()
	{
		return morto;
	}

	/**
	 * Retorna o estado do dragão, ve se está ativo ou não.
	 * Estar ativo é não estar mordo ou não estar adormecido.
	 * @return true se estiver ativo, false se nao estiver ativo
	 */
	public boolean Ativo()
	{
		if(adormecido || morto)
			return false;
		
		return true;
	}
	
/**
 * Adormece o dragão, muda o estado de "adormecido" e muda o caracter correspondente.
 */
	public void Adormecer()
	{
		adormecido=true;
		caracter='d';
	}
	/**
	 * Acorda o dragão, muda o estado de "adormecido" e muda o caracter correspondente.
	 */
	public void Acordar()
	{
		adormecido=false;
		caracter='D';
	}
/**
 * Mata o dragão,  muda o estado de "adormecido" e muda o caracter correspondente.
 */
	public void Morrer()
	{
		morto=true;
		caracter=' ';
	}
	
	
	

}
