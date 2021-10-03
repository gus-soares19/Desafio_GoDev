/*
 * Autor: Gustavo Felipe Soares
 */

package objetos;

@SuppressWarnings("serial")
public class SalaEvento extends Sala {

	// construtores
	public SalaEvento() {
		super();
	}

	public SalaEvento(String nome) {
		super(nome);
	}

	// m�todos

	// verifica se o objeto passado � igual � sala
	@Override
	public boolean equals(Object outro) {
		if (this == outro)
			return true;
		if (outro == null)
			return false;
		if (getClass() != outro.getClass())
			return false;

		SalaEvento sala = (SalaEvento) outro;
		return this.getNome().equals(sala.getNome());
	}

}
