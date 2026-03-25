"use client";

import { usePathname } from "next/navigation";
import Prompt from "@/components/Prompt";
import Logo from "@/components/Logo";
import Navbar from "@/components/Navbar";

export default function AppChrome({ children }: { children: React.ReactNode }) {
  const path = usePathname();
  // No perfil e em editar perfil, o header é o do próprio ecrã (gradiente do Figma)
  const hideLogo = path.startsWith("/profile");
  const hideNav = path.startsWith("/login") || path.startsWith("/register")
  
  return (
    <div
      className="min-h-dvh w-full"
      style={
        {
          ["--navbar-h" as any]: "96px",
          ["--nav-gap" as any]: "20px",
        } as React.CSSProperties
      }
    >
      <Prompt />
      {!hideLogo && <Logo />}

      <div className="flex justify-center w-full pb-[calc(var(--navbar-h)+env(safe-area-inset-bottom)+var(--nav-gap))]">{children}</div>

      {!hideNav && <Navbar />}
    </div>
    
  );
}
