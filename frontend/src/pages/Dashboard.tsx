import React from "react";
import { useEffect, useState } from "react";
import { getCurrentUser } from "../api/user";
import { getMyGroups } from "../api/groups";
import GroupCard from "../components/GroupCard";
import CreateGroupForm from "../components/CreateGroupForm";
import JoinGroupForm from "../components/JoinGroupForm";

export default function Dashboard() {
  const [user, setUser] = useState<{ name: string } | null>(null);
  const [groups, setGroups] = useState<any[]>([]);

  const fetchGroups = async () => {
    const groupData = await getMyGroups();
    setGroups(groupData);
  };

  useEffect(() => {
    const fetchData = async () => {
      const userData = await getCurrentUser();
      setUser(userData);
      await fetchGroups();
    };

    fetchData();
  }, []);

  return (
    <div className="space-y-8">
      <div>
        <h1 className="text-2xl font-bold text-gray-800">
          Welcome, {user?.name}
        </h1>
        <p className="text-gray-500">Manage your expense groups below</p>
      </div>

      <div className="grid md:grid-cols-2 gap-6">
        <div className="bg-white p-4 rounded shadow">
          <h2 className="font-semibold mb-2">Create Group</h2>
          <CreateGroupForm onCreate={fetchGroups} />
        </div>

        <div className="bg-white p-4 rounded shadow">
          <h2 className="font-semibold mb-2">Join Group</h2>
          <JoinGroupForm onJoin={fetchGroups} />
        </div>
      </div>

      <div>
        <h2 className="text-xl font-semibold mb-4">Your Groups</h2>
        {groups.length === 0 ? (
          <p className="text-gray-500">You're not in any groups yet.</p>
        ) : (
          <div className="grid sm:grid-cols-2 gap-4">
            {groups.map((group) => (
              <GroupCard key={group.id} group={group} />
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
