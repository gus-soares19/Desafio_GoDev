/*
 * Autor: Gustavo Felipe Soares
 */

package testesUnitarios;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import objetos.Pessoa;
import objetos.Sala;
import objetos.SalaCafe;
import objetos.SalaEvento;

class TestesPessoa {

	@Test
	void testeAddMesmaSala() {
		Pessoa p1 = new Pessoa("Gustavo", "Soares");
		Sala salaCafe1 = new SalaCafe("sala1");
		Sala salaCafe2 = new SalaCafe("sala2");

		try {
			p1.addSala(salaCafe1);
			p1.addSala(salaCafe2);
			p1.addSala(salaCafe2);

			fail("Falha. Uma exceção deve ser lançada!");
		} catch (IllegalArgumentException iae) {
			assertEquals(String.format(iae.getMessage(), p1),
					"A sala já foi adicionada.");
		}

	}

	@Test
	void testeAddSalaNula() {
		Pessoa p1 = new Pessoa("Gustavo", "Soares");
		Sala salaEvento = new SalaEvento("sala1");
		Sala salaCafe = null;

		try {
			p1.addSala(salaEvento);
			p1.addSala(salaCafe);

			fail("Falha. Uma exceção deve ser lançada!");
		} catch (NullPointerException npe) {
			assertEquals(String.format(npe.getMessage(), p1),
					"A sala a ser adicionada não pode ser nula.");
		}

	}
	
	@Test
	void testeRemoveSalaNula() {
		Pessoa p1 = new Pessoa("Gustavo", "Soares");
		Sala salaEvento = new SalaEvento("sala1");

		try {
			p1.addSala(salaEvento);
			p1.removerSala(null);

			fail("Falha. Uma exceção deve ser lançada!");
		} catch (NullPointerException npe) {
			assertEquals(String.format(npe.getMessage(), p1),
					"A sala a ser removida não pode ser nula.");
		}

	}
	
	@Test
	void testeRemoveSalaNaoPresente() {
		Pessoa p1 = new Pessoa("Gustavo", "Soares");
		Sala salaEvento = new SalaEvento("sala1");
		Sala salaCafe = new SalaCafe("sala2");

		try {
			p1.addSala(salaEvento);
			p1.removerSala(salaCafe);

			fail("Falha. Uma exceção deve ser lançada!");
		} catch (IllegalArgumentException iae) {
			assertEquals(String.format(iae.getMessage(), p1),
					"A sala não foi encontrada.");
		}

	}
}
