
import * as marketMock from '../mocks/marketMock'

const USE_MOCK = true // Set to false when backend is ready
const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api'


export async function getTicker(symbol = 'BTCUSD') {
  if (USE_MOCK) {
    return marketMock.getTicker(symbol)
  }

  // Real API implementation /*
  try {
    const response = await fetch(`${API_BASE_URL}/ticker/${symbol}`)
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    return await response.json()
  } catch (error) {
    console.error('Error fetching ticker:', error)
    throw error
  }
  */

  throw new Error('Real API not yet implemented')
}

export function getAvailableSymbols() {
  if (USE_MOCK) {
    return marketMock.getAvailableSymbols()
  }

  return ['BTCUSD', 'ETHUSD', 'XRPUSD'] // Default fallback
}

export function bumpPrice(symbol, delta) {
  if (USE_MOCK) {
    return marketMock.bumpPrice(symbol, delta)
  }
  console.warn('bumpPrice is only available in mock mode')
}

export function reset24hData(symbol) {
  if (USE_MOCK) {
    return marketMock.reset24hData(symbol)
  }
  console.warn('reset24hData is only available in mock mode')
}

export async function fetchFromBackend(endpoint, options = {}) {
  const url = `${API_BASE_URL}${endpoint}`
  const defaultOptions = {
    headers: {
      'Content-Type': 'application/json',
      // Add authentication headers here when needed
      // 'Authorization': `Bearer ${token}`
    },
    ...options
  }

  const response = await fetch(url, defaultOptions)

  if (!response.ok) {
    const error = await response.text()
    throw new Error(`API Error: ${response.status} - ${error}`)
  }

  return response.json()
}

export const config = {
  useMock: USE_MOCK,
  apiBaseUrl: API_BASE_URL
}

