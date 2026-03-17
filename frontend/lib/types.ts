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
  id: number;
  name: string;
  description: string;
  websiteUrl?: string;
};

export type Connection = {
  id: number;
  userId: number;
  enterpriseId: number;
  isInternshipNoJob: boolean;
  classname: string;
};

export type ApiUser = {
  id: number;
  name: string;
  level: "3" | "4" | "5" | "Aquele que não sabemos o nome";
  email: string;
  fieldsOfInterest: string;
  profilePicture: string;
  student: boolean;
};

export const DEFAULT_ENTERPRISE: Enterprise = {
  id: -1,
  name: "Empresa não encontrada",
  description:
    "A empresa que procuraste não foi encontrada. Verifica se o URL está correto.",
};
