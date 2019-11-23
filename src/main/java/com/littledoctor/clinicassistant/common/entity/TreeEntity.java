package com.littledoctor.clinicassistant.common.entity;

import java.util.List;

/**
 * @业务信息:
 * @Filename: TreeEntity.java
 * @Description:
 * @Create Message:
 * Date         Author   Version   Description
 * ------------------------------------------------------------------
 * 2019-04-04   周俊林
 * @Modification History:
 * Date         Author   Version   Description
 * ------------------------------------------------------------------
 * 2019-04-04   周俊林
 */
public class TreeEntity {

    /*节点名称*/
    protected String label;

    /*节点Id*/
    protected Long id;

    /*节点pId*/
    protected Long pId;

    protected List<TreeEntity> children;

    public TreeEntity() {

    }

    public TreeEntity(String label, Long id, Long pId) {
        this.label = label;
        this.id = id;
        this.pId = pId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public List<TreeEntity> getChildren() {
        return children;
    }

    public void setChildren(List<TreeEntity> children) {
        this.children = children;
    }
}
