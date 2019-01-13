package com.jangni.shiro.core;

import com.jangni.shiro.exception.CheckException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * ClassName ControllerAOP
 * Description ControllerAop
 *   <aop:aspectj-autoproxy />
 *   <beans:bean id="controllerAop" class="xxx.common.aop.ControllerAOP" />
 *
 *   <aop:config>
 *     <aop:aspect id="myAop" ref="controllerAop">
 *       <aop:pointcut id="target"
 *         expression="execution(public xxx.common.beans.ResultBean *(..))" />
 *       <aop:around method="handlerControllerMethod" pointcut-ref="target" />
 *     </aop:aspect>
 *   </aop:config>
 * Author Mr.Jangni
 * Date 2019/1/13 23:16
 * Version 1.0
 **/
@Aspect
@Configuration
public class ControllerAOP {
    //定义日志记录器--获取sl4j包下提供的logger
    Logger logger = LoggerFactory.getLogger(this.getClass());
    //线程副本类去记录各个线程的开始时间
    ThreadLocal<Long> startTime = new ThreadLocal<>();
    /*
     * 定义一个切入点
     * 1、execution 表达式主体
     * 2、第1个* 表示返回值类型  *表示所有类型
     * 3、包名  com.*.*.controller下
     * 4、第4个* 类名，com.*.*.controller包下所有类
     * 5、第5个* 方法名，com.*.*.controller包下所有类所有方法
     * 6、(..) 表示方法参数，..表示任何参数
     * @Pointcut("execution(public * com.*.*.controller.*.*(..))")
     * */
    @Pointcut("execution(public * com.*.*.controller.*.*(..))")
    public void execService(){

    }
    @Before("execService()")
    public void dobefore(JoinPoint joinPoint) {        //方法里面注入连接点
        logger.info("前置通知：");                     //info ,debug ,warn ,erro四种级别，这里我们注入info级别
        startTime.set(System.currentTimeMillis());

        //获取servlet请求对象---因为这不是控制器，这里不能注入HttpServletRequest，但springMVC本身提供ServletRequestAttributes可以拿到
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        logger.info("URL:" + request.getRequestURL().toString());         // 想那个url发的请求
        logger.info("METHOD:" + request.getMethod());
        logger.info("CLASS_METHOD:" + joinPoint.getSignature().getDeclaringTypeName() + "."
                + joinPoint.getSignature().getName());                     // 请求的是哪个类，哪种方法
        logger.info("ARGS:" + Arrays.toString(joinPoint.getArgs()));     // 方法本传了哪些参数
    }

    @Around("excudeService()")
    public Object twiceAsOld(ProceedingJoinPoint pjp){
        System.err.println ("切面执行了。。。。");
        long startTime = System.currentTimeMillis();

        ResultBean<?> result;

        try {
            result = (ResultBean<?>) pjp.proceed();
            logger.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
        } catch (Throwable e) {
            result = handlerException(pjp, e);
        }
        return result;
    }

    private ResultBean<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {
        ResultBean<?> result = new ResultBean();

        // 已知异常
        if (e instanceof CheckException) {
            result.setMsg(e.getLocalizedMessage());
            result.setCode(ResultBean.FAIL);
        } else {
            logger.error(pjp.getSignature() + " error ", e);

            result.setMsg(e.toString());
            result.setCode(ResultBean.FAIL);

            // 未知异常是应该重点关注的，这里可以做其他操作，如通知邮件，单独写到某个文件等等。
        }

        return result;
    }
}