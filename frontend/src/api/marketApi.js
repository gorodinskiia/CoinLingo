const API_BASE_URL = 'http://localhost:8080/api/market';

export async function getMarketData(pair) {
  try {
    const response = await fetch(`${API_BASE_URL}/data?pair=${pair}`);
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    const data = await response.json();
    // The Kraken API has a specific structure, so we need to parse it.
    // The actual ticker info is in result[pair].c[0]
    const result = data.result;
    const price = result[pair].c[0];
    return { price };
  } catch (error) {
    console.error("Could not fetch market data:", error);
    throw error;
  }
}

