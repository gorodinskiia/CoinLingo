// Simple market mock to simulate a ticker API
let basePrices = {
  BTCUSD: 60000,
  ETHUSD: 4000,
  XRPUSD: 0.5,
}

export async function getTicker(symbol = 'BTCUSD') {
  // simulate network latency
  await new Promise((r) => setTimeout(r, 120))
  const base = basePrices[symbol] ?? 100
  // small random drift
  const drift = (Math.random() - 0.5) * base * 0.01
  const price = Math.max(0.0001, Math.round((base + drift) * 100) / 100)
  return { symbol, price, ts: Date.now() }
}

// utility to gently update base for demo purposes
export function bumpPrice(symbol, delta) {
  if (basePrices[symbol] != null) basePrices[symbol] += delta
}

