import sun.misc.Queue;

/**
 * 二叉树
 * @param <T>
 */
public class BSTree<T extends Comparable<T>> {

    BSTNode<T> root = null;

    static class BSTNode<T> {
        BSTNode<T> left = null;
        BSTNode<T> right = null;
        T data;

        public BSTNode(T data) {
            this.data = data;
        }
    }

    private void add(BSTNode<T> node, BSTNode<T> element) {

        if(element.data.compareTo(node.data) <= 0) {
            if(node.left == null) {
                node.left = element;
                return;
            }
            add(node.left, element);
        } else {
            if(node.right == null) {
                node.right = element;
                return;
            }
            add(node.right, element);
        }

    }

    public void add(T element) {
        BSTNode node = new BSTNode(element);
        if(root == null) {
            root = node;
            return;
        }

        add(root, node);

    }

    <T> void preOrder(BSTNode<T> node) {

        if(node == null) {
            return;
        }

        System.out.println(node.data);
        preOrder(node.left);
        preOrder(node.right);

    }

    <T> void postOrder(BSTNode<T> node){
        if(node == null) {
            return;
        }

        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.data);

    }

    <T> void inOrder(BSTNode<T> node){
        if(node == null) {
            return;
        }

        inOrder(node.left);
        System.out.println(node.data);
        inOrder(node.right);

    }

    // Breadth First Search
    public static <T> void bfs(BSTNode<T> node){


        Queue queue = new Queue<BSTNode<T>>();
        queue.enqueue(node);

        while(!queue.isEmpty()) {
            BSTNode item = null;
            try {
                item = (BSTNode)queue.dequeue();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(item.data);

            if(item.left != null)
                queue.enqueue(item.left);
            if(item.right != null)
                queue.enqueue(item.right);
        }
    }

    public static <T> void reverse(BSTNode<T> node) {
        if(node == null) {
            return;
        }

        BSTNode t = node.left;
        node.left = node.right;
        node.right = t;

        reverse(node.left);
        reverse(node.right);

    }
}

