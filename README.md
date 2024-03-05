# Finazon Java gRPC Client

This is the official Java library for Finazon, offering access to:
- Lists of datasets, publishers, markets, and tickers.
- Market data: ticker snapshots, time series, trades, and technical indicators.
- Data from specific datasets such as Benzinga, Binance, Crypto, Forex, SEC, and SIP.

🔑 **API key** is essential. If you haven't got one yet, [sign up here](https://finazon.io/).

## Quick start

### Maven

You can now integrate this library in your project via Maven.

#### Maven
```bash
    <dependency>
        <groupId>io.finazon</groupId>
        <artifactId>finazon-grpc-java</artifactId>
        <version>1.0</version>
    </dependency>
```

#### Gradle
```
    implementation "io.finazon:finazon-grpc-java:1.0"
```

### 2. Create `App.java` script
```java
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
            System.out.println(String.format("Last 1h candle for BTC/USD: \n%s", result));
        } catch (StatusRuntimeException e) {
            System.out.println(e.getStatus().getCode() + ": " + e.getStatus().getDescription());
        }
    }
}
```

### 3. Input your API key
Replace `"your_api_key"` with your actual key.


📝 Expected output:
```
Last 1h candle for BTC/USD: 
timestamp: 1709366400
open: 62080.37
close: 61934.56
high: 62138.45
low: 61859.43
volume: 114.09
```

## Documentation
Delve deeper with our [official documentation](https://finazon.io/docs).

## Examples
Explore practical scenarios in the [examples](https://github.com/finazon-io/finazon-grpc-java/tree/main/finazon_grpc_java/examples) directory.

## Support
- 🌐 Visit our [contact page](https://finazon.io/contact-sales).
- 🛠 Or our [support center](https://support.finazon.io/en/).

## Stay updated
- Follow us on [𝕏](https://twitter.com/finazon_io).
- Join our community on [Discord](https://discord.gg/D5u4ZpB7w7).
- Follow us on [LinkedIn](https://www.linkedin.com/company/finazon).

## Contributing
1. Fork and clone: `$ git clone https://github.com/finazon-io/finazon-grpc-java.git`.
2. Branch out: `$ cd finazon-grpc-java && git checkout -b my-feature`.
3. Commit changes and test.
4. Push your branch and open a pull request with a comprehensive description.

For more guidance on contributing, see the [GitHub Docs](https://docs.github.com/en/get-started/quickstart/contributing-to-projects) on GitHub.

## License

This project is licensed under the MIT License. See the `LICENSE.txt` file in this repository for more details.
