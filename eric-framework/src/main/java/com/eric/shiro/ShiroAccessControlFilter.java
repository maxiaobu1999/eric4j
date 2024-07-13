package com.eric.shiro;

import com.eric.BaseResponse;
import com.eric.BaseResponse1;
import com.eric.constant.Constant;
import com.eric.exception.BusinessException;
import com.eric.exception.BusinessException1;
import com.eric.utils.JacksonUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * 自定义的 token 过滤器。
 * 提供了访问控制的基础功能；比如是否允许访问/当访问拒绝时如何处理等：
 */
public class ShiroAccessControlFilter extends AccessControlFilter {
    private static final Logger log = LoggerFactory.getLogger(ShiroAccessControlFilter.class);

    /**
     * 是否 允许 访问下一层
     * true： 允许，交下一个Filter 处理
     * false： 交给自己处理，往下执行 onAccessDenied 方法
     *
     * @param servletRequest
     * @param servletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info(request.getMethod());
        // 去除Session,每次都要验证token
        // Subject subject = getSubject(servletRequest, servletResponse);
        // 获取Subject，然后调用isAuthenticated()判断是否已经认证过了
        // return subject.isAuthenticated();
        return false;
    }

    /**
     * 表示访问拒绝时是否自己处理，
     * 如果返回 true 表示自己不处理且 继续拦截器执行，往下执行
     * 返回 false 表示自己已经处理了（比如重定向到另一个界面）处理完毕。
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info("onAccessDenied = " + request.getMethod());
        log.info("onAccessDenied = " + request.getRequestURL().toString());
        boolean access = true;
//        if (request.getMethod().equalsIgnoreCase("OPTIONS")){
//            return access;
//        }
        //判断客户端是否携带accessToken
        try {
            String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
            if (StringUtils.isEmpty(accessToken)) {  // 判断 token 是否为空
                throw new BusinessException(BaseResponse1.TOKEN_NOT_NULL);
            }
            // 判断是否有效
            BearerAuthToken token = new BearerAuthToken(accessToken);
            getSubject(servletRequest, servletResponse).login(token);
            // 登录之后， 进入CustomRealm类 进入认证与授权
        } catch (BusinessException e) {
            response(e.getCode(), e.getDefaultMessage(), servletRequest, servletResponse);
            log.info("onAccessDenied e.getDefaultMessage() = " + e.getDefaultMessage());
            access = false; // 直接返回客户端
        } catch (AuthenticationException e) {  // 认证异常
            if (e.getCause() instanceof BusinessException1) {
                BusinessException exception = (BusinessException) e.getCause();
                log.info("onAccessDenied exception.getDefaultMessage() = " + exception.getDefaultMessage());
                response(exception.getCode(), exception.getDefaultMessage(), servletRequest, servletResponse);
            } else {
                log.info("onAccessDenied BaseResponse1.SHIRO_AUTHENTICATION_ERROR.message() = " + BaseResponse1.SHIRO_AUTHENTICATION_ERROR.message());
                response(BaseResponse1.SHIRO_AUTHENTICATION_ERROR.messageCode(),
                        BaseResponse1.SHIRO_AUTHENTICATION_ERROR.message(), servletRequest, servletResponse);
            }
            access = false; // 直接返回客户端
        }
        return access; // 继续往下执行
    }

    /**
     * @param code
     * @param msg
     * @param res
     */
    private void response(int code, String msg, ServletRequest servletRequest, ServletResponse res) {
        // 自定义异常的类，用户返回给客户端相应的JSON格式的信息
        try {
            BaseResponse<?> result = new BaseResponse<>();
            result.setCode(code);
            result.setMsg(msg);

            HttpServletRequest reqs = (HttpServletRequest) servletRequest;
            String curOrigin = reqs.getHeader("Origin");
            HttpServletResponse response = (HttpServletResponse) res;
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, HEAD");
            response.setHeader("Access-Control-Max-Age", "3600");
//            response.setHeader("Access-Control-Allow-Headers", "access-control-allow-headers, access-control-allow-methods, access-control-allow-origin, authorization");
            response.setHeader("Access-Control-Allow-Headers", "Access-Control-Allow-Headers, Origin,Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,CompanyId,TrackId, Authorization");
            response.setContentType("application/json; charset=utf-8");
            String userJson = JacksonUtils.obj2json(result);
            // 写入到 流中，返回到客户端
            OutputStream out = response.getOutputStream();
            out.write(userJson.getBytes(StandardCharsets.UTF_8)); // "UTF-8"
            out.flush();
        } catch (IOException e) {
            log.error("error={}", e.getLocalizedMessage());
        }
    }
}
