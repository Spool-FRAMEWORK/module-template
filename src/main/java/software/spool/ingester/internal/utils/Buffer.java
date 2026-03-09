package software.spool.ingester.api;

import software.spool.core.model.ItemPublished;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Buffer {
    private final List<ItemPublished> items = new ArrayList<>();
    private final FlushPolicy policy;
    private Instant lastFlush = Instant.now();

    public Buffer(FlushPolicy policy) {
        this.policy = policy;
    }

    public void insert(ItemPublished item) {
        items.add(item);
    }

    public boolean shouldFlush() {
        return policy.shouldFlush(items.size(), Duration.between(lastFlush, Instant.now()));
    }

    public Collection<ItemPublished> flush() {
        List<ItemPublished> snapshot = List.copyOf(items);
        items.clear();
        lastFlush = Instant.now();
        return snapshot;
    }
}
