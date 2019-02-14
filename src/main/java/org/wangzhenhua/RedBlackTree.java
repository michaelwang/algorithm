package org.wangzhenhua;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RedBlackTree {

  private Node root;

  private Set<Node> nodeSet;

  /**
   * 初始化传入一个节点集合，并以此集合构造出一个红黑树
   */
  public RedBlackTree(List<Node> nodes) {
    if (null == nodes || nodes.size() == 0) {
      return;
    }
    nodeSet = new HashSet<>(20);
    for (Node node : nodes) {
      insert(node);
    }
  }

  public Node getRoot() {
    return root;
  }

  public static class Node<T> {

    public Node(int key) {
      this.key = key;
    }

    private int key;

    private Node left;

    private Node right;

    private Node parent;

    private String color;

    public int getKey() {
      return key;
    }

    public void setKey(int key) {
      this.key = key;
    }

    public Node getLeft() {
      return left;
    }

    public void setLeft(Node left) {
      this.left = left;
    }

    public Node getRight() {
      return right;
    }

    public void setRight(Node right) {
      this.right = right;
    }

    public Node getParent() {
      return parent;
    }

    public void setParent(Node parent) {
      this.parent = parent;
    }

    public String getColor() {
      return color;
    }

    public void setColor(String color) {
      this.color = color;
    }
  }

  public Node search(Node key, Node baseNode) {
    if (key.getKey() < baseNode.getKey()) {
      return search(key, baseNode.getLeft());
    } else if (key.getKey() > baseNode.getKey()) {
      return search(key, baseNode.getRight());
    } else {
      return baseNode;
    }
  }

  /**
   * @param node 待插入节点
   */
  public void insert(Node node) {
    // 如果当前root是null，那么就将root设置为当前插入的节点
    if (root == null) {
      root = node;
      setBlack(node);
      return;
    }
    insert(root, node);
  }

  /**
   *   x            y
   *    \   ->     /
   *    y         x
   *
   *
   * @param x 当前旋转的节点
   */
  private void leftRotate(Node x) {

       Node y = x.right;
       x.right = y.left;
       if (y.left != null) {
         y.left.parent = x;
       }

       y.parent = x.parent;
       if (x.parent == null) {
           this.root = y;
       }else {
         if (x.parent.left == x) {
             x.parent.left = y;
         }else {
             x.parent.right = y;
         }
       }

       x.parent = y;
       y.left = x;

  }

  /**
   *     x           y
   *    /    ->       \
   *   y               x
   *
   * @param x 当前被旋转的节点
   */
  private void rightRotate(Node x) {
      Node y = x.left;
      x.left = y.right;
      if (y.right != null) {
          y.right.parent = x;
      }

      y.parent = x.parent;
      if (x.parent == null) {
          this.root = y;
      }else {
          if (x.parent.left == x){
              x.parent.left = y;
          }else {
              x.parent.right = y;
          }
      }

      x.parent = y;
      y.right = x;

  }

  /**
   * @param base 当前搜索节点
   * @param target 将要被插入的node
   */
  private Node insert(Node base, Node target) {

    if (target.key < base.key) {
      if (base.left != null) {
        return insert(base.left, target);
      }
      if (base.left == null) {
        base.left = target;
        target.parent = base;
      }
    }

    if (target.key >= base.key) {
      if (base.right != null) {
        return insert(base.right, target);
      }
      if (base.right == null) {
        base.right = target;
        target.parent = base;
      }
    }

    target.setColor(RED);
    insertCase1(target);
    BTreePrinter.printNode(root);
    return target;
  }

  private void insertCase1(Node node) {
    if (node.getParent() == null) {
        setBlack(node);
    }else {
         insertCase2(node);
    }
  }

  private void insertCase2(Node node) {
    if (BLACK.equals(node.getColor())) {
      return;
    }else
      insertCase3(node);
  }

  private void insertCase3(Node node) {
     if (RED.equals(node.getParent().color) && getUncle(node) != null && RED.equals(getUncle(node).getColor())) {
       setRed(getGrandfather(node));
       setBlack(getGrandfather(node).left);
       setBlack(getGrandfather(node).right);
       insertCase1(getGrandfather(node));
     } else
       insertCase4(node);
  }

  private void insertCase4(Node node) {
     if (RED.equals(node.getParent().color) && node.getParent().right == node &&
         getGrandfather(node).left == node.getParent()) {
         leftRotate(node);
         insertCase5(node);
     }else if (RED.equals(node.getParent().color) && node.getParent().left == node &&
         getGrandfather(node).right == node.getParent()) {
         rightRotate(node);
         insertCase5(node);
     }else
         insertCase5(node);
  }

  private void insertCase5(Node node) {
      if (RED.equals(node.getParent().getColor()) && node.getParent().left == node &&
          getGrandfather(node).left == node.getParent()) {
          rightRotate(getGrandfather(node));
          flipColor(node);
      }else if (RED.equals(node.getParent().getColor()) && node.getParent().right == node &&
          getGrandfather(node).right == node.getParent()) {
          leftRotate(getGrandfather(node));
          flipColor(node);
      }
  }


  private Node getUncle(Node node) {

    if (node.getParent() == null || node.getParent().getParent() == null) {
        return null;
    }

    if (node.getParent().getParent().left == node) {
      return node.getParent().getParent().right;
    }else {
      return node.getParent().getParent().left;
    }
  }

  private Node getGrandfather(Node node) {
    return node.getParent().getParent();
  }


  private static final String RED = "red";

  private static final String BLACK = "black";

  /**
   * 修复颜色
   * @param node
   * @param uncle
   * @param parent
   */
  private void flipColor(Node node) {
     Node parent = node.getParent();
     setBlack(parent);
     setRed(parent.right);
     setRed(parent.left);
  }

  private void setBlack(Node node) {
    node.setColor(BLACK);
  }

  private void setRed(Node node) {
    node.setColor(RED);
  }

  public void delete(Node key) {

  }

}
