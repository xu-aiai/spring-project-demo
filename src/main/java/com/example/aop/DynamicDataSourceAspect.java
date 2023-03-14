package com.example.aop;

import com.example.annotation.TargetDataSource;
import com.example.init.datasource.DataSourceContextHolder;
import java.util.Objects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

/**
 * 切面
 * @author xuhd32025
 * @date 2021-07-01 16:08
 **/
@Aspect
@Component
public class DynamicDataSourceAspect {

    @Pointcut("@annotation(com.example.annotation.TargetDataSource)")
    public void dataSourcePointCut(){

    }


    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String dsKey = getDSAnnotation(joinPoint).value();
        DataSourceContextHolder.setContextKey(dsKey);
        try{
            return joinPoint.proceed();
        }finally {
            DataSourceContextHolder.removeContextKey();
        }
    }


    /**
     * 根据类或方法获取数据源注解
     */
    private TargetDataSource getDSAnnotation(ProceedingJoinPoint joinPoint){
        Class<?> targetClass = joinPoint.getTarget().getClass();
        TargetDataSource dsAnnotation = targetClass.getAnnotation(TargetDataSource.class);
        // 先判断类的注解，再判断方法注解
        if(Objects.nonNull(dsAnnotation)){
            return dsAnnotation;
        }else{
            MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
            return methodSignature.getMethod().getAnnotation(TargetDataSource.class);
        }
    }

}
