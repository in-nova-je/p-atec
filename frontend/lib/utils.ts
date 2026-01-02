import { Event, ParsedEvent } from "@/lib/types";

export function formatPortugueseDate(date: Date) {
  return date
    .toLocaleDateString("pt-PT", {
      weekday: "long",
      day: "2-digit",
      month: "long",
    })
    .split(" ")
    .map((word) =>
      word.length <= 2 ? word : word.charAt(0).toUpperCase() + word.slice(1)
    )
    .join(" ");
}

export function formatShortPortugueseDate(date: Date) {
  return date
    .toLocaleDateString("pt-PT", {
      day: "2-digit",
      month: "long",
    })
    .split(" ")
    .map((word) =>
      word.length <= 2 ? word : word.charAt(0).toUpperCase() + word.slice(1)
    )
    .join(" ");
}

export function isTimeBetween(time: Date, start: Date, end: Date) {
  const t = time.getTime();
  return t >= start.getTime() && t <= end.getTime();
}

export function hasEventEnded(time: Date, end: Date) {
  return time.getTime() >= end.getTime();
}

export function getDayLabel(date: Date) {
  const today = new Date();
  const tomorrow = new Date();

  today.setHours(0, 0, 0, 0);
  tomorrow.setDate(today.getDate() + 1);
  tomorrow.setHours(0, 0, 0, 0);

  const target = new Date(date);
  target.setHours(0, 0, 0, 0);

  if (target.getTime() === today.getTime()) {
    return `Hoje, ${formatShortPortugueseDate(target)}`;
  }

  if (target.getTime() === tomorrow.getTime()) {
    return `Amanhã, ${formatShortPortugueseDate(target)}`;
  }

  return formatPortugueseDate(date);
}

export function parseEvents(events: Event[]): ParsedEvent[] {
  return events.map((event) => ({
    ...event,
    dateBegin: new Date(event.dateBegin),
    dateEnd: new Date(event.dateEnd),
  }));
}

export function cn(...classes: (string | undefined)[]) {
  return classes.filter(Boolean).join(" ");
}
