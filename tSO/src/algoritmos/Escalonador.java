package algoritmos;

import estruturas.Lista;
import ferramentas.Calculos;
import processos.Processo;

public class Escalonador{
	
	private Lista processosFCFS;
	private Lista processosSJF;
	private Lista processosRR;
	
	private int quantum;
	
	public Escalonador(String arquivo, int quantum){
		
		this.quantum = quantum;
		
		processosFCFS = new Lista(arquivo);
		processosSJF = new Lista(arquivo);
		processosRR = new Lista(arquivo);
		
		fcfs();
		sjf();
		rr();
		//rr2();
		
	}
	
	private void fcfs(){

		int tam = processosFCFS.nElementos;
		Processo processo;
		
		int tempo = 0, inicio, fim;
		float mTRet = 0, mTRes = 0, mTEsp = 0;
		
		while(processosFCFS.primeiro != null){
			
			processo = processosFCFS.primeiro;
			
			inicio = tempo;
			tempo += processo.duracao;
			fim = tempo;
			
			//System.out.print(fim + " ");
			
			mTRet += (fim - processo.chegada);
			
			mTRes += (inicio - processo.chegada);
			
			processosFCFS.removerDoInicio();
			
		}
	
		mTRet /= tam;
		mTRet = Calculos.round(mTRet, (byte) 1);
		mTRes /= tam;
		mTRes = Calculos.round(mTRes, (byte) 1);
		mTEsp = mTRes;
		
		int expoenteMTRet = (int) mTRet;
		byte mantissaMTRet = (byte) ((mTRet - (float) expoenteMTRet) * 10);
		
		int expoenteMTRes = (int) mTRes;
		byte mantissaMTRes = (byte) ((mTRes - (float) expoenteMTRes) * 10);
		
		int expoenteMTEsp = (int) mTEsp;
		byte mantissaMTEsp= (byte) ((mTEsp - (float) expoenteMTEsp) * 10);
		
		System.out.println("FCFS " + expoenteMTRet + "," + mantissaMTRet + " " +
									expoenteMTRes + "," + mantissaMTRes + " " +
									expoenteMTEsp + "," + mantissaMTEsp);
	
	}

	private void sjf(){
		
		int tam = processosSJF.nElementos;
		Processo processo, aux;
		//aux = processo = processosSJF.primeiro;
		
		int tempo = 0, inicio, fim;
		float mTRet = 0, mTRes = 0, mTEsp = 0;
		
		while(processosSJF.primeiro != null){
			
			aux = processo = processosSJF.primeiro;

			while((aux = aux.proximo) != null && aux.chegada <= tempo)				
				if(aux.duracao < processo.duracao)
					processo = aux;
			
			inicio = tempo;
			tempo += processo.duracao;
			fim = tempo;
			
			//System.out.print(fim + " ");
			
			mTRet += (fim - processo.chegada);
			
			mTRes += (inicio - processo.chegada);
			
			processosSJF.remover(processo);
			
		}
		
		mTRet /= tam;
		mTRet = Calculos.round(mTRet, (byte) 1);
		mTRes /= tam;
		mTRes = Calculos.round(mTRes, (byte) 1);
		mTEsp = mTRes;
		
		int expoenteMTRet = (int) mTRet;
		byte mantissaMTRet = (byte) ((mTRet - (float) expoenteMTRet) * 10);
		
		int expoenteMTRes = (int) mTRes;
		byte mantissaMTRes = (byte) ((mTRes - (float) expoenteMTRes) * 10);
		
		int expoenteMTEsp = (int) mTEsp;
		byte mantissaMTEsp= (byte) ((mTEsp - (float) expoenteMTEsp) * 10);
		
		System.out.println("SJF " + expoenteMTRet + "," + mantissaMTRet + " " +
									expoenteMTRes + "," + mantissaMTRes + " " +
									expoenteMTEsp + "," + mantissaMTEsp);
		
	}
	
	private int executar(Processo processo, int inicio){
		
		int fim = inicio + quantum;
		
		processo.tempoExecutado += quantum;
		if(processo.tempoExecutado > processo.duracao){
			
			fim -= (processo.tempoExecutado - processo.duracao);
			
			processo.tempoExecutado = processo.duracao;
		
		}
		
		return (fim - inicio);
		
	}
	
	private void rr(){
		
		int tam = processosRR.nElementos;
		Processo processo;
		
		int tempo = 0, inicio, fim;
		float mTRet = 0, mTRes = 0, mTEsp = 0;
		
		while(tempo < processosRR.tempoTotal){
			
			processo = processosRR.primeiro;
			
			inicio = tempo;
			tempo += executar(processo, inicio);
			fim = tempo;
			
			//System.out.print("P" + processo.id + ":" + fim + " ");
			
			if(processo.primeiraExecucao){
				
				processo.primeiraExecucao = false;
				mTRes += (inicio - processo.chegada);
				mTEsp += (inicio - processo.chegada);				
				
			} else
				mTEsp += (inicio - processo.chegadaAux);

			processo.chegadaAux = fim;

			if(processo.tempoExecutado >= processo.duracao){
				mTRet += (fim - processo.chegada);
				processosRR.removerDoInicio();
							
			} else{
				processosRR.removerDoInicio();
				processosRR.inserirEmOrdem2(processo);
			}
						
		}
	
		mTRet /= tam;
		mTRet = Calculos.round(mTRet, (byte) 1);
		mTRes /= tam;
		mTRes = Calculos.round(mTRes, (byte) 1);
		mTEsp /= tam;
		mTEsp = Calculos.round(mTEsp, (byte) 1);
		
		int expoenteMTRet = (int) mTRet;
		byte mantissaMTRet = (byte) ((mTRet - (float) expoenteMTRet) * 10);
		
		int expoenteMTRes = (int) mTRes;
		byte mantissaMTRes = (byte) ((mTRes - (float) expoenteMTRes) * 10);
		
		int expoenteMTEsp = (int) mTEsp;
		byte mantissaMTEsp= (byte) ((mTEsp - (float) expoenteMTEsp) * 10);
		
		System.out.println("RR " + expoenteMTRet + "," + mantissaMTRet + " " +
									expoenteMTRes + "," + mantissaMTRes + " " +
									expoenteMTEsp + "," + mantissaMTEsp);
		
	}
	
	private void rr2(){
		
		int tam = processosRR.nElementos;
		Processo processo, aux;
		
		int tempo = 0, inicio, fim;
		float mTRet = 0, mTRes = 0, mTEsp = 0;
		
		while(tempo < processosRR.tempoTotal){
			
			aux = processo = processosRR.primeiro;

			while(aux != null && aux.chegada <= tempo){				
				if(aux.primeiraExecucao){
					processo = aux;
					break;
				}
				
				aux = aux.proximo;
			}	
			
			inicio = tempo;
			tempo += executar(processo, inicio);
			fim = tempo;
			
			//System.out.print("P" + processo.id + ":" + fim + " ");
			
			if(processo.primeiraExecucao){
				
				processo.primeiraExecucao = false;
				mTRes += (inicio - processo.chegada);
				mTEsp += (inicio - processo.chegada);				
				
			} else
				mTEsp += (inicio - processo.chegadaAux);

			processo.chegadaAux = fim;

			if(processo.tempoExecutado >= processo.duracao){
				mTRet += (fim - processo.chegada);
				processosRR.remover(processo);
							
			} else{
				processosRR.remover(processo);
				processosRR.inserirEmOrdem2(processo);
			}
						
		}
	
		mTRet /= tam;
		mTRet = Calculos.round(mTRet, (byte) 1);
		mTRes /= tam;
		mTRes = Calculos.round(mTRes, (byte) 1);
		mTEsp /= tam;
		mTEsp = Calculos.round(mTEsp, (byte) 1);
		
		int expoenteMTRet = (int) mTRet;
		byte mantissaMTRet = (byte) ((mTRet - (float) expoenteMTRet) * 10);
		
		int expoenteMTRes = (int) mTRes;
		byte mantissaMTRes = (byte) ((mTRes - (float) expoenteMTRes) * 10);
		
		int expoenteMTEsp = (int) mTEsp;
		byte mantissaMTEsp= (byte) ((mTEsp - (float) expoenteMTEsp) * 10);
		
		System.out.println("RR " + expoenteMTRet + "," + mantissaMTRet + " " +
									expoenteMTRes + "," + mantissaMTRes + " " +
									expoenteMTEsp + "," + mantissaMTEsp);
		
	}
	
}
