import { API_BASE_URL } from "./api";

export interface Tag {
  id: number;
  label: string;
}

const FALLBACK_TAGS: Tag[] = [
  { id: 1, label: "액션" },
  { id: 2, label: "로맨스" },
  { id: 3, label: "판타지" },
  { id: 4, label: "코미디" },
  { id: 5, label: "드라마" },
  { id: 6, label: "스릴러" },
  { id: 7, label: "SF" },
  { id: 8, label: "일상" },
  { id: 9, label: "스포츠" },
  { id: 10, label: "음악" },
  { id: 11, label: "호러" },
  { id: 12, label: "미스터리" },
  { id: 13, label: "모험" },
  { id: 14, label: "학원" },
  { id: 15, label: "이세계" },
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
