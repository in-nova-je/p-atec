"use server";

import { apiFetchServer } from "./apiServer";
import { cookies } from "next/headers";
import { Enterprise } from "./types";

export async function getAllEnterprises() {
  const token = (await cookies()).get("token")?.value;

  const params = new URLSearchParams({
    pageSize: "1000",
  });

  return apiFetchServer<Enterprise[]>(`/Enterprise?${params}`, {
    method: "GET",
    headers: {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    },
  });
}
