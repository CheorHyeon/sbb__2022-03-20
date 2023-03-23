package com.mysite.sbb.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswerForm {
    @NotBlank(message = "내용은 필수항목합니다.")
//최대 사이즈,
    @Size(max=20_000, message = "내용을 20,000자 이하로 입력해주세요.")
    private String content;

}
