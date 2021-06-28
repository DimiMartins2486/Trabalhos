package br.com.cod3r.cm.Modelo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.cod3r.cm.Excessao.ExplosaoException;

// bão precisei importar a classe campo pois a classe campoteste esta no mesmo diretorio que a classe campo
public class CampoTeste {

	private Campo campo ;
	@BeforeEach  // Para cada um dos metodos execute essa função 
	void iniciarCampo () {
		campo = new Campo(3, 3);
	}
	@Test
	void testeVizinhoDireita() {
		Campo vizinho = new Campo(3, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoEsquerda() {
		Campo vizinho = new Campo(3, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoEmbaixo() {
		Campo vizinho = new Campo(2, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoEmCima() {
		Campo vizinho = new Campo(4, 3);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoDiagonal1 () {
		Campo vizinho = new Campo(4, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoDiagonal2 () {
		Campo vizinho = new Campo(4, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoDiagonal3() {
		Campo vizinho = new Campo(2, 2);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	@Test
	void testeVizinhoDiagonal4() {
		Campo vizinho = new Campo(2, 4);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertTrue(resultado);
	}
	// teste para não vizinho
	@Test
	void naoVizinho() {
		Campo vizinho = new Campo(5, 5);
		boolean resultado = campo.adicionarVizinho(vizinho);
		assertFalse(resultado);
	}
	@Test
	void testeValorPadraoAtributomarcado() {
		assertFalse(campo.isMarcado());
	}
	@Test
	void testeAlternarMarcacao() {
		campo.alternarMarcaçao(); // alternando a marcação significa que estarei marcando ele
		assertTrue(campo.isMarcado());
	}
	@Test
	void testeAlternarMarcacao2Chamadas() {
		campo.alternarMarcaçao(); 
		campo.alternarMarcaçao(); // chamando duas vezes siginifica que eu marquei e desmarquei o campo 
		assertFalse(campo.isMarcado()); // deve retornar falso
	}
	@Test
	void testeAbrirNaoMarcadoNaoMinado() {
		assertTrue(campo.abrir());
	}
	@Test
	void testeAbrirMarcadoNaoMinado() {
		campo.alternarMarcaçao();
		assertFalse(campo.abrir());
	}
	@Test
	void testeAbrirMarcadoMinado() {
		campo.minar();
		campo.alternarMarcaçao();
		assertFalse(campo.abrir());
	}
	@Test
	void testeAbrirMinado() {
		campo.minar();
		assertThrows(ExplosaoException.class, ()->{
			campo.abrir();
		});
		}
	@Test
	void testeAbrirComVizinhos1() {
		//campo (3,3)
		Campo campo11 = new Campo(1, 1);
		Campo campo22 = new Campo(2, 2);
		campo.adicionarVizinho(campo22);
		campo22.adicionarVizinho(campo11);
		campo.abrir();
		
		assertTrue(campo22.isAberto() && campo11.isAberto());
		}
	void testeAbrirComVizinhos2() {
		//campo (3,3)
		Campo campo22 = new Campo(2, 2);
		Campo campo11 = new Campo(1, 1);
		Campo campo12 = new Campo(1, 2);
		campo.adicionarVizinho(campo22);
		campo22.adicionarVizinho(campo11);
		campo.adicionarVizinho(campo12);
		campo12.minar();
		
		campo.abrir();
		boolean resultado = campo.isAberto()
				&&campo22.isAberto() 
				&&campo11.isAberto() 
				&&campo12.isFechado();
		assertTrue(resultado);
	}
	@Test
	void testeToStringMarcado() {
		campo.alternarMarcaçao();
		String resultado = campo.toString();
		assertEquals("x", resultado);
	}
	@Test
	void TesteToStringAbrirMinado () {
		campo.abrir();
		campo.minar();
		String resultado = campo.toString();
		assertEquals("*", resultado);
	}
	@Test
	void testeToStringAbrirEBombasNaVizinhança() {
		//Campo(3,3)
		Campo v22 = new Campo(2, 2);
		Campo v11 = new Campo(1, 1);
		Campo v12 = new Campo(1, 2);
		
		campo.adicionarVizinho(v22);
		v22.adicionarVizinho(v11);
		v11.adicionarVizinho(v12);
		v12.minar();
		campo.abrir();
		String resultado = v11.toString();
		assertEquals("1", resultado);
	}
	@Test
	void testeToStringCampoAberto() {
		campo.abrir();
		String resultado = campo.toString();
		assertEquals(" ", resultado);
	}
	@Test
	void testeToStringCampoNaoAberto() {
		String resultado = campo.toString();
		assertEquals("?", resultado);
	}
	@Test
	void testeReiniciarCampo() {
		campo.reiniciar();
		boolean resultado = campo.isFechado()&& campo.isNaoMarcado() && campo.isNaoMinado();
		assertTrue(resultado);
	}
	@Test
	void testeObjetivoAlcancadoProtegido() {
		campo.minar();
		campo.alternarMarcaçao();
		boolean resultado = campo.objetivoAlcançado();
		assertTrue(resultado);
	}
	@Test
	void testeObjetivoAlcancadoDesvendado() {
		campo.abrir();
		boolean resultado = campo.objetivoAlcançado();
		assertTrue(resultado);
	}
	@Test
	void testeGetLinha() {
		int num = campo.getLinha();
		assertEquals(3, num);
	}
	@Test
	void testeGetColuna() {
		int num = campo.getColuna();
		assertEquals(3, num);
	}
	
}
