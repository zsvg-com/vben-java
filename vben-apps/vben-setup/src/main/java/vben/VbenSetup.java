package vben;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

/**
 * 启动程序
 *
 * @author Lion Li
 */

@SpringBootApplication
public class VbenSetup {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(VbenSetup.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("(♥◠‿◠)ﾉﾞ  vben-setup初始化完成   ლ(´ڡ`ლ)ﾞ");
        System.exit(0);
    }

}
