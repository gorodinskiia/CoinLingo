import './componentStyles.css'

export default function Instructions({ streak = 0, onReset = () => {} }) {
  return (
    <aside className="card instructions">
      <h2>How to use</h2>
      <p>
        This area will host short instructional materials and tips for using the trading terminal. Use the Order Panel to place mock orders. Orders appear in the Order Book where you can cancel them.
      </p>
      <h3>Streak</h3>
      <div className="streak">{streak} day(s)</div>
      <button className="btn small" onClick={onReset}>Reset Streak</button>
      <hr />
      <ol>
        <li>Check the Market price.</li>
        <li>Place a buy or sell in the Order Panel.</li>
        <li>Manage open orders in the Order Book.</li>
      </ol>
    </aside>
  )
}

