"use client";

import { IconChevronLeft } from "@tabler/icons-react";

export default function CompanyPageSkeleton() {
  return (
    <>
      <div className="fixed left-0 flex justify-center bottom-20 w-screen p-4 font-sans"></div>

      <div className="absolute left-0 w-screen font-sans">
        <div className="w-full h-40 mask-b-from-0%">
          <a href="/companies">
            <IconChevronLeft className="stroke-background m-4 h-8 w-8 absolute" />
          </a>
        </div>
        <div className="px-4 flex flex-col gap-4">
          <div className="flex flex-row gap-2 items-center">
            <div className="h-10 w-10 rounded-full object-cover bg-gray-500/25 animate-pulse" />
            <div className="h-4 w-32 rounded-full bg-secondary/25 animate-pulse" />
          </div>
          <hr className="border-secondary/25" />
          <div className="flex flex-col gap-2">
            <div className="h-4 w-48 rounded-full bg-secondary/25 animate-pulse" />
            <div className="h-4 w-64 rounded-full bg-secondary/25 animate-pulse" />
            <div className="h-4 w-56 rounded-full bg-secondary/25 animate-pulse" />
          </div>
        </div>
      </div>
    </>
  );
}
