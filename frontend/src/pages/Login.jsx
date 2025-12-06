import { useState } from 'react'
import './../components/componentStyles.css'

export default function Login({ onLogin }) {
  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState(null)

  async function submit(e) {
    e.preventDefault()
    setError(null)
    if (!username || !password) return setError('username and password required')
    setLoading(true)
    try {
      // try to call backend login endpoint; fall back to mock if it fails
      const resp = await fetch('/api/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
      })

      if (resp.ok) {
        const data = await resp.json()
        onLogin({ token: data.token, username })
      } else {
        // server returned non-200 -> fallback mock
        console.warn('Login endpoint returned', resp.status)
        onLogin({ token: 'mock-token-' + Date.now(), username })
      }
    } catch (e) {
      // network error -> mock success for placeholder
      console.warn('Login network error, using mock token', e)
      onLogin({ token: 'mock-token-' + Date.now(), username })
    } finally {
      setLoading(false)
    }
  }

  return (
    <div className="card login-card">
      <h2>Sign In</h2>
      <form onSubmit={submit} className="order-form">
        <div className="form-row">
          <label>
            Username
            <input value={username} onChange={(e) => setUsername(e.target.value)} />
          </label>
        </div>
        <div className="form-row">
          <label>
            Password
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} />
          </label>
        </div>
        {error && <div className="error">{error}</div>}
        <div className="form-row actions">
          <button className="btn" type="submit" disabled={loading}>{loading ? 'Signing...' : 'Sign In'}</button>
        </div>
      </form>
      <p className="muted">This is a placeholder login page — real backend wiring will be added later.</p>
    </div>
  )
}

