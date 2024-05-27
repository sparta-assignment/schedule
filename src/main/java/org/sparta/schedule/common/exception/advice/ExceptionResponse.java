package org.sparta.schedule.common.exception.advice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class ExceptionResponse {
    String msg;
    String path;
}
