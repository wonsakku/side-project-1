package com.study.member.application.port.in;

import com.study.member.domain.MemberId;

public interface CreateMemberUseCase {

    MemberId createMember(CreateMemberCommand command);

    record CreateMemberCommand(String email, String rawPassword, String name) {
    }
}
