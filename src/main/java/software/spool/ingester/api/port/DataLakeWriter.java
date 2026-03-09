package software.spool.ingester.api.port;

import java.util.stream.Stream;

public interface DataLakeWriter {
    void write(Stream<String> items);
}