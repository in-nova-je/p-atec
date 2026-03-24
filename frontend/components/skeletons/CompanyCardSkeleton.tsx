import { IconChevronRight } from "@tabler/icons-react";
import Image from "next/image";

export default function CompanyCardSkeleton() {
  return (
    <div className="flex gap-4 h-30 font-sans border-secondary/25 pb-5 border-b">
      <div className="h-full aspect-square rounded-xl bg-gray-500/25 animate-pulse" />
      <div className="h-full w-full">
        <div className="mb-2 bg-gray-500/25 animate-pulse h-6 w-full rounded" />
        <div className="text-ellipsis line-clamp-2 text-sm text-secondary">
          <div className="bg-gray-500/25 animate-pulse h-4 w-full rounded mb-1" />
          <div className="bg-gray-500/25 animate-pulse h-4 w-5/6 rounded" />
        </div>
      </div>
    </div>
  );
}
