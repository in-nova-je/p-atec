"use client";

import { type Enterprise, DEFAULT_ENTERPRISE } from "@/lib/types";
import { use, useEffect, useState } from "react";
import { Vibrant } from "node-vibrant/browser";
import FloatingButton from "@/components/FloatingButton";
import { IconChevronLeft, IconX } from "@tabler/icons-react";
import { getEnterpriseByName } from "@/lib/actions/enterprise";
import CompanyPageSkeleton from "@/components/skeletons/CompanyPageSkeleton";
import { createConnection } from "@/lib/connection";
import { getUserByEmail } from "@/lib/actions/user";

export default function Company({
  params,
}: {
  params: Promise<{ company: string }>;
}) {
  const { company } = use(params);
  const [loading, setLoading] = useState(true);
  const [companyData, setCompanyData] =
    useState<Enterprise>(DEFAULT_ENTERPRISE);

  const [color, setColor] = useState<string>();
  const [isContactOpen, setIsContactOpen] = useState(false);
  const [isContactMounted, setIsContactMounted] = useState(false);
  const [toast, setToast] = useState<string | null>(null);
  const [nome, setNome] = useState("");
  const [turma, setTurma] = useState("");
  const [motivo, setMotivo] = useState<string>("");

  useEffect(() => {
    if (!companyData.profilePicture) {
      setColor("#333");
      return;
    }
    Vibrant.from(`data:image/png;base64,${companyData.profilePicture}`)
      .getPalette()
      .then((palette) => setColor(palette.Vibrant?.hex));
    setLoading(false);
  }, [companyData]);

  useEffect(() => {
    (async () => {
      setCompanyData(await getEnterpriseByName(company));
    })();

    if (!isContactOpen) {
      return;
    }

    const prev = document.body.style.overflow;
    document.body.style.overflow = "hidden";

    return () => {
      document.body.style.overflow = prev;
    };
  }, [isContactOpen, company]);

  function openContact() {
    setIsContactMounted(true);
    requestAnimationFrame(() => setIsContactOpen(true));
  }

  function closeContact() {
    setIsContactOpen(false);
    window.setTimeout(() => setIsContactMounted(false), 300);
  }

  async function handleSubmit() {
    const nomeOk = nome.trim().length > 0;
    const turmaOk = turma.trim().length > 0;
    const motivoOk = motivo.trim().length > 0;

    if (!nomeOk || !turmaOk || !motivoOk) {
      setToast("Preenche todos os campos antes de submeter");
      window.setTimeout(() => setToast(null), 2000);
      return;
    }

    const userEmail = (document.cookie
      .split("; ")
      .find((row) => row.startsWith("userEmail="))
      ?.split("=")[1] ?? null) as string | null;
    if (userEmail) {
      const userId = await getUserByEmail(decodeURIComponent(userEmail));
      if (userId)
        createConnection(
          userId.id,
          companyData.id,
          motivo === "estágio",
          turma,
        );
      else setToast("Erro ao obter dados do utilizador.");
    }

    setToast("Submetido com sucesso!");
    setNome("");
    setTurma("");
    setMotivo("");
    closeContact();
    window.setTimeout(() => setToast(null), 2000);
  }

  if (loading) {
    return <CompanyPageSkeleton />;
  }

  return (
    <>
      <FloatingButton
        style={{ background: color }}
        onClick={() => openContact()}
      >
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
          <div className="flex flex-row gap-2 items-center">
            <img
              src={"data:image/png;base64," + companyData.profilePicture}
              alt={companyData.name}
              className="h-10 w-10 rounded-full object-cover"
            />
            <h1 className="">{companyData.name}</h1>
          </div>
          <hr className="border-secondary/25" />
          <p className="text-justify text-secondary">
            {companyData.description}
          </p>
        </div>
      </div>

      {isContactMounted && (
        <div className="fixed inset-0 z-50">
          {/* overlay */}
          <button
            type="button"
            className={`absolute inset-0 bg-black/40 transition-opacity duration-300 ${
              isContactOpen ? "opacity-100" : "opacity-0"
            }`}
            aria-label="Fechar"
            onClick={closeContact}
          />

          {/* sheet */}
          <div
            className={`absolute inset-x-0 bottom-0 rounded-t-3xl bg-white p-5 shadow-xl max-h-[85vh] overflow-y-auto transition-transform duration-300 ease-out ${isContactOpen ? "translate-y-0" : "translate-y-full"}`}
          >
            <div className="mx-auto mb-3 h-1.5 w-12 rounded-full bg-secondary/20" />

            <div className="flex items-center justify-between">
              <h2 className="text-base font-semibold">Entrar em Contacto</h2>

              <button
                type="button"
                onClick={() => closeContact()}
                className="grid h-9 w-9 place-items-center rounded-full hover:bg-secondary/10"
                aria-label="Fechar"
              >
                <IconX className="h-5 w-5 text-secondary" />
              </button>
            </div>

            {/* FORM PLACEHOLDER */}
            <form
              className="mt-4 space-y-3"
              onSubmit={(e) => {
                e.preventDefault();
                handleSubmit();
              }}
            >
              <div>
                <label className="block text-xs font-semibold text-foreground">
                  Nome Completo
                </label>
                <input
                  value={nome}
                  onChange={(e) => setNome(e.target.value)}
                  className="mt-2 w-full rounded-xl border border-secondary/25 px-3 py-2 text-sm bg-white
                             focus:outline-none focus:ring-2 focus:ring-primary"
                  placeholder="O teu nome"
                />
              </div>

              <div>
                <label className="block text-xs font-semibold text-foreground">
                  Turma
                </label>
                <textarea
                  value={turma}
                  onChange={(e) => setTurma(e.target.value)}
                  className="mt-2 w-full rounded-xl border border-secondary/25 px-3 py-2 text-sm bg-white
                             focus:outline-none focus:ring-2 focus:ring-primary"
                  placeholder="Escreve a tua turma"
                  rows={4}
                />
              </div>

              <div>
                <label className="block text-xs font-semibold text-foreground">
                  Motivo
                </label>
                <div className="mt-2 relative">
                  <select
                    value={motivo}
                    onChange={(e) => setMotivo(e.target.value)}
                    className="w-full appearance-none rounded-xl border border-secondary/25 bg-white
                              px-3 py-2 pr-9 text-sm
                              focus:outline-none focus:ring-2 focus:ring-primary"
                  >
                    <option value="" disabled>
                      Seleciona uma opção
                    </option>
                    <option value="estágio">Estágio</option>
                    <option value="emprego">Emprego</option>
                  </select>

                  <span className="pointer-events-none absolute right-3 top-1/2 -translate-y-1/2 text-secondary/50">
                    ▾
                  </span>
                </div>
              </div>

              <button
                type="submit"
                className="mt-2 w-full rounded-xl py-3 text-background font-semibold hover:opacity-95"
                style={{ background: color }}
              >
                Submeter
              </button>
            </form>
          </div>
        </div>
      )}
      {toast && (
        <div className="fixed left-1/2 top-5 z-[60] -translate-x-1/2">
          <div className="rounded-xl bg-black/80 px-4 py-2 text-sm text-white shadow-lg">
            {toast}
          </div>
        </div>
      )}
    </>
  );
}
