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
        <version>1.0.0</version>
    </dependency>
```

#### Gradle
```
    implementation "io.finazon:finazon-grpc-java:1.0.0"
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

👀 Check the full example and other examples [here](https://github.com/finazon-io/finazon-grpc-java/tree/main/examples)

## RPC support

The following table outlines the supported rpc calls:
|Service                |rpc                        |Description                                                                                                                                                      |
|-----------------------|---------------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------|
| ApiUsageService       | GetApiUsage               | Get a list of products with quota limit/usage                                                                                                                   |
| BenzingaService       | GetDividentsCalendar      | Returns the dividends calendar from Benzinga                                                                                                                    |
| BenzingaService       | GetEarningsCalendar       | Returns the earnings calendar from Benzinga                                                                                                                     |
| BenzingaService       | GetNews                   | Returns the news articles from Benzinga                                                                                                                         |
| BenzingaService       | GetIPO                    | Returns IPO data from Benzinga                                                                                                                                  |
| BinanceService        | GetTimeSeries             | Get time series data without technical indicators                                                                                                               |
| CryptoService         | GetTimeSeries             | Get time series data for any given ticker                                                                                                                       |
| DatasetsService       | GetDatasets               | Get a list of all datasets available at Finazon.                                                                                                                |
| ExchangeService       | GetMarketsCrypto          | Returns a list of supported crypto markets                                                                                                                      |
| ExchangeService       | GetMarketsStocks          | Returns a list of supported stock markets                                                                                                                       |
| ForexService          | GetTimeSeries             | Get time series data for any given ticker                                                                                                                       |
| PublisherService      | GetPublishers             | Get a list of all publishers available at Finazon.                                                                                                              |
| SecService            | GetFilings                | Real-time and historical access to all forms, filings, and exhibits directly from the SEC's EDGAR system.                                                       |
| SipService            | GetTrades                 | Returns detailed information on trades executed through the Securities Information Processor (SIP)                                                              |
| SipService            | GetMarketCenter           | Returns a list of market centers                                                                                                                                |
| SnapshotService       | GetSnapshot               | This endpoint returns a combination of different data points, such as daily performance, last quote, last trade, minute data, and previous day performance.     |
| TickersService        | FindTickersStocks         | This API call returns an array of stocks tickers available at Finazon Data API. This list is updated daily.                                                     |
| TickersService        | FindTickersCrypto         | This API call returns an array of crypto tickers available at Finazon Data API. This list is updated daily.                                                     |
| TickersService        | FindTickersForex          | This API call returns an array of forex tickers available at Finazon Data API. This list is updated daily.                                                      |
| TickersService        | FindTickerUS              | This API call returns an array of US tickers available at Finazon Data API. This list is updated daily.                                                         |
| TimeSeriesService     | GetTimeSeries             | Get time series data without technical indicators                                                                                                               |
| TimeSeriesService     | GetTimeSeriesAtr          | Get time series data for ATR technical indicator.                                                                                                               |
| TimeSeriesService     | GetTimeSeriesBBands       | Get time series data for BBands technical indicator.                                                                                                            |
| TimeSeriesService     | GetTimeSeriesIchimoku     | Get time series data for Ichimoku technical indicator.                                                                                                          |
| TimeSeriesService     | GetTimeSeriesMa           | Get time series data for Ma technical indicator.                                                                                                                |
| TimeSeriesService     | GetTimeSeriesMacd         | Get time series data for Macd technical indicator.                                                                                                              |
| TimeSeriesService     | GetTimeSeriesObv          | Get time series data for Obv technical indicator.                                                                                                               |
| TimeSeriesService     | GetTimeSeriesRsi          | Get time series data for Rsi technical indicator.                                                                                                               |
| TimeSeriesService     | GetTimeSeriesSar          | Get time series data for Sar technical indicator.                                                                                                               |
| TimeSeriesService     | GetTimeSeriesStoch        | Get time series data for Stoch technical indicator.                                                                                                             |
| TradeService          | GetTrades                 | Returns general information on executed trades                                                                                                                  |

## Documentation
Delve deeper with our [official documentation](https://finazon.io/docs).

## Examples
Explore practical scenarios in the [examples](https://github.com/finazon-io/finazon-grpc-java/tree/main/examples) directory.

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