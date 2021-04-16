package it.polito.tdp.nobel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polito.tdp.nobel.db.EsameDAO;

public class Model {
	private List<Esame> partenza;
	private Set<Esame> soluzioneMigliore;
	private double mediaSoluzioneMigliore;
	
	public Model() {
		EsameDAO dao = new EsameDAO();
		this.partenza=dao.getTuttiEsami();
	}
	
	
	public Set<Esame> calcolaSottoinsiemeEsami(int numeroCrediti) {
		Set<Esame> parziale = new HashSet<Esame>();
		soluzioneMigliore = new HashSet<Esame>();
		mediaSoluzioneMigliore=0;
		
		cerca(parziale,0,numeroCrediti);//funzione ricorsiva
		
			
		return soluzioneMigliore;	
	}

	/*COMPLESSITA: N! */
	private void cerca(Set<Esame> parziale, int L, int m) {
		//casi terminali
		int crediti = sommaCrediti(parziale);
		
		if(crediti>m) { //controllare i crediti
			
		}
		if(crediti==m) {
			double media = calcolaMedia(parziale);
			if(media>mediaSoluzioneMigliore) {
				soluzioneMigliore = new HashSet<>(parziale); //sovrascrivere
				mediaSoluzioneMigliore = media;
			}
			return; //non creare sotto-alberi inutili ( L>m)
		}
		
		//siccuramente, crediti<m: 
		
		// 1) non ci sono più esami da aggiungere: L=m
		if(L==partenza.size()) {
			return;
		}
		
		//generare i sotto-problemi
		for(Esame e : partenza) { //lascio partenza come è
			if(!parziale.contains(e)) {
				parziale.add(e);
				cerca(parziale,L+1,m);
				parziale.remove(e); //backtracking
			}
		}
		
		
	}


	public double calcolaMedia(Set<Esame> esami) {
		
		int crediti = 0;
		int somma = 0;
		
		for(Esame e : esami){
			crediti += e.getCrediti();
			somma += (e.getVoto() * e.getCrediti());
		}
		
		return somma/crediti;
	}
	
	public int sommaCrediti(Set<Esame> esami) {
		int somma = 0;
		
		for(Esame e : esami)
			somma += e.getCrediti();
		
		return somma;
	}

}
