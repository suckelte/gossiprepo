package szamhal.gossip.flooding;

import java.util.Random;

public class RandomFloodStrategyFactory implements FloodStrategyFactory {

    private final Random random;

    public RandomFloodStrategyFactory(final Random random) {
        this.random = random;
    }

    @Override
    public FloodStrategy createInstance() {
        return new RandomFloodStrategy(random);
    }
}
