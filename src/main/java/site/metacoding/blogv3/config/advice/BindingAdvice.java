package site.metacoding.blogv3.config.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

@Aspect
@Component
public class BindingAdvice {

    @Around("execution(* site.metacoding.blogv3.web..*Controller.*(..))")
    public Object bindingValidation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        String type = proceedingJoinPoint.getSignature().getDeclaringTypeName();
        String method = proceedingJoinPoint.getSignature().getName();

        System.out.println(type);
        System.out.println(method);

        Object[] args = proceedingJoinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Errors) {
                Errors errors = (Errors) arg;
            }
        }
        return proceedingJoinPoint.proceed();
    }
}
