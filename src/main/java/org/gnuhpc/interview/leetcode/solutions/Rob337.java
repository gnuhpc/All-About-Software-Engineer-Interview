package org.gnuhpc.interview.leetcode.solutions;

import org.gnuhpc.interview.datastructure.tree.basicimpl.TreeNode;

import java.util.HashMap;
import java.util.Map;

public class Rob337 {
    /*
    Method1: DFS
     */
    public int rob(TreeNode root) {
        if (root == null) return 0;
        return Math.max(robInclude(root), robExclude(root));
    }

    public int robInclude(TreeNode node) {
        if (node == null) return 0;
        return robExclude(node.left) + robExclude(node.right) + node.val;
    }

    public int robExclude(TreeNode node) {
        if (node == null) return 0;
        return rob(node.left) + rob(node.right);
    }

    /*
     优化：dfs + 记忆化搜索
     */
    Map<TreeNode, Integer> mapInclude = new HashMap<>();
    Map<TreeNode, Integer> mapExclude = new HashMap<>();

    public int rob2(TreeNode root) {
        return Math.max(rob(root, true), rob(root, false));
    }

    public int rob(TreeNode node, boolean isInclude) {
        if (node == null) {
            return 0;
        }
        if (isInclude) {
            if (mapInclude.get(node) != null) {
                return mapInclude.get(node);
            }
            int rob = node.val + rob(node.left, false) +
                    rob(node.right, false);
            mapInclude.put(node, rob);
            return rob;
        } else {
            if (mapExclude.get(node) != null) {
                return mapExclude.get(node);
            }
            int leftMax = Math.max(rob(node.left, true),
                    rob(node.left, false));
            int rightMax = Math.max(rob(node.right, true),
                    rob(node.right, false));
            int rob = (leftMax + rightMax); //Exclude, so skip the value of root
            mapExclude.put(node, rob);
            return rob;
        }
    }


    //add by tina: memo search，需要想清楚状态方程，容易错
    private HashMap<TreeNode, Integer> map;

    public int rob3(TreeNode root) {
        if (root == null) return 0;
        map = new HashMap<>();
        return memoSearch(root);
    }

    public int memoSearch(TreeNode root) {
        if (root == null) return 0;
        if (map.containsKey(root)) return map.get(root);
        int res = Math.max(
                root.val +
                        (root.left == null ? 0 : memoSearch(root.left.left)) +
                        (root.left == null ? 0 : memoSearch(root.left.right)) +
                        (root.right == null ? 0 : memoSearch(root.right.left)) +
                        (root.right == null ? 0 : memoSearch(root.right.right)),
                memoSearch(root.left) + memoSearch(root.right));
        map.put(root, res);//第一版写成6行数据作比较了，严重跑偏
        return res;
    }


    /*
    动态规划
     */
    public int rob4(TreeNode root) {
        int[] res = dfs(root);
        return Math.max(res[0], res[1]);
    }

    // res[0] 选择当前节点的最大值， res[1] 不选择当前节点的最大值
    private int[] dfs(TreeNode node) {
        int[] res = {0, 0};
        if (node != null) {
            int[] left = dfs(node.left);
            int[] right = dfs(node.right);
            //选择当前结点则必可以选择 不选l1和r1的
            res[0] = node.val + left[1] + right[1];
            res[1] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        }
        return res;
    }

    public int rob5(TreeNode root) {
        int[] res = robRecursion(root);
        return Math.max(res[0],res[1]);
    }

    /**
     * 当前节点node存在两种情况，
     * 1.选当前，则左右子树不能选
     * 2.不选当前，则选左右子树最优（左右孩子不是必选，左最优+右最优）
     * 取两者中的较大值即可
     *
     * @param node node
     * @return 结果数组
     */
    private int[] robRecursion(TreeNode node) {
        if (node == null){
            // res 0-表示选择当前，1-表示不选当前
            return new int[]{0,0};
        }
        // 后序遍历，先考虑左右子树的情况
        int[] leftRes = robRecursion(node.left);
        int[] rightRes = robRecursion(node.right);
        // 选中根节点的情况，左右孩子是不选的
        int choose = node.val + leftRes[1] + rightRes[1];
        // 不选根节点的情况，左右孩子任意节点可选，所以要取左右孩子选或不选两者情况的较大值累加
        // 即左子树的最优解+右子树的最优解
        int noChoose = Math.max(leftRes[0],leftRes[1]) + Math.max(rightRes[0],rightRes[1]);
        // 0-选择当前节点的最大情况，1-不选当前节点的最大情况
        return new int[]{choose,noChoose};
    }

    //链接：https://leetcode-cn.com/problems/house-robber-iii/solution/hou-xu-shen-du-you-xian-bian-li-zi-di-xiang-shang-/

}
