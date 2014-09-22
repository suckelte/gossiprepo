package szamhal.gossip;

import java.util.Set;
import java.util.HashSet;

public class Network {

    private final Set<Node> nodes = new HashSet<Node>();

    public Network(final Set<Node> nodes) {
        this.nodes.addAll(nodes);
    }

    public void step() {
        exchangeRumors();
        mergeNewlyGatheredRumors();
    }
    private void exchangeRumors() {
        for (final Node node : nodes) {
            node.step();
        }
    }

    private void mergeNewlyGatheredRumors() {
        for (final Node node : nodes) {
            node.finalizeStep();
        }
    }

    @Override
    public String toString() {
        return "Network{" +
                "nodes=" + nodes +
                '}';
    }
}
