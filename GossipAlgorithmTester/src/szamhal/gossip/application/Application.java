package szamhal.gossip.application;

import szamhal.gossip.Network;
import szamhal.gossip.network.Example1NetworkFactory;
import szamhal.gossip.network.NetworkFactory;

import java.util.concurrent.TimeUnit;

public class Application {

    public static void main(final String[] args) throws InterruptedException {
        final NetworkFactory networkFactory = new Example1NetworkFactory();
        final Network network = networkFactory.createInstance();

        System.out.println(network);

        for (int i = 1; i < 10; i++) {
            network.step();
            Thread.sleep(TimeUnit.SECONDS.toMillis(5));
            System.out.println(network);
        }


    }
}
