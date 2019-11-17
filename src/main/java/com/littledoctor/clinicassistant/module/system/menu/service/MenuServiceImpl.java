package com.littledoctor.clinicassistant.module.system.menu.service;

import com.littledoctor.clinicassistant.common.constant.Constant;
import com.littledoctor.clinicassistant.common.entity.TreeEntity;
import com.littledoctor.clinicassistant.common.entity.TreeNodeType;
import com.littledoctor.clinicassistant.common.util.TreeUtils;
import com.littledoctor.clinicassistant.module.system.menu.dao.MenuRepository;
import com.littledoctor.clinicassistant.module.system.menu.entity.MenuEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2018/10/16 20:36
 * @Description:  系统管理--菜单管理
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuRepository menuRepository;

    @Override
    public MenuEntity save(MenuEntity menuEntity) {
        if (menuEntity != null) {
            if (menuEntity.getParentMenuId() == null) menuEntity.setParentMenuId((long)0);
            return menuRepository.saveAndFlush(menuEntity);
        }
        return new MenuEntity();
    }

    /**
     * 查询菜单树
     * @return
     */
    @Override
    public List<TreeEntity> findTreeEntity() throws Exception {
        List<TreeEntity> list = menuRepository.findTreeEntity();
        if (!CollectionUtils.isEmpty(list)) {
            // 设置节点类型
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setNodeType(TreeNodeType.MENU);
            }
        }
        return list;
    }

    /**
     * 根据ID查询菜单
     * @param menuId
     * @return
     */
    @Override
    public MenuEntity getById(String menuId) throws Exception {
        return   menuRepository.findById(Long.parseLong(menuId)).get();
    }

    /**
     * 查询下拉树
     * @param menuIds 需要被排除的ids，用逗号分隔
     * @return
     */
    @Override
    public List<TreeEntity> querySelectTree(String menuIds) throws Exception {
        List<TreeEntity> list = new ArrayList<>();
        if (StringUtils.isNotBlank(menuIds)) {
            // id串转换
            String[] ids = menuIds.split(Constant.SPLIT_STR);
            Integer[] id = new Integer[ids.length];
            for (int i = 0; i < ids.length; i++) {
                id[i] = Integer.parseInt(ids[i]);
            }
            // 查询
            list  = menuRepository.findSelectTree(id);
        } else {
            list = menuRepository.findTreeEntity();
        }
        list = TreeUtils.listToTree(list);// list转tree
        return list;
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @Override
    public boolean delete(Long menuId) throws Exception {
        if (menuId != null) {
            menuRepository.deleteById(menuId);
            return true;
        }
        return false;
    }
}
