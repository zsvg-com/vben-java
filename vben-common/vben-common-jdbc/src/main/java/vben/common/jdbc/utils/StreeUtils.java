package vben.common.jdbc.utils;


import vben.common.jdbc.dto.Stree;

import java.util.ArrayList;
import java.util.List;

public class StreeUtils {

    //高性能转换，但是要求顶层有pid,这里假设为0
//    public static List<Stree> buildTree(List<Stree> pidList) {
//        Map<String, List<Stree>> pidListMap = pidList.stream().collect(Collectors.groupingBy(Stree::getPid));
//        pidList.stream().forEach(item -> item.setChildren(pidListMap.get(item.getId())));
//        //返回结果也改为返回顶层节点的list
//        return pidListMap.get("0");
//    }

    public static List<Stree> build(List<Stree> nodes) {
        List<Stree> list = new ArrayList<>();
        for (Stree node : nodes) {
            if (node.getPid() == null) {
                list.add(findChildrenByTier(node, nodes));
            } else {
                boolean flag = false;
                for (Stree node2 : nodes) {
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
    private static Stree findChildrenByTier(Stree node, List<Stree> nodes) {
        for (Stree item : nodes) {
            if (node.getId().equals(item.getPid())) {
                if (node.getChildren() == null) {
                    node.setChildren(new ArrayList<>());
                }
                node.getChildren().add(findChildrenByTier(item, nodes));
            }
        }
        return node;
    }

    public static <T extends Stree> List<T> buildEtree(List<T> nodes) {
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

    private static <T extends Stree> T findEtreeChildrenByTier(T node, List<T> nodes) {
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
