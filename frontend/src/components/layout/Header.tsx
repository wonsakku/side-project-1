"use client";

import Link from "next/link";
import { useState } from "react";

export default function Header() {
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  return (
    <header className="fixed top-0 left-0 right-0 z-50 bg-[var(--nav-bg)] backdrop-blur-md border-b border-[var(--border-color)]">
      <div className="max-w-[1400px] mx-auto px-4 lg:px-8">
        <div className="flex items-center justify-between h-16">
          {/* Logo */}
          <Link href="/" className="text-xl font-bold text-[var(--primary)]">
            StudyPlay
          </Link>

          {/* Desktop Navigation */}
          <nav className="hidden lg:flex items-center gap-8">
            <Link
              href="/search"
              className="text-sm text-[var(--text-secondary)] hover:text-white transition-colors"
            >
              태그검색
            </Link>
            <Link
              href="/schedule"
              className="text-sm text-[var(--text-secondary)] hover:text-white transition-colors"
            >
              요일별 신작
            </Link>
            <Link
              href="/membership"
              className="text-sm text-[var(--text-secondary)] hover:text-white transition-colors"
            >
              멤버십
            </Link>
            <Link
              href="/admin"
              className="text-sm text-[var(--text-secondary)] hover:text-white transition-colors"
            >
              관리자페이지
            </Link>
          </nav>

          {/* Desktop Right Actions */}
          <div className="hidden lg:flex items-center gap-4">
            <button className="text-sm text-[var(--text-secondary)] hover:text-white transition-colors">
              로그인
            </button>
            <button className="text-sm bg-[var(--primary)] hover:bg-[var(--primary-hover)] text-white px-4 py-2 rounded-lg transition-colors">
              회원가입
            </button>
          </div>

          {/* Mobile Menu Button */}
          <button
            className="lg:hidden text-white p-2"
            onClick={() => setMobileMenuOpen(!mobileMenuOpen)}
          >
            <svg
              className="w-6 h-6"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
            >
              {mobileMenuOpen ? (
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M6 18L18 6M6 6l12 12"
                />
              ) : (
                <path
                  strokeLinecap="round"
                  strokeLinejoin="round"
                  strokeWidth={2}
                  d="M4 6h16M4 12h16M4 18h16"
                />
              )}
            </svg>
          </button>
        </div>

        {/* Mobile Menu */}
        {mobileMenuOpen && (
          <nav className="lg:hidden pb-4 border-t border-[var(--border-color)] pt-4 flex flex-col gap-4">
            <Link
              href="/search"
              className="text-sm text-[var(--text-secondary)] hover:text-white transition-colors"
            >
              태그검색
            </Link>
            <Link
              href="/schedule"
              className="text-sm text-[var(--text-secondary)] hover:text-white transition-colors"
            >
              요일별 신작
            </Link>
            <Link
              href="/membership"
              className="text-sm text-[var(--text-secondary)] hover:text-white transition-colors"
            >
              멤버십
            </Link>
            <div className="flex gap-3 pt-2">
              <button className="text-sm text-[var(--text-secondary)] hover:text-white transition-colors">
                로그인
              </button>
              <button className="text-sm bg-[var(--primary)] hover:bg-[var(--primary-hover)] text-white px-4 py-2 rounded-lg transition-colors">
                회원가입
              </button>
            </div>
          </nav>
        )}
      </div>
    </header>
  );
}
