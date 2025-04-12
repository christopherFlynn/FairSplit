import api from "./axios";

export const getMyGroups = async () => {
  const res = await api.get("/groups");
  return res.data;
};

export const createGroup = async (name: string) => {
  const res = await api.post("/groups", { name });
  return res.data;
};

export const joinGroup = async (inviteCode: string) => {
  const res = await api.post("/groups/join", { inviteCode });
  return res.data;
};

export const getGroupById = async (id: string) => {
  const res = await api.get(`/groups/${id}`);
  return res.data;
};

export const getGroupMembers = async (id: string) => {
  const res = await api.get(`/groups/${id}/members`);
  return res.data;
};
