package szamhal.gossip.flooding;

import java.util.Set;
import szamhal.gossip.Node;

public interface FloodStrategy {

    void setNeighborNodes(final Set<Node> neighborNodes);
    Node selectNodeToExchange();
}
