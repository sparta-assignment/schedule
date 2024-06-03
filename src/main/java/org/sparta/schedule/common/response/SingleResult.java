package org.sparta.schedule.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class SingleResult<T> extends CommonResult {
    T data;
}
