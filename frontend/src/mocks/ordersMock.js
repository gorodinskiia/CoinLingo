// Simple in-memory orders mock
let orders = [
  // { id: '1', side: 'buy', price: 60000, amount: 0.01, createdAt: Date.now() - 100000, status: 'pending' },
  // { id: '2', side: 'sell', price: 60500, amount: 0.02, createdAt: Date.now() - 50000, status: 'pending' },
]
let nextId = 3

export async function getOrders() {
  await new Promise((r) => setTimeout(r, 80))
  return orders.slice().sort((a, b) => b.createdAt - a.createdAt)
}

export async function placeOrder({ side, price, amount, asset, status = 'fulfilled' }) {
  await new Promise((r) => setTimeout(r, 120))
  const order = {
    id: String(nextId++),
    side,
    asset: asset || 'BTC',
    price: Number(price),
    amount: Number(amount),
    createdAt: Date.now(),
    status // 'pending' or 'fulfilled'
  }
  orders.push(order)
  return order
}

export async function cancelOrder(id) {
  await new Promise((r) => setTimeout(r, 80))
  orders = orders.filter((o) => o.id !== id)
}

export async function fulfillOrder(id) {
  await new Promise((r) => setTimeout(r, 80))
  const order = orders.find((o) => o.id === id)
  if (order) {
    order.status = 'fulfilled'
  }
  return order
}

export function resetMock() {
  orders = []
  nextId = 1
}

