package vben.base.mon.job.root;

@java.lang.annotation.Documented
@java.lang.annotation.Target({java.lang.annotation.ElementType.TYPE})
@java.lang.annotation.Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface IJobGroup
{
    String value() default "";
}

