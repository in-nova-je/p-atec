import {
  IconBriefcase2,
  IconCalendar,
  IconMapPin,
  IconUsers,
  TablerIcon,
} from "@tabler/icons-react";
import Image from "next/image";
import InfoCard from "./_components/InfoCard";
import EventCard from "@/components/EventCard";
import { hasEventEnded, isTimeBetween, parseEvents } from "@/lib/utils";
import events from "@/json/events.json";
import { Fragment } from "react/jsx-runtime";

const cards = [
  {
    icon: IconCalendar,
    label: "Data",
    info: "26 a 27 Dez.",
  },
  {
    icon: IconMapPin,
    label: "Local",
    info: "Campus ATEC",
  },
  {
    icon: IconBriefcase2,
    label: "Empresas",
    info: "50+",
  },
  {
    icon: IconUsers,
    label: "Participantes",
    info: "500+",
  },
];

export default function Home() {
  const filteredEvents = parseEvents(events).filter(
    (event) =>
      !hasEventEnded(new Date(), event.dateEnd) &&
      event.dateBegin.getDay() === new Date().getDay()
  );
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
      <div className="mt-34 px-4 text-justify w-screen flex flex-col items-center">
        <h2 className="text-primary">20ª Feira Empresarial</h2>
        <h3 className="mb-4 self-baseline">Sobre</h3>
        <p className="text-sm text-secondary">
          A Feira Empresarial ATEC liga talento jovem a oportunidades
          profissionais através do contacto com empresas e workshops ao longo de
          dois dias.
        </p>
        <h3 className="mt-4 self-baseline">Informações</h3>
        <div className="grid grid-cols-2 gap-2 w-full mt-4">
          {cards.map((card) => {
            return (
              <InfoCard
                key={card.label}
                icon={card.icon as TablerIcon}
                label={card.label}
                info={card.info}
              />
            );
          })}
        </div>
        <h3 className="my-4 self-baseline">Eventos</h3>
        {filteredEvents.length > 0 ? (
          <div className="flex w-screen overflow-x-scroll overflow-y-hidden px-4 [scrollbar-width:none] snap-x snap-mandatory gap-4 h-auto">
            {filteredEvents.map((event) => {
              return (
                <EventCard
                  key={event.title}
                  title={event.title}
                  dateBegin={event.dateBegin}
                  dateEnd={event.dateEnd}
                  location={event.location}
                />
              );
            })}
          </div>
        ) : (
          <p className="text-secondary">Não há eventos para hoje.</p>
        )}
      </div>
    </div>
  );
}
