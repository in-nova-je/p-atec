import { NextResponse } from "next/server";
import type { NextRequest } from "next/server";

export async function proxy(request: NextRequest) {
  const hasToken = request.cookies.has("token");
  if (
    !hasToken &&
    request.nextUrl.pathname !== "/login" &&
    request.nextUrl.pathname !== "/register"
  ) {
    return NextResponse.redirect(new URL("/login", request.url));
  } else if (
    hasToken &&
    (request.nextUrl.pathname === "/login" ||
      request.nextUrl.pathname === "/register")
  ) {
    return NextResponse.redirect(new URL("/home", request.url));
  }

  if (request.nextUrl.pathname === "/") {
    return NextResponse.redirect(new URL("/home", request.url));
  }
  return NextResponse.next();
}
