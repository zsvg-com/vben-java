package vben.base.mon.job.root;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface IJob
{
    String value() default "";

    String cron() default "";

    String name() default "";

    String code() default "";
}
