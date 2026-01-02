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

export type Company = {
  name: string;
  logoPath: string;
  description: string;
};

export const DEFAULT_COMPANY: Company = {
  name: "Nome da Empresa",
  logoPath: "atec_logo.png",
  description: "Descrição da empresa.",
};
