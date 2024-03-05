package io.finazon.example;

import io.finazon.GetTimeSeriesRequest;
import io.finazon.TimeSeries;
import io.finazon.TimeSeriesService;
import io.grpc.StatusRuntimeException;


public class Main {
    public static void main(String[] args) {
        try {
            TimeSeries result = TimeSeriesService
                .blockingStub("your_api_key")
                .getTimeSeries(
                    GetTimeSeriesRequest.newBuilder()
                        .setPageSize(10)
                        .setTicker("BTC/USD")
                        .setDataset("crypto")
                        .setInterval("1h")
                        .build()
                )
                .getResultList()
                .get(0);
            System.out.printf("Last 1h candle for BTC/USD: \n%s%n", result);
        } catch (StatusRuntimeException e) {
            System.out.println(e.getStatus().getCode() + ": " + e.getStatus().getDescription());
        }
    }
}