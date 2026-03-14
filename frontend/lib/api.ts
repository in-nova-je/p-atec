
const BASE_URL = "http://localhost:8080/api";

type ApiOptions = RequestInit & {
  auth?: boolean; // se false, não inclui token de autenticação. padrão/default: true
};

export async function apiFetch<T>(path: string, options: ApiOptions = {}): Promise<T> {
  const auth = options.auth ?? true;

  const headers = new Headers(options.headers);

  const res = await fetch(`${BASE_URL}${path}`, {
    ...options,
    headers,
    credentials: auth ? "include" : "omit", // só inclui cookies (token de autenticação) se auth for true
  });

  if (!res.ok) {  // tenta ler erro
    const text = await res.text().catch(() => "");
    throw new Error(`API ${res.status}: ${text || res.statusText}`);
  }

  // há endpoints que podem devolver vazio
  const contentType = res.headers.get("content-type") || "";
  if (!contentType.includes("application/json")) {
    return undefined as T;
  }

  return (await res.json()) as T;
}