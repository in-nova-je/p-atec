"use client";

import { usePathname } from "next/navigation";
import Prompt from "@/components/Prompt";
import Logo from "@/components/Logo";
import Navbar from "@/components/Navbar";

export default function AppChrome({ children }: { children: React.ReactNode }) {
  const path = usePathname();
  // No perfil e em editar perfil, o header é o do próprio ecrã (gradiente do Figma)
  const hideLogo = path.startsWith("/profile");

  return (
    <>
      <Prompt />
      {!hideLogo && <Logo />}

      <div className="flex justify-center w-full h-full">{children}</div>

      <Navbar />
    </>
  );
}
