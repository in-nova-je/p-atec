"use client";

import companies from "@/json/companies.json";
import { type Company, DEFAULT_COMPANY } from "@/lib/types";
import { use, useEffect, useState } from "react";
import { Vibrant } from "node-vibrant/browser";
import FloatingButton from "@/components/FloatingButton";
import { IconChevronLeft } from "@tabler/icons-react";

export default function Company({
  params,
}: {
  params: Promise<{ company: string }>;
}) {
  const { company } = use(params);
  const companyData: Company =
    companies.find((c) => c.name === company) ?? DEFAULT_COMPANY;

  const [color, setColor] = useState<string>();
  useEffect(() => {
    Vibrant.from(companyData.logoPath)
      .getPalette()
      .then((palette) => setColor(palette.Vibrant?.hex));
  }, []);

  return (
    <>
      <FloatingButton style={{ background: color }}>
        Entrar em Contacto
      </FloatingButton>
      <div className="absolute left-0 w-screen font-sans">
        <div
          style={{ background: color }}
          className="w-full h-40 mask-b-from-0%"
        >
          <a href="/companies">
            <IconChevronLeft className="stroke-background m-4 h-8 w-8 absolute" />
          </a>
        </div>
        <div className="px-4 flex flex-col gap-4">
          <h1 className="">{companyData.name}</h1>
          <hr className="border-secondary/25" />
          <p className="text-justify text-secondary">
            {companyData.description}
          </p>
        </div>
      </div>
    </>
  );
}
