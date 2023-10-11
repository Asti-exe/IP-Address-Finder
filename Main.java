import java.util.Random;

class Node {
    int ipAdd;
    int dataPacket;
    Node left;
    Node right;
    Node parent;

    public Node(int ipAdd) {
        this.ipAdd = ipAdd;
        this.parent = null;
        this.right = null;
        this.left = null;
    }
}

class SplayTree {
    Node root;

    public SplayTree() {
        this.root = null;
    }

    private Node maximum(Node x) {
        while (x.right != null)
            x = x.right;
        return x;
    }

    private void leftRotate(Node x) {
        Node y = x.right;
        x.right = y.left;
        if (y.left != null) {
            y.left.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.left) {
            x.parent.left = y;
        } else {
            x.parent.right = y;
        }
        y.left = x;
        x.parent = y;
    }

    private void rightRotate(Node x) {
        Node y = x.left;
        x.left = y.right;
        if (y.right != null) {
            y.right.parent = x;
        }
        y.parent = x.parent;
        if (x.parent == null) {
            root = y;
        } else if (x == x.parent.right) {
            x.parent.right = y;
        } else {
            x.parent.left = y;
        }
        y.right = x;
        x.parent = y;
    }

    private void splay(Node n) {
        while (n.parent != null) {
            if (n.parent == root) {
                if (n == n.parent.left) {
                    rightRotate(n.parent);
                } else {
                    leftRotate(n.parent);
                }
            } else {
                Node p = n.parent;
                Node g = p.parent;
                if (n.parent.left == n && p.parent.left == p) {
                    rightRotate(g);
                    rightRotate(p);
                } else if (n.parent.right == n && p.parent.right == p) {
                    leftRotate(g);
                    leftRotate(p);
                } else if (n.parent.right == n && p.parent.left == p) {
                    leftRotate(p);
                    rightRotate(g);
                } else if (n.parent.left == n && p.parent.right == p) {
                    rightRotate(p);
                    leftRotate(g);
                }
            }
        }
    }

    public void insert(Node n) {
        Node y = null;
        Node temp = root;
        while (temp != null) {
            y = temp;
            if (n.ipAdd < temp.ipAdd)
                temp = temp.left;
            else
                temp = temp.right;
        }
        n.parent = y;
        if (y == null)
            root = n;
        else if (n.ipAdd < y.ipAdd)
            y.left = n;
        else
            y.right = n;
        splay(n);
    }

    public Node search(int x) {
        Node n = root;
        while (n != null) {
            if (x == n.ipAdd) {
                splay(n);
                return n;
            } else if (x < n.ipAdd) {
                n = n.left;
            } else {
                n = n.right;
            }
        }
        return null;
    }

    private void inorder(Node n, String cmn) {
        if (n != null) {
            inorder(n.left, cmn);
            System.out.println(cmn + n.ipAdd + " -> " + n.dataPacket);
            inorder(n.right, cmn);
        }
    }

    public void inorderTraversal(String cmn) {
        inorder(root, cmn);
    }
}

public class Main {
    public static void main(String[] args) {
        String cmn = "192.168.3.";
        SplayTree t = new SplayTree();
        Node[] nodes = new Node[11];

        nodes[0] = new Node(104);
        nodes[1] = new Node(112);
        nodes[2] = new Node(117);
        nodes[3] = new Node(124);
        nodes[4] = new Node(121);
        nodes[5] = new Node(108);
        nodes[6] = new Node(109);
        nodes[7] = new Node(111);
        nodes[8] = new Node(122);
        nodes[9] = new Node(125);
        nodes[10] = new Node(129);

        for (Node node : nodes) {
            t.insert(node);
        }
        

        Random rand = new Random();
        int[] find = {104, 112, 117, 124, 121, 108, 109, 111, 122, 125, 129};

        for (int x = 0; x < 11; x++) {
            int data = rand.nextInt(200);
            Node temp = t.search(find[x]);
            if (temp != null) {
                temp.dataPacket = data;
            }
        }

        System.out.println("IP ADDRESS -> DATA PACKET");
        t.inorderTraversal(cmn);
    }
}
