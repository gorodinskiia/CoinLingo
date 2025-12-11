import { useWallet } from '../../contexts/WalletContext'
import { useTicker } from '../../hooks/useTicker'
import './WalletDisplay.css'

export default function WalletDisplay() {
  const { wallet, dispatch } = useWallet()
  const { ticker: btcTicker } = useTicker('BTCUSD', 3000)
  const { ticker: ethTicker } = useTicker('ETHUSD', 3000)
  const { ticker: xrpTicker } = useTicker('XRPUSD', 3000)

  const prices = {
    BTC: btcTicker?.price || 0,
    ETH: ethTicker?.price || 0,
    XRP: xrpTicker?.price || 0
  }

  // Calculate portfolio value
  const portfolioValue =
    wallet.balances.USD +
    wallet.balances.BTC * prices.BTC +
    wallet.balances.ETH * prices.ETH +
    wallet.balances.XRP * prices.XRP

  const profitLoss = portfolioValue - 5000
  const profitLossPercent = (profitLoss / 5000) * 100

  function handleReset() {
    if (confirm('Reset wallet to $5,000 USD? This will clear all balances and transactions.')) {
      dispatch({ type: 'RESET_WALLET' })
    }
  }

  return (
    <div className="wallet-display">
      <h3>Portfolio</h3>

      <div className="portfolio-summary">
        <div className="total-value">
          <span className="label">Total Value</span>
          <span className="value">${portfolioValue.toFixed(2)}</span>
        </div>
        <div className={`profit-loss ${profitLoss >= 0 ? 'profit' : 'loss'}`}>
          <span className="label">P&L</span>
          <span className="value">
            {profitLoss >= 0 ? '+' : ''}${profitLoss.toFixed(2)} ({profitLossPercent.toFixed(2)}%)
          </span>
        </div>
      </div>

      <div className="balances">
        <BalanceRow asset="USD" amount={wallet.balances.USD} price={1} symbol="$" />
        <BalanceRow asset="BTC" amount={wallet.balances.BTC} price={prices.BTC} symbol="₿" />
        <BalanceRow asset="ETH" amount={wallet.balances.ETH} price={prices.ETH} symbol="Ξ" />
        <BalanceRow asset="XRP" amount={wallet.balances.XRP} price={prices.XRP} symbol="✕" />
      </div>

      <button className="btn small" onClick={handleReset} style={{ marginTop: '1rem', width: '100%' }}>
        Reset Wallet
      </button>
    </div>
  )
}

function BalanceRow({ asset, amount, price, symbol }) {
  const usdValue = amount * price

  return (
    <div className="balance-row">
      <div className="asset-info">
        <span className="symbol">{symbol}</span>
        <span className="asset-name">{asset}</span>
      </div>
      <div className="amounts">
        <span className="amount">
          {asset === 'USD' ? `$${amount.toFixed(2)}` : amount.toFixed(8)}
        </span>
        {asset !== 'USD' && (
          <span className="usd-value">${usdValue.toFixed(2)}</span>
        )}
      </div>
    </div>
  )
}

