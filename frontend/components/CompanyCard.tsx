import { IconChevronRight } from "@tabler/icons-react";
import Image from "next/image";

export default function CompanyCard({
  name,
  logo,
  description,
}: {
  name: string;
  logo: string;
  description: string;
}) {
  return (
    <a
      className="flex gap-4 font-sans border-secondary/25 pb-5 border-b"
      href={`/companies/${name}`}
    >
      <Image
        src={logo}
        width={100}
        height={100}
        alt={name}
        className="h-full aspect-square rounded-xl"
      />
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
