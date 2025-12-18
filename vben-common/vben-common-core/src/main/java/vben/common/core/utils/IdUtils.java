package vben.common.core.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;

import java.util.Random;

public class IdUtils {

    public static long getSnowflakeNextId() {
        return IdUtil.getSnowflakeNextId();
    }

    public static String getSnowflakeNextIdStr() {
        return IdUtil.getSnowflakeNextIdStr();
    }

    public static String simpleUUID() {
        return UUID.randomUUID().toString(true);
    }

    public static String fastSimpleUUID() {
        return UUID.fastUUID().toString(true);
    }

    public static String getRSID(int length) {
        StringBuilder val = new StringBuilder();
        Random random = new Random();
        //参数length，表示生成几位随机数
        for(int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if( "char".equalsIgnoreCase(charOrNum) ) {
                //输出是大写字母还是小写字母
//                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + 97));
            } else {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();

    }


    public static String getRSID4() { return getRSID(4); }

    public static String getRSID8() {
        return getRSID(8);
    }

}
