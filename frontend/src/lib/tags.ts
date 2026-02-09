import { API_BASE_URL } from "./api";

export interface Tag {
  tagId: number;
  tagName: string;
}

const FALLBACK_TAGS: Tag[] = [
  { tagId: 1, tagName: "액션" },
  { tagId: 2, tagName: "로맨스" },
  { tagId: 3, tagName: "판타지" },
  { tagId: 4, tagName: "코미디" },
  { tagId: 5, tagName: "드라마" },
  { tagId: 6, tagName: "스릴러" },
  { tagId: 7, tagName: "SF" },
  { tagId: 8, tagName: "일상" },
  { tagId: 9, tagName: "스포츠" },
  { tagId: 10, tagName: "음악" },
  { tagId: 11, tagName: "호러" },
  { tagId: 12, tagName: "미스터리" },
  { tagId: 13, tagName: "모험" },
  { tagId: 14, tagName: "학원" },
  { tagId: 15, tagName: "이세계" },
];

const CACHE_KEY = "cached_tags";
const CACHE_TTL = 1000 * 60 * 60; // 1시간

interface CachedData {
  tags: Tag[];
  timestamp: number;
}

function getCached(): Tag[] | null {
  try {
    const raw = localStorage.getItem(CACHE_KEY);
    if (!raw) return null;
    const cached: CachedData = JSON.parse(raw);
    if (Date.now() - cached.timestamp > CACHE_TTL) {
      localStorage.removeItem(CACHE_KEY);
      return null;
    }
    return cached.tags;
  } catch {
    return null;
  }
}

function setCache(tags: Tag[]) {
  try {
    const data: CachedData = { tags, timestamp: Date.now() };
    localStorage.setItem(CACHE_KEY, JSON.stringify(data));
  } catch {
    // localStorage 사용 불가 시 무시
  }
}

export async function fetchTags(): Promise<Tag[]> {
  // 캐시 확인
  const cached = getCached();
  if (cached) return cached;

  // API 호출
  try {
    const response = await fetch(`${API_BASE_URL}/api/contents/tags`);
    if (!response.ok) throw new Error(`HTTP ${response.status}`);
    const tags: Tag[] = await response.json();
    setCache(tags);
    return tags;
  } catch (error) {
    console.error("태그 조회 실패, fallback 사용:", error);
    return FALLBACK_TAGS;
  }
}
