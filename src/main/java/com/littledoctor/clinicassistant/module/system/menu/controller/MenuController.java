package com.littledoctor.clinicassistant.module.system.menu.controller;

import com.littledoctor.clinicassistant.common.msg.Message;
import com.littledoctor.clinicassistant.common.entity.TreeEntity;
import com.littledoctor.clinicassistant.common.util.TreeUtils;
import com.littledoctor.clinicassistant.module.system.menu.entity.MenuEntity;
import com.littledoctor.clinicassistant.module.system.menu.service.MenuService;
import com.littledoctor.clinicassistant.module.system.menu.vo.MenuVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 周俊林
 * @Date: 2018/10/16 19:12
 * @Description: 系统管理--菜单管理
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MenuService menuService;

    /**
     * 保存菜单信息
     * @param menuEntity
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public MenuEntity save(MenuEntity menuEntity) {
        try {
            Assert.notNull(menuEntity, Message.PARAMETER_IS_NULL);
            return menuService.save(menuEntity);// 保存
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new MenuEntity();
    }

    /**
     * 查询菜单树
     * @return
     */
    @RequestMapping(value = "/queryTree", method = RequestMethod.GET)
    public Map<String, Object> queryTree() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<TreeEntity> data = menuService.findTreeEntity();
            map.put("code", "0");
            map.put("data", TreeUtils.listToTree(data));
            return map;
        } catch (Exception e) {
            map.put("code", "1");
            map.put("data", null);
            log.error(e.getMessage(), e);
        }
        return map;
    }

    /**
     * 根据角色查询菜单
     * @param roleId
     */
    @RequestMapping(value = "/queryTreeByRole", method = RequestMethod.GET)
    public List<MenuVo> queryTreeByRole(Long roleId) {
        try {
            // 后续若有需要再改成根据角色查询菜单
            return TreeUtils.listToTree(menuService.findAllByRole(roleId));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new ArrayList<>();
    }

    /**
     * 查询下拉菜单树
     * @param menuIds 需要排除的菜单id，逗号分隔
     * @return
     */
    @RequestMapping(value = "/querySelectTree", method = RequestMethod.GET)
    public Map<String, Object> querySelectTree(String menuIds) {
        Map<String, Object> map = new HashMap<>();
        try {
            List<TreeEntity> data = menuService.querySelectTree(menuIds);
            map.put("code", "0");
            map.put("data", data);
            return map;
        } catch (Exception e) {
            map.put("code", "1");
            map.put("data", null);
            log.error(e.getMessage(), e);
        }
        return map;
    }

    /**
     * 根据ID查询菜单
     * @param menuId
     * @return
     */
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    public MenuEntity getById(String menuId) {
        try {
            Assert.notNull(menuId, Message.PARAMETER_IS_NULL);
            return menuService.getById(menuId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new MenuEntity();
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable("id") Long menuId) {
        try {
            Assert.notNull(menuId, Message.PARAMETER_IS_NULL);
            return menuService.delete(menuId);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return false;
    }
}


