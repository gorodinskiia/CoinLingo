// Base Strategy
export class OrderExecutionStrategy {
  validate(order, wallet, currentPrice) {
    throw new Error('validate() must be implemented')
  }

  execute(order, wallet, currentPrice) {
    throw new Error('execute() must be implemented')
  }
}

// Market Order Strategy - Execute immediately
export class MarketOrderStrategy extends OrderExecutionStrategy {
  validate(order, wallet, currentPrice) {
    const { side, asset, amount } = order
    const total = amount * currentPrice

    if (side === 'buy') {
      if (wallet.balances.USD < total) {
        throw new Error(`Insufficient balance. Need $${total.toFixed(2)}, have $${wallet.balances.USD.toFixed(2)}`)
      }
    } else {
      if (wallet.balances[asset] < amount) {
        throw new Error(`Insufficient ${asset}. Need ${amount}, have ${wallet.balances[asset]}`)
      }
    }

    return true
  }

  execute(order, wallet, currentPrice) {
    this.validate(order, wallet, currentPrice)

    return {
      asset: order.asset,
      amount: order.amount,
      price: currentPrice
    }
  }
}

// Limit Order Strategy - Execute at specific price (future implementation)
export class LimitOrderStrategy extends OrderExecutionStrategy {
  validate(order, wallet, currentPrice) {
    // Similar validation but check limit price
    return true
  }

  execute(order, wallet, currentPrice) {
    // Execute when price reaches limit
    return null // Pending until condition met
  }
}

// Strategy Factory
export function getExecutionStrategy(orderType) {
  switch (orderType) {
    case 'market':
      return new MarketOrderStrategy()
    case 'limit':
      return new LimitOrderStrategy()
    default:
      return new MarketOrderStrategy()
  }
}

