package software.spool.ingester.internal.control;

import software.spool.core.control.Handler;
import software.spool.core.exception.SpoolException;
import software.spool.core.model.InboxItemStatus;
import software.spool.core.model.ItemPublished;
import software.spool.ingester.api.port.DataLakeWriter;
import software.spool.ingester.api.port.EventBusEmitter;
import software.spool.ingester.api.port.InboxUpdater;

import java.util.Collection;

public class ItemPublishedHandler implements Handler<Collection<ItemPublished>> {
    private final DataLakeWriter dataLakeWriter;
    private final InboxUpdater updater;
    private final EventBusEmitter emitter;

    public ItemPublishedHandler(DataLakeWriter dataLakeWriter, InboxUpdater updater, EventBusEmitter emitter) {
        this.dataLakeWriter = dataLakeWriter;
        this.updater = updater;
        this.emitter = emitter;
    }

    @Override
    public void handle(Collection<ItemPublished> items) throws SpoolException {
        dataLakeWriter.write(items.stream().map(ItemPublished::payload));
        items.forEach(item -> {
            updater.update(item.idempotencyKey(), InboxItemStatus.PERSISTED);
            emitter.emit(ItemPersisted.of(item));
        });
    }
}
