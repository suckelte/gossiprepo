package hu.elte.szamhalo.gossip;

import hu.elte.szamhalo.gossip.gui.GraphView;
import hu.elte.szamhalo.gossip.io.GraphGenerator;
import hu.elte.szamhalo.gossip.util.RumorUtil;
import hu.elte.szamhalo.gossip.vo.Node;
import hu.elte.szamhalo.gossip.vo.Rumor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

public class Tester {
	private static SortedSet<Node> graph = new TreeSet<Node>();
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		graph = GraphGenerator.getComplexGraph(new Integer[]{4,2,6,4}, new Integer[]{50,70,50,70});
        
        Rumor r = RumorUtil.setRandomRumor(graph, "rumor1");
        
        final GraphView graphView = new GraphView(graph);
		
        Thread.sleep(TimeUnit.SECONDS.toMillis(5));
        for (int i = 1; i < 10; i++) {
        	List<String> thisroundnodes = new ArrayList<String>();
        	for (Iterator<Node> it = graph.iterator(); it.hasNext(); ) {
        		Node node = it.next();
        		if(RumorUtil.knowsThatRumor(node, "rumor1") && !thisroundnodes.contains(node.getNodeID())){
        			for (Iterator<Node> it2 = node.getNeighbours().iterator(); it2.hasNext(); ) {
        				Node neighbour = it2.next();
        				if(!RumorUtil.knowsThatRumor(neighbour, "rumor1")){{
        					neighbour.getKnownRumors().add(node.getKnownRumors().first());
        					thisroundnodes.add(neighbour.getNodeID());
        				}
        		}}}
        	}
        	graphView.repaint();
        	System.out.println(RumorVerifier.verify(r, 3));
        	System.out.println("sleep1");
            Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            System.out.println("sleep2");
        }
	}
}
