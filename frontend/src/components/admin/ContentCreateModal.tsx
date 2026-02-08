"use client";

import { useState, useEffect } from "react";
import { API_BASE_URL } from "@/lib/api";
import { fetchTags, Tag } from "@/lib/tags";

interface ContentCreateModalProps {
  isOpen: boolean;
  onClose: () => void;
}

export default function ContentCreateModal({ isOpen, onClose }: ContentCreateModalProps) {
  const [title, setTitle] = useState("");
  const [selectedTagIds, setSelectedTagIds] = useState<number[]>([]);
  const [age, setAge] = useState("");
  const [isCompleted, setIsCompleted] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [tags, setTags] = useState<Tag[]>([]);

  useEffect(() => {
    fetchTags().then(setTags);
  }, []);

  if (!isOpen) return null;

  const toggleTag = (id: number) => {
    setSelectedTagIds((prev) =>
      prev.includes(id) ? prev.filter((t) => t !== id) : [...prev, id]
    );
  };

  const handleClose = () => {
    setTitle("");
    setSelectedTagIds([]);
    setAge("");
    setIsCompleted(false);
    onClose();
  };

  const handleCreate = async () => {
    if (!title.trim() || isLoading) return;

    setIsLoading(true);
    try {
      const response = await fetch(`${API_BASE_URL}/api/contents`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          title: title.trim(),
          tags: selectedTagIds,
          age: age ? Number(age) : null,
          completed: isCompleted,
        }),
      });

      if (!response.ok) {
        throw new Error(`HTTP ${response.status}`);
      }

      alert("컨텐츠가 생성되었습니다.");
      handleClose();
    } catch (error) {
      console.error("컨텐츠 생성 실패:", error);
      alert("컨텐츠 생성에 실패했습니다.");
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div
      className="fixed inset-0 z-50 flex items-center justify-center"
      onClick={handleClose}
    >
      {/* Backdrop */}
      <div className="absolute inset-0 bg-black/60 backdrop-blur-sm" />

      {/* Modal */}
      <div
        className="relative bg-[var(--card-bg)] border border-[var(--border-color)] rounded-2xl w-full max-w-lg mx-4 p-6 max-h-[90vh] overflow-y-auto"
        onClick={(e) => e.stopPropagation()}
      >
        {/* Header */}
        <div className="flex items-center justify-between mb-6">
          <h2 className="text-xl font-bold">컨텐츠 생성</h2>
          <button
            onClick={handleClose}
            className="text-[var(--text-secondary)] hover:text-white transition-colors"
          >
            <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>

        {/* Form */}
        <div className="flex flex-col gap-5">
          {/* 제목 */}
          <div>
            <label className="block text-sm font-medium mb-1.5">제목</label>
            <input
              type="text"
              value={title}
              onChange={(e) => setTitle(e.target.value)}
              placeholder="컨텐츠 제목을 입력하세요"
              className="w-full text-sm bg-[var(--background)] border border-[var(--border-color)] rounded-lg px-3 py-2.5 text-[var(--foreground)] outline-none focus:border-[var(--primary)] transition-colors placeholder:text-[var(--text-secondary)]"
            />
          </div>

          {/* 태그 선택 */}
          <div>
            <label className="block text-sm font-medium mb-1.5">
              태그 선택
              {selectedTagIds.length > 0 && (
                <span className="ml-2 text-xs text-[var(--primary)]">
                  {selectedTagIds.length}개 선택됨
                </span>
              )}
            </label>
            <div className="flex flex-wrap gap-2">
              {tags.map((tag) => (
                <button
                  key={tag.id}
                  onClick={() => toggleTag(tag.id)}
                  className={`text-xs px-3 py-1.5 rounded-full border transition-colors ${
                    selectedTagIds.includes(tag.id)
                      ? "bg-[var(--primary)] border-[var(--primary)] text-white"
                      : "border-[var(--border-color)] text-[var(--text-secondary)] hover:border-[var(--text-secondary)]"
                  }`}
                >
                  {tag.label}
                </button>
              ))}
            </div>
          </div>

          {/* 나이 */}
          <div>
            <label className="block text-sm font-medium mb-1.5">시청 연령</label>
            <input
              type="number"
              value={age}
              onChange={(e) => setAge(e.target.value)}
              placeholder="예: 15"
              min={0}
              className="w-full text-sm bg-[var(--background)] border border-[var(--border-color)] rounded-lg px-3 py-2.5 text-[var(--foreground)] outline-none focus:border-[var(--primary)] transition-colors placeholder:text-[var(--text-secondary)]"
            />
          </div>

          {/* 완결 여부 */}
          <div>
            <label className="flex items-center gap-3 cursor-pointer">
              <div
                className={`w-5 h-5 rounded border-2 flex items-center justify-center transition-colors ${
                  isCompleted
                    ? "bg-[var(--primary)] border-[var(--primary)]"
                    : "border-[var(--border-color)]"
                }`}
                onClick={() => setIsCompleted(!isCompleted)}
              >
                {isCompleted && (
                  <svg className="w-3 h-3 text-white" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                    <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={3} d="M5 13l4 4L19 7" />
                  </svg>
                )}
              </div>
              <span className="text-sm font-medium">완결 작품</span>
            </label>
          </div>
        </div>

        {/* Actions */}
        <div className="flex gap-3 mt-6">
          <button
            onClick={handleClose}
            className="flex-1 text-sm font-medium py-2.5 rounded-lg bg-white/10 hover:bg-white/20 transition-colors"
          >
            취소
          </button>
          <button
            onClick={handleCreate}
            disabled={!title.trim() || isLoading}
            className="flex-1 text-sm font-medium py-2.5 rounded-lg bg-[var(--primary)] hover:bg-[var(--primary-hover)] transition-colors disabled:opacity-40 disabled:cursor-not-allowed"
          >
            {isLoading ? "생성 중..." : "생성"}
          </button>
        </div>
      </div>
    </div>
  );
}
