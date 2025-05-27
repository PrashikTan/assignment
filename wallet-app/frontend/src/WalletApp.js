
import React, { useState, useEffect } from 'react';
import axios from 'axios';

export default function WalletApp() {
  const userId = 1;
  const [balance, setBalance] = useState(0);
  const [amount, setAmount] = useState(0);
  const [transactions, setTransactions] = useState([]);

  const fetchBalance = async () => {
    const res = await axios.get(`/api/wallet/${userId}/balance`);
    setBalance(res.data);
  };

  const fetchTransactions = async () => {
    const res = await axios.get(`/api/wallet/${userId}/transactions`);
    setTransactions(res.data);
  };

  const handleAdd = async () => {
    await axios.post(`/api/wallet/${userId}/add`, null, { params: { amount } });
    fetchBalance();
    fetchTransactions();
  };

  const handleWithdraw = async () => {
    await axios.post(`/api/wallet/${userId}/withdraw`, null, { params: { amount } });
    fetchBalance();
    fetchTransactions();
  };

  useEffect(() => {
    fetchBalance();
    fetchTransactions();
  }, []);

  return (
    <div className="p-4 max-w-md mx-auto">
      <h1 className="text-xl font-bold mb-4">Wallet Dashboard</h1>
      <div className="mb-2">Current Balance: ₹{balance}</div>
      <input
        type="number"
        placeholder="Enter amount"
        value={amount}
        onChange={e => setAmount(Number(e.target.value))}
        className="border rounded px-2 py-1 mb-2 w-full"
      />
      <div className="flex gap-2 mb-4">
        <button onClick={handleAdd} className="bg-green-500 text-white px-4 py-2 rounded">Add Money</button>
        <button onClick={handleWithdraw} className="bg-red-500 text-white px-4 py-2 rounded">Withdraw Money</button>
      </div>
      <h2 className="text-lg font-semibold mb-2">Transaction History</h2>
      <ul className="border rounded p-2">
        {transactions.map(txn => (
          <li key={txn.id} className="border-b py-1 flex justify-between">
            <span>{txn.type}</span>
            <span>₹{txn.amount}</span>
            <span>{new Date(txn.timestamp).toLocaleString()}</span>
          </li>
        ))}
      </ul>
    </div>
  );
}
