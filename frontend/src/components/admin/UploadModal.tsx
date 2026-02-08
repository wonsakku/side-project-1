"use client";

import { useRef, useState, useCallback, DragEvent } from "react";

interface UploadModalProps {
  isOpen: boolean;
  onClose: () => void;
}

export default function UploadModal({ isOpen, onClose }: UploadModalProps) {
  const fileInputRef = useRef<HTMLInputElement>(null);
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [isDragging, setIsDragging] = useState(false);
  const [duration, setDuration] = useState("");

  const extractDuration = useCallback((file: File) => {
    const video = document.createElement("video");
    video.preload = "metadata";
    video.onloadedmetadata = () => {
      const totalSeconds = Math.floor(video.duration);
      const h = Math.floor(totalSeconds / 3600);
      const m = Math.floor((totalSeconds % 3600) / 60);
      const s = totalSeconds % 60;
      const pad = (n: number) => n.toString().padStart(2, "0");
      setDuration(h > 0 ? `${pad(h)}:${pad(m)}:${pad(s)}` : `${pad(m)}:${pad(s)}`);
      URL.revokeObjectURL(video.src);
    };
    video.src = URL.createObjectURL(file);
  }, []);

  if (!isOpen) return null;

  const handleFileSelect = (file: File | null) => {
    setSelectedFile(file);
    setDuration("");
    if (file) extractDuration(file);
  };

  const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    handleFileSelect(e.target.files?.[0] || null);
  };

  const handleDragOver = (e: DragEvent) => {
    e.preventDefault();
    setIsDragging(true);
  };

  const handleDragLeave = (e: DragEvent) => {
    e.preventDefault();
    setIsDragging(false);
  };

  const handleDrop = (e: DragEvent) => {
    e.preventDefault();
    setIsDragging(false);
    handleFileSelect(e.dataTransfer.files?.[0] || null);
  };

  const handleClose = () => {
    setSelectedFile(null);
    setDuration("");
    onClose();
  };

  const handleUpload = () => {
    if (!selectedFile) return;
    // TODO: 실제 업로드 로직
    console.log("업로드:", selectedFile);
    handleClose();
  };

  const formatFileSize = (bytes: number) => {
    if (bytes < 1024 * 1024) return `${(bytes / 1024).toFixed(1)} KB`;
    if (bytes < 1024 * 1024 * 1024) return `${(bytes / (1024 * 1024)).toFixed(1)} MB`;
    return `${(bytes / (1024 * 1024 * 1024)).toFixed(2)} GB`;
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
        className="relative bg-[var(--card-bg)] border border-[var(--border-color)] rounded-2xl w-full max-w-lg mx-4 p-6"
        onClick={(e) => e.stopPropagation()}
      >
        {/* Header */}
        <div className="flex items-center justify-between mb-6">
          <h2 className="text-xl font-bold">영상 업로드</h2>
          <button
            onClick={handleClose}
            className="text-[var(--text-secondary)] hover:text-white transition-colors"
          >
            <svg className="w-6 h-6" fill="none" stroke="currentColor" viewBox="0 0 24 24">
              <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>

        {/* Drop Zone */}
        <div
          className={`border-2 border-dashed rounded-xl p-10 text-center cursor-pointer transition-colors ${
            isDragging
              ? "border-[var(--primary)] bg-[var(--primary)]/10"
              : selectedFile
              ? "border-[var(--primary)] border-solid"
              : "border-[var(--border-color)] hover:border-[var(--text-secondary)]"
          }`}
          onClick={() => fileInputRef.current?.click()}
          onDragOver={handleDragOver}
          onDragLeave={handleDragLeave}
          onDrop={handleDrop}
        >
          <input
            ref={fileInputRef}
            type="file"
            accept="video/*"
            className="hidden"
            onChange={handleFileChange}
          />

          {selectedFile ? (
            <div>
              <svg className="w-12 h-12 mx-auto mb-3 text-[var(--primary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M5 13l4 4L19 7" />
              </svg>
              <p className="text-sm font-medium">파일이 선택되었습니다</p>
              <p className="text-xs text-[var(--text-secondary)] mt-1">
                다시 클릭하여 변경할 수 있습니다
              </p>
            </div>
          ) : (
            <div>
              <svg className="w-12 h-12 mx-auto mb-3 text-[var(--text-secondary)]" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M7 16a4 4 0 01-.88-7.903A5 5 0 1115.9 6L16 6a5 5 0 011 9.9M15 13l-3-3m0 0l-3 3m3-3v12" />
              </svg>
              <p className="text-sm text-[var(--text-secondary)] mb-1">
                파일을 드래그하거나 클릭하여 선택하세요
              </p>
              <p className="text-xs text-[var(--text-secondary)]">
                MP4, AVI, MOV 등 영상 파일
              </p>
            </div>
          )}
        </div>

        {/* File Info Inputs */}
        {selectedFile && (
          <div className="flex flex-col gap-3 mt-5">
            <div>
              <label className="block text-xs text-[var(--text-secondary)] mb-1.5">파일명</label>
              <input
                type="text"
                readOnly
                value={selectedFile.name}
                className="w-full text-sm bg-[var(--background)] border border-[var(--border-color)] rounded-lg px-3 py-2.5 text-[var(--foreground)] outline-none"
              />
            </div>
            <div className="flex gap-3">
              <div className="flex-1">
                <label className="block text-xs text-[var(--text-secondary)] mb-1.5">파일 크기</label>
                <input
                  type="text"
                  readOnly
                  value={formatFileSize(selectedFile.size)}
                  className="w-full text-sm bg-[var(--background)] border border-[var(--border-color)] rounded-lg px-3 py-2.5 text-[var(--foreground)] outline-none"
                />
              </div>
              <div className="flex-1">
                <label className="block text-xs text-[var(--text-secondary)] mb-1.5">러닝타임</label>
                <input
                  type="text"
                  readOnly
                  value={duration || "분석 중..."}
                  className="w-full text-sm bg-[var(--background)] border border-[var(--border-color)] rounded-lg px-3 py-2.5 text-[var(--foreground)] outline-none"
                />
              </div>
            </div>
          </div>
        )}

        {/* Actions */}
        <div className="flex gap-3 mt-6">
          <button
            onClick={handleClose}
            className="flex-1 text-sm font-medium py-2.5 rounded-lg bg-white/10 hover:bg-white/20 transition-colors"
          >
            취소
          </button>
          <button
            onClick={handleUpload}
            disabled={!selectedFile}
            className="flex-1 text-sm font-medium py-2.5 rounded-lg bg-[var(--primary)] hover:bg-[var(--primary-hover)] transition-colors disabled:opacity-40 disabled:cursor-not-allowed"
          >
            업로드
          </button>
        </div>
      </div>
    </div>
  );
}
