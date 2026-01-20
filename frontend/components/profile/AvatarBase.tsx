"use client";

import Image from "next/image";
import { IconUser } from "@tabler/icons-react";

export default function AvatarBase({ src }: { src?: string | null }) {
  return (
    <div className="h-28 w-28 rounded-full bg-white shadow-lg grid place-items-center overflow-hidden">
      {src ? (
        <Image
          src={src}
          alt="Foto de perfil"
          width={112}
          height={112}
          className="h-full w-full object-cover"
          unoptimized
        />
      ) : (
        <IconUser className="text-secondary/60" size={42} />
      )}
    </div>
  );
}