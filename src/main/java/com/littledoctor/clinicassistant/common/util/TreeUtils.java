package com.littledoctor.clinicassistant.common.util;

import com.littledoctor.clinicassistant.common.constant.Constant;
import com.littledoctor.clinicassistant.common.entity.TreeEntity;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @业务信息:
 * @Filename: TreeUtils.java
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
public class TreeUtils {

    /**
     * 将list转换为树形结构
     * @param source
     * @return
     */
    public static <T extends TreeEntity> List<T> listToTree(List<T> source) {
        List<T> result = new ArrayList<>();
        if (!CollectionUtils.isEmpty(source)) {
            for (T treeEntity : source) {
                if (treeEntity.getpId().equals(Constant.ROOT_NODE_ID)) {// 根节点
                    result.add(treeEntity);
                }
                // 找每个节点的子节点
                for (T it : source) {
                    /*1. Integer 类型的值在[-128,127] 期间,Integer 用 “==”是可以的   ， Integer  与 int 类型比较（==）比较的是值。
                      2. 如果要比较Integer的值，比较靠谱的是通过Integer.intValue();这样出来的就是int值，就可以直接比较了；或者equals()比较*/
                    if (it.getpId().equals(treeEntity.getId())) {
                        if (treeEntity.getChildren() == null) {
                            treeEntity.setChildren(new ArrayList<>());
                        }
                        treeEntity.getChildren().add(it);
                    }
                }
            }
        }
        return result;
    }
}
