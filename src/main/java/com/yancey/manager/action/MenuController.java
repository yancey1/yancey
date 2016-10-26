package com.yancey.manager.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yancey.manager.form.MenuForm;
import com.yancey.manager.service.IMenuService;
import com.yancey.manager.service.IRoleService;
import com.yancey.manager.util.ResponseUtils;

@Controller
@RequestMapping("/menu")
public class MenuController {

  private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

  @Autowired
  private IRoleService        roleService;

  @Autowired
  private IMenuService        menuService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String list(Model model) {
    return "menu/list";
  }

  @RequestMapping(value = "/list", params = "json")
  @ResponseBody
  public List<?> queryList(MenuForm form, HttpServletRequest request, Model model) throws Exception {
    String resourceId = request.getParameter("id");
    model.addAttribute("menu", new MenuForm());
    if (resourceId == null) {
      form.setParent(0);
    } else {
      form.setParent(Integer.parseInt(resourceId));
    }
    return menuService.findMenuTreeList(form);
  }

  @RequestMapping(value = "/parent", params = "json")
  @ResponseBody
  public List<?> queryParentList(MenuForm form, HttpServletRequest request) throws Exception {
    form.setParent(0);
    return menuService.findMenuTreeList(form);
  }

  /**
   * 跳转到新增菜单界面
   * 
   * @return
   */
  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String add(Model model) {
    return "menu/add";
  }
  /**
   * 保存菜单并跳转至列表页
   * 
   * @return
   */
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  @ResponseBody
  public void add(MenuForm form, HttpServletResponse response, Model model) {
    try {
      if (verifyFormInfo(form)) {
        menuService.saveMenu(form);
        ResponseUtils.responseSuccess(response);
      } else {
        ResponseUtils.responseInfoExists(response);
      }
    } catch (Exception e1) {
      logger.error(e1.getMessage(), e1);
      ResponseUtils.responseFailure(response);
    }
  }
  /**
   * 编辑菜单
   * 
   * @return
   */
  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public void update(MenuForm form, HttpServletResponse response, Model model) {
    logger.debug("receive menuForm -> {0}" + form);
    try {
      if (verifyFormInfo(form)) {
        menuService.modifyMenu(form);
        ResponseUtils.responseSuccess(response);
      } else {
        ResponseUtils.responseInfoExists(response);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      ResponseUtils.responseFailure(response);
    }

  }

  /**
   * 删除菜单
   * 
   * @return
   */
  @RequestMapping(value = "/{resourceId}/delete", method = RequestMethod.POST)
  public void delete(@PathVariable String resourceId, HttpServletResponse response, Model model) {
    logger.debug("menuId -> " + resourceId);
    try {
      menuService.deleteMenuById(resourceId);
      roleService.deleteRoleByMenuId(resourceId);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    ResponseUtils.responseSuccess(response);
    return;
  }
  @RequestMapping(value = "/form", params = "json")
  @ResponseBody
  public List<MenuForm> queryMenuforms() {
    MenuForm form = new MenuForm();
    List<MenuForm> result = null;
    try {
      result = menuService.findMenuForms(form);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return result;

  }

  /**
   * 检验form信息，检测form 信息中NotNull属性是否有空值 检查 菜单名是否已存在，
   * 
   * @param form
   * @return
   * @throws Exception
   */
  private boolean verifyFormInfo(MenuForm form) throws Exception {
    return true;
  }
}
