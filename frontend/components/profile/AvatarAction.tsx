"use client";

import Link from "next/link";
import { useRef } from "react";
import { IconCamera, IconPencil } from "@tabler/icons-react";

type AvatarActionProps =
  | {
      variant: "pencil";
      href: string;
      className?: string;
    }
  | {
      variant: "camera";
      onFileSelected: (file: File) => void;
      className?: string;
    };

export default function AvatarAction(props: AvatarActionProps) {
  const inputRef = useRef<HTMLInputElement | null>(null);

  const common =
    "absolute -right-1 bottom-3 h-8 w-8 rounded-full bg-primary grid place-items-center " +
    // sombra
    "shadow-[0px_8px_18px_rgba(0,0,0,0.25)] " +
    // animação/feedback
    "transition-all duration-150 ease-out " +
    "hover:-translate-y-[1px] hover:shadow-[0px_10px_22px_rgba(0,0,0,0.28)] hover:opacity-95 " +
    "active:translate-y-0 active:shadow-[0px_6px_14px_rgba(0,0,0,0.22)] ";
    // acessibilidade, utilizador de teclado consegue focar e perceber que o elemento é interativo
    "focus:outline-none focus:ring-2 focus:ring-primary focus:ring-offset-2";


  if (props.variant === "pencil") {
    return (
      <Link href={props.href} className={`${common} ${props.className ?? ""}`} aria-label="Editar perfil">
        <IconPencil className="text-background" size={16} />
      </Link>
    );
  }

  return (
    <>
      <button
        type="button"
        className={`${common} ${props.className ?? ""}`}
        aria-label="Alterar foto"
        onClick={() => inputRef.current?.click()}
      >
        <IconCamera className="text-background" size={16} />
      </button>

      <input
        ref={inputRef}
        type="file"
        accept="image/*"
        className="hidden"
        onChange={(e) => {
          const file = e.target.files?.[0];
          if (file) props.onFileSelected(file);
          e.currentTarget.value = ""; // permite escolher a mesma imagem outra vez
        }}
      />
    </>
  );
}