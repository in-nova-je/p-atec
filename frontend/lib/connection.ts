"use server";

import { apiFetchServer } from "./apiServer";

export async function createConnection(
  userId: number,
  enterpriseId: number,
  isInternshipNoJob: boolean,
  classname: string,
) {
  const params = new URLSearchParams({
    userid: String(userId),
    enterpriseid: String(enterpriseId),
    isInternshipNoJob: String(isInternshipNoJob),
    classname,
  });
  return await apiFetchServer(`/connections?${params.toString()}`, {
    method: "POST",
  });
}
