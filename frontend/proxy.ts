import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

const public_routes = ["/login", "/register"];
const protected_routes = ["/home", "/profile", "/companies", "/events"];

export async function proxy(request: NextRequest) {
  const hasToken = request.cookies.has("token");
  if (!hasToken && protected_routes.includes(request.nextUrl.pathname)) {
    return NextResponse.redirect(new URL("/login", request.url));
  } else if (hasToken && public_routes.includes(request.nextUrl.pathname)) {
    return NextResponse.redirect(new URL("/home", request.url));
  }

  if (request.nextUrl.pathname === "/") {
    return NextResponse.redirect(new URL("/home", request.url));
  }
  return NextResponse.next();
}
