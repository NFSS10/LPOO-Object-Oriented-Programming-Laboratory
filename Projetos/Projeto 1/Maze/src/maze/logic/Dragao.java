package maze.logic;
/**
 * Esta classe visa representar a pe�a drag�o e cont�m m�todos que permitem manipular e obter informa��o sobre o mesmo.
 * 
 * @author Nuno Silva e Luis Soares
 *
 */
public class Dragao extends Peca
{
	private boolean adormecido;
	private boolean morto;
	
	/**
	 * Construtor base que cria um drag�o com as especifica��es da 1� itera��o do projeto.
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
	 * Construtor que cria um drag�o em coordenadas espec�ficas.
	 * @param x coordenada x do labirinto onde o drag�o ir� ser colocado
	 * @param y coordenada x do labirinto onde o drag�o ir� ser colocado
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
 * Retorna o estado do drag�o
 * @return true se estiver morto, false se estiver vivo
 */
	public boolean GetEstado_Morto()
	{
		return morto;
	}

	/**
	 * Retorna o estado do drag�o, ve se est� ativo ou n�o.
	 * Estar ativo � n�o estar mordo ou n�o estar adormecido.
	 * @return true se estiver ativo, false se nao estiver ativo
	 */
	public boolean Ativo()
	{
		if(adormecido || morto)
			return false;
		
		return true;
	}
	
/**
 * Adormece o drag�o, muda o estado de "adormecido" e muda o caracter correspondente.
 */
	public void Adormecer()
	{
		adormecido=true;
		caracter='d';
	}
	/**
	 * Acorda o drag�o, muda o estado de "adormecido" e muda o caracter correspondente.
	 */
	public void Acordar()
	{
		adormecido=false;
		caracter='D';
	}
/**
 * Mata o drag�o,  muda o estado de "adormecido" e muda o caracter correspondente.
 */
	public void Morrer()
	{
		morto=true;
		caracter=' ';
	}
	
	
	

}
