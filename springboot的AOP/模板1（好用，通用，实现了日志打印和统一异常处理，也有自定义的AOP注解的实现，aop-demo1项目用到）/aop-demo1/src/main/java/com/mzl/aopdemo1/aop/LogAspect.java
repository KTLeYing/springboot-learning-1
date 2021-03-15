package com.mzl.aopdemo1.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

/**
 * @ClassName :   LogAspect
 * @Description: 日志切面类，使用aop来完成全局请求日志处理
 * @Author: mzl
 * @CreateDate: 2020/9/30 11:25
 * @Version: 1.0
 */
@Aspect
@Component    //组件来的，会注册到spring容器中
public class LogAspect {

  /**
   * 指定切点：定义切入点，切入点为com.mzl.aopdemo1.controller下的所有函数, 匹配com.mzl.aopdemo1.controller包及其子包下的所有类的所有方法
   * 作用是把当前类标识为一个切面供容器读取
   *
   * 创建一个切点，切面可以用通配符表示， 例如public （代表任意返回值类型）
   * com.spring.controller.WebSiteController.(..)//（..）表示任意参数: 也可以指定方法如：public
   * com.spring.controller.WebSiteController.view(..) 还可以任意类：public com.spring.controller..(..)都可以
   */
  /**
   * 1.@Pointcut是创建切入点 切入点方法不用写代码，返回类型为void
   * execution:用于匹配表达式
   * execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?
   * name-pattern(param-pattern)throws-pattern?) 修饰符匹配（modifier-pattern?）
   * 返回值匹配（ret-type-pattern）可以为*表示任何返回值,全路径的类名等 类路径匹配（declaring-type-pattern?）
   * 方法名匹配（name-pattern）可以指定方法名 或者 *代表所有, set* 代表以set开头的所有方法
   * 参数匹配（(param-pattern)）可以指定具体的参数类型，多个参数间用“,”隔开，各个参数也可以用“*”来表示匹配任意类型的参数，如(String)表示匹配一个String参数的方法；(*,String)
   * 表示匹配有两个参数的方法，第一个参数可以是任意类型，而第二个参数是String类型；可以用(..)表示零个或多个任意参数 异常类型匹配（throws-pattern?） 其中后面跟
   * 着“?”的是可选项
   * 1）execution(* *(..)) //表示匹配所有方法
   * 2）execution(public * com. example.controller.*(..))  //表示匹配com. example.controller中所有的public方法
   * 3）execution(* com. example.controller..*.*(..))  //表示匹配com. example.controller包及其子包下的所有方法
   */
  @Pointcut("execution(public * com.mzl.aopdemo1.controller.*.*(..))")
  public void webLog() {}

    /**
     * 前置通知：在连接点之前执行的通知,即法调用前被调用(执行方法前，根据切点规则拦截),标识一个前置增强方法，相当于BeforeAdvice的功能
     * @param
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        System.out.println("- - - - - 前置通知- - - - -");
        // AOP代理类的信息
        System.out.println("AOP代理类的信息" + joinPoint.getThis());
        // 代理的目标对象
        System.out.println("代理的目标对象：" + joinPoint.getTarget());

        //用的最多 通知的签名
        Signature signature = joinPoint.getSignature();
        //代理的是哪一个方法
        System.out.println("代理的是哪一个方法: " + signature.getName());
        //AOP代理类的名字
        System.out.println("AOP代理类的名字: " + signature.getDeclaringTypeName());
        // AOP代理类的类（class）信息
        System.out.println("代理的类的信息： " + signature.getDeclaringType());

        // 接收到请求，记录请求内容
        //获取RequestAttributes
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = attributes.getRequest();    //从servlet服务属性里获取请求
        //如果要获取Session信息的话，可以这样写：
        HttpSession session = (HttpSession) attributes.resolveReference(RequestAttributes.REFERENCE_SESSION);

        // 记录下请求内容
        System.out.println("URL:" + request.getRequestURI());
        System.out.println("HTTP_METHOD:" + request.getMethod());
        System.out.println("IP:" + request.getRemoteAddr());
        System.out.println("CLASS_METHOD:" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        System.out.println("ARGS：" + Arrays.toString(joinPoint.getArgs()));
    }

    /**
     * 后置返回通知，方法运行后执行,后置增强，相当于AfterReturningAdvice，方法退出时执行
     * 这里需要注意的是:
     *      如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     *      如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
     * @param result
     * @param
     */
    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void doAfterReturning(Object result) throws Throwable{
        // 处理完请求，返回内容，result是方法的返回值
        System.out.println("- - - - - 后置返回通知- - - - -");
        System.out.println("方法的返回值 : " + result);
    }

    /**
     * 后置异常通知，异常抛出增强，相当于ThrowsAdvice
     *  定义一个名字，该名字用于匹配通知实现方法的一个参数名，当目标方法抛出异常返回后，将把目标方法抛出的异常传给通知方法；
     *  throwing 限定了只有目标方法抛出的异常与通知方法相应参数异常类型时才能执行后置异常通知，否则不执行，
     *      对于throwing对应的通知方法参数为Throwable类型将匹配任何异常。
     * @param joinPoint
     * @param
     */
    @AfterThrowing(value = "webLog()", throwing = "e")     //throwing = "e"对应参数Exception e
//    @AfterThrowing("webLog()")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e){
        System.out.println("- - - - - 后置异常通知 - - - - -");
        //目标方法名：
        System.out.println("目标方法名：" + joinPoint.getSignature().getName());
        System.out.println("方法出现异常时执行.....");
        if (e instanceof NullPointerException){
             System.out.println("出现了空指针异常！");
        }
        if (e instanceof ArithmeticException){
            System.out.println("出现了算术异常，被除数为0了！");
        }
    }

    /**
     * 后置最终通知,final增强，不管是抛出异常或者正常退出都会执行，退出方法时执行（方法完全执行完了，方法结束了）
     * @param joinPoint
     * @throws Throwable
     */
    @After("webLog()")
    public void doAfter(JoinPoint joinPoint) throws Throwable{
        System.out.println("- - - - - 后置最终通知 - - - - -");
        System.out.println("后置通知执行了!!!");
    }

  /**
   * 环绕通知： 环绕增强，相当于MethodInterceptor  （是个过程，从开始到结尾）
   * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
   * (可以控制类中的方法是否可以执行，也可以修改参数，修改返回内容 ，植入方法处理逻辑)
   * 环绕通知第一个参数必须是org.aspectj.lang.ProceedingJoinPoint类型,ProceedingJoinPoint继承了JointPoint
   */
  @Around("webLog()")
  public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("- - - - -  环绕通知 - - - - -");
        System.out.println("环绕通知的目标方法名："+ proceedingJoinPoint.getSignature().getName());
        try{
            //proceedingJoinPoint.proceed()是在最后面执行的，也就是其他的切面方法执行完以后再执行proceed()方法
            Object object = proceedingJoinPoint.proceed();  //这里的object是方法的返回值
            System.out.println("方法环绕proceed，结果是 :" + object);
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            System.out.println("环绕通知 end !!!");
        }
      return null;
    }

  /**
   * 各方法参数说明： 除了@Around外，每个方法里都可以加或者不加参数JoinPoint，如果有用JoinPoint的地方就加，不加也可以，JoinPoint里包含了类名
   * 、被切面的方法名，参数等属性，可供读取使用。@Around参数必须为ProceedingJoinPoint，pjp.proceed相应于执行被切面的方法。 @AfterReturning方法里，可以加returning
   * = “XXX”，XXX即为在controller里方法的返回值，本例中的返回值是“first controller”。 @AfterThrowing方法里，可以加throwing =
   * "XXX"，供读取异常信息，如本例中可以改为：
   *
   * <p>//后置异常通知 @AfterThrowing(throwing = "ex", pointcut = "webLog()") public void
   * throwss(JoinPoint jp, Exception ex){ System.out.println("方法异常时执行....."); }
   */
  /**
   * spring aop就是一个同心圆，要执行的方法为圆心，最外层的order最小。从最外层按照AOP1、AOP2的顺序依次执行doAround方法，doBefore方法。
   * 然后执行method方法，最后按照AOP2、AOP1的顺序依次执行doAfter、doAfterReturn方法。也就是说对多个AOP来说，先before的，一定后after。
   * 对于上面的例子就是，先外层的就是对所有controller的切面，内层就是自定义注解的。
   * 那不同的切面，顺序怎么决定呢，尤其是同格式的切面处理，譬如两个execution的情况，那spring就是随机决定哪个在外哪个在内了。
   * 所以大部分情况下，我们需要指定顺序，最简单的方式就是在Aspect切面类上加上@Order(1)注解即可，order越小最先执行，也就是位于最外层。像一些全
   * 局处理的就可以把order设小一点，具体到某个细节的就设大一点。
   */
}
