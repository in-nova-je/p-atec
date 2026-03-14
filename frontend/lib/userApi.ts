import { apiFetch } from "@/lib/api";
import type { ApiUser } from "@/lib/types";

export function getUserById(id: number) {
  return apiFetch<ApiUser>(`/users/${id}`);
}

export function getUserByName(name: string) {
  const q = new URLSearchParams({ name }).toString();
  return apiFetch<ApiUser>(`/users/by-name?${q}`);
}

export function updateUserAll(id: number, data: {
  name: string;
  level: number;
  fieldsOfInterest: string;
  profilePicture?: string;
}) {
  const params = new URLSearchParams({
    name: data.name,
    level: String(data.level),
    FieldsOfInterest: data.fieldsOfInterest,
  });

  params.set("ProfilePicture", data.profilePicture ?? "not available");

  return apiFetch<ApiUser>(`/users/${id}?${params.toString()}`, {
    method: "PUT",
  });
}