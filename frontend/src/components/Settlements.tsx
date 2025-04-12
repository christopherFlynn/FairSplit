import React from "react";
interface Props {
  settlements: { from: string; to: string; amount: number }[];
  members: { id: string; name: string }[];
}

export default function Settlements({ settlements, members }: Props) {
  return (
    <div>
      <h2 className="text-xl font-semibold mt-6 mb-2">Settle Up</h2>
      {settlements.length === 0 ? (
        <p className="text-gray-500">All settled!</p>
      ) : (
        settlements.map((s, i) => {
          const fromName =
            members.find((m) => m.id === s.from)?.name || "Someone";
          const toName = members.find((m) => m.id === s.to)?.name || "Someone";
          return (
            <p key={i}>
              {fromName} pays {toName}:{" "}
              <span className="text-blue-600">${s.amount.toFixed(2)}</span>
            </p>
          );
        })
      )}
    </div>
  );
}
