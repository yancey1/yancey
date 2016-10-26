package com.yancey.manager.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.yancey.manager.domain.Role;
import com.yancey.manager.domain.User;
import com.yancey.manager.form.MenuForm;
import com.yancey.manager.form.UserForm;
import com.yancey.manager.service.IUserService;

public class MonitorRealm extends AuthorizingRealm {

  private static final Logger logger = LoggerFactory.getLogger(MonitorRealm.class);

  @Autowired
  private IUserService        userService;

  public MonitorRealm() {
    super();
  }

  /**
   * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调�?
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    String userName = (String) principals.fromRealm(getName()).iterator().next();
    UserForm form = new UserForm();
    form.setUserName(userName);
    User currentUser = null;
    try {
      currentUser = userService.findUser(form);
      List<Role> roles = currentUser.getRoles();
      List<MenuForm> list = userService.queryMenu(form);

      // 这里编写授权代码
      Set<String> roleSet = new HashSet<String>();
      Set<String> permissions = new HashSet<String>();
      if (roles != null && roles.size() > 0) {
        for (Role string : roles) {
          roleSet.add(string.getRoles());
        }
      }
      if (list != null && list.size() > 0) {
        for (MenuForm resource : list) {
          permissions.add(resource.getPermission());
        }
      }
      SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
      info.setRoles(roleSet);
      info.setStringPermissions(permissions);
      return info;
    } catch (Exception e) {
      logger.debug(e.getMessage(), e);
    }

    return null;
  }
  /**
   * 认证回调函数,登录时调�?
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
    UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
    UserForm form = new UserForm();
    form.setUserName(token.getUsername());
    User currentUser = null;
    try {
      currentUser = userService.findUser(form);
      System.out.println("22");
    } catch (Exception e) {
      throw new AuthenticationException();
    }
    if (currentUser == null) {
      throw new AuthenticationException();
    }
    return new SimpleAuthenticationInfo(currentUser.getUserName(), currentUser.getPassword(), getName());
  }

  /**
   * 更新用户授权信息缓存
   * 
   * @param principal
   */
  public void clearCachedAuthorizationInfo(String principal) {
    SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
    clearCachedAuthorizationInfo(principals);
  }

  /**
   * 清除�?��用户授权信息缓存.
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
