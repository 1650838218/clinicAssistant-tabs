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
    protected String id;

    /*节点pId*/
    protected String pId;

    protected List<TreeEntity> children;

    public TreeEntity() {

    }

    public TreeEntity(String label, String id) {
        this.label = label;
        this.id = id;
    }

    public TreeEntity(String label, String id, String pId) {
        this.label = label;
        this.id = id;
        this.pId = pId;
    }

    public TreeEntity(String label, Long id, Long pId) {
        this.label = label;
        this.id = String.valueOf(id);
        this.pId = String.valueOf(pId);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public List<TreeEntity> getChildren() {
        return children;
    }

    public void setChildren(List<TreeEntity> children) {
        this.children = children;
    }
}
