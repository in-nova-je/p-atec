import { IconBook, IconSchool } from "@tabler/icons-react";
import AvatarBase from "@/components/profile/AvatarBase";
import AvatarAction from "@/components/profile/AvatarAction";

export default function Profile() {
  const user = {
    name: "Nome do User",
    level: null as number | null,
    interests: [] as string[],
  };

  return (
    <main className="min-h-dvh bg-background pb-28 font-sans">
      <header className="h-44 w-full bg-[linear-gradient(90deg,#222289_0%,#1C9CD8_98%)]">
        <div className="mx-auto max-w-3xl px-6 pt-10 text-center">
          <h1 className="text-2xl font-semibold text-[#D9D9D9]/60">Perfil</h1>
        </div>
      </header>

      <section className="mx-auto max-w-3xl px-8 -mt-14">
        <div className="flex flex-col items-center">
          {/* Avatar igual ao edit + lápis clicável */}
          <div className="relative">
            <AvatarBase />
            <AvatarAction variant="pencil" href="/profile/edit" />
          </div>

          <p className="mt-4 text-base text-foreground">{user.name}</p>

          {/* Pílula nível (mantém como tinhas, só exemplo) */}
          <div
            className="mt-6 inline-flex items-center rounded-full px-6 py-2"
            style={{
              backgroundColor: "#EFF6FF",
              boxShadow: "0px 8px 18px rgba(0,0,0,0.25)",
              gap: "8px",
            }}
          >
            <IconSchool size={16} stroke={2} color="#155DFC" />
            <span className="text-base font-medium" style={{ color: "#1447E6" }}>
              {user.level ? `Nível ${user.level}` : "Nível"}
            </span>
          </div>

          <div className="mt-10 w-full">
            <div className="flex items-center gap-2">
              <IconBook className="text-secondary" size={18} />
              <h2 className="text-sm font-medium text-foreground">Áreas de Interesse</h2>
            </div>

            {user.interests.length === 0 ? (
              <p className="mt-4 text-sm text-secondary">
                Ainda não tens áreas de interesse adicionadas.
              </p>
            ) : (
              <div className="mt-6 grid grid-cols-2 gap-4">
                {user.interests.map((t) => (
                  <div
                    key={t}
                    className="rounded-xl px-4 py-3 text-center text-sm font-semibold"
                    style={{ backgroundColor: "rgba(224,231,255,0.20)", color: "#222289" }}
                  >
                    {t}
                  </div>
                ))}
              </div>
            )}
          </div>
        </div>
      </section>
    </main>
  );
}