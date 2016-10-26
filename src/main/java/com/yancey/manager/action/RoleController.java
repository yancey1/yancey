package com.yancey.manager.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yancey.manager.domain.Role;
import com.yancey.manager.domain.RoleMenu;
import com.yancey.manager.domain.UserRole;
import com.yancey.manager.exception.IllegalParamException;
import com.yancey.manager.form.MenuForm;
import com.yancey.manager.form.RoleForm;
import com.yancey.manager.service.IMenuService;
import com.yancey.manager.service.IRoleService;
import com.yancey.manager.util.DataGridModel;
import com.yancey.manager.util.ResponseUtils;

@Controller
@RequestMapping("/role")
public class RoleController {

  private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
  @Autowired
  private IRoleService        roleService;
  @Autowired
  private IMenuService        menuService;

  @RequestMapping(value = "/list", method = RequestMethod.GET)
  public String list(Model model) {
    return "role/list";
  }

  @RequestMapping(value = "/list", params = "json")
  @ResponseBody
  public Map<String, Object> queryList(DataGridModel page, RoleForm form) throws Exception {
    return roleService.findRolePageList(page, form);
  }

  /**
   * 跳转到新增角色界面
   * 
   * @return
   */
  @RequestMapping(value = "/add", method = RequestMethod.GET)
  public String add() {
    return "role/add";
  }

  /**
   * 保存角色并跳转至列表页
   * 
   * @return
   */
  @RequestMapping(value = "/add", method = RequestMethod.POST)
  @ResponseBody
  public void add(RoleForm form, HttpServletResponse response, Model model) {
    try {
      // 验证角色是否已经存在\
      RoleForm rr = new RoleForm();

      rr.setRoleName(form.getRoleName());
      Role role = roleService.findRoleByName(rr);

      if (role == null) {
        roleService.saveRole(form);
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
   * 跳转到编辑角色界面
   * 
   * @return
   */
  @RequestMapping(value = "/{roleId}/update", method = RequestMethod.GET)
  public String update(@PathVariable String roleId, Model model) {
    RoleForm form = new RoleForm();
    form.setRoleId(StringUtils.isNotBlank(roleId) ? Integer.parseInt(roleId) : null);
    try {
      model.addAttribute("role", roleService.findRoleById(form));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return "role/update";
  }

  /**
   * 编辑角色
   * 
   * @return
   */
  @RequestMapping(value = "/{roleId}/update", method = RequestMethod.POST)
  public void update(@PathVariable String roleId, RoleForm form, HttpServletResponse response, Model model) {
    form.setRoleId((StringUtils.isNotBlank(roleId) ? Integer.parseInt(roleId) : null));
    try {
      if (verifyFormInfo(form)) {
        roleService.modifyRole(form);
        ResponseUtils.responseSuccess(response);
      } else { // 角色名重复，输入其他
        ResponseUtils.responseInfoExists(response);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      ResponseUtils.responseFailure(response);
    }

  }

  /**
   * 删除角色
   * 
   * @return
   */
  @RequestMapping(value = "/{roleId}/delete", method = RequestMethod.POST)
  public void delete(@PathVariable String roleId, HttpServletResponse response, Model model) {
    Role role = new Role();
    role.setRoleId(Integer.valueOf(roleId));

    try {
      List<UserRole> list = roleService.findUserRoles(role);

      if (list.size() > 0) {
        ResponseUtils.responseBeenApplied(response);
      } else {
        roleService.removeRole(role);
        ResponseUtils.responseSuccess(response);
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      ResponseUtils.responseFailure(response);
    }
  }

  /**
   * 跳转到权限管理界面
   * 
   * @return
   */

  @RequestMapping(value = "/{roleId}/authority", method = RequestMethod.GET)
  public String authority(@PathVariable String roleId, Model model) {
    RoleForm form = new RoleForm();
    form.setRoleId(StringUtils.isNotBlank(roleId) ? Integer.parseInt(roleId) : null);
    try {
      model.addAttribute("role", roleService.findRoleById(form));
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    return "role/authority";
  }
  @RequestMapping(value = "/{roleId}/authority", method = RequestMethod.POST)
  public void saveAuthority(@PathVariable Integer roleId, HttpServletRequest request, HttpServletResponse response, Model model) {
    String resObj = request.getParameter("resObj");
    String[] res = resObj.substring(1).split(";");
    List<RoleMenu> list = new ArrayList<RoleMenu>();
    for (int i = 0; i < res.length; i++) {
      String string = res[i];
      RoleMenu menu = new RoleMenu();
      menu.setRoleId(roleId);
      menu.setResourceId(Integer.parseInt(string));
      list.add(menu);
    }
    try {
      RoleMenu roleMenu = new RoleMenu();
      roleMenu.setRoleId(roleId);
      roleService.removeRoleMenu(roleMenu);
      roleService.saveRoleMenus(list);
      ResponseUtils.responseSuccess(response);
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
      ResponseUtils.responseFailure(response);
    }
  }

  @RequestMapping(value = "/menu", params = "json")
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
   * 检验form信息，检测form 信息中NotNull属性是否有空值 检查 角色名是否已存在，
   * 
   * @param form
   * @return
   * @throws Exception
   */
  private boolean verifyFormInfo(RoleForm form) throws Exception {
    if (form != null && form.getRoleName() != null && form.getRoles() != null) {
      // 角色名是否存在
      Role role = roleService.findRoleByName(form);
      if (role != null) {
        return false;// 存在
      }
      return true;
    }
    // 信息不完全
    throw new IllegalParamException("role form information not complete.");
  }

  /**
   * 根据role_name 动态获取角色
   * 
   * @param request
   * @param response
   * @param model
   */
  @RequestMapping(value = "/content", method = RequestMethod.GET)
  public void findlableFormList(HttpServletRequest request, HttpServletResponse response, Model model) {
    String roleName = request.getParameter("q");
    String defaultValues = request.getParameter("defaultValues");

    StringBuffer jsonData = new StringBuffer("[");
    try {
      RoleForm form = new RoleForm();
      form.setRoleName(roleName);
      List<Role> list = roleService.findRoleList(form);
      if (list != null && list.size() > 0) {
        for (Role role : list) {
          jsonData.append("{");
          jsonData.append("\"roleId\":\"" + role.getRoleId() + "\",");
          if (defaultValues != null) {
            String[] roleNames = defaultValues.split(";");
            if (roleNames.length > 0) {
              for (int i = 0; i < roleNames.length; i++) {
                String string = roleNames[i];
                if (role.getRoleName().equals(string)) {
                  jsonData.append("\"selected\":\"" + true + "\",");
                }

              }
            }
          }
          jsonData.append("\"roleName\":\"" + role.getRoleName() + "\"},");
        }
        jsonData.delete(jsonData.toString().length() - 1, jsonData.toString().length());
      }
    } catch (Exception e) {
      logger.error(e.getMessage(), e);
    }
    jsonData.append("]");
    ResponseUtils.renderJson(response, jsonData.toString());
  }

}
