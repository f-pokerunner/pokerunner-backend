package com.flab.pokerunner.service.user;

import com.flab.pokerunner.domain.dto.UserCommentDto;
import com.flab.pokerunner.domain.entity.UserJpo;
import com.flab.pokerunner.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserCommentService {

    private final UserRepository userRepository;

    public UserCommentDto getUserCommentInfo(int userId) {
        UserJpo foundUser = userRepository.findById(userId);
        return UserCommentDto.from(foundUser);
    }

    @Transactional
    public UserCommentDto addUserComment(UserCommentDto userCommentDto) {
        UserJpo foundUser = userRepository.findById(userCommentDto.userId);
        if (foundUser == null) return new UserCommentDto(0, "찾을 수 없는 사용자 아이디입니다. 코멘트 등록이 불가능합니다.");
        foundUser.setComment(userCommentDto.comment);
        return UserCommentDto.from(foundUser);
    }
}
