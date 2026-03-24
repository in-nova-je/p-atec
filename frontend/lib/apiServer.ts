import { cookies } from "next/headers";

const BASE_URL = process.env.BASE_URL || "http://localhost:8080/api";

type ApiOptions = RequestInit & { auth?: boolean };

export async function apiFetchServer<T>(
  path: string,
  options: ApiOptions = {},
): Promise<T> {
  const auth = options.auth ?? true;
  const headers = new Headers(options.headers);

  if (auth) {
    const cookieStore = await cookies();
    const token = cookieStore.get("token")?.value;
    if (token) {
      headers.set("Authorization", `Bearer ${token}`);
    }
  }

  const res = await fetch(`${BASE_URL}${path}`, {
    ...options,
    headers,
    cache: "no-store",
  });

  if (!res.ok) {
    const text = await res.text().catch(() => "");
    throw new Error(`API ${res.status}: ${text || res.statusText}`);
  }

  const contentType = res.headers.get("content-type") || "";
  if (!contentType.includes("application/json")) return undefined as T;
  return (await res.json()) as T;
}
