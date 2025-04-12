import React from "react";
interface Props {
  members: { id: string; name: string; email: string }[];
}

export default function MemberList({ members }: Props) {
  return (
    <div>
      <h2 className="text-xl font-semibold mb-2">Members</h2>
      <ul className="list-disc list-inside">
        {members.map((m) => (
          <li key={m.id}>
            {m.name} ({m.email})
          </li>
        ))}
      </ul>
    </div>
  );
}
