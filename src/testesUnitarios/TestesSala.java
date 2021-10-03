/*
 * Autor: Gustavo Felipe Soares
 */

package testesUnitarios;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import objetos.Pessoa;
import objetos.Sala;
import objetos.SalaCafe;
import objetos.SalaEvento;

class TestesSala {

	@Test
	void testeAddMesmaPessoa() {
		Pessoa p1 = new Pessoa("Gustavo", "Soares");
		Pessoa p2 = new Pessoa("Jo�o", "Silva");
		Sala salaCafe = new SalaCafe("sala1");

		try {
			salaCafe.adicionarPessoa(p1);
			salaCafe.adicionarPessoa(p2);
			salaCafe.adicionarPessoa(p2);

			fail("Falha. Uma exce��o deve ser lan�ada!");
		} catch (IllegalArgumentException iae) {
			assertEquals(String.format(iae.getMessage(), salaCafe),
					"A pessoa 'Jo�o Silva' j� foi adicionada � sala 'sala1'.");
		}

	}

	@Test
	void testeAddPessoaNula() {
		Pessoa p1 = new Pessoa("Gustavo", "Soares");
		Pessoa p2 = null;
		Sala salaEvento = new SalaEvento("sala1");

		try {
			salaEvento.adicionarPessoa(p1);
			salaEvento.adicionarPessoa(p2);

			fail("Falha. Uma exce��o deve ser lan�ada!");
		} catch (NullPointerException npe) {
			assertEquals(String.format(npe.getMessage(), salaEvento),
					"A pessoa a ser adicionada � sala 'sala1' n�o pode ser nula.");
		}

	}
	
	@Test
	void testeRemovePessoaNula() {
		Pessoa p1 = new Pessoa("Gustavo", "Soares");
		Pessoa p2 = new Pessoa("Jo�o", "Silva");
		Sala salaEvento = new SalaEvento("sala1");

		try {
			salaEvento.adicionarPessoa(p1);
			salaEvento.adicionarPessoa(p2);
			salaEvento.removerPessoa(null);

			fail("Falha. Uma exce��o deve ser lan�ada!");
		} catch (NullPointerException npe) {
			assertEquals(String.format(npe.getMessage(), salaEvento),
					"A pessoa a ser removida da sala 'sala1' n�o pode ser nula.");
		}

	}
	
	@Test
	void testeRemovePessoaNaoPresente() {
		Pessoa p1 = new Pessoa("Gustavo", "Soares");
		Pessoa p2 = new Pessoa("Jo�o", "Silva");
		Sala salaCafe = new SalaCafe("sala1");

		try {
			salaCafe.adicionarPessoa(p1);
			salaCafe.removerPessoa(p2);

			fail("Falha. Uma exce��o deve ser lan�ada!");
		} catch (IllegalArgumentException iae) {
			assertEquals(String.format(iae.getMessage(), salaCafe),
					"A pessoa 'Jo�o Silva' n�o foi encontrada na sala 'sala1'.");
		}

	}

}
