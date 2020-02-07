package processos;

public class Processo{
	
	public int id;
	public int chegada, chegadaAux, duracao;
	public int tempoExecutado;
	public boolean primeiraExecucao;
	
	public Processo anterior;
	public Processo proximo;
	
	public Processo(int id, int chegada, int duracao){
		
		this.id = id;
		chegadaAux = this.chegada = chegada;
		this.duracao = duracao;
		tempoExecutado = 0;
		primeiraExecucao = true;
		
		anterior = proximo = null;
		
	}
	
}
