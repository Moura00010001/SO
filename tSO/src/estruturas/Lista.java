package estruturas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import processos.Processo;

public class Lista{
	
	private static final String DIRETORIO = "recursos/";
	
	public int nElementos;
	public int tempoTotal;
	
	public Processo primeiro;
	public Processo ultimo;
	
	public Lista(String nomeArquivo){
		
		tempoTotal = nElementos = 0;
		ultimo = primeiro = null;
		carregarArquivoParaLista(nomeArquivo);
		
	}
	
	private void carregarArquivoParaLista(String nomeArquivo){

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
        		
       			if(linhaAtual.length == 2){
       				
       				Processo p = new Processo(++count, Integer.valueOf(linhaAtual[0]), Integer.valueOf(linhaAtual[1]));
       				tempoTotal += p.duracao;
       				inserirEmOrdem(p);
       				
       			}       				
    
        	}
        	
       		arquivo.close();
                
        } catch(IOException e){
            System.err.println("Erro ao tentar ler o arquivo");
        }
		
	}
	
	public void inserirEmOrdem(Processo novoProcesso){
		
		Processo p = primeiro, q = null;
		
		if(primeiro == null) {
			
			primeiro = novoProcesso;
			ultimo = primeiro;
			
			nElementos++;
			
			return;
			
		}
		
		while(p != null && p.chegada <= novoProcesso.chegada){
			
			q = p;
			p = p.proximo;
			
		}
		
		novoProcesso.anterior = q;
		novoProcesso.proximo = p;
		
		if(p == primeiro){
			
			primeiro = novoProcesso;
			primeiro.proximo.anterior = novoProcesso;
			
		}	
		
		else{
			
			if(q.proximo != null)
				q.proximo.anterior = novoProcesso;
			
			q.proximo = novoProcesso;
			
			if(novoProcesso.chegada >= ultimo.chegada)
				ultimo = novoProcesso;
			
		}
		
		nElementos++;
		
	}
	
	public void inserirEmOrdem2(Processo novoProcesso){
		
		Processo p = primeiro, q = null;
		
		if(primeiro == null) {
			
			primeiro = novoProcesso;
			ultimo = primeiro;
			
			nElementos++;
			
			return;
			
		}
		
		while(p != null && p.chegadaAux <= novoProcesso.chegadaAux){
			
			q = p;
			p = p.proximo;
			
		}
		
		novoProcesso.anterior = q;
		novoProcesso.proximo = p;
		
		if(p == primeiro){
			
			primeiro = novoProcesso;
			primeiro.proximo.anterior = novoProcesso;
						
		
		} else{
			
			if(q.proximo != null)
				q.proximo.anterior = novoProcesso;
			
			q.proximo = novoProcesso;
			
			if(novoProcesso.chegadaAux >= ultimo.chegadaAux)
				ultimo = novoProcesso;
			
		}
		
		nElementos++;
		
	}
	
	public void inserirNoFinal(Processo novoProcesso){
		
		if(primeiro == null) {
			
			primeiro = novoProcesso;
			ultimo = primeiro;
			
			nElementos++;
			
			return;
			
		}

		novoProcesso.anterior = ultimo;
		ultimo.proximo = novoProcesso;
		ultimo = novoProcesso;
		novoProcesso.proximo = null;
		
		nElementos++;
		
	}
	
	public void removerDoInicio(){
		
		if(primeiro == null)
			return;
		
		primeiro = primeiro.proximo;
		
		if(primeiro != null)
			primeiro.anterior = null;
		
		nElementos--;
		
	}
	
	public void remover(Processo processo){
		
		if(nElementos < 1){
			
			primeiro = ultimo = null;			
			return;
			
		}
		
		if(processo == primeiro){			
			removerDoInicio();
			return;			
		}
		
		if(processo.anterior != null)
			processo.anterior.proximo = processo.proximo;

		if(processo.proximo != null)
			processo.proximo.anterior = processo.anterior;
		
		//processo = null;
		nElementos--;
				
	}

}
