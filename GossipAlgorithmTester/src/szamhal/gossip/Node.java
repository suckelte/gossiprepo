package szamhal.gossip;

import szamhal.gossip.flooding.FloodStrategyFactory;
import szamhal.gossip.flooding.FloodStrategy;

import java.util.Set;
import java.util.HashSet;

public class Node {

    private enum Status {UNINITIALIZED, INITIALIZED};

    private final Set<Node> neighborNodes = new HashSet<Node>();

    private final Set<Rumor> knownRumors = new HashSet<Rumor>();

    private final Set<Rumor> arrivedRumors  = new HashSet<Rumor>();

    private Status status = Status.UNINITIALIZED;

    private FloodStrategy floodStrategy;

    public void addNeighbor(final Node node) {
        if (!status.equals(Status.UNINITIALIZED)) {
            throw new IllegalStateException("Can't change topology after initialization");
        }

        neighborNodes.add(node);
    }

    public void init(final FloodStrategyFactory floodStrategyFactory) {
        if (!status.equals(Status.UNINITIALIZED)) {
            throw new IllegalStateException("Can't initialize twice");
        }

        initFloodStrategy(floodStrategyFactory);
        initRumors();
        status = Status.INITIALIZED;
    }

    private void initFloodStrategy(final FloodStrategyFactory floodStrategyFactory) {
        floodStrategy = floodStrategyFactory.createInstance();
        floodStrategy.setNeighborNodes(neighborNodes);
    }

    private void initRumors() {
        for (final Node neighborNode :neighborNodes) {
            knownRumors.add(new Rumor(this, neighborNode));
        }
    }

    public void step() {
        if (!status.equals(Status.INITIALIZED)) {
            throw new IllegalStateException("Node is not initialized");
        }

        final Node exchangePartner = floodStrategy.selectNodeToExchange();
        final Set<Rumor> newRumors =  exchangePartner.exchangeRumors(knownRumors);
        arrivedRumors.addAll(newRumors);
    }

    public void finalizeStep() {
        if (!status.equals(Status.INITIALIZED)) {
            throw new IllegalStateException("Node is not initialized");
        }

        mergeRumors();
    }

    private Set<Rumor> exchangeRumors(final Set<Rumor> sentRumors) {
        arrivedRumors.addAll(sentRumors);
        return knownRumors;
    }

    private void mergeRumors() {
        knownRumors.addAll(arrivedRumors);
        arrivedRumors.clear();
    }

    @Override
    public String toString() {
        return "Node{" +
                "knownRumors=" + knownRumors +
                ", status=" + status +
                ", floodStrategy=" + floodStrategy +
                '}';
    }
}
