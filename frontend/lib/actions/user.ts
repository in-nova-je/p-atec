"use server";

import { cookies } from "next/headers";
import { apiFetchServer } from "@/lib/apiServer";

type ApiUser = {
  id: number;
  name: string;
  level: number;
  fieldsOfInterest: string;
  profilePicture: string | null;
  student: boolean;
  email: string;
};

export async function getUserByEmailAction(): Promise<ApiUser | null> {
  const email = (await cookies()).get("userEmail")?.value;
  if (!email) return null;

  return await apiFetchServer<ApiUser>(
    `/users/by-email?email=${encodeURIComponent(email)}`,
  );
}

export async function updateUserAction(input: {
  id: number;
  name: string;
  level: number;
  fieldsOfInterest: string;
  profilePicture?: string | null;
}) {
  return await apiFetchServer<ApiUser>(`/users/${input.id}/update-json`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({
      name: input.name,
      level: input.level,
      fieldsOfInterest: input.fieldsOfInterest,
      profilePicture: input.profilePicture ?? null,
    }),
  });
}

export async function getUserByEmail(email: string): Promise<ApiUser | null> {
  return await apiFetchServer<ApiUser>(
    `/users/by-email?email=${encodeURIComponent(email)}`,
  );
}
