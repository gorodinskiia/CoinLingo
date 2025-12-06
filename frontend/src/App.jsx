import { useEffect, useState } from 'react'
import MarketData from './components/MarketData'
import OrderBook from './components/OrderBook'
import OrderPanel from './components/OrderPanel'
import Instructions from './components/Instructions'
import Login from './pages/Login'
import './App.css'
import './components/componentStyles.css'
import * as ordersMock from './mocks/ordersMock'

function App() {
  const [orders, setOrders] = useState([])
  const [streak, setStreak] = useState(0)
  const [loading, setLoading] = useState(true)
  const [auth, setAuth] = useState(() => {
    try { return JSON.parse(localStorage.getItem('auth') || 'null') } catch { return null }
  })

  useEffect(() => {
    let mounted = true
    async function load() {
      setLoading(true)
      const list = await ordersMock.getOrders()
      if (mounted) setOrders(list)
      setLoading(false)
    }
    load()
    return () => { mounted = false }
  }, [])

  useEffect(() => {
    if (auth) localStorage.setItem('auth', JSON.stringify(auth))
    else localStorage.removeItem('auth')
  }, [auth])

  async function handlePlace(order) {
    const placed = await ordersMock.placeOrder(order)
    setOrders((s) => [placed, ...s])
    setStreak((s) => s + 1)
  }

  async function handleCancel(id) {
    await ordersMock.cancelOrder(id)
    setOrders((s) => s.filter((o) => o.id !== id))
  }

  function resetStreak() {
    setStreak(0)
  }

  function handleLogin({ token, username }) {
    console.log('Login success, auth:', { token, username })
    setAuth({ token, username })
  }

  function handleLogout() {
    setAuth(null)
  }

  if (!auth) {
    return (
      <div className="app">
        <header className="app-header">
          <h1>CoinLingo Terminal (Frontend)</h1>
        </header>
        <main className="centered">
          <Login onLogin={handleLogin} />
        </main>
      </div>
    )
  }

  const tokenSnippet = auth && auth.token ? String(auth.token).slice(0, 12) + (String(auth.token).length > 12 ? '...' : '') : null

  return (
    <div className="app">
      <header className="app-header">
        <h1>CoinLingo Terminal (Frontend)</h1>
        <div className="nav">
          <span className="muted">Signed in as {auth.username}</span>
          {auth.token && <span className="muted" style={{marginLeft:8}}>Connected: <strong style={{marginLeft:6}}>{tokenSnippet}</strong></span>}
          <button className="btn small" onClick={handleLogout}>Sign out</button>
        </div>
      </header>
      <div className="app-grid">
        <Instructions streak={streak} onReset={resetStreak} />
        <main className="main-col">
          <MarketData />
          <div className="row-split">
            <OrderBook orders={orders} onCancel={handleCancel} />
            <OrderPanel onPlace={handlePlace} />
          </div>
          {loading && <p className="muted">Loading orders...</p>}
        </main>
      </div>
    </div>
  )
}

export default App
