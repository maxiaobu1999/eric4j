package com.eric.shiro.realm;


import com.auth0.jwt.interfaces.Claim;
import com.eric.constant.Constant;
import com.eric.core.domain.entity.UserEntity;
import com.eric.jwt.JwtUtils;
import com.eric.redis.RedisUtils;
import com.eric.shiro.BearerAuthToken;
import com.eric.shiro.service.SysLoginService;
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

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * shiro 数据源
 * 自定义Realm 处理登录 权限
 *
 * @author zhimin
 */
public class ShiroRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(ShiroRealm.class);
    @Resource
    private RedisUtils redisUtils;
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
     *  获取身份认证信息
     *  前面被authc拦截后，需要认证，SecurityBean会调用这里进行认证
     */
    @Override
       protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        BearerAuthToken token = (BearerAuthToken) authenticationToken;
        // 三个参数：token，password，ShiroRealm字符串
        AuthenticationInfo info = new SimpleAuthenticationInfo(token.getToken(), token.getCredentials(), ShiroRealm.class.getName());
        return info;
    }

    /**
     * 获取授权信息
     * 10. 前面被roles拦截后，需要授权才能登录，SecurityManager需要调用这里进行权限查询
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 直接从 jwt 中拿即可，因为都在 claim载荷 中
        String accessToken = (String) principalCollection.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Long userId = JwtUtils.getUserId(accessToken);
        Map<String, Claim> claims = JwtUtils.claimsFromToken(accessToken);
        /*
         * 通过剩余的过期时间比较如果token的剩余过期时间大与标记key的剩余过期时间
         * 就说明这个token是在这个标记key之后生成的
         */
//        if (redisUtils.hasKey(Constant.JWT_REFRESH_KEY + userId) && redisUtils.getExpire(Constant.JWT_REFRESH_KEY + userId, TimeUnit.MILLISECONDS) > JwtUtils.getRemainingTime(accessToken)) {
//            //返回该用户的 角色信息 给授权器
//            List<String> roleNames = roleService.getRoleNamesByUserId(userId);
//            if (null != roleNames && !roleNames.isEmpty()) {
//                info.addRoles(roleNames);
//            }
//            //返回该用户的 权限信息 给授权器
//            Set<String> permissionPerms = permissionService.getPermissionPermsByUserId(userId);
//            if (permissionPerms != null) {
//                info.addStringPermissions(permissionPerms);
//            }
//        } else {
//            //返回该用户的 角色信息 给授权器
//            Map<String, ?> user = claims.get("user").asMap();
//            if (null != user.get(Constant.JWT_ROLES_KEY)) {
//                Collection<String> roles = (Collection<String>) user.get(Constant.JWT_ROLES_KEY);
//                info.addRoles(roles);
//            }
//            //返回该用户的 权限信息 给授权器
//            if (null != user.get(Constant.JWT_PERMISSIONS_KEY)) {
//                Collection<String> permissions = (Collection<String>) user.get(Constant.JWT_PERMISSIONS_KEY);
//                info.addStringPermissions(permissions);
//            }
//        }
        return info;
    }



    private void setTenantInfo(UserEntity user) {
//        if (user.isAdmin()) {
            return;
//        }
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
