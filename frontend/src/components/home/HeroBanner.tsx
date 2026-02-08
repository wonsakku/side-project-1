"use client";

import { useState } from "react";

const bannerItems = [
  {
    id: 1,
    title: "인기 애니메이션 1",
    description: "지금 가장 인기 있는 애니메이션을 만나보세요",
    gradient: "from-purple-900 via-indigo-900 to-blue-900",
  },
  {
    id: 2,
    title: "신작 애니메이션 2",
    description: "이번 시즌 새롭게 시작하는 작품들",
    gradient: "from-rose-900 via-pink-900 to-purple-900",
  },
  {
    id: 3,
    title: "추천 애니메이션 3",
    description: "에디터가 엄선한 추천 작품",
    gradient: "from-emerald-900 via-teal-900 to-cyan-900",
  },
];

export default function HeroBanner() {
  const [current, setCurrent] = useState(0);

  const prev = () =>
    setCurrent((c) => (c === 0 ? bannerItems.length - 1 : c - 1));
  const next = () =>
    setCurrent((c) => (c === bannerItems.length - 1 ? 0 : c + 1));

  const item = bannerItems[current];

  return (
    <section className="relative w-full h-[50vh] lg:h-[65vh] overflow-hidden">
      {/* Background Gradient */}
      <div
        className={`absolute inset-0 bg-gradient-to-r ${item.gradient} transition-all duration-700`}
      />

      {/* Content */}
      <div className="relative z-10 flex flex-col justify-end h-full max-w-[1400px] mx-auto px-4 lg:px-8 pb-12 lg:pb-20">
        <h2 className="text-3xl lg:text-5xl font-bold mb-3 transition-all duration-500">
          {item.title}
        </h2>
        <p className="text-base lg:text-lg text-[var(--text-secondary)] mb-6 max-w-xl">
          {item.description}
        </p>
        <div className="flex gap-3">
          <button className="bg-[var(--primary)] hover:bg-[var(--primary-hover)] text-white px-6 py-3 rounded-lg font-medium transition-colors">
            지금 보기
          </button>
          <button className="bg-white/10 hover:bg-white/20 text-white px-6 py-3 rounded-lg font-medium backdrop-blur-sm transition-colors">
            상세 정보
          </button>
        </div>
      </div>

      {/* Bottom Gradient Overlay */}
      <div className="absolute bottom-0 left-0 right-0 h-32 bg-gradient-to-t from-[var(--background)] to-transparent" />

      {/* Navigation Arrows */}
      <button
        onClick={prev}
        className="absolute left-4 top-1/2 -translate-y-1/2 z-20 bg-black/30 hover:bg-black/50 text-white w-10 h-10 rounded-full flex items-center justify-center transition-colors"
      >
        <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 19l-7-7 7-7" />
        </svg>
      </button>
      <button
        onClick={next}
        className="absolute right-4 top-1/2 -translate-y-1/2 z-20 bg-black/30 hover:bg-black/50 text-white w-10 h-10 rounded-full flex items-center justify-center transition-colors"
      >
        <svg className="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M9 5l7 7-7 7" />
        </svg>
      </button>

      {/* Dots */}
      <div className="absolute bottom-6 left-1/2 -translate-x-1/2 z-20 flex gap-2">
        {bannerItems.map((_, i) => (
          <button
            key={i}
            onClick={() => setCurrent(i)}
            className={`w-2 h-2 rounded-full transition-all ${
              i === current
                ? "bg-[var(--primary)] w-6"
                : "bg-white/40 hover:bg-white/60"
            }`}
          />
        ))}
      </div>
    </section>
  );
}
