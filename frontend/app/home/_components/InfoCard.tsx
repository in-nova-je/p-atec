import type { TablerIcon } from "@tabler/icons-react";

export default function InfoCard({
  icon: Icon,
  label,
  info,
}: {
  icon: TablerIcon;
  label: string;
  info: string;
}) {
  return (
    <div className="border border-secondary/25 p-4 rounded-xl flex flex-col gap-2">
      <div className="flex gap-2">
        <Icon className="text-secondary w-4 h-4" />
        <p className="text-sm text-secondary">{label}</p>
      </div>
      <h3 className="font-bold p-0">{info}</h3>
    </div>
  );
}
