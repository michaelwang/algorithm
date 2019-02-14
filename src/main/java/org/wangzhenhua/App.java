package org.wangzhenhua;

import java.util.ArrayList;
import java.util.List;
import org.wangzhenhua.RedBlackTree.Node;

/**
 * Hello world!
 */
public class App {

  public static void main(String[] args) {

    List<Node> nodeList = new ArrayList<>(20);
    for (int i = 1; i <= 7 ; i++) {
      Node node = new Node(i);
      nodeList.add(node);
    }

    RedBlackTree redBlackTree = new RedBlackTree(nodeList);

  }
}
