package estruturas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import processos.Processo;

public class Fila{
	
	private static final String DIRETORIO = "recursos/";
	
	public ArrayList<Processo> processos;
	private int tamanho;
	public int nElementos;
	private int comprimento;
	
	public Fila(String arquivo){
		
		processos = new ArrayList<Processo>();
		carregarArquivoParaFila(arquivo);
		buildMinHeap();
		
	}
	
	private void carregarArquivoParaFila(String nomeArquivo){

	    FileReader arquivo = null;
        File txtFile = new File(DIRETORIO + nomeArquivo);
        
        try{
        	
            arquivo = new FileReader(txtFile);
        
        } catch(FileNotFoundException e){
            System.err.println("Erro! Não foi possível encontrar o arquivo a ser carregado.");
        }

        BufferedReader reader = new BufferedReader(arquivo);
        
        int count = 0;
        String linha;
        
        try{        	
       	
        	while(true){
        		
       			linha = reader.readLine();
       			
       			if(linha == null)
       				break;
       			
       			String[] linhaAtual = linha.split(" ");
        		
       			if(linhaAtual.length == 2)
       				processos.add(new Processo(++count, (int) Integer.valueOf(linhaAtual[0]), (int) Integer.valueOf(linhaAtual[1])));
    
        	}
        	
       		arquivo.close();
                
        } catch(IOException e){
            System.err.println("Erro ao tentar ler o arquivo");
        }
        
        nElementos = tamanho = comprimento = processos.size();
		
	}
	
	private void trocarValores(int posOrigem, int posDestino){
		
		Processo aux = processos.get(posOrigem);
		
		processos.set(posOrigem, processos.get(posDestino));
	
		processos.set(posDestino, aux);
		
	}
	
	private int pai(int i){

	    if(i == 0)
	    	return -1;
	    
	    else
	    	return (int) (i-1)/2;
	    
	}
	
	private int filhoDir(int i){

	    if(2*i + 2 < comprimento)
	    	return 2*i + 2;
	    else
	    	return -1;
	    
	}

	private int filhoEsq(int i){
		
		if(2*i + 1 < comprimento)
			return 2*i + 1;
		
		else
			return -1;
	    
	}
	
	private void minHeapify(int i){

	    int direito = filhoDir(i);
	    int esquerdo = filhoEsq(i);
	    int menor = 0;
	    
	    /*if(isAux){
	    	
		    if(esquerdo == -1 )
		    	;
		    else if(esquerdo < tamanho && processos.get(esquerdo).duracao < processos.get(i).duracao){
		    	
		    	//if(processos.get(esquerdo).chegada == processos.get(i).chegada &&
		    	//		processos.get(esquerdo).id < processos.get(i).id)
		    	
		    	menor = esquerdo;
		    	
		    } else
		        menor = i;

		    if(direito == -1)
		    	;
		    else if(direito < tamanho && processos.get(direito).duracao < processos.get(menor).duracao)
		        menor = direito;

		    if(menor != i && (direito != -1 || esquerdo != -1)){

		        trocarValores(i, menor);

		        minHeapify(menor);

		    }	    	
	    	
	    } else{*/
	    	
		    if(esquerdo == -1 )
		    	;
		    else if(esquerdo < tamanho && processos.get(esquerdo).chegada < processos.get(i).chegada){
		    	
		    	//if(processos.get(esquerdo).chegada == processos.get(i).chegada &&
		    	//		processos.get(esquerdo).id < processos.get(i).id)
		    	
		    	menor = esquerdo;
		    	
		    } else
		        menor = i;

		    if(direito == -1)
		    	;
		    else if(direito < tamanho && processos.get(direito).chegada < processos.get(menor).chegada)
		        menor = direito;

		    if(menor != i && (direito != -1 || esquerdo != -1)){

		        trocarValores(i, menor);

		        minHeapify(menor);

		    }
	    	
	    //}

	}
	
	private void buildMinHeap(){

	    tamanho = nElementos;

	    for(int i = (int) (nElementos/2) - 1; i >= 0; i--)
	        minHeapify(i);

	}
	
	private void heapSort(){

		buildMinHeap();
		
		for(int i = nElementos - 1; i > 0; i--){

	        trocarValores(0, i);
	        
	        tamanho--;

	        minHeapify(0);
			
		}
		
	}
	
	public Processo heapExtractMin(){
		
		if(nElementos < 1){
			
			System.out.println();
			System.out.println("Não há mais elementos há serem removidos.");
			return null;
			
		}
		
		Processo elementoRemovido = processos.get(0);

        trocarValores(0, nElementos - 1);
        
        tamanho--;
        nElementos--;
        
        minHeapify(0);
        
        return elementoRemovido;
        
	}
	
	/*private void minHeapIncreaseKey(int i, int chave){
		
		if(chave < processos.get(i).duracao){
			
			System.out.println();
			System.out.println("Chave menor que elemento atual. Não acontecerá nada.");
			return;
			
		}
		
		setValorVetor(nElementos, chave);
		
		while(i < 0 && vetor[heap.Pai(heap, i)].getDistancia() > vetor[i].getDistancia()){
			
			trocarValores(i, heap.Pai(heap, i));
			i = heap.Pai(heap, i);
			
		}
		
	}
	
	private void minHeapDecreaseKey(int i, int chave){
		
		if(chave > processos.get(i).duracao){
			
			System.out.println();
			System.out.println("Chave maior que elemento atual. Não acontecerá nada.");
			return;
			
		}
		
		setValorVetor(i, chave);
		
		while(i > 0 && vetor[heap.Pai(heap, i)].getDistancia() > vetor[i].getDistancia()){
			
			trocarValores(i, heap.Pai(heap, i));
			i = heap.Pai(heap, i);
						
		}
		
	}
	
	private void minHeapInsert(int chave){
		
		//if(nElementos == comprimento){
			
		//	System.out.println();
		//	System.out.println("Heap cheio. Novos elementos não podem ser inseridos.");
		//	return;
			
		//}
		
		tamanho++;
		nElementos++;
				
		setValorVetor(nElementos - 1, duracao);
		
		minHeapIncreaseKey(heap, nElementos - 1, new Vertice(nElementos, chave, null));
		
	}*/

}
