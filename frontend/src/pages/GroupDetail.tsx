import React from "react";
import { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";

import { getGroupById, getGroupMembers } from "../api/groups";
import {
  getGroupExpenses,
  getGroupBalances,
  getGroupSettlements,
} from "../api/expenses";

import MemberList from "../components/MemberList";
import ExpenseList from "../components/ExpenseList";
import AddExpenseForm from "../components/AddExpenseForm";
import Balances from "../components/Balances";
import Settlements from "../components/Settlements";

export default function GroupDetail() {
  const { id } = useParams();
  const [group, setGroup] = useState<any>(null);
  const [members, setMembers] = useState<any[]>([]);
  const [expenses, setExpenses] = useState<any[]>([]);
  const [balances, setBalances] = useState<any[]>([]);
  const [settlements, setSettlements] = useState<any[]>([]);
  const [user, setUser] = useState<any>(null);

  const fetchAll = async () => {
    if (!id) return;

    const g = await getGroupById(id);
    const m = await getGroupMembers(id);
    const e = await getGroupExpenses(id);
    const b = await getGroupBalances(id);
    const s = await getGroupSettlements(id);

    setGroup(g);
    setMembers(m);
    setExpenses(e);
    setBalances(b);
    setSettlements(s);
  };

  useEffect(() => {
    fetchAll();
  }, [id]);

  if (!group) return <p className="p-6">Loading group...</p>;

  return (
    <div className="p-8 space-y-8 max-w-2xl mx-auto">
      <Link
        to="/dashboard"
        className="text-blue-600 hover:underline block mb-2"
      >
        ← Back to Dashboard
      </Link>

      <div>
        <h1 className="text-3xl font-bold">{group.name}</h1>
        <p className="text-gray-500">Invite Code: {group.inviteCode}</p>
      </div>

      <MemberList members={members} />
      <ExpenseList expenses={expenses} />
      <AddExpenseForm groupId={id!} members={members} onAdd={fetchAll} />
      <Balances balances={balances} members={members} />
      <Settlements
        settlements={settlements}
        members={members}
        currentUserId={user?.id}
        groupId={id!}
        onRefresh={fetchAll}
      />
    </div>
  );
}
