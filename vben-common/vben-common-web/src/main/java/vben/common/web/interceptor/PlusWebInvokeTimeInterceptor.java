package vben.common.web.interceptor;

import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.map.MapUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import vben.common.core.utils.DateUtils;
import vben.common.core.utils.ObjectUtils;
import vben.common.core.utils.StrUtils;
import vben.common.json.utils.JsonUtils;
import vben.common.web.filter.RepeatedlyRequestWrapper;

import java.io.BufferedReader;
import java.util.Map;

/**
 * web的调用时间统计拦截器
 *
 * @author Lion Li
 * @since 3.3.0
 */
@Slf4j
public class PlusWebInvokeTimeInterceptor implements HandlerInterceptor {

//    private final static ThreadLocal<StopWatch> KEY_CACHE = new ThreadLocal<>();
    private final static ThreadLocal<TimeInterval> KEY_CACHE2 = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getMethod() + " " + request.getRequestURI();

        // 打印请求参数
        if (isJsonRequest(request)) {
            String jsonParam = "";
            if (request instanceof RepeatedlyRequestWrapper) {
                BufferedReader reader = request.getReader();
                jsonParam = IoUtil.read(reader);
            }
            log.info("[PLUS]开始请求 => URL[{}],参数类型[json],参数:[{}]", url, jsonParam);
        } else {
            Map<String, String[]> parameterMap = request.getParameterMap();
            if (MapUtil.isNotEmpty(parameterMap)) {
                String parameters = JsonUtils.toJsonString(parameterMap);
                log.info("[PLUS]开始请求 => URL[{}],参数类型[param],参数:[{}]", url, parameters);
            } else {
                log.info("[PLUS]开始请求 => URL[{}],无参数", url);
            }
        }

//        StopWatch stopWatch = new StopWatch();
//        KEY_CACHE.set(stopWatch);
//        stopWatch.start();

        TimeInterval timer = DateUtils.timer();
        KEY_CACHE2.set(timer);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        StopWatch stopWatch = KEY_CACHE.get();
//        if (ObjectUtils.isNotNull(stopWatch)) {
//            stopWatch.stop();
//            log.info("[PLUS]结束请求 => URL[{}],耗时:[{}]毫秒", request.getMethod() + " " + request.getRequestURI(), stopWatch.getDuration().toMillis());
//            KEY_CACHE.remove();
//        }

        TimeInterval timer = KEY_CACHE2.get();
        if (ObjectUtils.isNotNull(timer)) {
            log.info("[PLUS2]结束请求 => URL[{}],耗时:[{}]毫秒", request.getMethod() + " " + request.getRequestURI(),  timer.interval());
            KEY_CACHE2.remove();
        }
    }

    /**
     * 判断本次请求的数据类型是否为json
     *
     * @param request request
     * @return boolean
     */
    private boolean isJsonRequest(HttpServletRequest request) {
        String contentType = request.getContentType();
        if (contentType != null) {
            return StrUtils.startWithIgnoreCase(contentType, MediaType.APPLICATION_JSON_VALUE);
        }
        return false;
    }

}
