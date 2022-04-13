package site.metacoding.blogv3.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import site.metacoding.blogv3.handler.ex.CustomException;

public class UtilValid {

    public static void 요청에러처리(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            // 이부분에서 data리턴인지 html 리턴인지 이것만 구분해서 터트려줘!!
            throw new CustomException(errorMap.toString());
        }
    }
}
