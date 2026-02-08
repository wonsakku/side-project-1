package com.study.member.application.service;

import com.study.member.application.port.in.CreateMemberUseCase;
import com.study.member.application.port.out.MemberRepositoryPort;
import com.study.member.domain.Email;
import com.study.member.domain.Member;
import com.study.member.domain.MemberId;
import com.study.member.domain.Password;
import com.study.member.domain.PasswordFactory;
import com.study.member.domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberService implements CreateMemberUseCase {

    private final MemberRepositoryPort memberRepositoryPort;
    private final PasswordFactory passwordFactory;

    @Override
    public MemberId createMember(CreateMemberCommand command) {
        Email email = Email.of(command.email());

        if (memberRepositoryPort.existsByEmail(email)) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Password password = passwordFactory.create(command.rawPassword());

        LocalDateTime now = LocalDateTime.now();
        Member member = Member.create(email, password, command.name(), UserRole.USER, now, now);
        Member savedMember = memberRepositoryPort.save(member);

        return savedMember.getId();
    }
}
