import { useState } from 'react'
import { useWallet } from '../contexts/WalletContext'
import { useTicker } from '../hooks/useTicker'
import { TradingService } from '../services/TradingService'
import './componentStyles.css'

export default function OrderPanel({ onPlace = () => {} }) {
  const [side, setSide] = useState('buy')
  const [asset, setAsset] = useState('BTC')
  const [amount, setAmount] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)
  const [success, setSuccess] = useState(null)

  const { wallet, dispatch } = useWallet()
  const symbol = `${asset}USD`
  const { ticker } = useTicker(symbol, 3000)

  const currentPrice = ticker?.price || 0
  const total = amount ? (Number(amount) * currentPrice).toFixed(2) : '0.00'

  async function submit(e) {
    e.preventDefault()
    setError(null)
    setSuccess(null)

    if (!amount || Number(amount) <= 0) {
      return setError('Amount must be greater than 0')
    }

    if (!currentPrice) {
      return setError('Waiting for price data...')
    }

    setLoading(true)

    try {
      let result

      if (side === 'buy') {
        result = TradingService.executeBuyOrder(
          asset,
          amount,
          currentPrice,
          wallet,
          dispatch
        )
      } else {
        result = TradingService.executeSellOrder(
          asset,
          amount,
          currentPrice,
          wallet,
          dispatch
        )
      }

      if (result.success) {
        setSuccess(`${side.toUpperCase()} order executed! ${amount} ${asset} @ $${currentPrice.toLocaleString()}`)
        setAmount('')

        onPlace({
          side,
          asset,
          amount: Number(amount),
          price: currentPrice,
          status: 'fulfilled' // Mark as fulfilled since trade executed immediately
        })
          setTimeout(() => setSuccess(null), 3000)
      } else {
        setError(result.error)
      }
    } catch (e) {
      setError(String(e))
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="card orderpanel">
      <h2>Place Order</h2>
      <form onSubmit={submit} className="order-form">
        <div className="form-row">
          <label>
            Side
            <select value={side} onChange={(e) => setSide(e.target.value)}>
              <option value="buy">Buy</option>
              <option value="sell">Sell</option>
            </select>
          </label>
        </div>

        <div className="form-row">
          <label>
            Asset
            <select value={asset} onChange={(e) => setAsset(e.target.value)}>
              <option value="BTC">Bitcoin (BTC)</option>
              <option value="ETH">Ethereum (ETH)</option>
              <option value="XRP">Ripple (XRP)</option>
            </select>
          </label>
        </div>

        <div className="form-row">
          <label>
            Amount
            <input
              type="number"
              step="0.00000001"
              value={amount}
              onChange={(e) => setAmount(e.target.value)}
              placeholder={`e.g. 0.01`}
            />
          </label>
        </div>

        <div className="order-summary">
          <div className="summary-row">
            <span>Current Price:</span>
            <span className="value">${currentPrice.toLocaleString()}</span>
          </div>
          <div className="summary-row">
            <span>Total Cost:</span>
            <span className="value">${total}</span>
          </div>
          <div className="summary-row">
            <span>Available {side === 'buy' ? 'USD' : asset}:</span>
            <span className="value">
              {side === 'buy'
                ? `$${wallet.balances.USD.toFixed(2)}`
                : wallet.balances[asset].toFixed(8)
              }
            </span>
          </div>
        </div>

        {error && <div className="error">{error}</div>}
        {success && <div className="success">{success}</div>}

        <div className="form-row actions">
          <button
            className={`btn ${side === 'buy' ? 'btn-buy' : 'btn-sell'}`}
            type="submit"
            disabled={loading}
          >
            {loading ? 'Processing...' : `${side === 'buy' ? 'Buy' : 'Sell'} ${asset}`}
          </button>
        </div>
      </form>
    </div>
  )
}

