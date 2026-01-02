import { IconSearch } from "@tabler/icons-react";

export default function SearchBar({
  setSearch,
}: {
  setSearch: (search: string) => void;
}) {
  return (
    <div className="fixed left-0 flex justify-center bottom-20 w-screen p-4 font-sans">
      <div
        className="bg-secondary/25 [&:has(input:focus)]:outline-2 outline-primary text-secondary 
      font-medium flex justify-between gap-2 w-full md:w-md p-4 rounded-[14px] transition-all duration-50
      relative backdrop-blur-sm"
      >
        <input
          placeholder="Procurar Empresas..."
          className="[all:unset]"
          onChange={(e) => setSearch(e.currentTarget.value)}
        />
        <IconSearch />
      </div>
    </div>
  );
}
