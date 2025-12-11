import { createContext, useContext, useReducer, useEffect } from 'react'

const WalletContext = createContext()

const INITIAL_WALLET = {
  balances: {
    USD: 5000.00,
    BTC: 0,
    ETH: 0,
    XRP: 0
  },
  orders: [],
  transactions: []
}

function walletReducer(state, action) {
  switch (action.type) {
    case 'EXECUTE_BUY': {
      const { asset, amount, price } = action.payload
      const total = amount * price

      return {
        ...state,
        balances: {
          ...state.balances,
          USD: state.balances.USD - total,
          [asset]: state.balances[asset] + amount
        },
        transactions: [
          ...state.transactions,
          {
            id: Date.now(),
            type: 'BUY',
            asset,
            amount,
            price,
            total,
            timestamp: Date.now()
          }
        ]
      }
    }

    case 'EXECUTE_SELL': {
      const { asset, amount, price } = action.payload
      const total = amount * price

      return {
        ...state,
        balances: {
          ...state.balances,
          USD: state.balances.USD + total,
          [asset]: state.balances[asset] - amount
        },
        transactions: [
          ...state.transactions,
          {
            id: Date.now(),
            type: 'SELL',
            asset,
            amount,
            price,
            total,
            timestamp: Date.now()
          }
        ]
      }
    }

    case 'ADD_ORDER':
      return {
        ...state,
        orders: [...state.orders, action.payload]
      }

    case 'REMOVE_ORDER':
      return {
        ...state,
        orders: state.orders.filter(o => o.id !== action.payload)
      }

    case 'RESET_WALLET':
      return INITIAL_WALLET

    default:
      return state
  }
}

export function WalletProvider({ children }) {
  // Initialize from localStorage or use default
  const [state, dispatch] = useReducer(walletReducer, INITIAL_WALLET, (initial) => {
    try {
      const saved = localStorage.getItem('coinlingo_wallet')
      return saved ? JSON.parse(saved) : initial
    } catch {
      return initial
    }
  })

  // Persist to localStorage on changes
  useEffect(() => {
    localStorage.setItem('coinlingo_wallet', JSON.stringify(state))
  }, [state])

  return (
    <WalletContext.Provider value={{ wallet: state, dispatch }}>
      {children}
    </WalletContext.Provider>
  )
}

export function useWallet() {
  const context = useContext(WalletContext)
  if (!context) {
    throw new Error('useWallet must be used within WalletProvider')
  }
  return context
}

