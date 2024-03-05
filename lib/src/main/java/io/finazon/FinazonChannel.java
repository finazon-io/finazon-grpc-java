package io.finazon;

import io.grpc.ChannelCredentials;
import io.grpc.CompositeChannelCredentials;
import io.grpc.Grpc;
import io.grpc.ManagedChannel;
import io.grpc.TlsChannelCredentials;
import io.grpc.auth.MoreCallCredentials;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FinazonChannel {

    private static final String VERSION = "1-0";
    private static final String HOST = "grpc-v" + VERSION + ".finazon.io";
    private static final int PORT = 443;

    private Map<Object, ManagedChannel> map;

    private static FinazonChannel fc = new FinazonChannel();

    private FinazonChannel() {
        map = new ConcurrentHashMap<>();
    }

    public static ManagedChannel create(String apiKey) {
        if (!fc.map.containsKey(apiKey)) {
            ChannelCredentials channelCredentials = CompositeChannelCredentials.create(
                TlsChannelCredentials.create(),
                MoreCallCredentials.from(new FinazonApiCredentials(apiKey))
            );
            fc.map.put(apiKey, Grpc.newChannelBuilderForAddress(HOST, PORT, channelCredentials).build());
        }
        return fc.map.get(apiKey);
    }
}
