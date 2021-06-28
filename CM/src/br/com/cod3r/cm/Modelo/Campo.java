package br.com.cod3r.cm.Modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.cod3r.cm.Excessao.ExplosaoException;

public class Campo {

	private final int linha;
	private final int coluna;
	
	private boolean aberto;
	private boolean minado;
	private boolean marcado;
	
	// autorelacionamento
	private List<Campo>vizinhos = new ArrayList<Campo>();
	
	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}
	
	boolean adicionarVizinho(Campo vizinho) {
		// se o campo estiver na mesma linha e na mesma coluna 
		// e a diferença for de 1 = é vizinho
		// se o campo estiver na diagonal e a diferença for de 2 
		// também é vizinho
		
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;
		// calcular a diferença entre linha e coluna
		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;
		
		if (deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		}else {
			return false;
		}
	}
	
	void alternarMarcaçao() {
		if (!aberto) {
			marcado = !marcado;
		}
	}
	
	boolean abrir() {
		if (!aberto && !marcado) {
			aberto = true;
			if (minado) {
				throw new ExplosaoException();
			}
			if (vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		}else {
			return false;
		}
	}
	
	boolean vizinhancaSegura() { // dar match, se nenhum vizinho estiver minado posso abrir os campos
		return vizinhos.stream().noneMatch(v -> v.minado);
	}
	
	void minar() {
		minado = true;
	}
	public boolean isMarcado() {
		return marcado;
	}
	public boolean isNaoMarcado() {
		return !isMarcado();
	}
	public boolean isAberto() {
		return aberto;
	}
	public boolean isFechado() {
		return !isAberto();
	}
	public boolean isMinado() {
		return minado;
	}
	public boolean isNaoMinado() {
		return !isMinado();
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}
	
	boolean objetivoAlcançado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}
	// quantas minas tenho na vizinhança
	long minasNaVizinhança() {// filtrar apenas os vizinhos que tem mina e depois uso o count para contar as quantidades de mina na vizinhança
		return vizinhos.stream().filter(v-> v.minado).count();
	}
	
	void reiniciar() {
		aberto = false;
		minado = false;
		marcado = false;
	}

	@Override
	public String toString() {
		if (marcado) {
			return "x";
		}else if (aberto && minado) {
			return "*";
		}else if (aberto&& minasNaVizinhança() > 0) {
			return Long.toString(minasNaVizinhança());
		}else if (aberto) {
			return " ";
		}else {
			return "?";
		}
	}
	
	// para mostrar no terminal precisaremos usar o toString
	
	
}
