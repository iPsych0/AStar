import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

public class Screen extends Canvas implements MouseListener, MouseMotionListener {

    private static final String TITLE = "A* visual demonstration";
    private static final int WIDTH = 640, HEIGHT = 640;
    private static final int NODE_SIZE = 32;
    private int startX, startY, goalX, goalY;
    private int xNodes, yNodes;
    private AStarGrid aStarGrid;
    private static final Color START_COLOR = new Color(49, 226, 255);
    private static final Color BG_COLOR = new Color(255, 252, 200);
    private static final Color TEXT_COLOR = new Color(98, 172, 255);

    Screen(AStarGrid aStarGrid) {
        this.aStarGrid = aStarGrid;
        this.xNodes = aStarGrid.getWidth();
        this.yNodes = aStarGrid.getHeight();

        if (xNodes > 20 || yNodes > 20) {
            throw new IllegalArgumentException("Grid size cannot exceed 20x20.");
        }

        JFrame frame = new JFrame(TITLE);
        frame.setUndecorated(false);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add the mouse listeners to the screen
        frame.addMouseListener(this);
        frame.addMouseMotionListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        setBackground(BG_COLOR);

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setFocusable(false);

        frame.add(this);
        frame.pack();

    }

    @Override
    public void paint(Graphics g) {
        // Draw the path
        List<Node> path = aStarGrid.findPath(startX, startY, goalX, goalY);
        g.setColor(Color.YELLOW);
        for (Node n : path) {
            g.fillRect(n.getX() * NODE_SIZE, n.getY() * NODE_SIZE, NODE_SIZE, NODE_SIZE);
        }

        // Draw the start and end node
        g.setColor(START_COLOR);
        g.fillRect(startX * NODE_SIZE, startY * NODE_SIZE, NODE_SIZE, NODE_SIZE);

        g.setColor(Color.GREEN);
        g.fillRect(goalX * NODE_SIZE, goalY * NODE_SIZE, NODE_SIZE, NODE_SIZE);

        // Draw the grid
        g.setColor(Color.BLACK);
        for (int x = 0; x < xNodes; x++) {
            for (int y = 0; y < yNodes; y++) {
                if (!aStarGrid.getNodes()[x][y].isWalkable()) {
                    g.setColor(Color.RED);
                    g.fillRect(x * NODE_SIZE, y * NODE_SIZE, NODE_SIZE, NODE_SIZE);
                    g.setColor(Color.BLACK);
                }
                g.drawRect(x * NODE_SIZE, y * NODE_SIZE, NODE_SIZE, NODE_SIZE);
            }
        }

        // Draw help text
        g.setFont(new Font("TimesRoman", Font.BOLD, 14));
        g.setColor(Color.BLACK);
        g.drawString("Left-click to change start. Right-click to change goal. Middle-click to add obstacles.", 31, 21);
        g.setColor(TEXT_COLOR);
        g.drawString("Left-click to change start. Right-click to change goal. Middle-click to add obstacles.", 30, 20);

    }

    @Override
    public void mouseClicked(MouseEvent e) {

        // Get mouse coords
        Rectangle mouse = new Rectangle(e.getX(), e.getY(), 1, 1);

        // Check if we're hovering over a node
        for (int x = 0; x < xNodes; x++) {
            for (int y = 0; y < yNodes; y++) {
                Rectangle bounds = new Rectangle(x * NODE_SIZE, y * NODE_SIZE, NODE_SIZE, NODE_SIZE);
                if (bounds.contains(mouse)) {
                    // If left-clicked
                    if (e.getButton() == MouseEvent.BUTTON1) {
                        startX = x;
                        startY = y;
                    }
                    if (e.getButton() == MouseEvent.BUTTON2) {
                        boolean solid = aStarGrid.getNodes()[x][y].isWalkable();
                        aStarGrid.getNodes()[x][y].setWalkable(!solid);
                    }
                    // If right-clicked
                    else if (e.getButton() == MouseEvent.BUTTON3) {
                        goalX = x;
                        goalY = y;
                    }
                    repaint();
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
