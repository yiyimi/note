

public class MultiRootTreeUtil {

    public static <T extends TreeNode> List<T> treeList(List<T> list) {
        List<T> resList = new ArrayList<>();
        List<T> rootNodeList = getRootNodeList(list);

        for(T rootNode ： rootNodeList){
            buildChildren(rootNode, list);
            resList.add(rootNode);
        }
        return resList;
    }

    /**
    * 递归子节点
    */
    private static <T extends TreeNode> void buildChildren(T node, List<T> list) {
        List<T> children = getRootNodeList(node, list);
        if(!children.isEmpty()){
            for(T child : children){
                buildChildren(child, list);
            }
            node.setChildren((List<TreeNode)children);
        }
    }

    /**
     * 获取父节点下的所有子节点
     */
    public static <T extends TreeNode> List<T> getChildren(TreeNode pNode, List<T> list) {
        List<T> children = new ArrayList<>();
        for (T node : list){
            if (pNode.getId().equals(node.getParentId())){
                children.add(node);
            }
        }
        return children;
    }

    /**
     * 判断是否为根节点
     */
    public static <T extends TreeNode> boolean rootNode(TreeNode node, List<T> list) {
        boolean isRootNode = true;
        for (TreeNode treeNode : list){
            if (node.getParentId().equals(treeNode.getId())) {
                isRootNode = false;
                break;
            }
        }
        return isRootNode;
    }

    /**
     * 获取集合中所有的根节点
     */
    public static <T extends TreeNode> List<T> getRootNode(List<T> list) {
        List<T> rootNodeList = new ArrayList<>();
        for (T node : list){
            if (rootNode(node, list)){
                rootNodeList.add(node);
            }
        }
        return rootNodeList;
    }
}