package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private SimpleWeightedGraph <Match,DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map <Integer, Match> idMap;
	
	public Model(){
		
	dao = new PremierLeagueDAO();
	idMap = new HashMap<>();
	dao.listAllMatches(idMap);
		
	}
	
	public void creaGrafo(int mese, int minuti) {
		grafo = new SimpleWeightedGraph <>(DefaultWeightedEdge.class);
		//vertici
		Graphs.addAllVertices(grafo,dao.getVertici(mese, idMap));
		
		//archi
		for(Adiacenza a : dao.getAdiacenze(mese, idMap, minuti)) {
			Graphs.addEdgeWithVertices(grafo, a.getM1(), a.getM2(), a.getPeso());
		}
		
		
		System.out.println("GRAFO CREATO!" + this.grafo.vertexSet().size()+" "+this.grafo.edgeSet().size());
	}
	
	public Integer nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public Integer nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	/*
	 Premendo il bottone “Connessione Max”, si stampi la coppia (o le coppie) di match in cui hanno giocato,
	 per almeno MIN minuti in entrambe le partite, il maggior numero di giocatori.
	 */
	
	public List <Adiacenza> getMatchMigliori(int mese, int minuti){
		List <Adiacenza> result = new ArrayList<>();
		int max=0;
		for(Adiacenza a : this.dao.getAdiacenze(mese, idMap, minuti)) {
			
			if(a.getPeso()>max) {
				max = a.getPeso();
			}
						
		}
		
		for(Adiacenza a : this.dao.getAdiacenze(mese, idMap, minuti)) {
			if(a.getPeso()==max) {
				result.add(a);
			}
		}
		
		return result;
	}
	
	
}