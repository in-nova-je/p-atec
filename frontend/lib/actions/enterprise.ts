"use server";

import { apiFetchServer } from "../apiServer";
import { Enterprise } from "../types";

export async function getAllEnterprises() {
  const params = new URLSearchParams({
    pageSize: "1000",
  });

  return apiFetchServer(`/Enterprise?${params}`, {
    method: "GET",
  });
}

export async function getEnterpriseByName(name: string): Promise<Enterprise> {
  const params = new URLSearchParams({
    name,
  });

  return apiFetchServer(`/Enterprise/${name}`, {
    method: "GET",
  });
}
