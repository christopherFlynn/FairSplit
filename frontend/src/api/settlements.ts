import api from "./axios";

export const markSettlementPaid = async (
  groupId: string,
  from: string,
  to: string
) => {
  await api.post(`/settlements/mark-paid`, {
    from,
    to,
  });
};
