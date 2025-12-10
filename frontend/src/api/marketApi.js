const API_BASE_URL = 'http://localhost:8080/api/market';

export async function getMarketData(pair) {
  try {
    const response = await fetch(`${API_BASE_URL}/data?pair=${pair}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    // The Kraken API has a specific structure, so we need to parse it.
    // Kraken normalizes pair names (e.g., XBTUSD -> XXBTZUSD), so we get the first key
    const result = data.result;
    if (!result || Object.keys(result).length === 0) {
      throw new Error('No data returned from API');
    }
    // Get the first (and only) pair from the result
    const pairKey = Object.keys(result)[0];
    const tickerData = result[pairKey];

    if (!tickerData || !tickerData.c || !tickerData.c[0]) {
      throw new Error('Invalid ticker data structure');
    }

    const price = tickerData.c[0];
    return { price };
  } catch (error) {
    console.error("Could not fetch market data:", error);
    throw error;
  }
}

