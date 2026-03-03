import { cn } from "@/lib/utils";
import Link from "next/link";

export default function LinkComponent({
  href,
  className,
  ...props
}: React.AnchorHTMLAttributes<HTMLAnchorElement> & { href: string }) {
  return (
    <Link className={cn(className, "text-primary underline")} href={href}>
      {props.children}
    </Link>
  );
}
