package software.spool.ingester.api.port;

import software.spool.core.exception.InboxReadException;
import software.spool.core.model.InboxItemStatus;
import software.spool.ingester.api.InboxItem;

import java.util.stream.Stream;

public interface InboxReader {
    Stream<InboxItem> findByStatus(InboxItemStatus status) throws InboxReadException;
}
