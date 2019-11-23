package com.littledoctor.clinicassistant.module.system.menu.vo;

import com.littledoctor.clinicassistant.common.entity.TreeEntity;

import java.math.BigDecimal;

/**
 * @Auther: 周俊林
 * @Date: 2019-11-23 14:54
 * @Description: 菜单VO
 */
public class MenuVo extends TreeEntity {

    /** 主键ID */
    private Long menuId;

    /** 父级菜单ID */
    private Long parentMenuId;

    /** 菜单名称 */
    private String menuName;

    /** 菜单URL */
    private String menuUrl;

    /** 菜单图标 */
    private String menuIcon;

    /** 菜单顺序号 */
    private BigDecimal menuOrder;

    /** 是否有效 */
    private Integer isValid;

    public MenuVo() {
    }

    public MenuVo(Long menuId, Long parentMenuId, String menuName, String menuUrl, String menuIcon, BigDecimal menuOrder, Integer isValid) {
        this.menuId = menuId;
        this.parentMenuId = parentMenuId;
        this.menuName = menuName;
        this.menuUrl = menuUrl;
        this.menuIcon = menuIcon;
        this.menuOrder = menuOrder;
        this.isValid = isValid;
    }

    public MenuVo(Long menuId, Long parentMenuId, String menuName, String menuUrl, String menuIcon, BigDecimal menuOrder, Integer isValid, Long id, Long pId, String label) {
        super(label, id, pId);
        this.menuId = menuId;
        this.parentMenuId = parentMenuId;
        this.menuName = menuName;
        this.menuUrl = menuUrl;
        this.menuIcon = menuIcon;
        this.menuOrder = menuOrder;
        this.isValid = isValid;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(Long parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public BigDecimal getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(BigDecimal menuOrder) {
        this.menuOrder = menuOrder;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }
}
