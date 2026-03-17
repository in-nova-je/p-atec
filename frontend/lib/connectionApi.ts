"use server";

import { apiFetchServer } from "./apiServer";

export function createConnection(
  userId: number,
  enterpriseId: number,
  isInternshipNoJob: boolean,
  classname: string,
) {
  const params = new URLSearchParams({
    userId: String(userId),
    enterpriseId: String(enterpriseId),
    isInternshipNoJob: String(isInternshipNoJob),
    classname,
  });
  return apiFetchServer(`/connections?${params.toString()}`, {
    method: "POST",
  });
}
