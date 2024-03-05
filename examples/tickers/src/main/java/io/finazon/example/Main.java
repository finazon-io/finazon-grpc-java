package io.finazon.example;

import io.finazon.TickersService;
import io.finazon.FindTickersCryptoRequest;

public class Main {
    public static void main(String[] args) {
        TickersService
            .blockingStub("your_api_key")
            .findTickersCrypto(
                FindTickersCryptoRequest.newBuilder()
                    .setPageSize(10)
                    .setPublisher("binance")
                    .build()
            )
            .getResultList()
            .forEach(item -> {
                System.out.println(item.getTicker());
            });
    }
}