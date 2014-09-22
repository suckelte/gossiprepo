package szamhal.gossip;

import java.util.Collections;
import java.util.Set;
import java.util.HashSet;

public class Rumor {

    private Set tuple = new HashSet<Node>();

    public Rumor(final Node nodeOne, final Node nodeTwo) {
        tuple.add(nodeOne);
        tuple.add(nodeTwo);
    }

    public Set<Node> getNodes() {
        return Collections.unmodifiableSet(tuple);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null) {
            return false;
        }

        if (o.getClass().equals(this.getClass())) {
            return false;
        }

        Rumor r = (Rumor) o;

        return tuple.equals(r.tuple);
    }

    @Override
    public int hashCode() {
        return 23 * tuple.hashCode();
    }
}
