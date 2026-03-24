"use server";

import { cookies } from "next/headers";

const BACKEND_BASE = "http://localhost:8080/api";

function extractCookieValue(setCookie: string, name: string) {
  const match = setCookie.match(new RegExp(`${name}=([^;]+)`));
  return match?.[1];
}

export async function loginAction(formData: FormData) {
  const email = String(formData.get("email") ?? "");
  const password = String(formData.get("password") ?? "");

  if (!email || !password) {
    return { ok: false, message: "Email e password são obrigatórios" };
  }

  const url =
    `${BACKEND_BASE}/auth/login?` +
    new URLSearchParams({ email, password }).toString();

  const res = await fetch(url, { method: "POST" });

  if (!res.ok) {
    return { ok: false, message: "Credenciais inválidas" };
  }

  const setCookie = res.headers.get("set-cookie");
  if (!setCookie) {
    return { ok: false, message: "Backend não devolveu Set-Cookie" };
  }

  const token = extractCookieValue(setCookie, "token");
  if (!token) {
    return { ok: false, message: "Não foi possível ler token do Set-Cookie" };
  }

  const cookieStore = await cookies();

  cookieStore.set("token", token, {
    httpOnly: true,
    sameSite: "lax",
    secure: false,
    path: "/",
    maxAge: 60 * 60,
  });

  cookieStore.set("userEmail", email, {
    httpOnly: false,
    sameSite: "lax",
    secure: false,
    path: "/",
    maxAge: 60 * 60,
  });

  cookieStore.delete("idToken");

  return { ok: true };
}

export async function registerAction(formData: FormData) {
  const email = String(formData.get("email") ?? "");
  const password = String(formData.get("password") ?? "");
  const confirmPassword = String(formData.get("confirmPassword") ?? "");

  const name = email.split("@")[0] || "user";
  const level = "3";
  const FieldsOfInterest = "";
  const Profilepicture = "not available";

  if (!email || !password || !confirmPassword) {
    return { ok: false, message: "Preenche email e passwords" };
  }
  if (password !== confirmPassword) {
    return { ok: false, message: "As passwords não coincidem" };
  }

  const url =
    `${BACKEND_BASE}/auth/register?` +
    new URLSearchParams({
      name,
      level,
      password,
      email,
      FieldsOfInterest,
      Profilepicture,
    }).toString();

  const res = await fetch(url, { method: "POST" });
  if (!res.ok) {
    const text = await res.text().catch(() => "");
    return { ok: false, message: text || "Erro no registo" };
  }

  const fd = new FormData();
  fd.set("email", email);
  fd.set("password", password);
  return await loginAction(fd);
}