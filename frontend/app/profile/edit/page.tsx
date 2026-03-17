"use client";

import { useMemo, useState } from "react";
import Link from "next/link";
import { IconSchool, IconHeart } from "@tabler/icons-react";
import Button from "@/components/Button";
import AvatarBase from "@/components/profile/AvatarBase";
import AvatarAction from "@/components/profile/AvatarAction";
import { useRouter } from "next/navigation";
import { useEffect } from "react";
import { getUserByEmailAction, updateUserAction } from "@/lib/actions/user";

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
  const router = useRouter();
  const [userId, setUserId] = useState<number | null>(null);
  const [name, setName] = useState("");
  const [level, setLevel] = useState<number | "">("");
  const [interest, setInterest] = useState("");
  const [tags, setTags] = useState<string[]>([]);
  const [newTag, setNewTag] = useState("");
  const [avatarSrc, setAvatarSrc] = useState<string | null>(null);
  const [avatarFile, setAvatarFile] = useState<File | null>(null);
  const [toast, setToast] = useState<string | null>(null);

  useEffect(() => {
    (async () => {
      const user = await getUserByEmailAction();
      if (!user) return;

      setUserId(user.id);
      setName(user.name ?? "");
      setLevel(typeof user.level === "number" ? user.level : "");
      setInterest(user.fieldsOfInterest ?? "");
    })();
  }, []);

  function setPreview(file: File) {
    setAvatarFile(file);
    const url = URL.createObjectURL(file);
    setAvatarSrc((prev) => {
      if (prev) URL.revokeObjectURL(prev);
      return url;
    });
  }

  //func all chatgpt
  function fileToBase64(file: File): Promise<string> {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onload = () => {
        const result = String(reader.result ?? "");
        // "data:image/png;base64,AAAA" -> "AAAA"
        const base64 = result.includes(",") ? result.split(",")[1] : result;
        resolve(base64);
      };
      reader.onerror = reject;
      reader.readAsDataURL(file);
    });
  }

  async function handleSave() {
    const nameOk = name.trim().length > 0;
    const levelOk = level !== "";
    const interestOk = interest !== "";
    const idOk = userId !== null;

    if (!nameOk || !levelOk || !interestOk || !idOk) {
      setToast("Preenche todos os campos antes de guardar");
      window.setTimeout(() => setToast(null), 2000);
      return;
    }
    try {
      const profilePicture =
        avatarFile ? await fileToBase64(avatarFile) : undefined;
      await updateUserAction({
        id: userId!,
        name: name.trim(),
        level: Number(level),
        fieldsOfInterest: interest,
        profilePicture,
      });

      router.push("/profile");
    } catch (e) {
      console.error(e);
      setToast("Erro a guardar alterações");
      window.setTimeout(() => setToast(null), 2000);
    }
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
                  className={`w-full appearance-none rounded-xl border border-secondary/25 bg-white
                            px-3 py-2 pl-9 pr-9 text-sm font-normal
                            focus:outline-none focus:ring-2 focus:ring-primary"
                            ${level === "" ? "text-secondary/60" : "text-foreground"}`}
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
                  className={`w-full appearance-none rounded-xl border border-secondary/25 bg-white
                              px-3 py-2 pl-9 pr-9 text-sm font-normal
                              focus:outline-none focus:ring-2 focus:ring-primary
                              ${interest === "" ? "text-secondary/60" : "text-foreground"}`}
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
              onClick={handleSave}
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
      {toast && (
        <div className="fixed left-1/2 top-5 z-[60] -translate-x-1/2">
          <div className="rounded-xl bg-black/80 px-4 py-2 text-sm text-white shadow-lg">
            {toast}
          </div>
        </div>
      )}
    </main>
  );
}
