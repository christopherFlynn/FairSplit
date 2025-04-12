import React from "react";
interface Props {
  balances: { userId: string; balance: number }[];
  members: { id: string; name: string }[];
}

export default function Balances({ balances, members }: Props) {
  return (
    <div>
      <h2 className="text-xl font-semibold mt-6 mb-2">Balances</h2>
      {balances.map((b) => (
        <p key={b.userId}>
          {members.find((m) => m.id === b.userId)?.name}:{" "}
          <span
            className={
              b.balance > 0
                ? "text-green-600"
                : b.balance < 0
                ? "text-red-600"
                : "text-gray-600"
            }
          >
            ${b.balance.toFixed(2)}
          </span>
        </p>
      ))}
    </div>
  );
}
