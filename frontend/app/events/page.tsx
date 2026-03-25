import EventCard from "@/components/EventCard";
import {
  getDayLabel,
  hasEventEnded,
  isTimeBetween,
  parseEvents,
} from "@/lib/utils";
import { Fragment } from "react/jsx-runtime";
import events from "@/json/events.json";

export default function Events() {
  const time = new Date().toLocaleTimeString([], {
    hour: "2-digit",
    minute: "2-digit",
  });
  const filteredEvents = parseEvents(events).filter(
    (event) => !hasEventEnded(new Date(), event.dateEnd),
  );
  return (
    <div className="flex font-sans p-4 flex-col">
      <div className="flex flex-col">
        <h1 className="mb-2">Eventos</h1>
        <p className="text-secondary">
          Encontra aqui os próximos eventos na feira do trabalho da ATEC!
        </p>
      </div>
      <hr className="text-secondary/25 my-5" />
      <div className="flex flex-col gap-4">
        {filteredEvents.map((event, i) => {
          const lastEvent = filteredEvents[Math.max(i - 1, 0)];
          const lastEventIsNow = isTimeBetween(
            new Date(),
            lastEvent.dateBegin,
            lastEvent.dateEnd,
          );
          const now = isTimeBetween(new Date(), event.dateBegin, event.dateEnd);
          const newDay =
            event.dateBegin.getDate() != lastEvent.dateBegin.getDate() ||
            lastEventIsNow;
          return (
            <div key={i}>
              {now ? (
                <h2 className="text-secondary my-2" key={i}>
                  {`Agora, às ${time}`}
                </h2>
              ) : (
                (newDay || (lastEventIsNow && !now) || (!now && i === 0)) && (
                  <h2 className="text-secondary my-2" key={i}>
                    {getDayLabel(event.dateBegin)}
                  </h2>
                )
              )}
              <EventCard
                key={event.title}
                title={event.title}
                dateBegin={event.dateBegin}
                dateEnd={event.dateEnd}
                location={event.location}
              />
            </div>
          );
        })}
      </div>
    </div>
  );
}
