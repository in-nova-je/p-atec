import { cn } from "@/lib/utils";
import React from "react";

type ButtonProps = React.ButtonHTMLAttributes<HTMLButtonElement> & {
  variant?: "outline" | "solid";
};

export default function Button({ className, variant, ...props }: ButtonProps) {
  return (
    <button
      className={cn(
        "py-3 px-6 font-sans rounded-md text-background font-medium",
        variant === "outline"
          ? "border border-primary text-primary hover:bg-background/10"
          : "bg-background hover:bg-opacity-90",
        className,
      )}
      {...props}
    />
  );
}
