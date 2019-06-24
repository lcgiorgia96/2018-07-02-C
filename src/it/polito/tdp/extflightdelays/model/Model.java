package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;


import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	Graph<Airport,DefaultWeightedEdge> grafo ; 
	List<Airport>  aer;
	ExtFlightDelaysDAO dao;
	Map<Integer,Airport> idMap;
	int tot;
	List<Airport> best;
	
	public Model () {
		dao = new ExtFlightDelaysDAO();
		
	}
	public void creaGrafo(int x) {
		aer = dao.getAer(x);
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		idMap = new HashMap<>();
		Graphs.addAllVertices(this.grafo, aer);
		
		for (Airport a: this.grafo.vertexSet()) {
			idMap.put(a.getId(), a);
		}
		
		List<Rotta> rotte = dao.getRotte(idMap);
		
		for (Rotta r: rotte) {
			Airport a1 = r.getA1();
			Airport a2 = r.getA2();
			double peso =  r.getVoli();
			
			DefaultWeightedEdge edge = grafo.getEdge(a1, a2);
			if (edge==null) {
				Graphs.addEdge(this.grafo, a1, a2, peso);
			} else {
				grafo.setEdgeWeight(edge, grafo.getEdgeWeight(edge)+peso);
			}
		}
		
		System.out.println(this.grafo.vertexSet().size()+" "+this.grafo.edgeSet().size());
	
	}
	public Graph<Airport, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}
	public List<Airport> getAer() {
		return aer;
	}
	public List<Rotta> getConnessi(Airport a) {
		List<Airport> adiacenti = Graphs.neighborListOf(this.grafo, a);
		List<Rotta> res = new ArrayList<>();
		for (Airport a2: adiacenti) {
			DefaultWeightedEdge edge = this.grafo.getEdge(a, a2);
			double peso = this.grafo.getEdgeWeight(edge);
			res.add(new Rotta (a,a2,peso));
		}
		
		Collections.sort(res);
		return res;
	}
	public List<Airport> cercaItinerario(Airport a1, Airport a2, int t) {
		
		best = new ArrayList<>();
		
		List<Airport> parziale = new ArrayList<>();
		
		parziale.add(a1);
		
		cerca(a1,parziale,a2,t);
		
		
		return best;
	}
	
	private void cerca(Airport a1, List<Airport> parziale, Airport a2, int t) {
		List<Airport> toVisit = Graphs.neighborListOf(this.grafo, parziale.get(parziale.size()-1));
		if (toVisit == null) {
			System.out.println("Nessun aeroporto vicino trovato");
		}
		for (Airport temp: toVisit) {
			if(!parziale.contains(temp)  && parziale.size()<=t) {
				parziale.add(temp);
				cerca(a1,parziale,a2,t);
				parziale.remove(parziale.size()-1);
			}
			
		}
		
		if(parziale.get(parziale.size()-1).equals(a2)) {
		if(calcolaTot(parziale)>calcolaTot(best)) {
					best = new ArrayList<>(parziale);
				}
		} 
		}
	
	private int calcolaTot(List<Airport> parziale) {
		tot = 0;
		for (int i=0; i<parziale.size()-1;i++) {
			Airport a1 = parziale.get(i);
			Airport a2 = parziale.get(i+1);
			
			tot+= this.grafo.getEdgeWeight(this.grafo.getEdge(a1, a2));
		}
		return tot;
	}
	public int getTot() {
		return tot;
	}

	
}
