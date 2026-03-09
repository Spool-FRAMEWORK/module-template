package software.spool.ingester.api;

import software.spool.core.control.Handler;
import software.spool.core.model.ItemPublished;
import software.spool.ingester.api.port.EventBusListener;
import software.spool.ingester.api.port.Subscription;
import software.spool.ingester.internal.utils.Buffer;

import java.util.Collection;

public class Ingester {
    private final EventBusListener listener;
    private final Handler<Collection<ItemPublished>> handler;
    private final Buffer buffer;
    private Subscription subscription;

    public Ingester(EventBusListener listener, Handler<Collection<ItemPublished>> handler, Buffer buffer) {
        this.listener = listener;
        this.handler = handler;
        this.buffer = buffer;
        this.subscription = Subscription.NULL;
    }

    public void startIngestion() {
        if (subscription != Subscription.NULL) return;
        subscription = listener.on(ItemPublished.class, buffer::insert);
        if (buffer.shouldFlush()) handler.handle(buffer.flush());
    }

    public void stopIngestion() {
        if (subscription == Subscription.NULL) return;
        subscription.cancel();
        subscription = Subscription.NULL;
    }
}
