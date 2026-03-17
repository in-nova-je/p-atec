"use server";

import { cookies } from "next/headers";

const BACKEND_BASE = "http://localhost:8080/api";

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
  const text = await res.text().catch(() => "");

  if (!res.ok) {
    // tenta devolver a msg do backend (409 etc)
    return { ok: false, message: text || "Erro no registo" };
  }

  const loginUrl =
    `${BACKEND_BASE}/auth/login?` +
    new URLSearchParams({ email, password }).toString();

  const loginRes = await fetch(loginUrl, { method: "POST" });
  const loginText = await loginRes.text().catch(() => "");

  if (!loginRes.ok) {
    return { ok: true, needsLogin: true };
  }

  const data = loginText ? JSON.parse(loginText) : {};
  const token = data?.token;

  if (token) {
    const cookieStore = await cookies();
    cookieStore.set("idToken", token, {
      httpOnly: true,
      sameSite: "lax",
      secure: false,
      path: "/",
      maxAge: 60 * 60 * 24 * 3,
    });
    cookieStore.set("userEmail", email, {
      httpOnly: false,
      sameSite: "lax",
      secure: false,
      path: "/",
      maxAge: 60 * 60 * 24 * 3,
    });
  }

  return { ok: true };
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
  const text = await res.text().catch(() => "");

  if (!res.ok) {
    return { ok: false, message: "Credenciais inválidas" };
  }

  const data = text ? JSON.parse(text) : {};
  const token = data?.token;
  if (!token) {
    return { ok: false, message: "Backend não devolveu token" };
  }
  const cookieStore = await cookies();
  cookieStore.set("idToken", token, {
    httpOnly: true,
    sameSite: "lax",
    secure: false, // em prod: true (https) !!
    path: "/",
    maxAge: 60 * 60 * 24 * 3, // 3 dias
  });
  cookieStore.set("userEmail", email, { httpOnly: false, sameSite: "lax", secure: false, path: "/", maxAge: 60 * 60 * 24 * 3 });

  return { ok: true };
}