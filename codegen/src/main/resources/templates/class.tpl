package {{ package }};

import io.grpc.ManagedChannel;

public final class {{ name }} {

    private {{ name }}() {}

    public static {{ name }}BlockingStub blockingStub(String apiKey) {
        return new {{ name }}BlockingStub(FinazonChannel.create(apiKey));
    }

    public static {{ name }}Stub stub(String apiKey) {
        return new {{ name }}Stub(FinazonChannel.create(apiKey));
    }

    public static {{ name }}FutureStub futureStub(String apiKey) {
        return new {{ name }}FutureStub(FinazonChannel.create(apiKey));
    }

    public static final class {{ name }}BlockingStub {
        private final {{ name }}Grpc.{{ name }}BlockingStub service;

        private {{ name }}BlockingStub(ManagedChannel channel) {
            service = {{ name }}Grpc.newBlockingStub(channel);
        }

        {% for item in rpc %}
        /**
         * {{ item.documentation }}
         */
        public {{ item.responseType }} {{ item.name }}({{ item.requestType }} request) {
            return service.{{ item.name }}(request);
        }
        {% endfor %}
    }

    public static final class {{ name }}Stub {
        private final {{ name }}Grpc.{{ name }}Stub service;

        private {{ name }}Stub(ManagedChannel channel) {
            service = {{ name }}Grpc.newStub(channel);
        }

        {% for item in rpc %}
        /**
         * {{ item.documentation }}
         */
        public void {{ item.name }}({{ item.requestType }} request, io.grpc.stub.StreamObserver<{{ item.responseType }}> response) {
            service.{{ item.name }}(request, response);
        }
        {% endfor %}
    }

    public static final class {{ name }}FutureStub {
        private final {{ name }}Grpc.{{ name }}FutureStub service;

        private {{ name }}FutureStub(ManagedChannel channel) {
            service = {{ name }}Grpc.newFutureStub(channel);
        }

        {% for item in rpc %}
        /**
         * {{ item.documentation }}
         */
        public com.google.common.util.concurrent.ListenableFuture<{{ item.responseType }}> {{ item.name }}({{ item.requestType }} request) {
            return service.{{ item.name }}(request);
        }
        {% endfor %}
    }

}