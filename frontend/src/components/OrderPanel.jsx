import { useState } from 'react'
import './componentStyles.css'

export default function OrderPanel({ onPlace = () => {} }) {
  const [side, setSide] = useState('buy')
  const [price, setPrice] = useState('')
  const [amount, setAmount] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  async function submit(e) {
    e.preventDefault()
    setError(null)
    if (!price || Number(price) <= 0) return setError('Price must be > 0')
    if (!amount || Number(amount) <= 0) return setError('Amount must be > 0')
    setLoading(true)
    try {
      await onPlace({ side, price: Number(price), amount: Number(amount) })
      setPrice('')
      setAmount('')
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
            Price
            <input value={price} onChange={(e) => setPrice(e.target.value)} placeholder="e.g. 60000" />
          </label>
        </div>
        <div className="form-row">
          <label>
            Amount
            <input value={amount} onChange={(e) => setAmount(e.target.value)} placeholder="e.g. 0.01" />
          </label>
        </div>
        {error && <div className="error">{error}</div>}
        <div className="form-row actions">
          <button className="btn" type="submit" disabled={loading}>{loading ? 'Placing...' : 'Place Order'}</button>
        </div>
      </form>
    </div>
  )
}

