import './componentStyles.css'

export default function OrderBook({ orders = [], onCancel = () => {} }) {
  return (
    <div className="card orderbook">
      <h2>Order Book</h2>
      {orders.length === 0 ? (
        <p className="muted">No open orders</p>
      ) : (
        <table className="orders-table">
          <thead>
            <tr>
              <th>Side</th>
              <th>Price</th>
              <th>Amount</th>
              <th>Age</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {orders.map((o) => (
              <tr key={o.id}>
                <td>
                  <span className={`badge ${o.side === 'buy' ? 'buy' : 'sell'}`}>{o.side}</span>
                </td>
                <td>${o.price}</td>
                <td>{o.amount}</td>
                <td>{Math.round((Date.now() - o.createdAt) / 1000)}s</td>
                <td>
                  {o.status === 'fulfilled' ? (
                    <span className="badge" style={{ background: '#10b981', color: 'white' }}>
                      Fulfilled
                    </span>
                  ) : (
                    <button className="btn small" onClick={() => onCancel(o.id)} aria-label={`cancel-${o.id}`}>
                      Cancel
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  )
}

