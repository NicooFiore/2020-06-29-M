package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	private ImdbDAO dao;
	private List<Director> direttori;
	private Map<Integer,Director> idMap;
	private Graph<Director,DefaultWeightedEdge> grafo;
	public Model() {
	dao=new ImdbDAO();
	}
	public List<Director> getAllDirettoriAnno(int anno){
			direttori=new ArrayList<>();
			idMap=new HashMap<>();
			direttori=dao.listAllDirectorsAnno(anno, idMap);
		
		return direttori;
			
	}
	public String creaGrafo(int anno) {
		grafo=new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, this.getAllDirettoriAnno(anno));
		for(Coppia c :dao.listAllCoppie(anno, idMap)) {
			Graphs.addEdgeWithVertices(grafo, c.getD1(), c.getD2(), c.getPeso());
		}
		return "Vertici: "+grafo.vertexSet().size()+" Archi: "+grafo.edgeSet().size();
	}
	public String adiacente(Director d) {
		List<DirettoreConPeso> result=new ArrayList<>();
		for(Director dd:Graphs.neighborListOf(grafo, d)) {
			DefaultWeightedEdge e =grafo.getEdge(dd, d);
			int peso=(int)(grafo.getEdgeWeight(e));
			result.add(new DirettoreConPeso(dd,peso));		
		}
		String res="Vicini del direttore:"+d.getFirstName()+"\n\n";
		Collections.sort(result);
		Collections.reverse(result);
		for(DirettoreConPeso dcp:result) {
			res+=dcp.toString()+"\n";
		}
		return res;
	}
	private List<Director> parziale;
	private List<Director> migliore;
	private int massimo;
	private Director iniziale;
	public void init(Director d,int c) {
		iniziale=d;
		massimo=c;
		parziale=new ArrayList<>();
		parziale.add(d);
		migliore=new ArrayList<>();
		ricorsione();
	}
	private void ricorsione() {
		if(parziale.size()>migliore.size()) {
			migliore=new ArrayList<>(parziale);
		}
		for(Director d :Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(d)) {
			parziale.add(d);
			if(calcolaAttoriComune(parziale)<=massimo) {
				ricorsione();
				parziale.remove(d);
			}
			else 
				parziale.remove(d);
		}
		}
	}
	private int calcolaAttoriComune(List<Director> parziale2) {
		int peso=0;
		for(int i=0;i<parziale2.size()-1;i++) {
			DefaultWeightedEdge e=grafo.getEdge(parziale2.get(i), parziale2.get(i+1));
			peso+=grafo.getEdgeWeight(e);
		}
		return peso;
	}
	
	public String listRegistiAffini() {
		String res="";
		for(Director d:migliore) {
			res+=d.getFirstName()+" "+d.getLastName()+"\n";
		}
		res+=this.calcolaAttoriComune(migliore);
		return res;
	}
	
	
	

}
