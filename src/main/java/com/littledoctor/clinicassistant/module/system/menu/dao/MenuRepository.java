package com.littledoctor.clinicassistant.module.system.menu.dao;

import com.littledoctor.clinicassistant.common.entity.TreeEntity;
import com.littledoctor.clinicassistant.module.system.menu.entity.MenuEntity;
import com.littledoctor.clinicassistant.module.system.menu.vo.MenuVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Auther: 周俊林
 * @Date: 2018/10/16 20:34
 * @Description: 系统管理--菜单管理 dao
 */
public interface MenuRepository extends JpaRepository<MenuEntity,Long> {

    /**
     * 查询菜单树
     * @return
     */
    @Query(value = "select " +
            "new com.littledoctor.clinicassistant.common.entity.TreeEntity(t.menuName , t.menuId, t.parentMenuId) " +
            "from MenuEntity t order by t.menuOrder")
    List<TreeEntity> findTreeEntity();

    /**
     * 查询下拉菜单树
     * @param id
     * @return
     */
    @Query(value = "select " +
            "new com.littledoctor.clinicassistant.common.entity.TreeEntity(t.menuName , t.menuId, t.parentMenuId) " +
            "from MenuEntity t where t.menuId not in (?1) order by t.menuOrder")
    List<TreeEntity> findSelectTree(Long[] id);

    /**
     * 根据角色查询菜单
     * @param roleId
     * @return
     */
    @Query(value = "select " +
            "new com.littledoctor.clinicassistant.module.system.menu.vo.MenuVo(t.menuId, t.parentMenuId, t.menuName, t.menuUrl, t.menuIcon, t.menuOrder, t.isValid, t.menuId, t.parentMenuId, t.menuName) " +
            "from MenuEntity t where t.isValid = 1 order by t.menuOrder")
    List<MenuVo> findAllByRole(Long roleId);
}
