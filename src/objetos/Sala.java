/*
 * Autor: Gustavo Felipe Soares
 */

package objetos;

import java.io.Serializable;
import java.util.LinkedList;

@SuppressWarnings("serial")
public abstract class Sala implements Serializable {

	// atributos
	private String nome;
	private LinkedList<Pessoa> pessoas = new LinkedList<Pessoa>();

	// construtores
	public Sala() {
		this.setPessoas(new LinkedList<Pessoa>());
	}

	public Sala(String nome) {
		this.setNome(nome);
		this.setPessoas(new LinkedList<Pessoa>());
	}

	// m�todos

	// retorna o nome da sala
	@Override
	public String toString() {
		return this.getNome();
	}

	// adiciona uma pessoa � sala
	public void adicionarPessoa(Pessoa pessoa) {
		if (pessoa == null) {
			throw new NullPointerException(
					"A pessoa a ser adicionada � sala '" + this.getNome() + "' n�o pode ser nula.");
		}

		if (this.getPessoas().contains(pessoa)) {
			throw new IllegalArgumentException(
					"A pessoa '" + pessoa.getNomeCompleto() + "' j� foi adicionada � sala '" + this.getNome() + "'.");
		}

		this.getPessoas().add(pessoa);
	}

	// remove uma pessoa da sala
	public void removerPessoa(Pessoa pessoa) {
		if (pessoa == null) {
			throw new NullPointerException(
					"A pessoa a ser removida da sala '" + this.getNome() + "' n�o pode ser nula.");
		}

		if (!this.getPessoas().contains(pessoa)) {
			throw new IllegalArgumentException(
					"A pessoa '" + pessoa.getNomeCompleto() + "' n�o foi encontrada na sala '" + this.getNome() + "'.");
		}

		this.getPessoas().remove(pessoa);
	}

	// remove uma pessoa da sala pelo �ndice
	public Pessoa removerPessoa(int index) {

		if (index < 0 || index > getPessoas().size()) {
			throw new IllegalArgumentException(
					"O �ndice precisa ser maior que 0 e menor que " + getPessoas().size() + ".");
		}

		return this.getPessoas().remove(index);
	}

	// retorna todas as informa��es da sala
	public String getInfos() {
		int cont = 1;
		String retorno = "nome da sala: " + this.getNome() + "\nQuantidade de pessoas na sala: " + getPessoas().size();

		if (!getPessoas().isEmpty()) {
			retorno += "\nPessoas na sala: ";
			for (Pessoa pessoa : getPessoas()) {
				if (cont < getPessoas().size()) {
					retorno += pessoa.getNome() + " " + pessoa.getSobrenome() + ", ";
				} else {
					retorno += pessoa.getNome() + " " + pessoa.getSobrenome() + ".";
				}
				cont++;
			}
		}

		return retorno;

	}

	// getters & setters
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		if (nome == null) {
			throw new NullPointerException("O nome n�o pode ser nulo.");
		}
		if (nome.isBlank()) {
			throw new IllegalArgumentException("O nome n�o foi preenchido corretamente.");
		}
		this.nome = nome;
	}

	public LinkedList<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(LinkedList<Pessoa> pessoas) {
		if (pessoas == null) {
			throw new NullPointerException("O nome n�o pode ser nulo.");
		}
		this.pessoas = pessoas;
	}
}
