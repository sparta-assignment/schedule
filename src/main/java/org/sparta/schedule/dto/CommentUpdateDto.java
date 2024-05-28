package org.sparta.schedule.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommentUpdateDto {
    @NotEmpty
    private String content;
    @NotEmpty
    private String writer;
}
