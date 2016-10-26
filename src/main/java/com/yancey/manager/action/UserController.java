package com.yancey.manager.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yancey.manager.common.ConstantsCMP;
import com.yancey.manager.domain.Role;
import com.yancey.manager.domain.User;
import com.yancey.manager.domain.UserChannel;
import com.yancey.manager.domain.UserMarket;
import com.yancey.manager.domain.UserRole;
import com.yancey.manager.form.UserForm;
import com.yancey.manager.service.IUserService;
import com.yancey.manager.util.DataGridModel;
import com.yancey.manager.util.EncryptUtils;
import com.yancey.manager.util.ResponseUtils;
import com.yancey.manager.util.ReturnJsonCode;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/user")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @Autowired
  private IUserService        userService;

  @RequestMapping(value = "/login")
  public String login(HttpServletRequest request, Model model) {
    return "user/login";
  }
  
  /**
   * 用户中心-后台首页
   * 
   * @return
   */
  @RequestMapping(value = "/index")
  public String index(HttpServletRequest request, Model model) {
    try {
      model.addAttribute("user", ConstantsCMP.getSessionUser(request));
    } catch (Exception e) {
      logger.debug(e.getMessage(), e);
    }
    return "user/index";
  }
  /**
   * 用户列表
   * 
   * @param model
   * @return
   */
  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String list(Model model) {
    return "user/list";
  }

  @RequestMapping(value = "/list", params = "json")
  @ResponseBody
  public Map<String, Object> queryList(DataGridModel page, UserForm form) throws Exception {
    return userService.findUserPageList(page, form);
  }

  /**
   * 跳转到新增用户界面
   * 
   * @return
   */
  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String add() {
    return "user/add";
  }

  /**
   * 保存用户并跳转至列表页
   * 
   * @return
   */
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  @ResponseBody
  public void add(UserForm form, HttpServletResponse response, Model model) {
    if (form == null) {
      ResponseUtils.responseFailure(response);
      return;
    }
    try {
      // 验证用户是否已经存在
      if (StringUtils.isNotBlank(form.getUserName())) {
        UserForm query = new UserForm();
        query.setUserName(form.getUserName());
        if (userService.findUser(query) != null) {
          ResponseUtils.responseInfoExists(response);
          return;
        }
      }

      // 加密用户密码
      if (StringUtils.isNotBlank(form.getPassword())) {
        form.setPassword(EncryptUtils.encryptMD5(form.getPassword()));
      }

      userService.saveUser(form);

      // 存用户的角色
      Integer[] roleIds = form.getRoleId();
      List<UserRole> list = new ArrayList<UserRole>();
      for (int i = 0; i < roleIds.length; i++) {
        UserRole role = new UserRole();
        role.setUserId(form.getUserId());
        role.setRoleId(roleIds[i]);
        list.add(role);
      }
      userService.saveUserRoles(list);
      ResponseUtils.responseSuccess(response);
    } catch (Exception e1) {
      logger.error(e1.getMessage(), e1);
      ResponseUtils.responseFailure(response);
      return;
    }

  }

  /**
   * 跳转到添加渠道页面
   * 
   * @param userId
   * @param model
   * @return
   */
  @RequestMapping(value = "/{userId}/channel", method = RequestMethod.GET)
  public String channel(@PathVariable Integer userId, Model model) {
    UserForm form = new UserForm();
    form.setUserId(userId);

    try {
      User user = userService.findUser(form);
      model.addAttribute("user", user);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }

    return "user/channel";
  }

  @RequestMapping(value = "/{userId}/channel", method = RequestMethod.POST)
  public void saveChannel(@PathVariable Integer userId, HttpServletRequest request, HttpServletResponse response, Model model) {
    String cObj = request.getParameter("cObj");
    String[] cid = cObj.substring(1).split(";");

    List<UserChannel> list = new ArrayList<UserChannel>();

    for (int i = 0; i < cid.length; i++) {
      String channelId = cid[i];

      UserChannel userChannel = new UserChannel();

      userChannel.setUserId(userId);
      userChannel.setChannelId(Integer.parseInt(channelId));

      list.add(userChannel);
    }
    UserChannel form = new UserChannel();
    form.setUserId(userId);
    try {
      userService.removeUserChannel(form);
      userService.saveUserChannels(list);
      ResponseUtils.responseSuccess(response);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      ResponseUtils.responseFailure(response);
    }

  }

  /**
   * 跳转到添加市场页面
   * 
   * @param userId
   * @param model
   * @return
   */
  @RequestMapping(value = "/{userId}/market", method = RequestMethod.GET)
  public String market(@PathVariable Integer userId, Model model) {

    UserForm form = new UserForm();
    form.setUserId(userId);
    try {
      User user = userService.findUser(form);
      model.addAttribute("user", user);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }

    return "user/market";
  }

  @RequestMapping(value = "/{userId}/market", method = RequestMethod.POST)
  public void saveMarket(@PathVariable Integer userId, HttpServletRequest request, HttpServletResponse response, Model model) {

    String resObj = request.getParameter("resObj");
    String[] res = resObj.substring(1).split(";");

    List<UserMarket> list = new ArrayList<UserMarket>();
    for (int i = 0; i < res.length; i++) {
      String string = res[i];

      String[] userm = string.split("/");

      UserMarket form = new UserMarket();
      form.setUserId(userId);
      form.setMarketId(userm[0]);
      form.setPindaoId(Integer.parseInt(userm[1]));

      list.add(form);

    }

    try {
      UserMarket userMarket = new UserMarket();

      userMarket.setUserId(userId);
      userService.removeUserMarket(userMarket);
      userService.saveUserMarkets(list);

      ResponseUtils.responseSuccess(response);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      ResponseUtils.responseFailure(response);
    }
  }

  /**
   * 跳转到编辑用户界面
   * 
   * @return
   */
  @RequestMapping(value = "/{userId}/update", method = RequestMethod.GET)
  public String update(@PathVariable Integer userId, Model model) {
    UserForm form = new UserForm();
    form.setUserId(userId);
    try {

      User user = userService.findUser(form);

      String roleNames = "";
      List<Role> list = user.getRoles();
      for (Role role : list) {
        roleNames += role.getRoleName();
        roleNames += ";";
      }

      if (roleNames != "") {
        user.setRoleNames(roleNames.substring(0, roleNames.length() - 1));
      }

      model.addAttribute("user", user);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return "user/update";
  }

  /**
   * 编辑用户
   * 
   * @return
   */
  @RequestMapping(value = "/{userId}/update", method = RequestMethod.POST)
  public void update(@PathVariable String userId, UserForm form, HttpServletResponse response, Model model) {

    JSONObject resultJson = new JSONObject();
    UserForm userForm = new UserForm();
    userForm.setUserName(form.getUserName());
    form.setUserId(StringUtils.isNotBlank(userId) ? Integer.parseInt(userId) : null);

    try {
      User currentUser = userService.findUser(userForm);
      // 校验是否同一个用户
      if (currentUser == null || currentUser.getUserId().equals(form.getUserId().intValue())) {
        // 验证用户是否重置了密码
        if (StringUtils.isNotBlank(form.getPassword())) {
          form.setPassword(EncryptUtils.encryptMD5(form.getPassword()));
        }
        userService.modifyUser(form);
        // 更新用户角色
        UserRole role = new UserRole();
        role.setUserId(form.getUserId());

        userService.removeUserRole(role);

        Integer[] roleIds = form.getRoleId();
        List<UserRole> list = new ArrayList<UserRole>();
        for (int i = 0; i < roleIds.length; i++) {
          UserRole role1 = new UserRole();
          role1.setUserId(form.getUserId());
          role1.setRoleId(roleIds[i]);
          list.add(role1);
        }
        userService.saveUserRoles(list);
        ResponseUtils.responseSuccess(response);
      } else { // 登录名重复，输入其他
        resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.INFO_EXISTS.getCode());
        ResponseUtils.responseInfoExists(response);
        return;
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      ResponseUtils.responseFailure(response);
      return;
    }

  }

  /**
   * 删除用户
   * 
   * @return
   */
  @RequestMapping(value = "/{userId}/delete", method = RequestMethod.POST)
  public void delete(@PathVariable String userId, HttpServletResponse response, Model model) {
    // TODO 删除用户业务逻辑
    logger.debug("userId -> " + userId);
    try {
      UserForm form = new UserForm();
      form.setUserId(Integer.parseInt(userId));
      userService.removeUser(form);
      UserRole role = new UserRole();
      role.setUserId(Integer.parseInt(userId));
      userService.removeUserRole(role);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    ResponseUtils.responseSuccess(response);
    return;
  }

  /**
   * 跳转到修改密码界面
   * 
   * @return
   */
  @RequestMapping(value = "/resetPwd", method = RequestMethod.GET)
  public String resetPwd() {
    return "user/password";
  }
  /**
   * 修改密码
   * 
   * @return
   */
  @RequestMapping(value = "/resetPwd", method = RequestMethod.POST)
  @ResponseBody
  public void resetPwd(HttpServletRequest request, HttpServletResponse response, Model model) {
    String pwd = request.getParameter("pwd").replaceAll(" ", "");
    String npwd = request.getParameter("npwd").replaceAll(" ", "");
    String rpwd = request.getParameter("rpwd").replaceAll(" ", "");
    JSONObject resultJson = new JSONObject();
    try {
      User user = ConstantsCMP.getSessionUser(request);
      // 1、验证当前密码是否正确
      if (!user.getPassword().equals(EncryptUtils.encryptMD5(pwd))) {
        resultJson.put(ReturnJsonCode.RETURN_CODE, "100");
        resultJson.put(ReturnJsonCode.RETURN_MSG, "原密码输入不正确！");
        ResponseUtils.renderJson(response, resultJson.toString());
        return;
      }
      // 2、验证新密码是否相等
      if (!npwd.equals(rpwd)) {
        resultJson.put(ReturnJsonCode.RETURN_CODE, "101");
        resultJson.put(ReturnJsonCode.RETURN_MSG, "新密码两次输入不一致！");
        ResponseUtils.renderJson(response, resultJson.toString());
        return;
      }
      UserForm form = new UserForm();
      form.setUserId(user.getUserId());
      form.setPassword(EncryptUtils.encryptMD5(rpwd));
      userService.modifyUser(form);
      resultJson.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.SUCCESS.getCode());
      resultJson.put(ReturnJsonCode.RETURN_MSG, "密码修改成功，请重新登录系统！");
      ResponseUtils.renderJson(response, resultJson.toString());

      Subject currentUser = SecurityUtils.getSubject();
      try {
        currentUser.logout();
      } catch (AuthenticationException e) {
        logger.error(e.getMessage(), e);
      }
      return;
    } catch (Exception e1) {
      logger.error(e1.getMessage(), e1);
      ResponseUtils.responseFailure(response);
      return;
    }
  }

}
