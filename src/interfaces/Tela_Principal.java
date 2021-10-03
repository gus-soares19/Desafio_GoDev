/*
 * Autor: Gustavo Felipe Soares
 */

package interfaces;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import objetos.Pessoa;
import objetos.Sala;
import objetos.SalaCafe;
import objetos.SalaEvento;

import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.awt.event.ActionEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@SuppressWarnings("serial")
public class Tela_Principal extends JFrame {

	// atributos
	private JPanel contentPane;
	private JTextField tfNomePessoa;
	private JTextField tfSobrenomePessoa;
	private JTextField tfNomeSala;
	private JLabel lblNomeSala;
	private JTextField tfConsultaPessoa;
	private JTextField tfConsultaSala;
	private JComboBox<String> cbTiposSalaCadastro;
	private JComboBox<String> cbTiposSalaConsulta;
	private JComboBox<Sala> cbSalas;
	private JTextArea taResultado;
	private LinkedList<Pessoa> pessoas = new LinkedList<Pessoa>();
	private LinkedList<Sala> salas = new LinkedList<Sala>();
	private ArrayList<Pessoa> pessoasAdicionar = new ArrayList<Pessoa>();
	private ArrayList<Pessoa> pessoasRemover = new ArrayList<Pessoa>();
	private JTable tabelaPessoasForaSala;
	private JTable tabelaPessoasSala;
	private String nomeArq = "evento.txt";
	private JTextField tfStatusTreinamento;
	private boolean naoIniciado = true;
	private boolean intervalo = false;
	private int etapa = 0;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Tela_Principal frame = new Tela_Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Tela_Principal() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// serializa os dados salvos
				try {

					// (serializar)
					ObjectOutputStream escrever = new ObjectOutputStream(new FileOutputStream(nomeArq));
					escrever.writeObject(getPessoas());
					escrever.writeObject(getSalas());
					escrever.close();

				} catch (FileNotFoundException fnfe) {
					JOptionPane.showMessageDialog(contentPane, fnfe.getMessage());
				} catch (IOException ioe) {
					JOptionPane.showMessageDialog(contentPane, ioe.getMessage());
				}

				System.exit(0);
			}
		});

		// coleta os dados salvos no arquivo e move para as respectivas linkedlists
		Path arq = Paths.get(nomeArq);
		if (Files.exists(arq)) {
			try {
				// Leitura
				ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nomeArq));

				setPessoas((LinkedList<Pessoa>) entrada.readObject());
				setSalas((LinkedList<Sala>) entrada.readObject());

				entrada.close();

			} catch (ClassNotFoundException cnfe) {
				JOptionPane.showMessageDialog(contentPane, cnfe.getMessage());
			} catch (IOException ioe) {
				JOptionPane.showMessageDialog(contentPane, ioe.getMessage());
			}

			// caso não exista o arquivo, as linkedlists são criadas vazias
		} else {

			setPessoas(new LinkedList<Pessoa>());
			setSalas(new LinkedList<Sala>());

		}

		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 458, 594);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 10, 446, 551);
		contentPane.add(tabbedPane);

		JPanel jpPessoa = new JPanel();
		tabbedPane.addTab("Pessoa", null, jpPessoa, null);
		jpPessoa.setLayout(null);

		JLabel lblNomePessoa = new JLabel("Nome:");
		lblNomePessoa.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 16));
		lblNomePessoa.setBounds(10, 28, 100, 25);
		jpPessoa.add(lblNomePessoa);

		JLabel lblSobrenome = new JLabel("Sobrenome:");
		lblSobrenome.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 16));
		lblSobrenome.setBounds(10, 63, 100, 25);
		jpPessoa.add(lblSobrenome);

		tfNomePessoa = new JTextField();
		tfNomePessoa.setHorizontalAlignment(SwingConstants.CENTER);
		tfNomePessoa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfNomePessoa.setBounds(108, 28, 179, 24);
		jpPessoa.add(tfNomePessoa);
		tfNomePessoa.setColumns(10);

		tfSobrenomePessoa = new JTextField();
		tfSobrenomePessoa.setHorizontalAlignment(SwingConstants.CENTER);
		tfSobrenomePessoa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfSobrenomePessoa.setColumns(10);
		tfSobrenomePessoa.setBounds(108, 63, 179, 24);
		jpPessoa.add(tfSobrenomePessoa);

		JButton btnCadastrarPessoa = new JButton("cadastrar");
		btnCadastrarPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// irá cadastrar a pessoa
				try {

					String nome = tfNomePessoa.getText().trim();
					String sobrenome = tfSobrenomePessoa.getText().trim();
					Pessoa pessoa = new Pessoa();

					pessoa.setNome(nome);
					pessoa.setSobrenome(sobrenome);

					if (getPessoas().contains(pessoa)) {
						JOptionPane.showMessageDialog(contentPane, nome + " " + sobrenome + " já cadastrado.");
					} else {
						getPessoas().add(pessoa);

						JOptionPane.showMessageDialog(contentPane, nome + " " + sobrenome + " cadastrado com sucesso.");

						tfNomePessoa.setText("");
						tfSobrenomePessoa.setText("");
					}
				} catch (NullPointerException npe) {
					JOptionPane.showMessageDialog(contentPane, npe.getMessage());
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(contentPane, iae.getMessage());
				}

			}
		});
		btnCadastrarPessoa.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		btnCadastrarPessoa.setBounds(108, 110, 179, 24);
		btnCadastrarPessoa.setFocusable(false);
		jpPessoa.add(btnCadastrarPessoa);

		JPanel jpSala = new JPanel();
		tabbedPane.addTab("Sala", null, jpSala, null);
		jpSala.setLayout(null);

		lblNomeSala = new JLabel("Nome:");
		lblNomeSala.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 16));
		lblNomeSala.setBounds(10, 63, 100, 25);
		jpSala.add(lblNomeSala);

		tfNomeSala = new JTextField();
		tfNomeSala.setHorizontalAlignment(SwingConstants.CENTER);
		tfNomeSala.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfNomeSala.setColumns(10);
		tfNomeSala.setBounds(120, 63, 179, 24);
		jpSala.add(tfNomeSala);

		JButton btnCadastrarSala = new JButton("cadastrar");
		btnCadastrarSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// irá cadastrar a sala
				try {
					if (cbTiposSalaCadastro.getSelectedIndex() == 0) {
						JOptionPane.showMessageDialog(contentPane, "O tipo da sala não foi informado.");
					} else {
						String nome = tfNomeSala.getText().trim();
						Sala sala = null;

						switch (cbTiposSalaCadastro.getSelectedIndex()) {
						case 1: // Sala evento
							sala = new SalaEvento();
							break;

						case 2: // Sala café
							sala = new SalaCafe();
							break;
						}

						sala.setNome(nome);

						if (getSalas().contains(sala)) {
							JOptionPane.showMessageDialog(contentPane, "Sala '" + nome + "' já cadastrada.");
						} else {
							cbSalas.addItem(sala);
							getSalas().add(sala);

							JOptionPane.showMessageDialog(contentPane, "Sala '" + nome + "' cadastrada com sucesso.");
							tfNomeSala.setText("");
							cbTiposSalaCadastro.setSelectedIndex(0);
						}
					}
				} catch (NullPointerException npe) {
					JOptionPane.showMessageDialog(contentPane, npe.getMessage());
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(contentPane, iae.getMessage());
				}
			}
		});
		btnCadastrarSala.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		btnCadastrarSala.setBounds(120, 101, 179, 24);
		btnCadastrarSala.setFocusable(false);
		jpSala.add(btnCadastrarSala);

		JLabel lblTipoSala_1 = new JLabel("Tipo:");
		lblTipoSala_1.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 16));
		lblTipoSala_1.setBounds(10, 33, 80, 25);
		jpSala.add(lblTipoSala_1);

		cbTiposSalaCadastro = new JComboBox<String>();
		cbTiposSalaCadastro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfNomeSala.setText("");
			}
		});
		cbTiposSalaCadastro.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbTiposSalaCadastro
				.setModel(new DefaultComboBoxModel(new String[] { "", "Sala de evento", "Sala de caf\u00E9" }));
		cbTiposSalaCadastro.setFocusable(false);
		cbTiposSalaCadastro.setBounds(120, 32, 179, 21);
		cbTiposSalaCadastro.setFocusable(false);
		jpSala.add(cbTiposSalaCadastro);

		JPanel jpConsultas = new JPanel();
		tabbedPane.addTab("Consultas", null, jpConsultas, null);
		jpConsultas.setLayout(null);

		JLabel lblPessoa = new JLabel("Pessoa");
		lblPessoa.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblPessoa.setBounds(10, 10, 80, 22);
		jpConsultas.add(lblPessoa);

		JLabel lblNomeConsulta = new JLabel("Nome:");
		lblNomeConsulta.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 16));
		lblNomeConsulta.setBounds(10, 39, 80, 25);
		jpConsultas.add(lblNomeConsulta);

		tfConsultaPessoa = new JTextField();
		tfConsultaPessoa.setHorizontalAlignment(SwingConstants.CENTER);
		tfConsultaPessoa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfConsultaPessoa.setColumns(10);
		tfConsultaPessoa.setBounds(76, 39, 179, 24);
		jpConsultas.add(tfConsultaPessoa);

		JSeparator jSeparator1 = new JSeparator();
		jSeparator1.setBounds(0, 74, 441, 10);
		jpConsultas.add(jSeparator1);

		JButton btnConsultaPessoa = new JButton("consultar");
		btnConsultaPessoa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// irá consultar e retornar os dados que tiver sobre a pessoa passada como
				// parâmetro

				try {
					boolean encontrou = false;
					if (tfConsultaPessoa.getText().isBlank()) {
						JOptionPane.showMessageDialog(contentPane,
								"Informe o nome e o sobrenome da pessoa para pesquisar");
					} else {
						for (Pessoa pessoa : getPessoas()) {
							if (pessoa.getNomeCompleto().equals(tfConsultaPessoa.getText().trim())) {
								taResultado.setText(pessoa.toString());
								tfConsultaPessoa.setText("");
								encontrou = true;
								break;
							}
						}

						if (!encontrou) {
							taResultado.setText(
									"Nenhum registro encontrado para '" + tfConsultaPessoa.getText().trim() + "'.");
						}
					}
				} catch (NullPointerException npe) {
					JOptionPane.showMessageDialog(contentPane, npe.getMessage());
				}
			}
		});
		btnConsultaPessoa.setFocusable(false);
		btnConsultaPessoa.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		btnConsultaPessoa.setBounds(265, 39, 166, 24);
		jpConsultas.add(btnConsultaPessoa);

		JLabel lblSala = new JLabel("Sala");
		lblSala.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSala.setBounds(10, 84, 80, 22);
		jpConsultas.add(lblSala);

		JLabel lblNomeConsulta_1 = new JLabel("Nome:");
		lblNomeConsulta_1.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 16));
		lblNomeConsulta_1.setBounds(10, 144, 80, 25);
		jpConsultas.add(lblNomeConsulta_1);

		tfConsultaSala = new JTextField();
		tfConsultaSala.setHorizontalAlignment(SwingConstants.CENTER);
		tfConsultaSala.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfConsultaSala.setColumns(10);
		tfConsultaSala.setBounds(76, 144, 179, 24);
		jpConsultas.add(tfConsultaSala);

		JButton btnConsultaSala = new JButton("consultar");
		btnConsultaSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// irá consultar e retornar os dados que tiver sobre a sala passada como
				// parâmetro

				try {
					boolean encontrou = false;
					if (tfConsultaSala.getText().isBlank() || cbTiposSalaConsulta.getSelectedIndex() == 0) {
						JOptionPane.showMessageDialog(contentPane, "Informe o nome e o tipo da sala para pesquisar");
					} else {

						String classe = null;
						switch (cbTiposSalaConsulta.getSelectedIndex()) {
						case 1: // Sala evento
							classe = "SalaEvento";
							break;

						case 2: // Sala café
							classe = "SalaCafe";
							break;
						}

						for (Sala sala : getSalas()) {

							if (sala.getNome().equals(tfConsultaSala.getText().trim())
									&& sala.getClass().toString().contains(classe)) {
								taResultado.setText(sala.getInfos());
								tfConsultaSala.setText("");
								cbTiposSalaConsulta.setSelectedIndex(0);
								encontrou = true;
								break;
							}
						}

						if (!encontrou) {
							taResultado.setText(
									"Nenhum registro encontrado para '" + tfConsultaSala.getText().trim() + "'.");
						}
					}
				} catch (NullPointerException npe) {
					JOptionPane.showMessageDialog(contentPane, npe.getMessage());
				}
			}
		});
		btnConsultaSala.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		btnConsultaSala.setFocusable(false);
		btnConsultaSala.setBounds(265, 144, 166, 24);
		jpConsultas.add(btnConsultaSala);

		cbTiposSalaConsulta = new JComboBox<String>();
		cbTiposSalaConsulta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbTiposSalaConsulta
				.setModel(new DefaultComboBoxModel(new String[] { "", "Sala de evento", "Sala de caf\u00E9" }));
		cbTiposSalaConsulta.setBounds(76, 113, 179, 21);
		cbTiposSalaConsulta.setFocusable(false);
		jpConsultas.add(cbTiposSalaConsulta);

		JLabel lblTipoSala = new JLabel("Tipo:");
		lblTipoSala.setFont(new Font("Microsoft JhengHei UI", Font.PLAIN, 16));
		lblTipoSala.setBounds(10, 113, 80, 25);
		jpConsultas.add(lblTipoSala);

		JSeparator jSeparator1_1 = new JSeparator();
		jSeparator1_1.setBounds(0, 179, 441, 10);
		jpConsultas.add(jSeparator1_1);

		JLabel lblResultado = new JLabel("Resultado da pesquisa:");
		lblResultado.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblResultado.setBounds(10, 189, 245, 22);
		jpConsultas.add(lblResultado);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 221, 421, 87);
		jpConsultas.add(scrollPane);

		taResultado = new JTextArea();
		taResultado.setFocusable(false);
		taResultado.setEditable(false);
		scrollPane.setViewportView(taResultado);

		JPanel panel = new JPanel();
		tabbedPane.addTab("A\u00E7\u00F5es", null, panel, null);
		panel.setLayout(null);

		JLabel lblAdicionarPessoas = new JLabel("Adicionar pessoas \u00E0 sala");
		lblAdicionarPessoas.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblAdicionarPessoas.setBounds(10, 72, 254, 22);
		panel.add(lblAdicionarPessoas);

		JButton btnAdicionar = new JButton("adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// adiciona à sala as pessoas selecionadas

				try {
					if (cbSalas.getSelectedIndex() == 0) {
						JOptionPane.showMessageDialog(contentPane, "Informe a sala para adicionar as pessoas.");
					} else {
						Sala sala = (Sala) cbSalas.getSelectedItem();

						if (sala == null) {
							JOptionPane.showMessageDialog(contentPane, "Sala não encontrada.");
						} else {
							if (getPessoasAdicionar().isEmpty()) {
								JOptionPane.showMessageDialog(contentPane, "Selecione pessoas para adicionar.");
							} else {
								for (Pessoa pessoa : getPessoasAdicionar()) {
									sala.adicionarPessoa(pessoa);
									pessoa.addSala(sala);
								}

								JOptionPane.showMessageDialog(contentPane, "Pessoa(s) adicionada(s) com sucesso.");

								getPessoasAdicionar().clear();
								carregarTabelaForaSalaFiltrada(sala);
								carregarTabelaSalaFiltrada(sala);
							}
						}
					}
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(contentPane, iae.getMessage());
				} catch (NullPointerException npe) {
					JOptionPane.showMessageDialog(contentPane, npe.getMessage());
				}
			}
		});
		btnAdicionar.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		btnAdicionar.setBounds(120, 239, 179, 24);
		btnAdicionar.setFocusable(false);
		panel.add(btnAdicionar);

		cbSalas = new JComboBox<Sala>();
		cbSalas.setModel(new DefaultComboBoxModel(new String[] { "" }));
		cbSalas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// gerencia a aba de ações de acordo com a sala selecionada

				if (cbSalas.getSelectedIndex() == 0) {
					DefaultTableModel model = null;
					model = (DefaultTableModel) tabelaPessoasForaSala.getModel();
					model.setNumRows(0);
					model = (DefaultTableModel) tabelaPessoasSala.getModel();
					model.setNumRows(0);
				} else {
					carregarTabelaForaSalaFiltrada((Sala) cbSalas.getSelectedItem());
					carregarTabelaSalaFiltrada((Sala) cbSalas.getSelectedItem());
				}
			}
		});
		cbSalas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cbSalas.setFocusable(false);
		cbSalas.setBounds(64, 23, 179, 21);
		panel.add(cbSalas);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 111, 421, 118);
		panel.add(scrollPane_1);

		tabelaPessoasForaSala = new JTable();
		tabelaPessoasForaSala.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// seleciona a pessoa da tabela e sinaliza que que ela será adicionada à sala

				int linha = tabelaPessoasForaSala.getSelectedRow();
				String nome = tabelaPessoasForaSala.getValueAt(linha, 0).toString() + " "
						+ tabelaPessoasForaSala.getValueAt(linha, 1).toString();

				Pessoa pessoa = buscarPessoa(nome);

				if (pessoa == null) {
					JOptionPane.showMessageDialog(contentPane, "Pessoa não encontrada.");
				} else {
					if (getPessoasAdicionar().contains(pessoa)) {
						getPessoasAdicionar().remove(pessoa);
						tabelaPessoasForaSala.setSelectionBackground(Color.WHITE);
						tabelaPessoasForaSala.setSelectionForeground(Color.BLACK);
					} else {
						getPessoasAdicionar().add(pessoa);
						tabelaPessoasForaSala.setSelectionBackground(Color.GREEN);
						tabelaPessoasForaSala.setSelectionForeground(Color.WHITE);
					}
				}

			}
		});
		tabelaPessoasForaSala.setBackground(Color.WHITE);
		tabelaPessoasForaSala.setShowGrid(true);
		tabelaPessoasForaSala.setFont(new Font("Arial", Font.PLAIN, 16));
		tabelaPessoasForaSala.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Nome", "Sobrenome" }));
		scrollPane_1.setViewportView(tabelaPessoasForaSala);

		JLabel lblSala_1 = new JLabel("Sala:");
		lblSala_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblSala_1.setBounds(10, 21, 254, 22);
		panel.add(lblSala_1);

		JLabel lblRemoverPessoasDa = new JLabel("Remover pessoas da sala");
		lblRemoverPessoasDa.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblRemoverPessoasDa.setBounds(10, 290, 254, 22);
		panel.add(lblRemoverPessoasDa);

		JButton btnRemover = new JButton("remover");
		btnRemover.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// remove da sala as pessoas selecionadas

				try {
					if (cbSalas.getSelectedIndex() == 0) {
						JOptionPane.showMessageDialog(contentPane, "Informe a sala para adicionar as pessoas.");
					} else {
						Sala sala = (Sala) cbSalas.getSelectedItem();

						if (sala == null) {
							JOptionPane.showMessageDialog(contentPane, "Sala não encontrada.");
						} else {
							if (getPessoasRemover().isEmpty()) {
								JOptionPane.showMessageDialog(contentPane, "Selecione pessoas para remover.");
							} else {
								for (Pessoa pessoa : getPessoasRemover()) {
									sala.removerPessoa(pessoa);
									pessoa.removerSala(sala);
								}

								JOptionPane.showMessageDialog(contentPane, "Pessoa(s) removida(s) com sucesso.");

								getPessoasRemover().clear();
								carregarTabelaForaSalaFiltrada(sala);
								carregarTabelaSalaFiltrada(sala);
							}
						}
					}
				} catch (IllegalArgumentException iae) {
					JOptionPane.showMessageDialog(contentPane, iae.getMessage());
				} catch (NullPointerException npe) {
					JOptionPane.showMessageDialog(contentPane, npe.getMessage());
				}
			}
		});
		btnRemover.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		btnRemover.setBounds(120, 457, 179, 24);
		btnRemover.setFocusable(false);
		panel.add(btnRemover);

		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 329, 421, 118);
		panel.add(scrollPane_2);

		tabelaPessoasSala = new JTable();
		tabelaPessoasSala.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// seleciona a pessoa da tabela e sinaliza que que ela será removida da sala

				int linha = tabelaPessoasSala.getSelectedRow();
				String nome = tabelaPessoasSala.getValueAt(linha, 0).toString() + " "
						+ tabelaPessoasSala.getValueAt(linha, 1).toString();

				Pessoa pessoa = buscarPessoa(nome);

				if (pessoa == null) {
					JOptionPane.showMessageDialog(contentPane, "Pessoa não encontrada.");
				} else {
					if (getPessoasRemover().contains(pessoa)) {
						getPessoasRemover().remove(pessoa);
						tabelaPessoasSala.setSelectionBackground(Color.WHITE);
						tabelaPessoasSala.setSelectionForeground(Color.BLACK);
					} else {
						getPessoasRemover().add(pessoa);
						tabelaPessoasSala.setSelectionBackground(Color.RED);
						tabelaPessoasSala.setSelectionForeground(Color.WHITE);
					}
				}

			}
		});
		tabelaPessoasSala.setBackground(Color.WHITE);
		tabelaPessoasSala.setShowGrid(true);
		tabelaPessoasSala.setFont(new Font("Arial", Font.PLAIN, 16));
		tabelaPessoasSala.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Nome", "Sobrenome" }));
		scrollPane_2.setViewportView(tabelaPessoasSala);

		carergarCbSalas();

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Evento", null, panel_1, null);
		panel_1.setLayout(null);

		JButton btnIniciarEvento = new JButton("iniciar treinamento");
		btnIniciarEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// gerencia o treinamento

				if (isQtdPessoasMaior1()) {
					JOptionPane.showMessageDialog(contentPane,
							"O treinamento não pode ser iniciado, a diferença da quantidade de pessoas nas salas é maior que 1 (um).");
				} else {
					if (isNaoIniciado()) {
						setEtapa(1);
						setNaoIniciado(false);
					} else {
						if (getEtapa() == 1) {
							setEtapa(2);
							setIntervalo(false);
						} else {
							JOptionPane.showMessageDialog(contentPane, "O treinamento já está na etapa 2.");
						}
					}
				}
			}
		});
		btnIniciarEvento.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		btnIniciarEvento.setFocusable(false);
		btnIniciarEvento.setBounds(10, 100, 179, 24);
		panel_1.add(btnIniciarEvento);

		JLabel lblStatusDoTreinamento = new JLabel("Status do treinamento:");
		lblStatusDoTreinamento.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblStatusDoTreinamento.setBounds(10, 22, 254, 22);
		panel_1.add(lblStatusDoTreinamento);

		tfStatusTreinamento = new JTextField();
		tfStatusTreinamento.setEditable(false);
		tfStatusTreinamento.setText("n\u00E3o iniciado");
		tfStatusTreinamento.setHorizontalAlignment(SwingConstants.CENTER);
		tfStatusTreinamento.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfStatusTreinamento.setColumns(10);
		tfStatusTreinamento.setBounds(229, 22, 202, 24);
		panel_1.add(tfStatusTreinamento);

		JButton btnIntervalo = new JButton("intervalo");
		btnIntervalo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// gerencia o intervalo

				if (isNaoIniciado()) {
					JOptionPane.showMessageDialog(contentPane, "O treinamento ainda não foi iniciado.");
				} else {
					if (isIntervalo()) {
						JOptionPane.showMessageDialog(contentPane, "O treinamento já está no intervalo.");
					} else {
						setIntervalo(true);
						tfStatusTreinamento.setText("intervalo");
						ajustaQtdPessoas();
						JOptionPane.showMessageDialog(contentPane,
								"A troca de pessoas entre as salas foi realizada, verifique a lotação antes de continuar.");
					}

				}
			}

		});
		btnIntervalo.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
		btnIntervalo.setFocusable(false);
		btnIntervalo.setBounds(10, 134, 179, 24);
		panel_1.add(btnIntervalo);
	}

	// métodos

	// carrega a combobox com as salas cadastradas
	private void carergarCbSalas() {
		for (Sala sala : getSalas()) {
			cbSalas.addItem(sala);
		}
	}

	// exibe as pessoas que pertencem à sala selecionada
	private void carregarTabelaSalaFiltrada(Sala sala) {
		DefaultTableModel model = (DefaultTableModel) tabelaPessoasSala.getModel();
		model.setNumRows(0);

		if (!getPessoas().isEmpty()) {
			for (Pessoa pessoa : getPessoas()) {
				if (sala.getPessoas().contains(pessoa)) {
					model.addRow(new Object[] { pessoa.getNome(), pessoa.getSobrenome() });
				}
			}

		}
	}

	// exibe as pessoas que não pertencem à sala selecionada
	private void carregarTabelaForaSalaFiltrada(Sala sala) {
		DefaultTableModel model = (DefaultTableModel) tabelaPessoasForaSala.getModel();
		model.setNumRows(0);

		if (!getPessoas().isEmpty()) {
			for (Pessoa pessoa : getPessoas()) {
				if (!sala.getPessoas().contains(pessoa)) {
					model.addRow(new Object[] { pessoa.getNome(), pessoa.getSobrenome() });
				}
			}

		}
	}

	// seleciona aleatoriamente metade das pessoas cadastradas para trocarem de sala
	private LinkedList<Pessoa> selecionarPessoasTrocar() {
		Random rd = new Random();
		LinkedList<Pessoa> trocar = new LinkedList<Pessoa>();
		int metade = getPessoas().size() / 2;
		Pessoa pessoa = null;

		for (int i = 0; i <= metade; i++) {

			do {
				pessoa = getPessoas().get(rd.nextInt(getPessoas().size() - 1));
			} while (trocar.contains(pessoa));

			trocar.add(pessoa);
		}

		return trocar;
	}

	// troca as pessoas de uma sala para sala
	private void trocarPessoas(LinkedList<Pessoa> trocar) {
		Random rd = new Random();
		Pessoa pessoa = null;

		for (int i = 0; i < trocar.size(); i++) {
			pessoa = trocar.get(i);

			pessoa.trocarSala(pessoa.getSalasEvento().get(rd.nextInt(pessoa.getSalasEvento().size())),
					getSalaEventoDiferente(pessoa));

		}
	}

	// retorna uma sala de evento que a pessoa (parâmetro) não possua
	private Sala getSalaEventoDiferente(Pessoa pessoa) {

		for (SalaEvento sala : getSalasEvento()) {
			if (!pessoa.getSalas().contains(sala)) {
				return sala;
			}
		}
		return null;
	}

	// ajusta a quantidade de pessoas nas salas até que a diferença seja de no
	// máximo 1
	private void ajustaQtdPessoas() {
		do {
			trocarPessoas(selecionarPessoasTrocar());
		} while (isQtdPessoasMaior1());

	}

	// retorna as salas de evento cadastradas
	private LinkedList<SalaEvento> getSalasEvento() {
		LinkedList<SalaEvento> salasEvento = new LinkedList<SalaEvento>();
		for (Sala sala : getSalas()) {
			if (sala.getClass().toString().contains("SalaEvento")) {
				salasEvento.add((SalaEvento) sala);
			}
		}
		return salasEvento;
	}

	// retorna a pessoa que tiver o nome igual ao passado como parâmetro
	private Pessoa buscarPessoa(String nomeCompleto) {

		for (Pessoa pessoa : getPessoas()) {
			if (pessoa.getNomeCompleto().equals(nomeCompleto)) {
				return pessoa;
			}
		}

		return null;
	}

	// verifica se a diferença de pessoas entre as salas com maior e menor lotação é
	// maior que um
	private boolean isQtdPessoasMaior1() {
		if (getQtdSalaMaisPessoas() - getQtdSalaMenosPessoas() > 1) {
			return true;
		}
		return false;
	}

	// retorna a quantidade de pessoas da sala com maior lotação
	private int getQtdSalaMaisPessoas() {

		int maiorQtd = Integer.MIN_VALUE;

		for (Sala sala : getSalas()) {
			if (sala.getClass().toString().contains("SalaEvento")) {
				if (sala.getPessoas().size() > maiorQtd) {
					maiorQtd = sala.getPessoas().size();

				}

			}
		}

		return maiorQtd;
	}

	// retorna a quantidade de pessoas da sala com menor lotação
	private int getQtdSalaMenosPessoas() {

		int menorQtd = Integer.MAX_VALUE;

		for (Sala sala : getSalas()) {
			if (sala.getClass().toString().contains("SalaEvento")) {
				if (sala.getPessoas().size() < menorQtd) {
					menorQtd = sala.getPessoas().size();
				}

			}
		}

		return menorQtd;
	}

	// getters & setters
	public LinkedList<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(LinkedList<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public LinkedList<Sala> getSalas() {
		return salas;
	}

	public void setSalas(LinkedList<Sala> salas) {
		this.salas = salas;
	}

	public ArrayList<Pessoa> getPessoasAdicionar() {
		return pessoasAdicionar;
	}

	public void setPessoasAdicionar(ArrayList<Pessoa> pessoasCadastrar) {
		this.pessoasAdicionar = pessoasCadastrar;
	}

	public ArrayList<Pessoa> getPessoasRemover() {
		return pessoasRemover;
	}

	public void setPessoasRemover(ArrayList<Pessoa> pessoasRemover) {
		this.pessoasRemover = pessoasRemover;
	}

	public boolean isNaoIniciado() {
		return naoIniciado;
	}

	public void setNaoIniciado(boolean naoIniciado) {
		this.naoIniciado = naoIniciado;
	}

	public int getEtapa() {
		return etapa;
	}

	public void setEtapa(int etapa) {
		this.etapa = etapa;
		tfStatusTreinamento.setText("Etapa do treinamento: " + getEtapa());
	}

	public boolean isIntervalo() {
		return intervalo;
	}

	public void setIntervalo(boolean intervalo) {
		this.intervalo = intervalo;
	}
}
