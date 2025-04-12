import React from "react";
interface Props {
  expenses: { id: string; title: string; amount: number }[];
}

export default function ExpenseList({ expenses }: Props) {
  return (
    <div>
      <h2 className="text-xl font-semibold mb-2">Expenses</h2>
      {expenses.length === 0 ? (
        <p className="text-gray-500">No expenses yet</p>
      ) : (
        <ul className="space-y-2">
          {expenses.map((exp) => (
            <li key={exp.id} className="bg-white p-3 rounded shadow">
              <p className="font-semibold">{exp.title}</p>
              <p className="text-sm text-gray-500">${exp.amount}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}
