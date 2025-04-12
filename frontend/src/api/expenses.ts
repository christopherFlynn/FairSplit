import api from "./axios";

export const getGroupExpenses = async (id: string) => {
  const res = await api.get(`/expenses/group/${id}`);
  return res.data;
};

export const getGroupBalances = async (id: string) => {
  const res = await api.get(`/groups/${id}/balances`);
  return res.data;
};

export const getGroupSettlements = async (id: string) => {
  const res = await api.get(`/groups/${id}/settlements`);
  return res.data;
};

export const addExpense = async (payload: {
  title: string;
  amount: number;
  groupId: string;
  splits: { userId: string; amount: number }[];
}) => {
  const res = await api.post("/expenses", payload);
  return res.data;
};
