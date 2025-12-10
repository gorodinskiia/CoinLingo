// Simple market mock to simulate a ticker API
let basePrices = {
  BTCUSD: 60000,
  ETHUSD: 4000,
  XRPUSD: 0.5,
}

// Store previous prices for change calculation
let previousPrices = { ...basePrices }

// Store 24h data
let highPrices = { ...basePrices }
let lowPrices = { ...basePrices }

export async function getTicker(symbol = 'BTCUSD') {
  // simulate network latency
  await new Promise((r) => setTimeout(r, 120))
  const base = basePrices[symbol] ?? 100

  // small random drift
  const drift = (Math.random() - 0.5) * base * 0.01
  const price = Math.max(0.0001, Math.round((base + drift) * 100) / 100)

  // Update base price for next call
  basePrices[symbol] = price

  // Update 24h high/low
  if (!highPrices[symbol] || price > highPrices[symbol]) highPrices[symbol] = price
  if (!lowPrices[symbol] || price < lowPrices[symbol]) lowPrices[symbol] = price

  // Calculate change percentage
  const previous = previousPrices[symbol] ?? price
  const change = ((price - previous) / previous) * 100

  // Generate random volume
  const volume = Math.round(Math.random() * 1000 + 100)

  return {
    symbol,
    price,
    high: highPrices[symbol],
    low: lowPrices[symbol],
    volume,
    change: Math.round(change * 100) / 100,
    ts: Date.now()
  }
}

// utility to gently update base for demo purposes
export function bumpPrice(symbol, delta) {
  if (basePrices[symbol] != null) {
    previousPrices[symbol] = basePrices[symbol]
    basePrices[symbol] += delta
  }
}

// Reset 24h high/low (useful for demos)
export function reset24hData(symbol) {
  if (symbol) {
    highPrices[symbol] = basePrices[symbol]
    lowPrices[symbol] = basePrices[symbol]
    previousPrices[symbol] = basePrices[symbol]
  } else {
    // Reset all
    highPrices = { ...basePrices }
    lowPrices = { ...basePrices }
    previousPrices = { ...basePrices }
  }
}

// Get all available symbols
export function getAvailableSymbols() {
  return Object.keys(basePrices)
}

