import { useEffect, useState } from 'react'
import { getMarketData } from '../api/marketApi'
import './componentStyles.css'

export default function MarketData({ symbol = 'XBTUSD' }) {
  const [price, setPrice] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    let mounted = true
    async function fetch() {
      setLoading(true)
      try {
        const res = await getMarketData(symbol)
        if (mounted) setPrice(res.price)
      } catch (e) {
        setError(String(e))
      } finally {
        if (mounted) setLoading(false)
      }
    }
    fetch()
    const iv = setInterval(fetch, 5000)
    return () => {
      mounted = false
      clearInterval(iv)
    }
  }, [symbol])

  return (
    <div className="card market">
      <h2>Market</h2>
      <div className="market-row">
        <div className="market-symbol">{symbol}</div>
        <div className="market-price">
          {loading ? 'Loading...' : error ? `Error: ${error}` : `$${price}`}
        </div>
      </div>
      <p className="muted">Live feed from Kraken</p>
    </div>
  )
}

