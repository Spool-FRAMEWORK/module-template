package software.spool.ingester.api.port;

import software.spool.core.exception.InboxUpdateException;
import software.spool.core.model.IdempotencyKey;
import software.spool.core.model.InboxItemStatus;
import software.spool.ingester.api.InboxItem;

public interface InboxUpdater {
    InboxItem update(IdempotencyKey idempotencyKey, InboxItemStatus status) throws InboxUpdateException;
}
