package software.spool.ingester.api.builder;

import software.spool.ingester.api.Ingester;
import software.spool.ingester.api.port.DataLakeWriter;
import software.spool.ingester.api.port.EventBusEmitter;
import software.spool.ingester.api.port.EventBusListener;
import software.spool.ingester.api.port.InboxUpdater;
import software.spool.ingester.internal.control.ItemPublishedHandler;
import software.spool.ingester.internal.utils.Buffer;
import software.spool.ingester.internal.utils.FlushPolicy;

import java.util.Objects;

public class IngesterBuilder {
    private FlushPolicy flushPolicy;
    private EventBusListener listener;
    private DataLakeWriter writer;
    private InboxUpdater updater;
    private EventBusEmitter emitter;

    private IngesterBuilder() {}

    public static IngesterBuilder create() {
        return new IngesterBuilder();
    }

    protected IngesterBuilder flushWith(FlushPolicy flushPolicy) {
        this.flushPolicy = flushPolicy;
        return this;
    }

    public IngesterBuilder from(EventBusListener listener) {
        this.listener = listener;
        return this;
    }

    public IngesterBuilder storesWith(DataLakeWriter writer) {
        this.writer = writer;
        return this;
    }

    public IngesterBuilder updatedWith(InboxUpdater updater) {
        this.updater = updater;
        return this;
    }

    public IngesterBuilder on(EventBusEmitter emitter) {
        this.emitter = emitter;
        return this;
    }

    public Ingester build() {
        Objects.requireNonNull(flushPolicy);
        Objects.requireNonNull(listener);
        Objects.requireNonNull(writer);
        Objects.requireNonNull(updater);
        Objects.requireNonNull(emitter);
        return new Ingester(
                listener,
                new ItemPublishedHandler(writer, updater, emitter),
                new Buffer(flushPolicy));
    }
}
