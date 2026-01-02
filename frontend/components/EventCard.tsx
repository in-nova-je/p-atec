"use client";
import { formatPortugueseDate, isTimeBetween } from "@/lib/utils";
import { IconClock, IconMapPin } from "@tabler/icons-react";

export default function EventCard({
  title,
  dateBegin,
  dateEnd,
  location,
}: {
  title: string;
  dateBegin: Date;
  dateEnd: Date;
  location: string;
}) {
  const startTime = dateBegin.toLocaleTimeString([], {
    hour: "2-digit",
    minute: "2-digit",
  });
  const endTime = dateEnd.toLocaleTimeString([], {
    hour: "2-digit",
    minute: "2-digit",
  });

  const now = isTimeBetween(new Date(), dateBegin, dateEnd);

  const formatted = formatPortugueseDate(dateBegin);
  return (
    <div
      className={`${
        now ? "border-2 border-primary" : "border border-secondary/25"
      } rounded-xl p-4`}
    >
      <div
        className={`text-sm ${
          now ? "text-primary font-medium" : "text-secondary"
        }`}
      >
        {formatted}
      </div>
      <div className="flex items-baseline gap-2">
        {now && (
          <div className="w-4 h-4 aspect-square rounded-full bg-primary" />
        )}
        <h2 className={`mb-2 ${now && "font-bold"}`}>{title}</h2>
      </div>
      <div
        className={`text-sm ${
          now ? "text-primary font-medium" : "text-secondary"
        } flex items-center gap-2`}
      >
        <IconClock className="w-4 h-4" />
        {startTime} - {endTime}
      </div>
      <div
        className={`text-sm ${
          now ? "text-primary font-medium" : "text-secondary"
        } flex items-center gap-2`}
      >
        <IconMapPin className="w-4 h-4" />
        {location}
      </div>
    </div>
  );
}
