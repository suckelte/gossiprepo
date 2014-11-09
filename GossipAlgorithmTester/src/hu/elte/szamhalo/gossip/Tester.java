package hu.elte.szamhalo.gossip;

import hu.elte.szamhalo.gossip.gui.GraphView;
import hu.elte.szamhalo.gossip.gui.MainMenuFrame;
import hu.elte.szamhalo.gossip.gui.MultiGraphPanel;
import hu.elte.szamhalo.gossip.io.GraphGenerator;
import hu.elte.szamhalo.gossip.util.GraphUtil;
import hu.elte.szamhalo.gossip.vo.ChoosingAlgorithmEnum;
import hu.elte.szamhalo.gossip.vo.Node;
import hu.elte.szamhalo.gossip.vo.Rumor;

import java.io.IOException;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public class Tester {
	private static SortedSet<Node> graph = new TreeSet<Node>();
	private static SortedSet<Node> graph2 = new TreeSet<Node>();
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		graph = GraphGenerator.getComplexGraph(new Integer[]{4,2,6,4}, new Integer[]{70,50,70,50});
		graph2 = GraphGenerator.getComplexGraph(new Integer[]{4,2,6,4}, new Integer[]{70,50,70,50});
//		graph = GraphGenerator.getComplexGraph(new Integer[]{10}, new Integer[]{60});
		
		
		GraphUtil.setChoosingAlgorithm(graph, ChoosingAlgorithmEnum.FLOOD);
		GraphUtil.setChoosingAlgorithm(graph2, ChoosingAlgorithmEnum.RANDOM);
        
        Rumor r = GraphUtil.setRandomRumor(graph);
        Rumor r2 = GraphUtil.setRandomRumor(graph2);
        
        final GraphView graphView1 = new GraphView(graph,900,900);
        final GraphView graphView2 = new GraphView(graph2,900,900);
		
        new MainMenuFrame(graphView1,graphView2);
        
        Thread.sleep(TimeUnit.SECONDS.toMillis(5000));
        for (int i = 1; i < 10; i++) {
        	for (Iterator<Node> it = graph.iterator(); it.hasNext(); ) {
        		Node node = it.next();
        		node.getActiveAlgorithm().step();
        	}
        	for (Iterator<Node> it = graph.iterator(); it.hasNext(); ) {
        		Node node = it.next();
        		if(node.getRumor() != null){
        			node.getRumor().setFresh(false);
        		}
        	}
        	for (Iterator<Node> it = graph2.iterator(); it.hasNext(); ) {
        		Node node = it.next();
        		node.getActiveAlgorithm().step();
        	}
        	for (Iterator<Node> it = graph2.iterator(); it.hasNext(); ) {
        		Node node = it.next();
        		if(node.getRumor() != null){
        			node.getRumor().setFresh(false);
        		}
        	}
        	graphView1.repaint();
        	graphView2.repaint();
        	System.out.println(RumorVerifier.verify(r, 3));
        	System.out.println(RumorVerifier.verify(r2, 3));
            Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        }
	}
}
