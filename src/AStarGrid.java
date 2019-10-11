import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AStarGrid {

    private int x, y, width, height;
    private Node[][] nodes;
    private Rectangle grid;

    public AStarGrid(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        init();
    }

    public void init() {
        nodes = new Node[width][height];
        grid = new Rectangle(x, y, width, height);

        // Initialize your array of nodes here TODO: Add non-walkable tiles manually
        for (int x = 0; x < nodes.length; x++) {
            for (int y = 0; y < nodes.length; y++) {
                nodes[x][y] = new Node(x, y, true);
            }
        }
    }

    public final List<Node> findPath(int startX, int startY, int goalX, int goalY) {
        // Throw exception if the goal is outside the grid
        if (goalX >= this.width || goalX < this.x || goalY >= this.height || goalY < this.y) {
            throw new IllegalArgumentException("The goal cannot be outside the grid.");
        }

        // Throw exception if the goal node is a non-walkable tile
        if (!nodes[goalX][goalY].isWalkable()) {
            throw new IllegalArgumentException("The goal cannot be a non-walkable tile.");
        }

        // If our starting position is the same as our goal...
        if (startX == goalX && startY == goalY) {
            // Return an empty path, because we don't need to move at all.
            return new ArrayList<>();
        }

        // The set of nodes already visited.
        List<Node> openList = new ArrayList<>();
        // The set of currently discovered nodes still to be visited.
        HashSet<Node> closedList = new HashSet<>();

        // Add starting node to open list.
        openList.add(nodes[startX][startY]);

        // This loop will be broken as soon as the current node pause is
        // equal to the goal pause.
        while (true) {
            // Gets node with the lowest F score from open list.
            Node current = lowestFInList(openList);
            // Remove current node from open list.
            openList.remove(current);
            // Add current node to closed list.
            closedList.add(current);

            // If the current node pause is equal to the goal pause ...
            if ((current.getX() - this.x == goalX) && (current.getY() - this.y == goalY)) {
                // Return a ArrayList containing all of the visited nodes.
                return calcPath(nodes[startX][startY], current);
            }

            HashSet<Node> adjacentNodes = getAdjacent(current, closedList);
            for (Node adjacent : adjacentNodes) {
                // If node is not in the open list ...
                if (!openList.contains(adjacent)) {
                    // Set current node as parent for this node.
                    adjacent.setParent(current);
                    // Set H costs of this node (estimated costs to goal).
                    adjacent.setH(nodes[goalX][goalY]);
                    // Set G costs of this node (costs from start to this node).
                    adjacent.setG(current);
                    // Add node to openList.
                    openList.add(adjacent);
                }
                // Else if the node is in the open list and the G score from
                // current node is cheaper than previous costs ...
                else if (adjacent.getG() > adjacent.calculateG(current)) {
                    // Set current node as parent for this node.
                    adjacent.setParent(current);
                    // Set G costs of this node (costs from start to this node).
                    adjacent.setG(current);
                }
            }

            // If no path exists ...
            if (openList.isEmpty()) {
                // Return an empty list.
                return new ArrayList<>();
            }
            // But if it does, continue the loop.
        }
    }

    private List<Node> calcPath(Node start, Node goal) {
        ArrayList<Node> path = new ArrayList<>();

        Node node = goal;
        boolean done = false;
        while (!done) {
            path.add(0, node);
            node = node.getParent();
            if (node == null || start == null) {
                return null;
            }
            if (node.equals(start)) {
                done = true;
            }
        }

        return path;
    }

    private Node lowestFInList(List<Node> list) {
        Node cheapest = list.get(0);
        for (Node aList : list) {
            if (aList.getF() < cheapest.getF()) {
                cheapest = aList;
            }
        }
        return cheapest;
    }

    private HashSet<Node> getAdjacent(Node node, HashSet<Node> closedList) {
        HashSet<Node> adjacentNodes = new HashSet<>();
        int x = node.getX() - this.x;
        int y = node.getY() - this.y;

        Node adjacent;

        // Check left node
        if (x > 0) {
            adjacent = getNode(x - 1, y);
            if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent)) {
                adjacentNodes.add(adjacent);
            }
        }

        // Check right node
        if (x < nodes.length) {
            adjacent = getNode(x + 1, y);
            if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent)) {
                adjacentNodes.add(adjacent);
            }
        }

        // Check top node
        if (y > 0) {
            adjacent = this.getNode(x, y - 1);
            if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent)) {
                adjacentNodes.add(adjacent);
            }
        }

        // Check bottom node
        if (y < nodes.length) {
            adjacent = this.getNode(x, y + 1);
            if (adjacent != null && adjacent.isWalkable() && !closedList.contains(adjacent)) {
                adjacentNodes.add(adjacent);
            }
        }
        return adjacentNodes;
    }

    private Node getNode(int x, int y) {
        // Only check nodes within the grid
        if (x >= this.x && x <= nodes.length - 1 && y >= this.y && y <= nodes.length - 1) {
            return nodes[x][y];
        }
        return null;
    }

    public Rectangle getGrid() {
        return grid;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Node[][] getNodes() {
        return nodes;
    }

}