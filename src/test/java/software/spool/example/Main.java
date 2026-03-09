package software.spool.example;

import software.spool.ingester.api.Ingester;
import software.spool.ingester.api.builder.Ingesters;
import software.spool.ingester.internal.adapter.*;

public class Main {
    public static void main(String[] args) {
        InMemoryEventBus bus = new InMemoryEventBus();
        Ingester ingester = Ingesters.buffered()
                .from(bus)
                .storesWith(new InMemoryDataLake())
                .updatedWith(new InMemoryInbox())
                .on(bus)
                .build();
        ingester.startIngestion();
    }
}
