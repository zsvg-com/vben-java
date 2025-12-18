package vben.common.jdbc.utils;


import vben.common.jdbc.dto.Ltree;

import java.util.ArrayList;
import java.util.List;

public class LtreeUtils {

    //高性能转换，但是要求顶层有pid,这里假设为0
//    public static List<Ltree> buildTree(List<Ltree> pidList) {
//        Map<String, List<Ltree>> pidListMap = pidList.stream().collect(Collectors.groupingBy(Ltree::getPid));
//        pidList.stream().forEach(item -> item.setChildren(pidListMap.get(item.getId())));
//        //返回结果也改为返回顶层节点的list
//        return pidListMap.get("0");
//    }

    public static List<Ltree> build(List<Ltree> nodes) {
        List<Ltree> list = new ArrayList<>();
        for (Ltree node : nodes) {
            if (node.getPid() == null) {
                list.add(findChildrenByTier(node, nodes));
            } else {
                boolean flag = false;
                for (Ltree node2 : nodes) {
                    if (node.getPid().equals(node2.getId())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    list.add(findChildrenByTier(node, nodes));
                }
            }
        }
        return list;
    }

    //递归查找子节点
    private static Ltree findChildrenByTier(Ltree node, List<Ltree> nodes) {
        for (Ltree item : nodes) {
            if (node.getId().equals(item.getPid())) {
                if (node.getChildren() == null) {
                    node.setChildren(new ArrayList<>());
                }
                node.getChildren().add(findChildrenByTier(item, nodes));
            }
        }
        return node;
    }

    public static <T extends Ltree> List<T> buildEtree(List<T> nodes) {
        List<T> list = new ArrayList<>();
        for (T node : nodes) {
            if (node.getPid() == null) {
                list.add(findEtreeChildrenByTier(node, nodes));
            } else {
                boolean flag = false;
                for (T node2 : nodes) {
                    if (node.getPid().equals(node2.getId())) {
                        flag = true;
                        break;
                    }
                }
                if (!flag) {
                    list.add(findEtreeChildrenByTier(node, nodes));
                }
            }
        }
        return list;
    }

    private static <T extends Ltree> T findEtreeChildrenByTier(T node, List<T> nodes) {
        for (T item : nodes) {
            if (node.getId().equals(item.getPid())) {
                if (node.getChildren() == null) {
                    node.setChildren(new ArrayList<>());
                }
                node.getChildren().add(findEtreeChildrenByTier(item, nodes));
            }
        }
        return node;
    }

}
