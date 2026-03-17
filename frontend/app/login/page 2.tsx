"use client";

import Image from "next/image";
import Input from "@/components/Input";
import LinkComponent from "@/components/Link";
import Button from "@/components/Button";
import { useState } from "react";
import { LoggingInUser, RegisteringUser } from "@/lib/zod-schemas";
import z from "zod";

export default function Register() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [errors, setErrors] = useState<z.ZodError | null>(null);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log("Submitting form with values:", {
      email,
      password,
      confirmPassword,
    });
    try {
      LoggingInUser.parse({
        email,
        password,
        confirmPassword,
      });
      console.log("Form is valid, proceed with registration logic");
    } catch (error) {
      if (error instanceof z.ZodError) {
        setErrors(error);
      }
    }
  };

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
            alt="formandos da atec a trabalhar em conjunto"
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
        <form className="flex flex-col gap-2 w-full" onSubmit={handleSubmit}>
          <div>
            <Input
              className="w-full"
              placeholder="E-mail"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
            <div className="text-xs text-red-500 mt-1">
              {
                errors?.issues.find((issue) => issue.path[0] === "email")
                  ?.message
              }
            </div>
          </div>
          <div>
            <Input
              className="w-full"
              placeholder="Password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
            />
            <div className="text-xs text-red-500 mt-1">
              {
                errors?.issues.find((issue) => issue.path[0] === "password")
                  ?.message
              }
            </div>
          </div>

          <Button
            className="w-full bg-primary"
            variant="solid"
            disabled={!email || !password}
            type="submit"
          >
            Entrar
          </Button>
        </form>
      </div>
    </div>
  );
}
