package com.flab.pokerunner.domain.dto;

import com.flab.pokerunner.domain.entity.UserJpo;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserCommentDto {
    public int userId;
    public String comment;

    public static UserCommentDto from(UserJpo userJpo) {
        if (userJpo.getComment() == null) {
            return new UserCommentDto(userJpo.getId(), "comment 가 비어있습니다.");
        }
        return new UserCommentDto(userJpo.getId(), userJpo.getComment());
    }
}
