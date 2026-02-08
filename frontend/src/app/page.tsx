import HeroBanner from "@/components/home/HeroBanner";
import ContentCarousel from "@/components/home/ContentCarousel";

const mockSections = [
  {
    title: "오늘의 추천",
    items: Array.from({ length: 12 }, (_, i) => ({
      id: i + 1,
      title: `추천 콘텐츠 ${i + 1}`,
      genre: "액션 · 판타지",
    })),
  },
  {
    title: "실시간 인기",
    items: Array.from({ length: 12 }, (_, i) => ({
      id: i + 100,
      title: `인기 콘텐츠 ${i + 1}`,
      genre: "로맨스 · 드라마",
    })),
  },
  {
    title: "신작 업데이트",
    items: Array.from({ length: 12 }, (_, i) => ({
      id: i + 200,
      title: `신작 콘텐츠 ${i + 1}`,
      genre: "코미디 · 일상",
    })),
  },
  {
    title: "에디터 추천",
    items: Array.from({ length: 12 }, (_, i) => ({
      id: i + 300,
      title: `에디터 픽 ${i + 1}`,
      genre: "SF · 스릴러",
    })),
  },
];

export default function Home() {
  return (
    <>
      <HeroBanner />
      {mockSections.map((section) => (
        <ContentCarousel
          key={section.title}
          title={section.title}
          items={section.items}
        />
      ))}
    </>
  );
}
