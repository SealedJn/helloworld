package com.leetcode.Tree.FiveWeek;

import com.leetcode.TreeNode;
import com.leetcode.Tree.utils.PrintTree;

public class Tree_669 {
//    /**
//     * 递归法
//     *
//     * @param root
//     * @param low
//     * @param high
//     * @return
//     */
//    public TreeNode trimBST(TreeNode root, int low, int high) {
//        if (root == null) return null;
//
//        // 节点的值小于[low, high]，则返回该节点的右子树
//        if (root.val < low) {
//            return trimBST(root.right, low, high);
//        }
//        // 节点的值大于[low, high]，则返回该节点的左子树
//        else if (root.val > high) {
//            return trimBST(root.left, low, high);
//        }
//        root.left = trimBST(root.left, low, high);
//        root.right = trimBST(root.right, low, high);
//        return root;
//    }

    /**
     * @param root
     * @param low
     * @param high
     * @return
     */
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) return null;
        while (root != null && (root.val < low || root.val > high)) {
            if (root.val < low) root = root.right; // 小于low 往右走
            else root = root.left; // 大于low 往左走
        }
        TreeNode cur = root;
        // 处理左孩子元素小于L的情况
        while (cur != null) {
            if (cur.left != null && cur.left.val < low) {
                cur.left = cur.left.right;
            }
            cur = cur.left;
        }

        cur = root;
        while (cur != null) {
            if (cur.right != null && cur.right.val > high) {
                cur.right = cur.right.left;
            }
            cur = cur.right;
        }
        return root;
    }

    public static void main(String[] args) {
        TreeNode root_left_left = new TreeNode(4);
        TreeNode root_left_right = new TreeNode(5);
        TreeNode root_left = new TreeNode(2, root_left_left, root_left_right);

        TreeNode root_right_left = new TreeNode(6);
        TreeNode root_right_right = new TreeNode(7);
        TreeNode root_right = new TreeNode(3, root_right_left, root_right_right);

//        TreeNode root_left = new TreeNode(1);
//        TreeNode root_right = new TreeNode(2);
        TreeNode root = new TreeNode(1, root_left, root_right);

        Tree_669 tree_94 = new Tree_669();
        TreeNode result = tree_94.trimBST(root, 1, 5);
        PrintTree.printBinaryTree(result);
    }
}
