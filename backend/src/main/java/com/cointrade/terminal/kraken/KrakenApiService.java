package com.cointrade.terminal.kraken;

import com.cointrade.terminal.model.TickerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class KrakenApiService {

    private static final Logger logger = LoggerFactory.getLogger(KrakenApiService.class);
    private final RestTemplate restTemplate;
    private final String baseUrl = "https://api.kraken.com/0/public";

    public KrakenApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getSystemStatus() {
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/SystemStatus")
                .toUriString();
        return restTemplate.getForObject(url, String.class);
    }

    public TickerResponse getTickerInformation(String pair) {
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/Ticker")
                .queryParam("pair", pair)
                .toUriString();
        logger.info("Calling Kraken API: {}", url);
        try {
            // First get the raw response as String to see what we're getting
            String rawResponse = restTemplate.getForObject(url, String.class);
            logger.info("Raw response from Kraken: {}", rawResponse);

            // Now try to parse it to TickerResponse
            TickerResponse response = restTemplate.getForObject(url, TickerResponse.class);
            logger.info("Parsed TickerResponse: {}", response);
            return response;
        } catch (Exception e) {
            logger.error("Error fetching ticker information", e);
            throw e;
        }
    }

    public String getServerTime() {
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/Time")
                .toUriString();
        return restTemplate.getForObject(url, String.class);
    }

    public String getAssetInfo(String assets) {
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/Assets")
                .queryParam("asset", assets)
                .toUriString();
        return restTemplate.getForObject(url, String.class);
    }

    public String getOHLCData(String pair, Integer interval, Integer since) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/OHLC")
                .queryParam("pair", pair);
        if (interval != null) {
            builder.queryParam("interval", interval);
        }
        if (since != null) {
            builder.queryParam("since", since);
        }
        return restTemplate.getForObject(builder.toUriString(), String.class);
    }

    public String getOrderBook(String pair, Integer count) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/Depth")
                .queryParam("pair", pair);
        if (count != null) {
            builder.queryParam("count", count);
        }
        return restTemplate.getForObject(builder.toUriString(), String.class);
    }

    public String getRecentTrades(String pair, String since, Integer count) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/Trades")
                .queryParam("pair", pair);
        if (since != null) {
            builder.queryParam("since", since);
        }
        if (count != null) {
            builder.queryParam("count", count);
        }
        return restTemplate.getForObject(builder.toUriString(), String.class);
    }

    public String getRecentSpreads(String pair, Integer since) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(baseUrl)
                .path("/Spread")
                .queryParam("pair", pair);
        if (since != null) {
            builder.queryParam("since", since);
        }
        return restTemplate.getForObject(builder.toUriString(), String.class);
    }
}
