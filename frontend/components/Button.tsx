import { cn } from "@/lib/utils";
import React from "react";

type ButtonProps = React.ButtonHTMLAttributes<HTMLButtonElement>;

export default function Button({ className, ...props }: ButtonProps) {
  return (
    <button
      className={cn(
        "p-3 font-sans rounded-md text-background font-medium",
        className
      )}
      {...props}
    />
  );
}
