package com.eric.shiro.realm;

//import com.chinasoft.shunhe.common.core.domain.entity.SysDept;
//import com.chinasoft.shunhe.common.core.domain.entity.SysUser;
//import com.chinasoft.shunhe.common.exception.user.*;
//import com.chinasoft.shunhe.common.utils.ShiroUtils;
//import com.chinasoft.shunhe.common.utils.StringUtils;
//import com.chinasoft.shunhe.framework.shiro.service.SysLoginService;
//import com.chinasoft.shunhe.system.service.ISysDeptService;
//import com.chinasoft.shunhe.system.service.ISysMenuService;
//import com.chinasoft.shunhe.system.service.ISysRoleService;

import com.eric.core.domain.entity.SysUser;
import com.eric.exception.user.CaptchaException;
import com.eric.exception.user.RoleBlockedException;
import com.eric.exception.user.UserNotExistsException;
import com.eric.exception.user.UserPasswordNotMatchException;
import com.eric.exception.user.UserPasswordRetryLimitExceedException;
import com.eric.exception.user.UserBlockedException;
import com.eric.shiro.service.SysLoginService;
import com.eric.utils.ShiroUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * shiro 数据源
 * 自定义Realm 处理登录 权限
 *
 * @author zhimin
 */
@Component
public class UserRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

//    @Autowired
//    private ISysMenuService menuService;
//
//    @Autowired
//    private ISysRoleService roleService;
//
    @Autowired
    private SysLoginService loginService;
//
//    @Autowired
//    private ISysDeptService deptService;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
        SysUser user = ShiroUtils.getSysUser();
        // 角色列表
        Set<String> roles = new HashSet<String>();
        // 功能列表
        Set<String> menus = new HashSet<String>();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // 管理员拥有所有权限
        if (user.isAdmin()) {
            info.addRole("admin");
            info.addStringPermission("*:*:*");
        } else {
            // todo
//            roles = roleService.selectRoleKeys(user.getUserId());
//            menus = menuService.selectPermsByUserId(user.getUserId());
            // 角色加入AuthorizationInfo认证对象
            info.setRoles(roles);
            // 权限加入AuthorizationInfo认证对象
            info.setStringPermissions(menus);
        }

        return info;
    }

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String username = upToken.getUsername();
        String password = "";
        if (upToken.getPassword() != null) {
            password = new String(upToken.getPassword());
        }

        SysUser user = null;
        try {
            user = loginService.login(username, password);
        } catch (CaptchaException e) {
            throw new AuthenticationException(e.getMessage(), e);
        } catch (UserNotExistsException e) {
            throw new UnknownAccountException(e.getMessage(), e);
        } catch (UserPasswordNotMatchException e) {
            throw new IncorrectCredentialsException(e.getMessage(), e);
        } catch (UserPasswordRetryLimitExceedException e) {
            throw new ExcessiveAttemptsException(e.getMessage(), e);
        } catch (UserBlockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        } catch (RoleBlockedException e) {
            throw new LockedAccountException(e.getMessage(), e);
        } catch (Exception e) {
            log.info("对用户[" + username + "]进行登录验证..验证未通过{}", e.getMessage());
            throw new AuthenticationException(e.getMessage(), e);
        }

        setTenantInfo(user);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }

    private void setTenantInfo(SysUser user) {
        if (user.isAdmin()) {
            return;
        }
//        try {
//            // 将用户的商户ID、分店ID、门店ID 放到缓存里面
//            SysDept sysDept = deptService.selectDeptById(user.getDeptId());
//
//            if (sysDept != null) {
//                if (StringUtils.equals(sysDept.getLevel(), "1")) { // 商户
//                    user.setTenantId(sysDept.getDeptId());
//                } else if (StringUtils.equals(sysDept.getLevel(), "2")) { // 分店
//                    user.setTenantId(sysDept.getParentId());
//                    user.setBranchId(sysDept.getDeptId());
//                } else if (StringUtils.equals(sysDept.getLevel(), "3")) { // 门店
//                    SysDept tenantDept = deptService.selectDeptById(sysDept.getParentId());
//                    user.setTenantId(tenantDept.getParentId());
//                    user.setBranchId(sysDept.getParentId());
//                    user.setShopId(sysDept.getDeptId());
//                }
//            } else {
//                // 该用户的部门为空或不存在，给该用户设置一个不存在的商户ID
//                user.setTenantId(-1L);
//            }
//        } catch (Exception e) {
//            log.error("setTenantInfo failed, e: {}", e);
//        }
    }

    /**
     * 清理指定用户授权信息缓存
     */
    public void clearCachedAuthorizationInfo(Object principal) {
        SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
        this.clearCachedAuthorizationInfo(principals);
    }

    /**
     * 清理所有用户授权信息缓存
     */
    public void clearAllCachedAuthorizationInfo() {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        if (cache != null) {
            for (Object key : cache.keys()) {
                cache.remove(key);
            }
        }
    }
}
