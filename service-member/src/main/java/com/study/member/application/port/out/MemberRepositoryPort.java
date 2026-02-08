package com.study.member.application.port.out;

import com.study.member.domain.Email;
import com.study.member.domain.Member;

public interface MemberRepositoryPort {

    Member save(Member member);

    boolean existsByEmail(Email email);
}
