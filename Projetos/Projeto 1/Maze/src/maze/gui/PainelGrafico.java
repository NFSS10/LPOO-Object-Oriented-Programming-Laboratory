package maze.gui;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.omg.PortableServer.ID_ASSIGNMENT_POLICY_ID;

import maze.logic.Labirinto;
/**
 * Modo gráfico do jogo onde o jogo é jogado com rato/teclado e o tabuleiro é composto por imagens
 * em vez de caracteres.
 * Também tem métodos que possibilitam editar e gravar um labirinto.
 * @author Nuno Silva
 *
 */

public class PainelGrafico extends JPanel
{
	//Jframe principal (para ajustar tamanho da janela automaticamente)
	private JFrame frmPrincipal;
	
	//Imagens
	private BufferedImage dragao_Acordado;
	private BufferedImage dragao_Adormecido;
	private BufferedImage erva;
	private BufferedImage espada;
	private BufferedImage espada_Ind;
	private BufferedImage heroi_Armado;
	private BufferedImage heroi_Desarmado;
	private BufferedImage montanha;
	private BufferedImage saida_Disponivel;
	
	private BufferedImage aUsar;
	private BufferedImage select;
	private BufferedImage instr;

	//Utilidades
	private int x=8; //para ficar alinhado com a janela
	private int y=31;//para ficar alinhado com a janela
	
	//Coordenadas imagem Heroi
	private int coordX, coordY;
	//rato
	private int rx,ry;
	
	//Info Jogo
	private Labirinto labirinto;
	private static int tempoAdormecido = 2;
	private int modo;
	private int tamanho;
	private boolean sair=false;
	

	//Edição
	private boolean editar=false;
	private int selecionado;

	
	//----------------------------------------------------------------------------------------------------------------------------------------------

	
	/**
	 * Guarda o labirinto num ficheiro
	 */
	public void saveLabirinto()
	{
		try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("save.txt"), "utf-8")))
		{
			writer.write(labirinto.GetBoardString());
		} catch (Exception e)
		{
		}
		
	}
	

	/**
	 * Modo jogo onde os dragões estão parados
	 * @param movimento número que representa a direção do movimento do herói
	 * @return true quando terminar o jogo.
	 */
	public boolean MJ_Parado(int movimento)
	{
		Boolean sair = false;

		// Verificar estado do jogo
		sair = labirinto.EstadoJogo();
		if (sair)
			return true;

		// O movimento do heroi
		labirinto.MovimentaHeroi(movimento);
		labirinto.AtualizarPecas();

		sair = labirinto.EstadoJogo();
		if (sair)
			return true;

		return false;

	}

	/**
	 * Modo jogo onde os dragões se movimentam
	 * @param movimento número que representa a direção do movimento do herói
	 * @return true quando terminar o jogo.
	 */
	public boolean MJ_Movimento(int movimento)
	{
		Boolean sair = false;
		Random geradorAleatorio = new Random();

		int movimentoDragao;
		int nmov;

		// Verificar estado do jogo
		sair = labirinto.EstadoJogo();
		if (sair)
			return true;

		// O movimento do heroi
		labirinto.MovimentaHeroi(movimento);
		labirinto.AtualizarPecas();
		for (int i = 0; i < labirinto.GetDragoes().size(); i++)
		{
			if (labirinto.GetDragao(i).Ativo())
			{
				movimentoDragao = geradorAleatorio.nextInt(5) + 1;
				nmov = labirinto.MovimentaDragao(movimentoDragao, i);
				// para o Dragao mexer-se mais
				if (nmov == 1)
					labirinto.MovimentaDragao(movimentoDragao, i);
			}
		}
		labirinto.AtualizarPecas();
		sair = labirinto.EstadoJogo();

		if (sair)
			return true;
		return false;
	}

	/**
	 * Modo jogo onde os dragões se movimentam e adormecem aleatoriamente
	 * @param movimento número que representa a direção do movimento do herói
	 * @return true quando terminar o jogo.
	 */
	public boolean MJ_Movimento_e_Adormecer(int movimento)
	{
		Boolean sair = false;
		Random geradorAleatorio = new Random();

		int movimentoDragao;
		int nmov;

		// Verificar estado do jogo
		sair = labirinto.EstadoJogo();
		if (sair)
			return false;

		// O movimento do heroi
		labirinto.MovimentaHeroi(movimento);
		labirinto.AtualizarPecas();
		// Adormece ou nao o dragao
		if (tempoAdormecido > 0)
			tempoAdormecido--;

		if (tempoAdormecido == 0)
			tempoAdormecido = labirinto.AleAdormecer();

		for (int i = 0; i < labirinto.GetDragoes().size(); i++)
		{
			if (labirinto.GetDragao(i).Ativo()) // Se o dragao em questao esta
												// ativo:
			{
				movimentoDragao = geradorAleatorio.nextInt(5) + 1;
				nmov = labirinto.MovimentaDragao(movimentoDragao, i);
				// para o Dragao mexer-se mais
				if (nmov == 1)
					labirinto.MovimentaDragao(movimentoDragao, i);
			}
		}

		labirinto.AtualizarPecas();
		sair = labirinto.EstadoJogo();
		if (sair)
			return true;

		// ::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
		return false;
	}
	
	/**
	 * Desenha as imagens do tabuleiro no sitio correto
	 * @param g graphics
	 */
	public void draw_Labirinto(Graphics g)
	{
		labirinto.AtualizarPecas();
		for (int i = 0; i < tamanho; i++)
		{
			for (int j = 0; j < tamanho; j++)
			{
				//Montanha
				if(labirinto.GetBoardChar(i, j)=='X')
					g.drawImage(montanha, x+(j*50), y+(i*50), x+(j*50) + montanha.getWidth(), y+(i*50) + montanha.getHeight(), 0, 0, montanha.getWidth(), montanha.getHeight(), null);
				
				//Erva
				if(labirinto.GetBoardChar(i, j)==' ')
					g.drawImage(erva, x+(j*50), y+(i*50), x+(j*50) + erva.getWidth(), y+(i*50) + erva.getHeight(), 0, 0, erva.getWidth(), erva.getHeight(), null);
				
				//Heroi
				if (labirinto.GetBoardChar(i, j) == 'H')
				{
					coordX = x + (j * 50);
					coordY = y + (i * 50);
					g.drawImage(erva, x + (j * 50), y + (i * 50), x + (j * 50) + erva.getWidth(), y + (i * 50) + erva.getHeight(), 0, 0, erva.getWidth(), erva.getHeight(), null);
					g.drawImage(heroi_Desarmado, coordX, coordY, coordX + heroi_Desarmado.getWidth(), coordY + heroi_Desarmado.getHeight(), 0, 0, heroi_Desarmado.getWidth(), heroi_Desarmado.getHeight(), null);
				}
				if (labirinto.GetBoardChar(i, j) == 'A')
				{
					coordX = x + (j * 50);
					coordY = y + (i * 50);
					g.drawImage(erva, x + (j * 50), y + (i * 50), x + (j * 50) + erva.getWidth(), y + (i * 50) + erva.getHeight(), 0, 0, erva.getWidth(), erva.getHeight(), null);
					g.drawImage(heroi_Armado, coordX, coordY, coordX + heroi_Armado.getWidth(), coordY + heroi_Armado.getHeight(), 0, 0, heroi_Armado.getWidth(), heroi_Armado.getHeight(), null);
				}
				
				//Dragao
				if (labirinto.GetBoardChar(i, j) == 'D')
				{
					g.drawImage(erva, x + (j * 50), y + (i * 50), x + (j * 50) + erva.getWidth(), y + (i * 50) + erva.getHeight(), 0, 0, erva.getWidth(), erva.getHeight(), null);
					g.drawImage(dragao_Acordado, x + (j * 50), y + (i * 50), x + (j * 50) + dragao_Acordado.getWidth(), y + (i * 50) + dragao_Acordado.getHeight(), 0, 0, dragao_Acordado.getWidth(), dragao_Acordado.getHeight(), null);
				}
				if (labirinto.GetBoardChar(i, j) == 'd')
				{
					g.drawImage(erva, x + (j * 50), y + (i * 50), x + (j * 50) + erva.getWidth(), y + (i * 50) + erva.getHeight(), 0, 0, erva.getWidth(), erva.getHeight(), null);
					g.drawImage(dragao_Adormecido, x + (j * 50), y + (i * 50), x + (j * 50) + dragao_Adormecido.getWidth(), y + (i * 50) + dragao_Adormecido.getHeight(), 0, 0, dragao_Adormecido.getWidth(), dragao_Adormecido.getHeight(), null);
				}
				
				//Espada
				if (labirinto.GetBoardChar(i, j) == 'E')
				{
					g.drawImage(erva, x + (j * 50), y + (i * 50), x + (j * 50) + erva.getWidth(), y + (i * 50) + erva.getHeight(), 0, 0, erva.getWidth(), erva.getHeight(), null);
					g.drawImage(espada, x + (j * 50), y + (i * 50), x + (j * 50) + espada.getWidth(), y + (i * 50) + espada.getHeight(), 0, 0, espada.getWidth(), espada.getHeight(), null);
				}
				if (labirinto.GetBoardChar(i, j) == 'F')
				{
					g.drawImage(erva, x + (j * 50), y + (i * 50), x + (j * 50) + erva.getWidth(), y + (j * 50) + erva.getHeight(), 0, 0, erva.getWidth(), erva.getHeight(), null);
					g.drawImage(espada_Ind, x + (j * 50), y + (i * 50), x + (j * 50) + espada_Ind.getWidth(), y + (i * 50) + espada_Ind.getHeight(), 0, 0, espada_Ind.getWidth(), espada_Ind.getHeight(), null);
				}
				
				
				if(labirinto.GetBoardChar(i, j)=='S' && !labirinto.GetSaida())
					g.drawImage(montanha, x+(j*50), y+(i*50), x+(j*50) + montanha.getWidth(), y+(i*50) + montanha.getHeight(), 0, 0, montanha.getWidth(), montanha.getHeight(), null);

				if ((labirinto.GetBoardChar(i, j) == 'S' && labirinto.GetSaida()) || (editar && labirinto.GetBoardChar(i, j) == 'S'))
					g.drawImage(saida_Disponivel, x+(j*50), y+(i*50), x+(j*50) + saida_Disponivel.getWidth(), y+(i*50) + saida_Disponivel.getHeight(), 0, 0, saida_Disponivel.getWidth(), saida_Disponivel.getHeight(), null);
				
			}
		}
		
	}

	/**
	 * Desenha o em modo gráfico o modo de edição
	 * @param g graphics
	 */
	public void draw_EditarLabirinto(Graphics g)
	{
		// Montanha
		g.drawImage(montanha, x + 700, y + 50,  x + 700 + montanha.getWidth(),y + 50 + montanha.getHeight(), 0, 0, montanha.getWidth(), montanha.getHeight(), null);

		// Erva
		g.drawImage(erva, x + 700, y + 100+1, x + 700 + erva.getWidth(), y + 100+1 + erva.getHeight(), 0, 0,erva.getWidth(), erva.getHeight(), null);

		// Heroi
		g.drawImage(heroi_Desarmado,  x + 700, y+150+1,  x + 700 + heroi_Desarmado.getWidth(),y+150+1 + heroi_Desarmado.getHeight(), 0, 0, heroi_Desarmado.getWidth(), heroi_Desarmado.getHeight(),null);

		// Dragao
		g.drawImage(dragao_Acordado,  x + 700, y+200+1,  x + 700+ dragao_Acordado.getWidth(),y +200+1 + dragao_Acordado.getHeight(), 0, 0, dragao_Acordado.getWidth(),dragao_Acordado.getHeight(), null);

		// Espada
		g.drawImage(espada,  x + 700, y + 250+1,  x + 700+ espada.getWidth(),y + 250+1 + espada.getHeight(), 0, 0, espada.getWidth(), espada.getHeight(), null);
		
		//Informacoes
		g.drawImage(instr,  x + 35, y + 555,  x + 35+ instr.getWidth(),y + 555 + instr.getHeight(), 0, 0, instr.getWidth(), instr.getHeight(), null);
		g.drawImage(select,  x + 700, y + 20,  x + 700+ select.getWidth(),y + 20 + select.getHeight(), 0, 0, select.getWidth(), select.getHeight(), null);
		g.drawImage(aUsar,  x + 700, y + 465,  x + 700+ aUsar.getWidth(),y + 465 + aUsar.getHeight(), 0, 0, aUsar.getWidth(), aUsar.getHeight(), null);
		
		if(selecionado>0)
		{
			switch (selecionado)
			{
			case 1:
				g.drawImage(montanha, x + 700, y + 500,  x + 700 + montanha.getWidth(),y + 500 + montanha.getHeight(), 0, 0, montanha.getWidth(), montanha.getHeight(), null);
				break;
			case 2:
				g.drawImage(erva, x + 700, y + 500,  x + 700 + erva.getWidth(),y + 500 + erva.getHeight(), 0, 0, erva.getWidth(), erva.getHeight(), null);
				break;
			case 3:
				g.drawImage(heroi_Desarmado, x + 700, y + 500,  x + 700 + heroi_Desarmado.getWidth(),y + 500 + heroi_Desarmado.getHeight(), 0, 0, heroi_Desarmado.getWidth(), heroi_Desarmado.getHeight(), null);
				break;
			case 4:
				g.drawImage(dragao_Acordado, x + 700, y + 500,  x + 700 + dragao_Acordado.getWidth(),y + 500 + dragao_Acordado.getHeight(), 0, 0, dragao_Acordado.getWidth(), dragao_Acordado.getHeight(), null);
				break;
			case 5:
				g.drawImage(espada, x + 700, y + 500,  x + 700 + espada.getWidth(),y + 500 + espada.getHeight(), 0, 0, espada.getWidth(), espada.getHeight(), null);
				break;
			default:
				break;
			}
			
		}
		
		
	}

	/**
	 * Edita o labirinto numa posição específica com a peça selecionada.
	 * @param corx coordenada x do rato
	 * @param cory coordenada y do rato
	 */
	public void editarCampo(int corx, int cory)
	{
		if ((selecionado < 1) || (selecionado > 5))
			return;
		
		int cx = 0;
		int cy = 0;
		
		corx = corx - y; //retira o erro da janela para ficar com as coordenadas exatas
		//cory = cory - y;
		
		while (corx > 50)
		{
			corx = corx - 50;
			cx++;
		}
		while (cory > 50)
		{
			cory = cory - 50;
			cy++;
		}
		
		switch (selecionado)
		{
		case 1:
			labirinto.setCaracter(cx, cy, 'X');
			break;
		case 2:
			labirinto.setCaracter(cx, cy, ' ');
			break;
		case 3:
			labirinto.setCaracter(cx, cy, 'H');
			break;
		case 4:
			labirinto.setCaracter(cx, cy, 'D');
			break;
		case 5:
			labirinto.setCaracter(cx, cy, 'E');
			break;
		default:
			break;
		}
		
		
	}

	
	/**
	 * Contrutor do painel grafico para jogar em modo gráfico.
	 * Contém métodos relacionados com eventos do teclado e do rato, assim como o
	 * que cada ação representa.
	 * @param modojogo número de 1 a 3 que representa o modo de jogo.
	 * @param labir labirinto criado na janela principal com as especificações especificadas.
	 * @param tam tamanho do labirinto (comprimento de uma ponta à outra).
	 * @param frmLabirinto janela principal do programa.
	 */
	public PainelGrafico(int modojogo,  Labirinto labir, int tam, JFrame frmLabirinto)
	{
		frmPrincipal=frmLabirinto;
		labirinto=labir;
		modo=modojogo;
		tamanho=tam;
		
		//Carrega as imagens
		try {
			dragao_Acordado =  ImageIO.read(new File("DAcordado.png"));
			dragao_Adormecido =  ImageIO.read(new File("DAdormecido.png"));
			erva=  ImageIO.read(new File("Erva.png"));
			espada=  ImageIO.read(new File("Espada.png"));
			espada_Ind=  ImageIO.read(new File("EspadaInd.png"));
			heroi_Armado =  ImageIO.read(new File("HeroiA.png"));
			heroi_Desarmado =  ImageIO.read(new File("HeroiD.png"));
			montanha=  ImageIO.read(new File("Montanha.png"));
			saida_Disponivel=  ImageIO.read(new File("SaidaDisponivel.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Eventos do rato
		addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				rx = e.getX();
				ry = e.getY();
				if ((rx > -1) && (ry > -1))
				{
					if (rx < coordX + 50 && rx > coordX && ry < coordY) // CIMA
					{
						if (modo == 1)
							sair = MJ_Parado(1);
						else if (modo == 2)
							sair = MJ_Movimento(1);
						else if (modo == 3)
							sair = MJ_Movimento_e_Adormecer(1);
					}
					if (rx < coordX + 50 && rx > coordX && ry > coordY + 50) // Baixo
					{
						if (modo == 1)
							sair = MJ_Parado(2);
						else if (modo == 2)
							sair = MJ_Movimento(2);
						else if (modo == 3)
							sair = MJ_Movimento_e_Adormecer(2);
					}
					if (rx < coordX && ry > coordY && ry < coordY + 50) // Esquerda
					{
						if (modo == 1)
							sair = MJ_Parado(3);
						else if (modo == 2)
							sair = MJ_Movimento(3);
						else if (modo == 3)
							sair = MJ_Movimento_e_Adormecer(3);
					}
					if (rx > coordX + 50 && ry > coordY && ry < coordY + 50) // Direita
					{
						if (modo == 1)
							sair = MJ_Parado(4);
						else if (modo == 2)
							sair = MJ_Movimento(4);
						else if (modo == 3)
							sair = MJ_Movimento_e_Adormecer(4);
					}
					repaint();
					rx = -1;
					ry = -1;
					if (sair)
					{
						setVisible(false);
						frmPrincipal.setBounds(100, 100, 1024, 768);
					}
				}
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}	
		});
		
		//Evento do teclado
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				//INICIO SWITCH_____________________________________________________
				switch(e.getKeyCode())
				{
				case KeyEvent.VK_UP:
					if(modo==1)
						sair =MJ_Parado(1);
					else if(modo==2)
						sair = MJ_Movimento(1);
					else if(modo==3)
						sair = MJ_Movimento_e_Adormecer(1);
					break;
					
				case KeyEvent.VK_DOWN:
					if(modo==1)
						sair =MJ_Parado(2);
					else if(modo==2)
						sair = MJ_Movimento(2);
					else if(modo==3)
						sair = MJ_Movimento_e_Adormecer(2);
					break;

				case KeyEvent.VK_LEFT:
					if(modo==1)
						sair =MJ_Parado(3);
					else if(modo==2)
						sair = MJ_Movimento(3);
					else if(modo==3)
						sair = MJ_Movimento_e_Adormecer(3);
					break;

				case KeyEvent.VK_RIGHT:
					if(modo==1)
						sair =MJ_Parado(4);
					else if(modo==2)
						sair = MJ_Movimento(4);
					else if(modo==3)
						sair = MJ_Movimento_e_Adormecer(4);
					break;
					//Sair
				case KeyEvent.VK_ESCAPE:
					setVisible(false);
					frmPrincipal.setBounds(100, 100, 1024, 768);
					break;
				}
				//FIM SWITCH___________________________
				repaint();
				if (sair)
				{
					setVisible(false);
					frmPrincipal.setBounds(100, 100, 1024, 768);
				}

			}

			@Override
			public void keyReleased(KeyEvent e)
			{
			}			
		});
	}
	
	/**
	 * Contrutor do painel grafico para editar um labirinto
	 * Contém métodos relacionados com eventos do rato e dois eventos do teclado, assim como o
	 * que cada ação representa.
	 * OBS: Teclado é só usado para sair ou gravar e sair.
	 * @param frmLabirinto janela principal do programa.
	 */
	public PainelGrafico(JFrame frmLabirinto)
	{
		frmPrincipal=frmLabirinto;
		labirinto= new Labirinto(11,1);
		modo=1;
		tamanho=11;
		editar=true;
		selecionado=0;
		
		//Carregar as imagens
		try {
			dragao_Acordado =  ImageIO.read(new File("DAcordado.png"));
			erva=  ImageIO.read(new File("Erva.png"));
			espada=  ImageIO.read(new File("Espada.png"));
			heroi_Desarmado =  ImageIO.read(new File("HeroiD.png"));
			montanha=  ImageIO.read(new File("Montanha.png"));
			saida_Disponivel=  ImageIO.read(new File("SaidaDisponivel.png"));
			instr=  ImageIO.read(new File("Instrucoes.png"));
			select=  ImageIO.read(new File("Selecione.png"));
			aUsar=  ImageIO.read(new File("Usar.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
			}
			
			//Eventos do rato
			@Override
			public void mousePressed(MouseEvent e) {
				rx = e.getX();
				ry = e.getY();
				
				if ((rx > -1) && (ry > -1)) //Movimento do rato
				{
					if(rx>x+700 && rx < x+750 && ry > y+50 && ry < y+356) //Esta por cima dos icons das peças
					{
						if(ry>y+50 && ry <y+100)
							selecionado=1;
						if(ry>y+101 && ry <y+150)
							selecionado=2;
						if(ry>y+151 && ry <y+200)
							selecionado=3;
						if(ry>y+201 && ry <y+250)
							selecionado=4;
						if(ry>y+251 && ry <y+300)
							selecionado=5;
					}
					
					if((rx>50+x) && (rx < 500+x) && (ry > 50+y) && ry < 500+y) //Esta por cima do labirinto
						editarCampo(ry, rx);
					
					repaint();
					rx = -1;
					ry = -1;
				}
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}	
		});
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				//INICIO SWITCH_____________________________________________________
				switch (e.getKeyCode())
				{
				//Tecla s para gravar e sair
				case KeyEvent.VK_S:
					saveLabirinto();
					setVisible(false);
					frmPrincipal.setBounds(100, 100, 1024, 768);
					break;
				//Tecla ESC para sair
				case KeyEvent.VK_ESCAPE:
					setVisible(false);
					frmPrincipal.setBounds(100, 100, 1024, 768);
					break;
				}
				// FIM SWITCH___________________________
				
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
			}			
		});
	}
	
	/**
	 * Contrutor base, usado apenas para testar funcionalidades
	 */
	public PainelGrafico()
	{
		labirinto=new Labirinto();
		tamanho=10;
		
		try {
			dragao_Acordado =  ImageIO.read(new File("DAcordado.png"));
			dragao_Adormecido =  ImageIO.read(new File("DAdormecido.png"));
			erva=  ImageIO.read(new File("Erva.png"));
			espada=  ImageIO.read(new File("Espada.png"));
			espada_Ind=  ImageIO.read(new File("EspadaInd.png"));
			heroi_Armado =  ImageIO.read(new File("HeroiA.png"));
			heroi_Desarmado =  ImageIO.read(new File("HeroiD.png"));
			montanha=  ImageIO.read(new File("Montanha.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addMouseListener(new MouseListener(){
			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				coordX = e.getX();
				coordY = e.getY();
				repaint();

				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}	
		});
		
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println("x=" + x);
				switch(e.getKeyCode()){
				case KeyEvent.VK_LEFT: 
					coordX--; 
					break;
					
				case KeyEvent.VK_RIGHT: 
					coordX++; 
					//System.out.println("x=" + x);
					break;

				case KeyEvent.VK_UP: 
					coordY--;
					break;

				case KeyEvent.VK_DOWN: 
					coordY++; 
					break;
					
				case KeyEvent.VK_ESCAPE:
					setVisible(false);
					frmPrincipal.setBounds(100, 100, 1024, 768);
					break;
				}
				repaint();

				
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}			
		});
	}
	
	
	//Usada para desenhar as imagens
	/**
	 * Override de paintComponent para desenhar o estado do programa.
	 */
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw_Labirinto(g);

		if(editar)
		{
			draw_EditarLabirinto(g);
		}	
		
	}

}
