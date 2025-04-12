import React from "react";
import { useState } from "react";
import { addExpense } from "../api/expenses";
import Button from "./Button";

interface Props {
  groupId: string;
  members: { id: string; name: string }[];
  onAdd: () => void;
}

export default function AddExpenseForm({ groupId, members, onAdd }: Props) {
  const [title, setTitle] = useState("");
  const [amount, setAmount] = useState("");
  const [splits, setSplits] = useState(
    members.map((m) => ({ userId: m.id, amount: "" }))
  );

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    await addExpense({
      title,
      amount: parseFloat(amount),
      groupId,
      splits: splits.map((s) => ({
        userId: s.userId,
        amount: parseFloat(s.amount || "0"),
      })),
    });

    setTitle("");
    setAmount("");
    setSplits(members.map((m) => ({ userId: m.id, amount: "" })));
    onAdd();
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-2 mt-4">
      <h3 className="font-bold">Add Expense</h3>
      <input
        type="text"
        placeholder="Title"
        className="w-full p-2 border rounded"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />
      <input
        type="number"
        step="0.01"
        placeholder="Total Amount"
        className="w-full p-2 border rounded"
        value={amount}
        onChange={(e) => setAmount(e.target.value)}
      />

      {members.map((member, idx) => (
        <div key={member.id} className="flex items-center gap-2">
          <span className="w-24 text-sm">{member.name}</span>
          <input
            type="number"
            step="0.01"
            className="flex-1 p-1 border rounded"
            placeholder="Amount"
            value={splits[idx].amount}
            onChange={(e) => {
              const newSplits = [...splits];
              newSplits[idx].amount = e.target.value;
              setSplits(newSplits);
            }}
          />
        </div>
      ))}

      <Button
        type="submit"
        className="bg-purple-600 text-white px-4 py-2 rounded hover:bg-purple-700"
      >
        Add Expense
      </Button>
    </form>
  );
}
