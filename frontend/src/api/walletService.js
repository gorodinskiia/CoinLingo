const API_BASE_URL = import.meta.env.VITE_API_URL || 'http://localhost:8080/api'

export async function getWallet(username) {
  try {
    const response = await fetch(`${API_BASE_URL}/wallet/${username}`)
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    return await response.json()
  } catch (error) {
    console.error('Error fetching wallet:', error)
    throw error
  }
}

export async function executeTrade(username, tradeRequest) {
  try {
    const response = await fetch(`${API_BASE_URL}/wallet/${username}/trade`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(tradeRequest)
    })

    const data = await response.json()

    if (!data.success) {
      throw new Error(data.error || 'Trade execution failed')
    }

    return data
  } catch (error) {
    console.error('Error executing trade:', error)
    throw error
  }
}

export async function resetWallet(username) {
  try {
    const response = await fetch(`${API_BASE_URL}/wallet/${username}/reset`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      }
    })

    const data = await response.json()

    if (!data.success) {
      throw new Error(data.error || 'Wallet reset failed')
    }

    return data.wallet
  } catch (error) {
    console.error('Error resetting wallet:', error)
    throw error
  }
}

export async function getTradeHistory(username) {
  try {
    const response = await fetch(`${API_BASE_URL}/wallet/${username}/history`)
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`)
    }
    return await response.json()
  } catch (error) {
    console.error('Error fetching trade history:', error)
    throw error
  }
}

export const config = {
  apiBaseUrl: API_BASE_URL
}

