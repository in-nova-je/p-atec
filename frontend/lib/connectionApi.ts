"use server";

import { apiFetch } from "./api";

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
  return apiFetch(`/connections?${params.toString()}`, {
    method: "POST",
  });
}
