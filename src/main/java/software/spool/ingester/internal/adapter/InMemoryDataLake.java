package software.spool.ingester.internal.adapter;

import software.spool.ingester.api.port.DataLakeWriter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;

public class InMemoryDataLake implements DataLakeWriter {
    private final CopyOnWriteArrayList<String> store = new CopyOnWriteArrayList<>();

    @Override
    public void write(Stream<String> items) {
        items.forEach(store::add);
    }

    public List<String> findAll() {
        return List.copyOf(store);
    }
}