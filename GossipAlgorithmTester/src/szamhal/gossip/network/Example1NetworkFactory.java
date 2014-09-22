package szamhal.gossip.network;

import szamhal.gossip.Network;
import szamhal.gossip.Node;
import szamhal.gossip.flooding.FloodStrategyFactory;
import szamhal.gossip.flooding.RandomFloodStrategyFactory;

import java.util.Random;
import java.util.Set;
import java.util.HashSet;

public class Example1NetworkFactory implements NetworkFactory {


    @Override
    public Network createInstance() {
        final Random random = new Random();
        final FloodStrategyFactory floodStrategyFactory = new RandomFloodStrategyFactory(random);

        final Node node1 = new Node();
        final Node node2 = new Node();
        final Node node3 = new Node();

        node1.addNeighbor(node2);
        node2.addNeighbor(node1);

        node2.addNeighbor(node3);
        node3.addNeighbor(node2);

        node3.addNeighbor(node1);
        node1.addNeighbor(node3);

        node1.init(floodStrategyFactory);
        node2.init(floodStrategyFactory);
        node3.init(floodStrategyFactory);

        final Set<Node> nodes = new HashSet<Node>();
        nodes.add(node1);
        nodes.add(node2);
        nodes.add(node3);

        final Network network = new Network(nodes);
        return network;
    }
}
