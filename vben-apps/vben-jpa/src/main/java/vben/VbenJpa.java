package vben;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;

/**
 * 启动程序
 *
 */

@SpringBootApplication
public class VbenJpa {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(VbenJpa.class);
        application.setApplicationStartup(new BufferingApplicationStartup(2048));
        application.run(args);
        System.out.println("(♥◠‿◠)ﾉﾞ  Vben Jpa启动成功   ლ(´ڡ`ლ)ﾞ");
    }

}
