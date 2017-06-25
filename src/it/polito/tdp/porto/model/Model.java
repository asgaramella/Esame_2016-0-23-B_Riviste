package it.polito.tdp.porto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.porto.db.PortoDAO;

public class Model {
	
	private PortoDAO dao;
	private List<Paper>  articoli;
	private Map<Integer,Paper> mapArticoli;
	private List<Author> autori;
	private SimpleGraph<Nodo,DefaultEdge> graph;
	private Map<String,Integer> riviste;
	private Map<Integer,Integer> frequenze;
	private Map<String,List<Paper>> rivAndArt;
	private List<String> best;

	public Model() {
		super();
		dao=new PortoDAO();
		riviste=new HashMap<>();
		rivAndArt=new HashMap<>();
		this.getAllArticle();
		for(String s:dao.getAllRiviste()){
			riviste.put(s, 0);
			rivAndArt.put(s, dao.getArtForRivista(s,mapArticoli));
		}
		frequenze=new HashMap<>();
	}
	
	public List<Author> getAllAutori(){
		
		if(autori==null){
			autori=dao.getAllAutori();
		for(Author a: autori){
			a.getArticoli().addAll(dao.getArticoliForAuthor(a,mapArticoli));
		}
		}
		
		return autori;
	}
	
	public List<Paper> getAllArticle(){
		if(articoli==null){
			articoli=dao.getAllArticle();
			mapArticoli=new HashMap<>();
			for(Paper ptemp:articoli){
				mapArticoli.put(ptemp.getEprintid(),ptemp);
			}
			
		}
		
		return articoli;
	}
	
	public void creaGrafo(){
		graph=new SimpleGraph<Nodo,DefaultEdge>(DefaultEdge.class);
		
		Graphs.addAllVertices(graph, this.getAllArticle());
		Graphs.addAllVertices(graph, this.getAllAutori());
		
		for(Author atemp:this.getAllAutori()){
			for(Paper ptemp: atemp.getArticoli()){
				graph.addEdge(atemp, ptemp);
			}
			
		}
	}
	
	public List<Stat> getFrequenze(){
		List<Stat> stats=new ArrayList<>();
		this.creaGrafo();
		System.out.println(graph.edgeSet().size());
		for(Paper p:this.getAllArticle()){
			if(graph.edgesOf(p).size()!=0){
				riviste.put(p.getPublication(),riviste.get(p.getPublication())+1);
			}
		}
		
		for(int nrPubb: riviste.values()){
			Integer nrRiv= frequenze.get(nrPubb);
			if(nrRiv==null){
				frequenze.put(nrPubb, 1);
			}else{
				frequenze.put(nrPubb,nrRiv+1 );
			}
		}
		
		for(int k:frequenze.keySet()){
			stats.add(new Stat(k,frequenze.get(k)));
		}
		
		Collections.sort(stats);
		
		return stats;
		
	}
	
	public List<String> getMinSequenza(){
		List<String> parziale=new ArrayList<>();
		best=new ArrayList<>();
		
		recursive(0,parziale);
		
		return best;
	}

	private void recursive(int livello, List<String> parziale) {
		
		if(getInclusiAllAutori(parziale)){
			if(best.isEmpty()|| parziale.size()<best.size()){
				
				best.clear();
				best.addAll(parziale);
				System.out.println(best);
			}
		}
		
		for(String s:riviste.keySet()){
			
			if(parziale.isEmpty() || s.compareTo(parziale.get(parziale.size()-1))>0){
				
				parziale.add(s);
				
				recursive(livello+1,parziale);
				
				parziale.remove(s);
			}
			
		}
		
	}

	private boolean getInclusiAllAutori(List<String> parziale) {
	Set<Author> allAutori=new HashSet<>(this.getAllAutori());
	int tot=allAutori.size();
	
	boolean empty=false;
	
	for(String s: parziale){
		for(Paper p:rivAndArt.get(s)){
			allAutori.removeAll(Graphs.neighborListOf(graph, p));
			tot=allAutori.size();
		}
	}
	
	if(allAutori.isEmpty())
		empty=true;
	
		return empty;
	}
	
	

}
