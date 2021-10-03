/*
 * Autor: Gustavo Felipe Soares
 */

package objetos;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Pessoa implements Serializable {

	// atributos
	private String nome;
	private String sobrenome;
	private ArrayList<Sala> salas;

	// construtores
	public Pessoa() {
		this.setSalas(new ArrayList<Sala>());
	}

	public Pessoa(String nome, String sobrenome) {
		this.setNome(nome);
		this.setSobrenome(sobrenome);
		this.setSalas(new ArrayList<Sala>());
	}

	// métodos

	// retorna todas as informações sobre a pessoa
	@Override
	public String toString() {
		int cont = 1;
		String retorno = "nome completo: " + this.getNome() + " " + this.getSobrenome();

		if (!this.getSalas().isEmpty()) {
			retorno += "\n Salas em que ficará: ";

			for (Sala sala : this.getSalas()) {
				retorno += "\n" + cont + "- " + sala.getNome();
				cont++;
			}
		}
		return retorno;

	}

	// adiciona a pessoa à uma sala
	public void addSala(Sala sala) {
		if (sala == null) {
			throw new NullPointerException("A sala a ser adicionada não pode ser nula.");
		}

		if (this.getSalas().contains(sala)) {
			throw new IllegalArgumentException("A sala já foi adicionada.");
		}

		this.getSalas().add(sala);
	}

	// remove a pessoa de uma sala
	public void removerSala(Sala sala) {
		if (sala == null) {
			throw new NullPointerException("A sala a ser removida não pode ser nula.");
		}

		if (!this.getSalas().contains(sala)) {
			throw new IllegalArgumentException("A sala não foi encontrada.");
		}

		this.getSalas().remove(sala);
	}

	// troca a pessoa de uma sala para a outra
	public void trocarSala(Sala salaAntiga, Sala salaNova) {

		if (salaAntiga == null || salaNova == null) {
			throw new NullPointerException("As salas não podem ser nulas.");
		}

		if (!salaAntiga.getPessoas().contains(this)) {
			throw new IllegalArgumentException("A pessoa '" + this.getNome() + " " + this.getSobrenome()
					+ "' não está presente na sala '" + salaAntiga.getNome() + "'.");
		}

		if (salaNova.getPessoas().contains(this)) {
			throw new IllegalArgumentException("A pessoa '" + this.getNome() + " " + this.getSobrenome()
					+ "' já está presente na sala '" + salaAntiga.getNome() + "'.");
		}

		this.removerSala(salaAntiga);
		salaAntiga.removerPessoa(this);
		this.addSala(salaNova);
		salaNova.adicionarPessoa(this);
	}

	// verifica se o objeto passado é igual à pessoa
	@Override
	public boolean equals(Object outro) {
		if (this == outro)
			return true;
		if (outro == null)
			return false;
		if (getClass() != outro.getClass())
			return false;

		Pessoa pessoa = (Pessoa) outro;
		return this.getNome().equals(pessoa.getNome());
	}

	// retorna o nome completo
	public String getNomeCompleto() {
		return this.getNome() + " " + this.getSobrenome();
	}

	// getters & setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (nome == null) {
			throw new NullPointerException("O nome não pode ser nulo.");
		}
		if (nome.isBlank()) {
			throw new IllegalArgumentException("O nome não foi preenchido corretamente.");
		}
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		if (sobrenome == null) {
			throw new NullPointerException("O sobrenome não pode ser nulo.");
		}
		if (sobrenome.isBlank()) {
			throw new IllegalArgumentException("O sobrenome não foi preenchido corretamente.");
		}
		this.sobrenome = sobrenome;
	}

	public ArrayList<Sala> getSalas() {
		return salas;
	}

	public ArrayList<SalaEvento> getSalasEvento() {
		ArrayList<SalaEvento> salasEvento = new ArrayList<SalaEvento>();
		for (Sala sala : this.getSalas()) {
			if (sala.getClass().toString().contains("SalaEvento")) {
				salasEvento.add((SalaEvento) sala);
			}
		}
		return salasEvento;
	}

	public void setSalas(ArrayList<Sala> salas) {
		if (salas == null) {
			throw new NullPointerException("As salas não podem ser nulas.");
		}
		this.salas = salas;
	}

}
