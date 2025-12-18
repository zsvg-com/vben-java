package vben;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

/**
 * 启动程序
 *
 */

@SpringBootApplication
public class VbenMybatis {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(VbenMybatis.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("(♥◠‿◠)ﾉﾞ  Vben Mybatis启动成功   ლ(´ڡ`ლ)ﾞ");
    }

}
