/*
 * Autor: Gustavo Felipe Soares
 */

package objetos;

@SuppressWarnings("serial")
public class SalaCafe extends Sala {

	// construtores
	public SalaCafe() {
		super();
	}

	public SalaCafe(String nome) {
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

		SalaCafe sala = (SalaCafe) outro;
		return this.getNome().equals(sala.getNome());
	}

}
