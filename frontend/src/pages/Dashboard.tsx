import React from "react";
import { useEffect, useState } from "react";
import { getCurrentUser } from "../api/user";
import { getMyGroups } from "../api/groups";

export default function Dashboard() {
  const [user, setUser] = useState<{ name: string } | null>(null);
  const [groups, setGroups] = useState<any[]>([]);

  useEffect(() => {
    const fetchData = async () => {
      const userData = await getCurrentUser();
      setUser(userData);

      const groupData = await getMyGroups();
      setGroups(groupData);
    };

    fetchData();
  }, []);

  return (
    <div className="p-8">
      <h1 className="text-2xl font-bold mb-4">Welcome, {user?.name}</h1>

      <h2 className="text-xl font-semibold mb-2">Your Groups</h2>
      <div className="space-y-2">
        {groups.map((group) => (
          <div key={group.id} className="bg-white rounded shadow p-4">
            <h3 className="text-lg font-bold">{group.name}</h3>
            <p className="text-sm text-gray-500">
              Invite Code: {group.inviteCode}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
}
