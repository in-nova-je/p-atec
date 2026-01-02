import { IconSearch } from "@tabler/icons-react";
import Button from "./Button";
import { cn } from "@/lib/utils";

type ButtonProps = React.ButtonHTMLAttributes<HTMLButtonElement>;

export default function FloatingButton({ className, ...props }: ButtonProps) {
  return (
    <div className="fixed left-0 flex justify-center bottom-20 w-screen p-4 font-sans">
      <Button
        className={cn(
          className,
          "text-background font-medium gap-2 w-full md:w-md rounded-md transition-all duration-50 relative backdrop-blur-sm"
        )}
        {...props}
      />
    </div>
  );
}
