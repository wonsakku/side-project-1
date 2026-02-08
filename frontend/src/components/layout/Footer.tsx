import Link from "next/link";

export default function Footer() {
  return (
    <footer className="border-t border-[var(--border-color)] mt-12">
      <div className="max-w-[1400px] mx-auto px-4 lg:px-8 py-10">
        <div className="grid grid-cols-2 lg:grid-cols-4 gap-8">
          {/* Brand */}
          <div className="col-span-2 lg:col-span-1">
            <Link href="/" className="text-xl font-bold text-[var(--primary)]">
              StudyPlay
            </Link>
            <p className="text-sm text-[var(--text-secondary)] mt-3 leading-relaxed">
              최고의 콘텐츠를 가장 편리하게 즐기세요.
            </p>
          </div>

          {/* Links */}
          <div>
            <h4 className="text-sm font-semibold mb-4">서비스</h4>
            <ul className="space-y-2">
              <li>
                <Link href="/search" className="text-sm text-[var(--text-secondary)] hover:text-white transition-colors">
                  태그검색
                </Link>
              </li>
              <li>
                <Link href="/schedule" className="text-sm text-[var(--text-secondary)] hover:text-white transition-colors">
                  요일별 신작
                </Link>
              </li>
              <li>
                <Link href="/membership" className="text-sm text-[var(--text-secondary)] hover:text-white transition-colors">
                  멤버십
                </Link>
              </li>
            </ul>
          </div>

          <div>
            <h4 className="text-sm font-semibold mb-4">고객지원</h4>
            <ul className="space-y-2">
              <li>
                <Link href="/faq" className="text-sm text-[var(--text-secondary)] hover:text-white transition-colors">
                  자주 묻는 질문
                </Link>
              </li>
              <li>
                <Link href="/notice" className="text-sm text-[var(--text-secondary)] hover:text-white transition-colors">
                  공지사항
                </Link>
              </li>
              <li>
                <Link href="/contact" className="text-sm text-[var(--text-secondary)] hover:text-white transition-colors">
                  문의하기
                </Link>
              </li>
            </ul>
          </div>

          <div>
            <h4 className="text-sm font-semibold mb-4">정책</h4>
            <ul className="space-y-2">
              <li>
                <Link href="/terms" className="text-sm text-[var(--text-secondary)] hover:text-white transition-colors">
                  이용약관
                </Link>
              </li>
              <li>
                <Link href="/privacy" className="text-sm text-[var(--text-secondary)] hover:text-white transition-colors">
                  개인정보처리방침
                </Link>
              </li>
            </ul>
          </div>
        </div>

        {/* Bottom */}
        <div className="border-t border-[var(--border-color)] mt-8 pt-6 text-center">
          <p className="text-xs text-[var(--text-secondary)]">
            © 2026 StudyPlay. All rights reserved.
          </p>
        </div>
      </div>
    </footer>
  );
}
