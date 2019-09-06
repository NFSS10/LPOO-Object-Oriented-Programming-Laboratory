package maze.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.RepaintManager;

import maze.logic.*;

import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;
/**
 * Janela principal do onde faz o jogo correr em modo gráfico.
 * 
 * @author Nuno Silva e Luis Soares
 *
 */
public class LabirintoGUI {

	private JFrame frmLabirinto;
	private JTextField fldDimensao;
	private JTextField fldNumeroDragoes;
	private JButton btnDireita = new JButton("\u2192");
	private JButton btnCima = new JButton("\u2191");
	private JButton btnEsquerda=new JButton("\u2190");
	private JButton btnBaixo = new JButton("\u2193");
	private JComboBox comboBoxTipodeDragoes;
	private JComboBox ModosGraficos;
	private JButton btnGerarLabirinto;
	private JButton btnTerminarPrograma;
	private JTextArea textArea_caixa;
	
	
	private Labirinto labirinto;
	private boolean sair=false;
	private static int tempoAdormecido = 2; // Para manter minimamente 2 jogadas o dragao// adormecido quando o mesmo adormece
	private int modoJogo;
	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LabirintoGUI window = new LabirintoGUI();
					window.frmLabirinto.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LabirintoGUI() {
		initialize();
	}

	
	
	//Modos de Jogo
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
	public boolean MJ_Movimento(int movimento)
	{
		Boolean sair =false;
		Random geradorAleatorio = new Random();

		int movimentoDragao;
		int nmov;
			
			//Verificar estado do jogo
			sair = labirinto.EstadoJogo();
			if(sair)
				return true;
			
			//O movimento do heroi
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
	public boolean MJ_Movimento_e_Adormecer(int movimento)
	{
		Boolean sair =false;
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
			if (labirinto.GetDragao(i).Ativo()) //Se o dragao em questao esta ativo:
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
	 * Inicia o jopgo em modo gráfico, a utilizar imagens em vez de caracteres.
	 * @param mJogo número de 1 a 3 que corresponde ao modo de jogo.
	 * @param lab "labirinto" usado no jogo.
	 * @param tam tamanho do tabuleiro.
	 * @param modoEdit se true, entra em modo edição, se false entra em modo de jogar.
	 */
	public void M_Grafico(int mJogo, Labirinto lab, int tam, boolean modoEdit)
	{
		PainelGrafico painel;
		
		if (modoEdit)
			painel = new PainelGrafico(frmLabirinto); //Modo editar
		else
			painel = new PainelGrafico(mJogo, lab, tam, frmLabirinto); //Modo jogo com imagens
		
		
		painel.setBounds(0, 0, 1024, 768);
		//Aumentar a janela
		if(tam*50 > 770)
		{
			painel.setBounds(0, 0, 1800, 1000); // É grande para a maior parte dos ecras
			frmLabirinto.setBounds(100, 100, 1800, 1000);
		}
		frmLabirinto.getContentPane().add(painel);
		frmLabirinto.setVisible(true);
		//frmLabirinto.setFocusableWindowState(false);
		painel.setVisible(true);
		frmLabirinto.setComponentZOrder(painel, 0);
		painel.repaint();
		painel.requestFocus();

	}
	
	
	
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmLabirinto = new JFrame();
		frmLabirinto.setTitle("Labirinto");
		frmLabirinto.setBounds(100, 100, 1024, 768);
		frmLabirinto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLabirinto.getContentPane().setLayout(null);
		
				
		JLabel lblDimenso = new JLabel("Dimens\u00E3o");
		lblDimenso.setBounds(21, 11, 112, 14);
		frmLabirinto.getContentPane().add(lblDimenso);
		
		fldDimensao = new JTextField();
		fldDimensao.setText("11");
		fldDimensao.setBounds(143, 9, 46, 17);
		frmLabirinto.getContentPane().add(fldDimensao);
		fldDimensao.setColumns(10);
		
		JLabel lblDrages = new JLabel("Drag\u00F5es");
		lblDrages.setBounds(21, 36, 114, 14);
		frmLabirinto.getContentPane().add(lblDrages);
		
		fldNumeroDragoes = new JTextField();
		fldNumeroDragoes.setText("1");
		fldNumeroDragoes.setColumns(10);
		fldNumeroDragoes.setBounds(145, 34, 46, 17);
		frmLabirinto.getContentPane().add(fldNumeroDragoes);
		
		JLabel lblTipoDeDrages = new JLabel("Tipo de Drag\u00F5es");
		lblTipoDeDrages.setBounds(21, 61, 112, 14);
		frmLabirinto.getContentPane().add(lblTipoDeDrages);
		
		comboBoxTipodeDragoes = new JComboBox();
		comboBoxTipodeDragoes.setBounds(143, 62, 289, 20);
		frmLabirinto.getContentPane().add(comboBoxTipodeDragoes);
		comboBoxTipodeDragoes.addItem("Parados");
		comboBoxTipodeDragoes.addItem("Movimento");
		comboBoxTipodeDragoes.addItem("Movimento + Adormecer");
		
		JLabel ModoApresentacao = new JLabel("Modo");
		ModoApresentacao.setBounds(535, 61, 38, 14);
		frmLabirinto.getContentPane().add(ModoApresentacao);
		
		ModosGraficos = new JComboBox();
		ModosGraficos.setBounds(583, 58, 156, 20);
		frmLabirinto.getContentPane().add(ModosGraficos);
		ModosGraficos.addItem("Texto");
		ModosGraficos.addItem("Gráfico");
		
		
		
		
		btnTerminarPrograma = new JButton("Terminar programa");
		btnTerminarPrograma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnTerminarPrograma.setBounds(834, 76, 145, 29);
		frmLabirinto.getContentPane().add(btnTerminarPrograma);
		
		textArea_caixa = new JTextArea();
		textArea_caixa.setFont(new Font("Courier New", Font.PLAIN, 13));
		textArea_caixa.setEditable(false);
		textArea_caixa.setBounds(21, 93, 803, 592);
		frmLabirinto.getContentPane().add(textArea_caixa);
		
		JLabel lblEstadoAtual = new JLabel("Estado do Jogo");
		lblEstadoAtual.setBounds(21, 704, 411, 14);
		frmLabirinto.getContentPane().add(lblEstadoAtual);
		

		btnCima.setFont(new Font("Arial", Font.BOLD, 14));
		btnCima.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(modoJogo==1)
					sair =MJ_Parado(1);
				else if(modoJogo==2)
					sair = MJ_Movimento(1);
				else if(modoJogo==3)
					sair = MJ_Movimento_e_Adormecer(1);

				
				textArea_caixa.setText(labirinto.GetBoardString());
				lblEstadoAtual.setText(labirinto.GetStatus());
				if (sair)
				{
					btnBaixo.setEnabled(false);
					btnCima.setEnabled(false);
					btnEsquerda.setEnabled(false);
					btnDireita.setEnabled(false);
					textArea_caixa.setText("");
				}
			}
		});
		btnCima.setEnabled(false);
		btnCima.setBounds(880, 154, 57, 23);
		frmLabirinto.getContentPane().add(btnCima);
		
		btnEsquerda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				if(modoJogo==1)
					sair =MJ_Parado(3);
				else if(modoJogo==2)
					sair = MJ_Movimento(3);
				else if(modoJogo==3)
					sair = MJ_Movimento_e_Adormecer(3);
				textArea_caixa.setText(labirinto.GetBoardString());
				lblEstadoAtual.setText(labirinto.GetStatus());
				
				if (sair)
				{
					btnBaixo.setEnabled(false);
					btnCima.setEnabled(false);
					btnEsquerda.setEnabled(false);
					btnDireita.setEnabled(false);
					textArea_caixa.setText("");
				}
			}
		});
		btnEsquerda.setFont(new Font("Arial", Font.BOLD, 14));
		btnEsquerda.setEnabled(false);
		btnEsquerda.setBounds(834, 186, 57, 23);
		frmLabirinto.getContentPane().add(btnEsquerda);
		

		btnDireita.setFont(new Font("Arial", Font.BOLD, 14));
		btnDireita.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(modoJogo==1)
					sair =MJ_Parado(4);
				else if(modoJogo==2)
					sair = MJ_Movimento(4);
				else if(modoJogo==3)
					sair = MJ_Movimento_e_Adormecer(4);
				
				textArea_caixa.setText(labirinto.GetBoardString());
				lblEstadoAtual.setText(labirinto.GetStatus());
				if (sair)
				{
					btnBaixo.setEnabled(false);
					btnCima.setEnabled(false);
					btnEsquerda.setEnabled(false);
					btnDireita.setEnabled(false);
					textArea_caixa.setText("");
				}
				
			}
		});
		btnDireita.setEnabled(false);
		btnDireita.setBounds(922, 186, 57, 23);
		frmLabirinto.getContentPane().add(btnDireita);
		
		
		btnBaixo.setFont(new Font("Arial", Font.BOLD, 14));
		btnBaixo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				if(modoJogo==1)
					sair =MJ_Parado(2);
				else if(modoJogo==2)
					sair = MJ_Movimento(2);
				else if(modoJogo==3)
					sair = MJ_Movimento_e_Adormecer(2);
				
				textArea_caixa.setText(labirinto.GetBoardString());
				lblEstadoAtual.setText(labirinto.GetStatus());
				
				if (sair)
				{
					btnBaixo.setEnabled(false);
					btnCima.setEnabled(false);
					btnEsquerda.setEnabled(false);
					btnDireita.setEnabled(false);
					textArea_caixa.setText("");
				}
				
			}
		});
		btnBaixo.setEnabled(false);
		btnBaixo.setBounds(880, 220, 57, 23);
		frmLabirinto.getContentPane().add(btnBaixo);
		
		
		btnGerarLabirinto = new JButton("Gerar Labirinto");
		btnGerarLabirinto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String estado=new String();
				estado="Objetivo: Matar todos os Dragões.";
				
				//Verificações_________________________________________________
				//Se o tamanho do labirinto for <11, colocar a 11
				//Mínimo 11.
				if (Integer.parseInt(fldDimensao.getText()) < 11)
				{
					fldDimensao.setText("11");
					estado = "Dimensão minima: 11";
				}
				//Se o número de dragões for <0, colocar a 0
				if (Integer.parseInt(fldNumeroDragoes.getText()) < 0)
				{
					fldNumeroDragoes.setText("0");
					estado = "Numero de Dragões não pode ser < 0";
				}
				//Coloca a impar se ó tamanho for par
				if ((Integer.parseInt(fldDimensao.getText()) % 2) == 0)
				{
					fldDimensao.setText((Integer.parseInt(fldDimensao.getText()) + 1) + "");
					estado = "Dimensão tem de ser impar.";
				}
				//________________________________________________________________
				
				//Cria o labirinto com o tamanho e número de dragões especificados
				labirinto = new Labirinto(Integer.parseInt(fldDimensao.getText()),
						Integer.parseInt(fldNumeroDragoes.getText()));

				//Verifica o modo de jogo selecionado
				if (comboBoxTipodeDragoes.getSelectedItem().equals("Parados"))
					modoJogo = 1;
				else if (comboBoxTipodeDragoes.getSelectedItem().equals("Movimento"))
					modoJogo = 2;
				else if (comboBoxTipodeDragoes.getSelectedItem().equals("Movimento + Adormecer"))
					modoJogo = 3;

				labirinto.AtualizarPecas();

				
				
				//Entrar no modo grafico caso contrario, modo de texto
				if (ModosGraficos.getSelectedItem().equals("Gráfico"))
					M_Grafico(modoJogo, labirinto, Integer.parseInt(fldDimensao.getText()), false);
				else
				{
					//Ativa os butões
					btnBaixo.setEnabled(true);
					btnCima.setEnabled(true);
					btnEsquerda.setEnabled(true);
					btnDireita.setEnabled(true);

					
					textArea_caixa.setText(labirinto.GetBoardString());
					lblEstadoAtual.setText(estado);

				}
			}
		});
		btnGerarLabirinto.setBounds(834, 23, 145, 29);
		frmLabirinto.getContentPane().add(btnGerarLabirinto);
		//////////////////////////////////////////////////////////////////////
		
		JButton btnEditar = new JButton("Criar Labirinto");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0)
			{
				M_Grafico(modoJogo, labirinto, Integer.parseInt(fldDimensao.getText()), true);
			}
		});
		btnEditar.setBounds(834, 571, 145, 29);
		frmLabirinto.getContentPane().add(btnEditar);
		
		

		
		
		
		
	}
}
