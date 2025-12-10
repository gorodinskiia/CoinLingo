import { useState } from 'react'
import { useTicker } from '../hooks/useTicker'
import { getAvailableSymbols, bumpPrice } from '../mocks/marketMock'
import './componentStyles.css'

export default function MarketData({ symbol: initialSymbol = 'BTCUSD' }) {
  const [symbol, setSymbol] = useState(initialSymbol)
  const { ticker, loading, error } = useTicker(symbol, 3000)

  const availableSymbols = getAvailableSymbols()

  function handleBumpUp() {
    const delta = symbol === 'XRPUSD' ? 0.01 : symbol === 'ETHUSD' ? 50 : 500
    bumpPrice(symbol, delta)
  }

  function handleBumpDown() {
    const delta = symbol === 'XRPUSD' ? 0.01 : symbol === 'ETHUSD' ? 50 : 500
    bumpPrice(symbol, -delta)
  }

  return (
    <div className="card market">
      <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: '1rem' }}>
        <h2>Market Data</h2>
        <select
          value={symbol}
          onChange={(e) => setSymbol(e.target.value)}
          style={{ padding: '0.5rem', borderRadius: '4px', border: '1px solid #ccc' }}
        >
          {availableSymbols.map(sym => (
            <option key={sym} value={sym}>{sym}</option>
          ))}
        </select>
      </div>

      {loading && !ticker ? (
        <div className="market-row">
          <div className="muted">Loading...</div>
        </div>
      ) : error ? (
        <div className="market-row">
          <div className="error">Error: {error}</div>
        </div>
      ) : ticker ? (
        <>
          <div className="market-row" style={{ marginBottom: '1rem' }}>
            <div className="market-symbol" style={{ fontSize: '1.5rem', fontWeight: 'bold' }}>
              {ticker.symbol}
            </div>
            <div className="market-price" style={{ fontSize: '2rem', fontWeight: 'bold', color: ticker.change >= 0 ? '#10b981' : '#ef4444' }}>
              ${ticker.price.toLocaleString()}
            </div>
          </div>

          <div style={{ display: 'grid', gridTemplateColumns: '1fr 1fr', gap: '1rem', marginBottom: '1rem' }}>
            <div>
              <div className="muted" style={{ fontSize: '0.875rem' }}>24h Change</div>
              <div style={{ fontSize: '1.25rem', fontWeight: '600', color: ticker.change >= 0 ? '#10b981' : '#ef4444' }}>
                {ticker.change >= 0 ? '+' : ''}{ticker.change}%
              </div>
            </div>
            <div>
              <div className="muted" style={{ fontSize: '0.875rem' }}>24h Volume</div>
              <div style={{ fontSize: '1.25rem', fontWeight: '600' }}>{ticker.volume}</div>
            </div>
            <div>
              <div className="muted" style={{ fontSize: '0.875rem' }}>24h High</div>
              <div style={{ fontSize: '1.25rem', fontWeight: '600' }}>${ticker.high.toLocaleString()}</div>
            </div>
            <div>
              <div className="muted" style={{ fontSize: '0.875rem' }}>24h Low</div>
              <div style={{ fontSize: '1.25rem', fontWeight: '600' }}>${ticker.low.toLocaleString()}</div>
            </div>
          </div>

          <div style={{ display: 'flex', gap: '0.5rem', marginTop: '1rem' }}>
            <button className="btn small" onClick={handleBumpUp} style={{ flex: 1 }}>
              📈 Bump Up
            </button>
            <button className="btn small" onClick={handleBumpDown} style={{ flex: 1 }}>
              📉 Bump Down
            </button>
          </div>
        </>
      ) : null}

      <p className="muted" style={{ marginTop: '1rem', fontSize: '0.75rem' }}>
        Live mock feed — Kraken integration coming soon
      </p>
    </div>
  )
}

