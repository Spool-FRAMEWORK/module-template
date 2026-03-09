package software.spool.ingester.api.builder;

import software.spool.ingester.internal.utils.FlushPolicy;

import java.time.Duration;

public class Ingesters {
    public static IngesterBuilder reactive() {
        return IngesterBuilder.create().flushWith(FlushPolicy.immediate());
    }

    public static IngesterBuilder buffered() {
        return IngesterBuilder.create().flushWith(FlushPolicy.whenReaches(100).orEvery(Duration.ofSeconds(60)));
    }
}
