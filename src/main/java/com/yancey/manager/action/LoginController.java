package com.yancey.manager.action;

import java.awt.image.BufferedImage;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.google.code.kaptcha.Producer;
import com.yancey.manager.common.ConstantsCMP;
import com.yancey.manager.domain.User;
import com.yancey.manager.form.MenuForm;
import com.yancey.manager.form.UserForm;
import com.yancey.manager.service.IUserService;
import com.yancey.manager.util.EncryptUtils;

@Controller
public class LoginController {

  private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
  @Autowired
  private IUserService        userService;

  @Autowired
  private Producer            captchaProducer;

  /**
   * 验证码生成工具
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping("/captchaImage")
  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

    response.setDateHeader("Expires", 0);
    // Set standard HTTP/1.1 no-cache headers.
    response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
    // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
    response.addHeader("Cache-Control", "post-check=0, pre-check=0");
    // Set standard HTTP/1.0 no-cache header.
    response.setHeader("Pragma", "no-cache");
    // return a jpeg
    response.setContentType("image/jpeg");
    // create the text for the image
    String capText = captchaProducer.createText();
    // store the text in the session
    request.getSession().setAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, capText);
    // create the image with the text
    BufferedImage bi = captchaProducer.createImage(capText);
    ServletOutputStream out = response.getOutputStream();
    // write the data out
    ImageIO.write(bi, "jpg", out);
    try {
      out.flush();
    } finally {
      out.close();
    }
    return null;
  }

  @RequestMapping(value = { "/" }, method = RequestMethod.GET)
  public String login() {
    return "login";
  }

  @RequestMapping(value = { "/login" }, method = RequestMethod.POST)
  public String login(String account, String password, HttpServletResponse response, String randomCode, HttpSession session, RedirectAttributes ra) {
    Subject currentUser = SecurityUtils.getSubject();
    String checkCode = (String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
    if (StringUtils.isEmpty(randomCode) || !StringUtils.equalsIgnoreCase(randomCode, checkCode)) {
      // 1验证码不正确
      ra.addFlashAttribute("msg", "验证码不正确");
      return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";
    }
    UsernamePasswordToken token = new UsernamePasswordToken(account, EncryptUtils.encryptMD5(password));
    token.setRememberMe(false);
    try {
      currentUser.login(token);
    } catch (AuthenticationException e) {
      // 2用户名或密码不正确
      ra.addFlashAttribute("msg", "用户名或密码不正确");
      logger.error(e.getMessage(), e);
      return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";
    }
    boolean validUser = currentUser.isAuthenticated();
    if (validUser) {
      UserForm form = new UserForm();
      form.setUserName(account);
      try {
        User user = userService.findUser(form);
        List<MenuForm> menus = userService.queryMenu(form);
        user.setMenuForms(menus);
        session.setAttribute(ConstantsCMP.USER_SESSION_INFO, user);
      } catch (Exception e) {
        logger.debug(e.getMessage(), e);
      }
      // SecurityUtils.getSubject().getSession().setTimeout(3600000);
      // System.out.println(SecurityUtils.getSubject().getSession().getTimeout());
      return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/user/index";
    } else {
      // 3当前帐号没有访问系统的权限
      ra.addFlashAttribute("msg", "当前帐号没有访问系统的权限");
      return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";
    }
  }
  /**
   * 退出系统
   * 
   * @return
   */
  @RequestMapping(value = { "/logout" })
  public String logout() {
    Subject currentUser = SecurityUtils.getSubject();
    try {
      currentUser.logout();
    } catch (AuthenticationException e) {
      logger.error(e.getMessage(), e);
    }
    return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";
  }

  @RequestMapping(value = { "/error/nopermission" })
  public String noPermission() {
    return "common/noPermission";
  }
}
