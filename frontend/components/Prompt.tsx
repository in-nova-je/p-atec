"use client";

import Image from "next/image";
import { useState, useEffect } from "react";
import PromptSteps from "./PromptSteps";
export default function Prompt() {
  const [isStandalone, setIsStandalone] = useState(true);
  const [open, setOpen] = useState(false);
  useEffect(() => {
    //setIsStandalone(window.matchMedia("(display-mode: standalone)").matches);
  }, []);
  return (
    !isStandalone && (
      <>
        {open && <PromptSteps setOpen={setOpen} />}
        <div className="md:hidden fixed w-full flex justify-between top-0 border-b border-secondary/25 h-18 p-4 gap-2 font-sans bg-background">
          <div className="flex gap-2 items-center">
            <div className="h-full aspect-square p-2 border border-secondary/25 rounded-xl mt-1">
              <Image
                src={"/atec_logo.png"}
                width={1000}
                height={1000}
                alt="atec logo"
              />
            </div>
            <div>
              <div className="font-bold">ATEC</div>
              <div className="text-secondary text-sm">
                Instala a app no telemóvel
              </div>
            </div>
          </div>
          <button
            className="bg-primary text-background px-4 rounded-md"
            onClick={() => setOpen((open) => !open)}
          >
            Instalar
          </button>
        </div>
      </>
    )
  );
}
