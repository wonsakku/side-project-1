"use client";

import { useState, useEffect, useRef } from "react";
import { API_BASE_URL } from "@/lib/api";
import { fetchTags, Tag } from "@/lib/tags";

interface ContentCreateModalProps {
  isOpen: boolean;
  onClose: () => void;
}

export default function ContentCreateModal({ isOpen, onClose }: ContentCreateModalProps) {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [selectedTagIds, setSelectedTagIds] = useState<number[]>([]);
  const [age, setAge] = useState("");
  const [isCompleted, setIsCompleted] = useState(false);
  const [isLoading, setIsLoading] = useState(false);
  const [tags, setTags] = useState<Tag[]>([]);
  const [mainThumbnail, setMainThumbnail] = useState<File | null>(null);
  const [subThumbnail, setSubThumbnail] = useState<File | null>(null);
  const [mainPreview, setMainPreview] = useState<string>("");
  const [subPreview, setSubPreview] = useState<string>("");

  const mainInputRef = useRef<HTMLInputElement>(null);
  const subInputRef = useRef<HTMLInputElement>(null);

  useEffect(() => {
    fetchTags().then(setTags);
  }, []);

  if (!isOpen) return null;

  const toggleTag = (id: number) => {
    setSelectedTagIds((prev) =>
      prev.includes(id) ? prev.filter((t) => t !== id) : [...prev, id]
    );
  };

  const handleThumbnailChange = (file: File | null, type: "MAIN" | "SUB") => {
    if (type === "MAIN") {
      setMainThumbnail(file);
      if (file) {
        const reader = new FileReader();
        reader.onloadend = () => setMainPreview(reader.result as string);
        reader.readAsDataURL(file);
      } else {
        setMainPreview("");
      }
    } else {
      setSubThumbnail(file);
      if (file) {
        const reader = new FileReader();
        reader.onloadend = () => setSubPreview(reader.result as string);
        reader.readAsDataURL(file);
      } else {
        setSubPreview("");
      }
    }
  };

  const handleClose = () => {
    setTitle("");
    setDescription("");
    setSelectedTagIds([]);
    setAge("");
    setIsCompleted(false);
    setMainThumbnail(null);
    setSubThumbnail(null);
    setMainPreview("");
    setSubPreview("");
    onClose();
  };

  const handleCreate = async () => {
    if (!title.trim() || isLoading) return;

    setIsLoading(true);
    try {
      const formData = new FormData();

      // 기본 필드 추가
      formData.append("title", title.trim());
      formData.append("description", description.trim());
      formData.append("completed", String(isCompleted));

      if (age) {
        formData.append("age", age);
      }

      // tagIds 배열 추가
      selectedTagIds.forEach((id) => {
        formData.append("tagIds", String(id));
      });

      // 썸네일 추가
      let thumbnailIndex = 0;
      if (mainThumbnail) {
        formData.append(`thumbNails[${thumbnailIndex}].thumbNail`, mainThumbnail);
        formData.append(`thumbNails[${thumbnailIndex}].thumbnailType`, "MAIN");
        thumbnailIndex++;
      }
      if (subThumbnail) {
        formData.append(`thumbNails[${thumbnailIndex}].thumbNail`, subThumbnail);
        formData.append(`thumbNails[${thumbnailIndex}].thumbnailType`, "SUB");
      }

      const response = await fetch(`${API_BASE_URL}/api/contents`, {
        method: "POST",
        body: formData,
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

          {/* 설명 */}
          <div>
            <label className="block text-sm font-medium mb-1.5">설명</label>
            <textarea
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              placeholder="컨텐츠 설명을 입력하세요"
              rows={3}
              className="w-full text-sm bg-[var(--background)] border border-[var(--border-color)] rounded-lg px-3 py-2.5 text-[var(--foreground)] outline-none focus:border-[var(--primary)] transition-colors placeholder:text-[var(--text-secondary)] resize-none"
            />
          </div>

          {/* 썸네일 */}
          <div>
            <label className="block text-sm font-medium mb-2">썸네일</label>
            <div className="grid grid-cols-2 gap-3">
              {/* MAIN 썸네일 */}
              <div>
                <label className="block text-xs text-[var(--text-secondary)] mb-1.5">메인</label>
                <div
                  onClick={() => mainInputRef.current?.click()}
                  className="aspect-video border-2 border-dashed border-[var(--border-color)] rounded-lg cursor-pointer hover:border-[var(--text-secondary)] transition-colors overflow-hidden flex items-center justify-center bg-[var(--background)]"
                >
                  {mainPreview ? (
                    <img src={mainPreview} alt="Main thumbnail" className="w-full h-full object-cover" />
                  ) : (
                    <svg className="w-8 h-8 text-[var(--text-secondary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
                    </svg>
                  )}
                </div>
                <input
                  ref={mainInputRef}
                  type="file"
                  accept="image/*"
                  className="hidden"
                  onChange={(e) => handleThumbnailChange(e.target.files?.[0] || null, "MAIN")}
                />
                {mainThumbnail && (
                  <button
                    onClick={(e) => {
                      e.stopPropagation();
                      handleThumbnailChange(null, "MAIN");
                    }}
                    className="text-xs text-[var(--text-secondary)] hover:text-white mt-1"
                  >
                    제거
                  </button>
                )}
              </div>

              {/* SUB 썸네일 */}
              <div>
                <label className="block text-xs text-[var(--text-secondary)] mb-1.5">서브</label>
                <div
                  onClick={() => subInputRef.current?.click()}
                  className="aspect-video border-2 border-dashed border-[var(--border-color)] rounded-lg cursor-pointer hover:border-[var(--text-secondary)] transition-colors overflow-hidden flex items-center justify-center bg-[var(--background)]"
                >
                  {subPreview ? (
                    <img src={subPreview} alt="Sub thumbnail" className="w-full h-full object-cover" />
                  ) : (
                    <svg className="w-8 h-8 text-[var(--text-secondary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                      <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 16l4.586-4.586a2 2 0 012.828 0L16 16m-2-2l1.586-1.586a2 2 0 012.828 0L20 14m-6-6h.01M6 20h12a2 2 0 002-2V6a2 2 0 00-2-2H6a2 2 0 00-2 2v12a2 2 0 002 2z" />
                    </svg>
                  )}
                </div>
                <input
                  ref={subInputRef}
                  type="file"
                  accept="image/*"
                  className="hidden"
                  onChange={(e) => handleThumbnailChange(e.target.files?.[0] || null, "SUB")}
                />
                {subThumbnail && (
                  <button
                    onClick={(e) => {
                      e.stopPropagation();
                      handleThumbnailChange(null, "SUB");
                    }}
                    className="text-xs text-[var(--text-secondary)] hover:text-white mt-1"
                  >
                    제거
                  </button>
                )}
              </div>
            </div>
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
                  key={tag.tagId}
                  onClick={() => toggleTag(tag.tagId)}
                  className={`text-xs px-3 py-1.5 rounded-full border transition-colors ${
                    selectedTagIds.includes(tag.tagId)
                      ? "bg-[var(--primary)] border-[var(--primary)] text-white"
                      : "border-[var(--border-color)] text-[var(--text-secondary)] hover:border-[var(--text-secondary)]"
                  }`}
                >
                  {tag.tagName}
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
