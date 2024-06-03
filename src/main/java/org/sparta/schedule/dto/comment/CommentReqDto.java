package org.sparta.schedule.dto.comment;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sparta.schedule.common.dto.CommonDto;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentReqDto implements CommonDto {
    @NotEmpty
    private String content;
}
