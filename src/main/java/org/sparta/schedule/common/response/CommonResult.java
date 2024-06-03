package org.sparta.schedule.common.response;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CommonResult {
    private boolean success;
    private int code;
    private String msg;

    public void setSuccessResult() {
        this.success = true;
        this.code = HttpStatus.OK.value();
        this.msg = "성공했습니다.";
    }

    public void setFailureResult() {
        this.success = false;
        this.code = HttpStatus.INTERNAL_SERVER_ERROR.value();
        this.msg = "실패했습니다";
    }
}