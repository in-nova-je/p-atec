"use client";

import { useMemo, useState } from "react";
import Link from "next/link";
import { IconSchool, IconChevronDown, IconHeart } from "@tabler/icons-react";
import Button from "@/components/Button";
import AvatarBase from "@/components/profile/AvatarBase";
import AvatarAction from "@/components/profile/AvatarAction";
import { useRouter } from "next/navigation";

const LEVELS = [3, 4];
const INTERESTS = [
  "Inteligência Artificial",
  "Desenvolvimento Web",
  "Ciência de Dados",
  "Segurança Informática",
  "Redes de Computadores",
  "Sistemas Operativos",
];

export default function EditProfile() {
  const [name, setName] = useState("");
  const [level, setLevel] = useState<number | "">("");
  const [interest, setInterest] = useState("");
  const [tags, setTags] = useState<string[]>([]);
  const [newTag, setNewTag] = useState("");
  const [avatarSrc, setAvatarSrc] = useState<string | null>(null);
  const router = useRouter();

  function setPreview(file: File) {
    const url = URL.createObjectURL(file);
    setAvatarSrc((prev) => {
      if (prev) URL.revokeObjectURL(prev);
      return url;
    });
  }

  const canAdd = useMemo(() => {
    const v = newTag.trim();
    if (!v) return false;
    return !tags.some((t) => t.toLowerCase() === v.toLowerCase());
  }, [newTag, tags]);

  function addTag() {
    if (!canAdd) return;
    setTags((prev) => [...prev, newTag.trim()]);
    setNewTag("");
  }

  function removeTag(tag: string) {
    setTags((prev) => prev.filter((t) => t !== tag));
  }

  return (
    <main className="min-h-dvh w-full bg-background pb-28 font-sans">
      <header className="h-44 w-full bg-[linear-gradient(90deg,#222289_0%,#1C9CD8_98%)]">
        <div className="mx-auto max-w-3xl px-6 pt-10 text-center">
          <h1 className="text-2xl font-semibold text-[#D9D9D9]/60">Perfil</h1>
        </div>
      </header>

      <section className="mx-auto max-w-3xl px-8 -mt-14">
        <div className="flex flex-col items-center">
          {/* Avatar (hardcoded) + botão câmara (clicável) */}
          <div className="relative">
            <AvatarBase src={avatarSrc} />

            <AvatarAction variant="camera" onFileSelected={setPreview} />
          </div>

          <p className="mt-6 text-xs text-secondary">
            Clique no ícone para alterar a foto
          </p>

          <div className="mt-6 w-full space-y-4">
            <div>
              <label className="block text-xs font-semibold text-foreground">
                Nome Completo
              </label>
              <input
                value={name}
                onChange={(e) => setName(e.target.value)}
                className="mt-2 w-full rounded-xl border border-secondary/25 px-3 py-2 text-sm bg-white
                           focus:outline-none focus:ring-2 focus:ring-primary"
                placeholder="Nome Completo"
              />
            </div>

            {/* Nível */}
            <div>
              <label className="block text-xs font-semibold text-foreground">
                Nível de Formação
              </label>
              <div className="mt-2 relative">
                <IconSchool className="pointer-events-none absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-secondary/90" />
                <select
                  value={level}
                  onChange={(e) =>
                    setLevel(
                      e.target.value === "" ? "" : Number(e.target.value),
                    )
                  }
                  className="w-full appearance-none rounded-xl border border-secondary/25 bg-white
                            px-3 py-2 pl-9 pr-9 text-sm font-normal
                            focus:outline-none focus:ring-2 focus:ring-primary"
                >
                  <option value="" disabled>
                    Nível
                  </option>

                  {LEVELS.map((n) => (
                    <option key={n} value={n}>
                      Nível {n}
                    </option>
                  ))}
                </select>
                <span className="pointer-events-none absolute right-3 top-1/2 -translate-y-1/2 text-secondary/50">
                  ▾
                </span>
              </div>
            </div>

            {/* Áreas de interesse */}
            <div>
              <label className="block text-xs font-semibold text-foreground">
                Área de Interesse
              </label>

              <div className="mt-2 relative">
                <IconHeart className="pointer-events-none absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-secondary/90" />
                <select
                  value={interest}
                  onChange={(e) => setInterest(e.target.value)}
                  className="w-full appearance-none rounded-xl border border-secondary/25 bg-white
                            px-3 py-2 pl-9 pr-9 text-sm font-normal
                            focus:outline-none focus:ring-2 focus:ring-primary"
                >
                  <option value="" disabled>
                    Interesse
                  </option>

                  {INTERESTS.map((interest) => (
                    <option key={interest} value={interest}>
                      {interest}
                    </option>
                  ))}
                </select>
                <span className="pointer-events-none absolute right-3 top-1/2 -translate-y-1/2 text-secondary/50">
                  ▾
                </span>
              </div>
            </div>

            <Button
              type="button"
              onClick={() => router.push("/profile")}
              className="mt-8 w-full rounded-xl shadow-sm hover:opacity-95 bg-[linear-gradient(90deg,#222289_0%,#1C9CD8_98%)]"
            >
              Salvar Alterações
            </Button>

            <Link
              href="/profile"
              className="block text-center text-sm text-secondary underline"
            >
              Voltar
            </Link>
          </div>
        </div>
      </section>
    </main>
  );
}
