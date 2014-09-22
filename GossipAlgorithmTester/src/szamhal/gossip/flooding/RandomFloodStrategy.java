package szamhal.gossip.flooding;

import szamhal.gossip.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomFloodStrategy implements FloodStrategy {

    private final Random random;
    private final List<Node> neighborNodes = new ArrayList<Node>();

    public  RandomFloodStrategy(final Random random) {
        this.random = random;
    }

    @Override
    public void setNeighborNodes(final Set<Node> neighborNodes) {
       this.neighborNodes.addAll(neighborNodes);
    }

    @Override
    public Node selectNodeToExchange() {
        return neighborNodes.get(random.nextInt(neighborNodes.size() - 1));
    }
}
