package com.cointrade.terminal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TickerResponse {

    @JsonProperty("result")
    private Map<String, TickerInfo> result;

    public Map<String, TickerInfo> getResult() {
        return result;
    }

    public void setResult(Map<String, TickerInfo> result) {
        this.result = result;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TickerInfo {

        @JsonProperty("c")
        private List<String> lastTrade;

        public List<String> getLastTrade() {
            return lastTrade;
        }

        public void setLastTrade(List<String> lastTrade) {
            this.lastTrade = lastTrade;
        }
    }
}

