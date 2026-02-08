"use client";

import { useRef } from "react";

interface ContentItem {
  id: number;
  title: string;
  genre: string;
}

interface ContentCarouselProps {
  title: string;
  items: ContentItem[];
}

export default function ContentCarousel({ title, items }: ContentCarouselProps) {
  const scrollRef = useRef<HTMLDivElement>(null);

  const scroll = (direction: "left" | "right") => {
    if (!scrollRef.current) return;
    const amount = scrollRef.current.clientWidth * 0.8;
    scrollRef.current.scrollBy({
      left: direction === "left" ? -amount : amount,
      behavior: "smooth",
    });
  };

  return (
    <section className="py-6">
      <div className="max-w-[1400px] mx-auto px-4 lg:px-8">
        {/* Section Header */}
        <div className="flex items-center justify-between mb-4">
          <h3 className="text-lg lg:text-xl font-bold">{title}</h3>
          <div className="flex gap-2">
            <button
              onClick={() => scroll("left")}
              className="w-8 h-8 rounded-full bg-[var(--card-bg)] hover:bg-[var(--card-hover)] flex items-center justify-center transition-colors"
            >
              <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
              </svg>
            </button>
            <button
              onClick={() => scroll("right")}
              className="w-8 h-8 rounded-full bg-[var(--card-bg)] hover:bg-[var(--card-hover)] flex items-center justify-center transition-colors"
            >
              <svg className="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
              </svg>
            </button>
          </div>
        </div>

        {/* Scrollable Content */}
        <div
          ref={scrollRef}
          className="flex gap-3 lg:gap-4 overflow-x-auto scrollbar-hide scroll-smooth"
          style={{ scrollbarWidth: "none", msOverflowStyle: "none" }}
        >
          {items.map((item) => (
            <div
              key={item.id}
              className="flex-shrink-0 w-[140px] lg:w-[200px] group cursor-pointer"
            >
              {/* Thumbnail Placeholder */}
              <div className="aspect-[3/4] rounded-lg bg-[var(--card-bg)] group-hover:bg-[var(--card-hover)] transition-all duration-300 group-hover:scale-105 mb-2 overflow-hidden">
                <div className="w-full h-full flex items-center justify-center text-[var(--text-secondary)]">
                  <svg className="w-10 h-10 opacity-30" fill="currentColor" viewBox="0 0 24 24">
                    <path d="M8 5v14l11-7z" />
                  </svg>
                </div>
              </div>
              {/* Info */}
              <h4 className="text-sm font-medium truncate group-hover:text-[var(--primary)] transition-colors">
                {item.title}
              </h4>
              <p className="text-xs text-[var(--text-secondary)] truncate">
                {item.genre}
              </p>
            </div>
          ))}
        </div>
      </div>
    </section>
  );
}
