"use client";

import {
  IconHome,
  IconUserSquareRounded,
  IconCalendarEvent,
  IconBriefcase2,
} from "@tabler/icons-react";
import Link from "next/link";
import { usePathname } from "next/navigation";

const pages = [
  {
    name: "Home",
    href: "/home",
    icon: IconHome,
  },
  {
    name: "Perfil",
    href: "/profile",
    icon: IconUserSquareRounded,
  },
  {
    name: "Eventos",
    href: "/events",
    icon: IconCalendarEvent,
  },
  {
    name: "Empresas",
    href: "/companies",
    icon: IconBriefcase2,
  },
];

export default function Navbar() {
  const path = usePathname();
  const pathIdx = pages.findIndex((page) => path.startsWith(page.href));
  return (
    <div
      className="fixed bottom-0 left-0 right-0 z-40 flex w-screen justify-center font-sans"
      style={{ height: "calc(var(--navbar-h) + env(safe-area-inset-bottom))" }}
    >
      <div className="w-full px-4 pb-[calc(16px+env(safe-area-inset-bottom))] pt-4 flex justify-center">
        <div className="bg-secondary/25 flex w-full md:w-md p-2 rounded-[14px] relative backdrop-blur-sm">
          {pathIdx >= 0 && (
            <div
              className="inset-2 w-[calc(25%-4px)] box-content bg-background absolute rounded-md ease-in-out duration-100 transition-all"
              style={{
                transform: `translate(calc(100%*${pathIdx}))`,
              }}
            />
          )}
          {pages.map((page) => {
            return (
              <Link
                key={page.name}
                href={page.href}
                className="flex-1 flex flex-col items-center justify-center"
              >
                <button
                  className={`z-10 flex flex-col items-center text-xs p-2 w-full rounded-md transition-all
                  ${
                    path.startsWith(page.href)
                      ? "text-primary font-bold"
                      : "text-secondary font-semibold"
                  }`}
                >
                  <page.icon />
                  {page.name}
                </button>
              </Link>
            );
          })}
        </div>
      </div>
    </div>
  );
}
