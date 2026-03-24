"use client";

import Image from "next/image";
import Input from "@/components/Input";
import Button from "@/components/Button";
import { useState } from "react";
import { LoggingInUser } from "@/lib/zod-schemas";
import { useRouter } from "next/navigation";
import z from "zod";
import { loginAction } from "@/lib/actions/auth";

export default function Login() {
  const router = useRouter();
  const [errors, setErrors] = useState<z.ZodError | null>(null);
  const [serverError, setServerError] = useState<string | null>(null);

  async function onSubmit(formData: FormData) {
    setErrors(null);
    setServerError(null);

    const email = String(formData.get("email") ?? "");
    const password = String(formData.get("password") ?? "");

    // validação Zod no client (para UX)
    try {
      LoggingInUser.parse({ email, password });
    } catch (e) {
      if (e instanceof z.ZodError) setErrors(e);
      return;
    }

    // chama server action (set cookie acontece no servidor)
    const result = await loginAction(formData);

    if (!result.ok) {
      setServerError(result.message ?? "Erro no login");
      return;
    }
    if (result.ok) {
      router.push("/profile");
    }
  }

  return (
    <div className="flex h-screen justify-center font-sans">
      <div className="absolute top-0 w-full flex flex-col items-center">
        <div className="w-full h-50 mask-b-from-25%">
          <Image
            src={"/img/atec.jpg"}
            width={1000}
            height={1000}
            alt="formandos da atec a trabalhar em conjunto"
            className="h-[150%] object-cover bottom-0 absolute"
          />
        </div>
        <div className="w-full p-10 absolute bottom-0 translate-y-8">
          <Image
            src={"/atec_logotype.webp"}
            width={1000}
            height={1000}
            alt="atec logotype"
            className="w-full object-cover"
          />
        </div>
      </div>

      <div className="flex gap-5 mt-50 px-8 text-justify w-screen flex-col items-center">
        <div>
          <h2 className="pb-0 text-secondary font-bold">Entrar</h2>
          <p className="text-secondary">
            Entrar na sua conta para participar nos eventos da ATEC!
          </p>
        </div>

        <form className="flex flex-col gap-2 w-full" action={onSubmit}>
          {!!serverError && (
            <p className="text-sm text-red-500">{serverError}</p>
          )}

          <div>
            <Input
              className="w-full"
              placeholder="E-mail"
              type="email"
              name="email"
            />
            <div className="text-xs text-red-500 mt-1">
              {errors?.issues.find((i) => i.path[0] === "email")?.message}
            </div>
          </div>

          <div>
            <Input
              className="w-full"
              placeholder="Password"
              type="password"
              name="password"
            />
            <div className="text-xs text-red-500 mt-1">
              {errors?.issues.find((i) => i.path[0] === "password")?.message}
            </div>
          </div>

          <Button className="w-full bg-primary" variant="solid" type="submit">
            Entrar
          </Button>
        </form>
      </div>
    </div>
  );
}
