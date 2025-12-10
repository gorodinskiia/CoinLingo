import { useEffect, useState } from 'react'
import { getTicker } from '../mocks/marketMock'

/**
 * Custom hook to fetch and auto-update ticker data
 * @param {string} symbol - Trading pair symbol (e.g., 'BTCUSD')
 * @param {number} interval - Refresh interval in milliseconds (default: 5000)
 */
export function useTicker(symbol = 'BTCUSD', interval = 5000) {
  const [ticker, setTicker] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    let mounted = true

    async function fetchTicker() {
      try {
        const data = await getTicker(symbol)
        if (mounted) {
          setTicker(data)
          setError(null)
        }
      } catch (e) {
        if (mounted) {
          setError(String(e))
        }
      } finally {
        if (mounted) {
          setLoading(false)
        }
      }
    }

    fetchTicker()
    const intervalId = setInterval(fetchTicker, interval)

    return () => {
      mounted = false
      clearInterval(intervalId)
    }
  }, [symbol, interval])

  return { ticker, loading, error }
}

