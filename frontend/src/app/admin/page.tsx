"use client";

import { useState } from "react";
import UploadModal from "@/components/admin/UploadModal";
import ContentCreateModal from "@/components/admin/ContentCreateModal";

export default function AdminPage() {
  const [isUploadOpen, setIsUploadOpen] = useState(false);
  const [isContentCreateOpen, setIsContentCreateOpen] = useState(false);

  return (
    <div className="max-w-[1400px] mx-auto px-4 lg:px-8 py-10">
      <div className="flex items-center justify-between mb-8">
        <h1 className="text-2xl lg:text-3xl font-bold">관리자 페이지</h1>
        <div className="flex gap-3">
          <button
            onClick={() => setIsUploadOpen(true)}
            className="bg-[var(--primary)] hover:bg-[var(--primary-hover)] text-white text-sm font-medium px-5 py-2.5 rounded-lg transition-colors"
          >
            영상 업로드
          </button>
          <button
            onClick={() => setIsContentCreateOpen(true)}
            className="bg-[var(--primary)] hover:bg-[var(--primary-hover)] text-white text-sm font-medium px-5 py-2.5 rounded-lg transition-colors"
          >
            컨텐츠 생성
          </button>
        </div>
      </div>

      {/* 통계 카드 */}
      <div className="grid grid-cols-2 lg:grid-cols-4 gap-4 mb-10">
        {[
          { label: "총 콘텐츠", value: "1,234" },
          { label: "총 회원수", value: "5,678" },
          { label: "오늘 방문자", value: "890" },
          { label: "신규 가입", value: "23" },
        ].map((stat) => (
          <div
            key={stat.label}
            className="bg-[var(--card-bg)] border border-[var(--border-color)] rounded-xl p-5"
          >
            <p className="text-sm text-[var(--text-secondary)] mb-1">{stat.label}</p>
            <p className="text-2xl font-bold">{stat.value}</p>
          </div>
        ))}
      </div>

      {/* 관리 메뉴 */}
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-4">
        {[
          { title: "콘텐츠 관리", description: "콘텐츠 등록, 수정, 삭제" },
          { title: "회원 관리", description: "회원 조회, 권한 설정" },
          { title: "배너 관리", description: "메인 배너 등록 및 관리" },
        ].map((menu) => (
          <div
            key={menu.title}
            className="bg-[var(--card-bg)] border border-[var(--border-color)] rounded-xl p-6 hover:bg-[var(--card-hover)] transition-colors cursor-pointer"
          >
            <h3 className="text-lg font-semibold mb-2">{menu.title}</h3>
            <p className="text-sm text-[var(--text-secondary)]">{menu.description}</p>
          </div>
        ))}
      </div>

      {/* 업로드 모달 */}
      <UploadModal isOpen={isUploadOpen} onClose={() => setIsUploadOpen(false)} />
      <ContentCreateModal isOpen={isContentCreateOpen} onClose={() => setIsContentCreateOpen(false)} />
    </div>
  );
}
