export type Event = {
  title: string;
  dateBegin: string;
  dateEnd: string;
  location: string;
};

export type ParsedEvent = {
  title: string;
  dateBegin: Date;
  dateEnd: Date;
  location: string;
};

export type Enterprise = {
  name: string;
  logoPath: string;
  description: string;
  fieldsOfInterest: string[];
  websiteUrl?: string;
};

export type User = {
  id: string;
  name: string;
  level: "3" | "4" | "5" | "Aquele que não sabemos o nome";
  isStudent: boolean;
};

export const DEFAULT_ENTERPRISE: Enterprise = {
  name: "Nome da Empresa",
  logoPath: "atec_logo.png",
  description: "Descrição da empresa.",
  fieldsOfInterest: ["Área de Interesse 1", "Área de Interesse 2"],
};
