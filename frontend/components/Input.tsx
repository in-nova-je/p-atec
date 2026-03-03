import { cn } from "@/lib/utils";

export default function Input({
  className,
  ...props
}: React.InputHTMLAttributes<HTMLInputElement>) {
  return (
    <input
      className={cn(`border-secondary border p-2 rounded`, className)}
      {...props}
    />
  );
}
