import api from "./axios";

export const getMyGroups = async () => {
  const res = await api.get("/groups");
  return res.data;
};
