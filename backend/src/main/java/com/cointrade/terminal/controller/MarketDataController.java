package com.cointrade.terminal.controller;

import com.cointrade.terminal.kraken.KrakenApiService;
import com.cointrade.terminal.model.TickerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/market")
public class MarketDataController {

    private final KrakenApiService krakenApiService;

    public MarketDataController(KrakenApiService krakenApiService) {
        this.krakenApiService = krakenApiService;
    }

    @GetMapping("/status")
    public String getSystemStatus() {
        return krakenApiService.getSystemStatus();
    }

    @GetMapping(value = "/data", produces = "application/json")
    public TickerResponse getMarketData(@RequestParam String pair) {
        TickerResponse response = krakenApiService.getTickerInformation(pair);
        if (response == null) {
            throw new RuntimeException("Failed to fetch ticker information for pair: " + pair);
        }
        return response;
    }

    @GetMapping("/time")
    public String getServerTime() {
        return krakenApiService.getServerTime();
    }

    @GetMapping("/assets")
    public String getAssetInfo(@RequestParam(required = false) String assets) {
        return krakenApiService.getAssetInfo(assets);
    }

    @GetMapping("/ohlc")
    public String getOHLCData(@RequestParam String pair,
                                    @RequestParam(required = false) Integer interval,
                                    @RequestParam(required = false) Integer since) {
        return krakenApiService.getOHLCData(pair, interval, since);
    }

    @GetMapping("/depth")
    public String getOrderBook(@RequestParam String pair,
                                     @RequestParam(required = false) Integer count) {
        return krakenApiService.getOrderBook(pair, count);
    }

    @GetMapping("/trades")
    public String getRecentTrades(@RequestParam String pair,
                                        @RequestParam(required = false) String since,
                                        @RequestParam(required = false) Integer count) {
        return krakenApiService.getRecentTrades(pair, since, count);
    }
}
