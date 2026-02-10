import Button from "@/components/Button";

export default function NotFound() {
  return (
    <div className="flex flex-col gap-8 items-center justify-center h-full p-4 text-center font-sans">
      <h1 className="text-2xl font-bold">Página Não Encontrada</h1>
      <h1 className="font-mono text-9xl">404</h1>
      <p className="text-secondary">
        Woah, parece que chegaste a uma página que não existe!
      </p>
      <Button variant={"outline"}>Voltar para a página inicial</Button>
    </div>
  );
}
