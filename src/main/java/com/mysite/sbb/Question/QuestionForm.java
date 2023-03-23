package com.mysite.sbb.Question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionForm {
    //선언형
    // @NotNull : 널을 허용하지 않는다
    // @NotEmpty : 널 또는 빈문자열을 허용하지 않는다.
    // @NotBlank : 널 또는 빈문자열 + "  " 을 허용하지 않는다.
    @NotBlank(message = "제목은 필수항목입니다.") // NotEmpty 보다 좋음, "     " 스페이스만 친것도 데이터 없음 처리로 인식
    //최대 사이즈
    @Size(max=200, message = "제목을 200자 이하로 입력해주세요.")
    private String subject;
    @NotBlank(message = "내용은 필수항목합니다.")
    //최대 사이즈,
    @Size(max=20_000, message = "내용을 20,000자 이하로 입력해주세요.")
    private String content;
}
