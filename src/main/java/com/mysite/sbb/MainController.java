package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
    @GetMapping("/sbb")
    @ResponseBody  // 이거 없으면 리턴값은 템플릿 폴더에 HTML 파일 이름으로가서 해당 파일 리턴
    public String index() {
        return "안녕하세요 sbb에 오신것을 환영합니다.";
    }
}
