import WalletDisplay from './Wallet/WalletDisplay'
import './componentStyles.css'

export default function Instructions({ streak = 0, onReset = () => {} }) {
  return (
    <aside className="card instructions">
      <h2>How to use</h2>
      <p>
        Start with $5,000 virtual credits. Buy and sell crypto assets to simulate trading and learn the market!
      </p>

      <h3>Daily Streak</h3>
      <div className="streak">{streak} day(s)</div>
      <button className="btn small" onClick={onReset}>Reset Streak</button>

      <hr style={{ margin: '1rem 0' }} />

      <WalletDisplay />

      <hr style={{ margin: '1rem 0' }} />

      <ol>
        <li>Check the Market price.</li>
        <li>Select asset and amount in Order Panel.</li>
        <li>Click Buy or Sell to execute.</li>
        <li>Watch your portfolio grow!</li>
      </ol>
    </aside>
  )
}

