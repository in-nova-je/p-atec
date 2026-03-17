import * as z from "zod";

export const RegisteringUser = z
  .object({
    email: z.string().email("Este e-mail não é válido"),
    password: z.string().min(8, "A password deve ter pelo menos 8 caracteres"),
    confirmPassword: z.string(),
  })
  .refine((data) => data.password === data.confirmPassword, {
    message: "As passwords não coincidem",
    path: ["confirmPassword"],
  });

export const LoggingInUser = z.object({
  email: z.string().min(1, "O nome não pode estar vazio"),
  password: z.string().min(1, "A password não pode estar vazia"),
});
