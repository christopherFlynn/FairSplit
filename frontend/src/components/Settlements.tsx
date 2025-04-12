import React from "react";
import { markSettlementPaid } from "../api/settlements";

interface Props {
  settlements: { from: string; to: string; amount: number; paid?: boolean }[];
  members: { id: string; name: string }[];
  currentUserId: string;
  groupId: string;
  onRefresh: () => void;
}

export default function Settlements({
  settlements,
  members,
  currentUserId,
  groupId,
  onRefresh,
}: Props) {
  const getName = (id: string) =>
    members.find((m) => m.id === id)?.name || "Unknown";

  const handleMarkPaid = async (from: string, to: string) => {
    try {
      await markSettlementPaid(groupId, from, to);
      onRefresh();
    } catch (err) {
      alert("Failed to mark as paid");
    }
  };

  return (
    <div>
      <h2 className="text-xl font-semibold mt-6 mb-2">Settle Up</h2>
      {settlements.length === 0 ? (
        <p className="text-gray-500">All settled!</p>
      ) : (
        <ul className="space-y-2">
          {settlements.map((s, i) => {
            const fromName = getName(s.from);
            const toName = getName(s.to);

            return (
              <li
                key={i}
                className="bg-white p-4 rounded shadow flex justify-between items-center"
              >
                <div>
                  {fromName} pays {toName}:{" "}
                  <span className="text-blue-600">${s.amount.toFixed(2)}</span>
                </div>

                {s.from === currentUserId && (
                  <button
                    onClick={() => handleMarkPaid(s.from, s.to)}
                    className="text-sm text-green-600 hover:underline"
                  >
                    Mark as Paid
                  </button>
                )}
              </li>
            );
          })}
        </ul>
      )}
    </div>
  );
}
