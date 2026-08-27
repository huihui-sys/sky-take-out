package com.sky.aspect;


import com.sky.annotation.AutoFill;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.aspectj.lang.reflect.MethodSignature;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.sky.mapper.*.*(..)) &&@annotation(com.sky.annotation.AutoFill)")
    public void AutoFillPointCut() {
    };
        @Before("AutoFillPointCut()")
        public void AutoFill(JoinPoint joinPoint) {
            log.info("公共字段填充");

    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
    OperationType operationType = autoFill.value();

    //获取到当前被拦截的方法的参数--实体对象
    Object[] args = joinPoint.getArgs();
    if(args == null || args.length == 0|| args[0] == null){
        return;
    }
    Object entity = args[0];

    //准备赋值的数据
    LocalDateTime now = LocalDateTime.now();
    Long currentId = BaseContext.getCurrentId();

    //根据当前不同的操作类型，为对应的属性通过反射来赋值
    if(operationType == OperationType.INSERT){
        //为4个公共字段赋值
        try {
            Method setCreateTime = entity.getClass().getDeclaredMethod("setCreateTime", LocalDateTime.class);
            Method setCreateUser = entity.getClass().getDeclaredMethod("setCreateUser", Long.class);
            Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);

            //通过反射为对象属性赋值
            setCreateTime.invoke(entity,now);
            setCreateUser.invoke(entity,currentId);
            setUpdateTime.invoke(entity,now);
            setUpdateUser.invoke(entity,currentId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }else if(operationType == OperationType.UPDATE){
        //为2个公共字段赋值
        try {
            Method setUpdateTime = entity.getClass().getDeclaredMethod("setUpdateTime", LocalDateTime.class);
            Method setUpdateUser = entity.getClass().getDeclaredMethod("setUpdateUser", Long.class);

            //通过反射为对象属性赋值
            setUpdateTime.invoke(entity,now);
            setUpdateUser.invoke(entity,currentId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("自动填充反射出错", e);
        }
    }}
}

