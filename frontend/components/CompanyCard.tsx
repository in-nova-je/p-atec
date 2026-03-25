import { IconChevronRight } from "@tabler/icons-react";
import Image from "next/image";

export default function CompanyCard({
  id,
  name,
  logo,
  description,
}: {
  id: number;
  name: string;
  logo: string;
  description: string;
}) {
  return (
    <a
      className="flex gap-4 font-sans border-secondary/25 pb-5 border-b"
      href={`/companies/${id}`}
    >
      <div className="relative w-25 h-25 shrink-0 rounded-xl overflow-hidden">
        <Image
          src={logo}
          fill
          alt={name}
          className="object-contain"
        />
      </div>
      <div>
        <p className="font-semibold text-xl mb-2">{name}</p>
        <p className="text-ellipsis line-clamp-2 text-sm text-secondary">
          {description}
        </p>
        <p className="text-sm text-primary font-semibold flex items-center gap-2">
          Clica para saber mais <IconChevronRight className="w-5" />
        </p>
      </div>
    </a>
  );
}
