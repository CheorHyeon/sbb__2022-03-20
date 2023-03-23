package com.mysite.sbb.Question;

import com.mysite.sbb.answer.AnswerForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/question")
@RequiredArgsConstructor
// @Validated Valid 사용하려면 필요하나, 컨트롤러의 경우 생략 가능
public class QuestionController {
    public final QuestionService questionService;
    
    @GetMapping("/list")
    public String list(Model model) {
        List<Question> questionList = questionService.getList();
        model.addAttribute("questionList",questionList);
        return "question_list";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/question/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = questionService.getQuestion(id);
        model.addAttribute("question", question);
        model.addAttribute("answerForm",new AnswerForm());
        return "question_detail";
    }

    @GetMapping("/create")
    // Question 오류가 있는 경우 (아래 메서드) 와 동일한 리턴(html)을 사용하므로
    // questionForm을 매개변수로 받지 않으면 오류가난다.
    // 아래 메소드는 매개변수이자 뷰에서 다룰수 있도록 html을 설계했음.
    // Valid를 하지 않는 이유는 당연히 빈 객체이니, 밸리드 하면 당연히 오류나기에 하지 않음.
    public String questionCreate2(Model model) {
        model.addAttribute("questionForm", new QuestionForm());
        return "question_form";
    }

    @PostMapping("/create")
    // @Valid 의 경우 제약사항 적용하기 위해 필요
    // questionForm 값을 바인딩 할 때 유효성 체크를 해라
    // Binding 없으면 2개 중 하나 안써도 저장 됨
    // questionForm 변수와 bindingResult 변수는 model.addAttribue 없이 바로 뷰에서 접근할 수 있다.
    // 매개변수로 바인딩한 객체는 Model 객체로 전달하지 않아도 템플릿에서 사용 가능
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()) {
            // question_form.html 실행
            // 다시 작성하라는 의미로 응답에 폼을 실어서 보냄
            return "question_form";
        }
        questionService.create(questionForm.getSubject(), questionForm.getContent());
        return "redirect:/question/list";
    }
}
