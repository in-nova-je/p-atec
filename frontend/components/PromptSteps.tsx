import {
  IconShare2,
  IconSquarePlus,
  IconDotsCircleHorizontal,
} from "@tabler/icons-react";

export default function PromptSteps({
  setOpen,
}: {
  setOpen: (open: boolean) => void;
}) {
  return (
    <div
      className="fixed z-60 w-screen bottom-0 h-screen bg-secondary/25 backdrop-blur-xs flex flex-col-reverse p-4 font-sans"
      onClick={() => setOpen(false)}
    >
      <div className="flex flex-col bottom-0 gap-5 bg-background p-4 rounded-[14px]">
        <ol className="inline items-center">
          1. Clica em
          <IconDotsCircleHorizontal className="inline-flex font-bold mx-1 items-center text-center justify-center" />
          no canto inferior direito
        </ol>
        <ol className="flex">
          2. Clica em
          <IconShare2 className="inline-flex font-bold mx-1 items-center text-center justify-center" />
          <span className="font-bold">Partilhar</span>
        </ol>
        <ol className="inline items-center">
          3. Clica em
          <IconDotsCircleHorizontal className="inline-flex font-bold mx-1 items-center text-center justify-center" />
          <span className="font-bold">Mais</span> no canto inferior direito
        </ol>
        <ol className="inline items-center">
          4. Clica em
          <IconSquarePlus className="inline-flex font-bold mx-1 items-center text-center justify-center" />
          <span className="font-bold">Adicionar ao ecrã principal</span>
        </ol>
      </div>
    </div>
  );
}
