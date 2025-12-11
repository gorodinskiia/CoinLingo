import { useState, useEffect } from 'react'
import { getTicker, bumpPrice, reset24hData, getAvailableSymbols } from '../mocks/marketMock'

export default function TickerDemo() {
  const [symbol, setSymbol] = useState('BTCUSD')
  const [ticker, setTicker] = useState(null)
  const [history, setHistory] = useState([])

  useEffect(() => {
    let mounted = true

    async function fetchTicker() {
      const data = await getTicker(symbol)
      if (mounted) {
        setTicker(data)
        setHistory(prev => [...prev.slice(-9), data.price])
      }
    }

    fetchTicker()
    const interval = setInterval(fetchTicker, 2000)

    return () => {
      mounted = false
      clearInterval(interval)
    }
  }, [symbol])

  const handleBump = (delta) => {
    bumpPrice(symbol, delta)
  }

  const handleReset = () => {
    reset24hData(symbol)
    setHistory([])
  }

  if (!ticker) return <div>Loading...</div>

  return (
    <div style={{ padding: '2rem', maxWidth: '800px', margin: '0 auto' }}>
      <h1>Ticker Demo</h1>

      <div style={{ marginBottom: '2rem' }}>
        <label>
          Symbol:{' '}
          <select value={symbol} onChange={(e) => setSymbol(e.target.value)}>
            {getAvailableSymbols().map(sym => (
              <option key={sym} value={sym}>{sym}</option>
            ))}
          </select>
        </label>
      </div>

      <div style={{
        border: '1px solid #ccc',
        borderRadius: '8px',
        padding: '1.5rem',
        marginBottom: '2rem'
      }}>
        <h2 style={{ marginTop: 0 }}>{ticker.symbol}</h2>

        <div style={{ fontSize: '3rem', fontWeight: 'bold', marginBottom: '1rem' }}>
          ${ticker.price.toLocaleString()}
        </div>

        <div style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(2, 1fr)',
          gap: '1rem',
          marginBottom: '1rem'
        }}>
          <div>
            <strong>Change:</strong>{' '}
            <span style={{ color: ticker.change >= 0 ? 'green' : 'red' }}>
              {ticker.change >= 0 ? '+' : ''}{ticker.change}%
            </span>
          </div>
          <div><strong>Volume:</strong> {ticker.volume}</div>
          <div><strong>High:</strong> ${ticker.high.toLocaleString()}</div>
          <div><strong>Low:</strong> ${ticker.low.toLocaleString()}</div>
        </div>

        <div style={{ fontSize: '0.75rem', color: '#666' }}>
          Last updated: {new Date(ticker.ts).toLocaleTimeString()}
        </div>
      </div>

      <div style={{ marginBottom: '2rem' }}>
        <h3>Price History (last 10 ticks)</h3>
        <div style={{
          display: 'flex',
          gap: '0.5rem',
          alignItems: 'flex-end',
          height: '100px',
          borderBottom: '1px solid #ccc'
        }}>
          {history.map((price, i) => {
            const max = Math.max(...history)
            const min = Math.min(...history)
            const range = max - min || 1
            const height = ((price - min) / range) * 90 + 10

            return (
              <div
                key={i}
                style={{
                  flex: 1,
                  height: `${height}%`,
                  backgroundColor: '#3b82f6',
                  transition: 'height 0.3s'
                }}
                title={`$${price}`}
              />
            )
          })}
        </div>
      </div>

      <div>
        <h3>Demo Controls</h3>
        <div style={{ display: 'flex', gap: '0.5rem', flexWrap: 'wrap' }}>
          <button onClick={() => handleBump(symbol === 'XRPUSD' ? 0.01 : symbol === 'ETHUSD' ? 50 : 500)}>
            📈 Bump Up
          </button>
          <button onClick={() => handleBump(symbol === 'XRPUSD' ? -0.01 : symbol === 'ETHUSD' ? -50 : -500)}>
            📉 Bump Down
          </button>
          <button onClick={handleReset}>
            🔄 Reset 24h Data
          </button>
        </div>
      </div>

      <div style={{ marginTop: '2rem', padding: '1rem', backgroundColor: '#f5f5f5', borderRadius: '4px' }}>
        <h4>About This Demo</h4>
        <p>
          This demo showcases the mock ticker implementation. The price updates automatically
          every 2 seconds with small random variations. Use the controls to manually adjust
          prices or reset the 24h statistics.
        </p>
        <p>
          <strong>Price Bumps:</strong>
        </p>
        <ul>
          <li>BTCUSD: ±$500</li>
          <li>ETHUSD: ±$50</li>
          <li>XRPUSD: ±$0.01</li>
        </ul>
      </div>
    </div>
  )
}

