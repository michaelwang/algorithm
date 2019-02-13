package org.wangzhenhua;

import java.util.HashSet;
import java.util.Set;

public class RedBlackTree {

  private Node root;

  private Set<Node> nodeSet;

  /**
   * 初始化传入一个节点集合，并以此集合构造出一个红黑树
   */
  public RedBlackTree(Set<Node> nodes) {
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
      return;
    }
    insert(root, node);
  }

  /**
   *
   *   case 1
   *         z                  z
   *        /                   /
   *        y    --->           x
   *         \                 /
   *          x               y
   *         /                \
   *        u                 u
   *
   *   case 2
   *        z                  z
   *         \                  \
   *         y   ----->          x
   *          \                  /
   *           x                y
   *          /                 \
   *         u                   u
   *
   * @param x 当前旋转的节点
   */
  private void leftRotate(Node x) {

     Node father = x.parent;
     if (father == null) {
         this.root = x;
         return;
     }

     Node leftChild = x.left;
     father.right = leftChild;
     if (leftChild != null) {
         leftChild.parent = father;
     }

     father.parent = x;
     x.left = father;

     Node grandfather = x.parent.parent;
     if (root == father) {
       this.root = x;
       return;
     }

     x.parent = grandfather;

     if (grandfather != null) {
         if (grandfather.left == father) {
             grandfather.left = x;
         }else {
             grandfather.right = x;
         }
     }

  }

  /**
   *   case 1
   *
   *      z              z
   *     /              /
   *    y     --->     x
   *   /                \
   *  x                 y
   *   \               /
   *    u             u
   *
   *   case 2
   *
   *   z                z
   *    \    ----->      \
   *     y                x
   *    /                  \
   *   x                    y
   *    \                  /
   *     u                u
   *
   * @param x 当前被旋转的节点
   */
  private void rightRotate(Node x) {

     Node father = x.parent;
     if (father == null) {
         root = x;
         return;
     }

     Node rightChild = x.right;
     father.left = rightChild;
     if (rightChild != null) {
       rightChild.parent = father;
     }

     father.parent = x;
     x.right = father;

     Node grandFather = father.parent;
     if (father == root) {
         this.root = x;
     }

     x.parent = grandFather;

     if (grandFather != null) {
       if (grandFather.left == father) {
         grandFather.left = x;
       }else {
         grandFather.right = x;
       }
     }

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
      }
    }

    if (target.key > base.key) {
      if (base.right != null) {
        return insert(base.right, target);
      }
      if (base.right == null) {
        base.right = target;
      }
    }

    target.setColor(RED);
    insertCase1(target);
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
     if (RED.equals(node.getParent().color) && RED.equals(getUncle(node).getColor())) {
       flipColor(node);
       insertCase1(node.getParent().getParent());
     }else
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
     }
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
     Node uncle = getUncle(node);
     Node parent = node.getParent();
     if (uncle != null && RED.equals(uncle.getColor())) {
       setBlack(parent);
       setBlack(uncle);
       setRed(node);
     }
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
