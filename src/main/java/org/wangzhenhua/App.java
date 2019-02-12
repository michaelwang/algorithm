package org.wangzhenhua;

import java.util.HashSet;
import java.util.Set;
import org.wangzhenhua.RedBlackTree.Node;

/**
 * Hello world!
 */
public class App {

  public static void main(String[] args) {

    Node node1 = new Node(1);
    Node node2 = new Node(2);
    Node node3 = new Node(3);
    Node node4 = new Node(4);
    Node node5 = new Node(5);
    Node node6 = new Node(6);
    Node node7 = new Node(7);
    Node node8 = new Node(8);
    Node node9 = new Node(9);
    Node node10 = new Node(10);
    Node node11 = new Node(11);

    Set<Node> nodeList = new HashSet<>(20);
    nodeList.add(node1);
    nodeList.add(node2);
    nodeList.add(node3);
    nodeList.add(node4);
    nodeList.add(node5);
    nodeList.add(node6);
    nodeList.add(node7);
    nodeList.add(node8);
    nodeList.add(node9);
    nodeList.add(node10);
    nodeList.add(node11);
    RedBlackTree redBlackTree = new RedBlackTree(nodeList);
    BTreePrinter.printNode(redBlackTree.getRoot());

    //redBlackTree.search(node1,redBlackTree.getRoot());
  }
}
