package frontend.components;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.*;

public class TileManager {
    GamePanel gp;
    public List<TektonComponent> islands;

    public TileManager(GamePanel gp) {
        this.gp = gp;
        this.islands = new ArrayList<>();
        createIslands(5); // Example: Create 5 islands
    }

    private void createIslands(int numberOfIslands) {
        Random random = new Random();
        for (int i = 0; i < numberOfIslands; i++) {
            Tile[] tiles = new Tile[25]; // Each island has 25 tiles
            initializeTiles(tiles);

            int gridSize = (int) Math.sqrt(tiles.length); // Square layout
            int xOffset, yOffset;

            // Find a valid position that does not overlap
            do {
                xOffset = random.nextInt(gp.maxScreenCol - gridSize - 1); // Leave 1-tile margin
                yOffset = random.nextInt(gp.maxScreenRow - gridSize - 1); // Leave 1-tile margin
            } while (isOverlapping(xOffset, yOffset, gridSize));

            islands.add(new TektonComponent(tiles, xOffset, yOffset, gridSize, gp.tileSize));
        }
    }

    private boolean isOverlapping(int xOffset, int yOffset, int gridSize) {
        for (TektonComponent island : islands) {
            int islandRight = island.getXOffset() + island.getGridSize() + 1;
            int islandBottom = island.getYOffset() + island.getGridSize() + 1;
            int newRight = xOffset + gridSize + 1;
            int newBottom = yOffset + gridSize + 1;

            if (xOffset < islandRight && newRight > island.getXOffset() &&
                    yOffset < islandBottom && newBottom > island.getYOffset()) {
                return true; // Overlap detected
            }
        }
        return false; // No overlap
    }

    private void initializeTiles(Tile[] tiles) {
        Random random = new Random();
        try {
            for (int i = 0; i < tiles.length; i++) {
                tiles[i] = new Tile();
                int randomNumber = random.nextInt(2);
                if (randomNumber == 0) {
                    tiles[i].image = ImageIO.read(getClass().getResourceAsStream("/resources/textures/tekton1.png"));
                } else {
                    tiles[i].image = ImageIO.read(getClass().getResourceAsStream("/resources/textures/tekton2.png"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        // Draw the path between the islands
        drawPathAvoidingIslands(g, islands.get(0), islands.get(1)); // Example: Draw path between first two islands
        for (TektonComponent island : islands) {
            island.draw(g);
        }
    }

    private int[] findNearestWalkablePoint(boolean[][] grid, int x, int y) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Check if the current point is already walkable
        if (!grid[y][x]) {
            return new int[]{x, y};
        }

        // Search for the nearest walkable point
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[rows][cols];
        queue.add(new int[]{x, y});
        visited[y][x] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            for (int[] dir : directions) {
                int newX = current[0] + dir[0];
                int newY = current[1] + dir[1];

                if (newX >= 0 && newX < cols && newY >= 0 && newY < rows && !visited[newY][newX]) {
                    if (!grid[newY][newX]) {
                        return new int[]{newX, newY};
                    }
                    queue.add(new int[]{newX, newY});
                    visited[newY][newX] = true;
                }
            }
        }

        return null; // No walkable point found
    }

    private void drawPathAvoidingIslands(Graphics g, TektonComponent island1, TektonComponent island2) {
        int rows = gp.maxScreenRow;
        int cols = gp.maxScreenCol;
        int tileSize = gp.tileSize;

        boolean[][] grid = new boolean[rows][cols];
        for (TektonComponent island : islands) {
            int startX = island.getXOffset();
            int startY = island.getYOffset();
            int endX = startX + island.getGridSize();
            int endY = startY + island.getGridSize();
            for (int x = startX; x < endX; x++) {
                for (int y = startY; y < endY; y++) {
                    grid[y][x] = true;
                }
            }
        }

        int[] startEdge = findClosestEdge(grid, island1.getXOffset(), island1.getYOffset(), island2.getXOffset(), island2.getYOffset());
        int[] endEdge = findClosestEdge(grid, island2.getXOffset(), island2.getYOffset(), island1.getXOffset(), island1.getYOffset());

        if (startEdge == null || endEdge == null) {
            System.out.println("Could not determine valid edge points.");
            return;
        }

        startEdge[0] = Math.max(0, Math.min(grid[0].length - 1, startEdge[0] - 1));
        startEdge[1] = Math.max(0, Math.min(grid.length - 1, startEdge[1] - 1));
        endEdge[0] = Math.max(0, Math.min(grid[0].length - 1, endEdge[0] + 1));
        endEdge[1] = Math.max(0, Math.min(grid.length - 1, endEdge[1] + 1));

        // Ensure start and end points are walkable
        if (grid[startEdge[1]][startEdge[0]]) {
            startEdge = findNearestWalkablePoint(grid, startEdge[0], startEdge[1]);
            if (startEdge == null) {
                System.out.println("No valid start point found.");
                return;
            }
        }
        if (grid[endEdge[1]][endEdge[0]]) {
            endEdge = findNearestWalkablePoint(grid, endEdge[0], endEdge[1]);
            if (endEdge == null) {
                System.out.println("No valid end point found.");
                return;
            }
        }

        List<int[]> path = findPath(grid, startEdge[0], startEdge[1], endEdge[0], endEdge[1]);


        Graphics2D g2 = (Graphics2D) g;
        Stroke originalStroke = g2.getStroke(); // Save the original stroke
        g2.setStroke(new BasicStroke(10)); // Set the stroke width to 10px
        g2.setColor(new Color(144, 238, 144)); // Set the color to blue

        if (path.isEmpty()) {
            System.out.println("No path found.");
            return;
        }

        int x1 = startEdge[0] * tileSize + tileSize / 2;
        int y1 = startEdge[1] * tileSize + tileSize / 2;
        int x2 = path.get(0)[0] * tileSize + tileSize / 2;
        int y2 = path.get(0)[1] * tileSize + tileSize / 2;
        g.drawLine(x1, y1, x2, y2);

        for (int i = 0; i < path.size() - 1; i++) {
            int[] current = path.get(i);
            int[] next = path.get(i + 1);

            x1 = current[0] * tileSize + tileSize / 2;
            y1 = current[1] * tileSize + tileSize / 2;
            x2 = next[0] * tileSize + tileSize / 2;
            y2 = next[1] * tileSize + tileSize / 2;
            g.drawLine(x1, y1, x2, y2);
        }

        int[] lastPoint = path.get(path.size() - 1);
        x1 = lastPoint[0] * tileSize + tileSize / 2;
        y1 = lastPoint[1] * tileSize + tileSize / 2;
        x2 = endEdge[0] * tileSize + tileSize / 2;
        y2 = endEdge[1] * tileSize + tileSize / 2;
        g.drawLine(x1, y1, x2, y2);
    }
    private int[] findClosestEdge(boolean[][] grid, int centerX, int centerY, int targetX, int targetY) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[] closestEdge = null;
        double minDistance = Double.MAX_VALUE;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                if (!grid[y][x] && isEdge(grid, x, y)) {
                    double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
                    if (distance < minDistance) {
                        minDistance = distance;
                        closestEdge = new int[]{x, y};
                    }
                }
            }
        }

        if (closestEdge != null && grid[closestEdge[1]][closestEdge[0]]) {
            System.out.println("Closest edge is blocked.");
            return null;
        }

        return closestEdge;
    }

    private boolean isEdge(boolean[][] grid, int x, int y) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Check if the point is walkable and has at least one neighbor that is an obstacle
        if (!grid[y][x]) {
            int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
            for (int[] dir : directions) {
                int neighborX = x + dir[0];
                int neighborY = y + dir[1];
                if (neighborX >= 0 && neighborX < cols && neighborY >= 0 && neighborY < rows && grid[neighborY][neighborX]) {
                    return true;
                }
            }
        }
        return false;
    }

    private List<int[]> findPath(boolean[][] grid, int startX, int startY, int endX, int endY) {
        int rows = grid.length;
        int cols = grid[0].length;

        // Directions for moving in 4 cardinal directions (up, down, left, right)
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

        // Priority queue for A* (min-heap based on f = g + h)
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> node.f));
        boolean[][] closedSet = new boolean[rows][cols];

        // Validate start and end points
        if (startX < 0 || startX >= cols || startY < 0 || startY >= rows ||
                endX < 0 || endX >= cols || endY < 0 || endY >= rows) {
            System.out.println("Start or end point is out of bounds.");
            return new ArrayList<>();
        }
        if (grid[startY][startX] || grid[endY][endX]) {
            System.out.println("Start or end point is blocked.");
            return new ArrayList<>();
        }

        // Start node
        Node startNode = new Node(startX, startY, 0, heuristic(startX, startY, endX, endY), null);
        openSet.add(startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            // If the goal is reached, reconstruct the path
            if (current.x == endX && current.y == endY) {
                return reconstructPath(current);
            }

            // Mark the current node as visited
            if (closedSet[current.y][current.x]) {
                continue; // Skip if already processed
            }
            closedSet[current.y][current.x] = true;

            // Explore neighbors
            for (int[] dir : directions) {
                int neighborX = current.x + dir[0];
                int neighborY = current.y + dir[1];

                // Check bounds and obstacles
                if (neighborX < 0 || neighborX >= cols || neighborY < 0 || neighborY >= rows) {
                    continue; // Skip if out of bounds
                }
                if (grid[neighborY][neighborX] || closedSet[neighborY][neighborX]) {
                    continue; // Skip if it's an obstacle or already visited
                }

                int g = current.g + 1; // Cost to reach the neighbor
                int h = heuristic(neighborX, neighborY, endX, endY); // Estimated cost to the goal
                Node neighbor = new Node(neighborX, neighborY, g, g + h, current);

                // Add the neighbor to the open set
                openSet.add(neighbor);
            }
        }

        System.out.println("No path found.");
        return new ArrayList<>();
    }

    // Heuristic function (Manhattan distance)
    private int heuristic(int x1, int y1, int x2, int y2) {
        return Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    // Reconstruct the path from the goal to the start
    private List<int[]> reconstructPath(Node node) {
        List<int[]> path = new ArrayList<>();
        while (node != null) {
            path.add(new int[]{node.x, node.y});
            node = node.parent;
        }
        Collections.reverse(path); // Reverse to get the path from start to goal
        return path;
    }

    // Node class for A* algorithm
    private static class Node {
        int x, y, g, f;
        Node parent;

        Node(int x, int y, int g, int f, Node parent) {
            this.x = x;
            this.y = y;
            this.g = g;
            this.f = f;
            this.parent = parent;
        }
    }


    public void islandOszto(TektonComponent island) {
        // Get the original island's properties
        Tile[] originalTiles = island.getTiles();
        int gridSize = island.getGridSize();
        int tileSize = island.getTileSize();
        int xOffset = island.getXOffset();
        int yOffset = island.getYOffset();

        // Define the dimensions for the split
        int originalWidth = (gridSize * 3) / 5; // 3 columns for the original island
        int newWidth = gridSize - originalWidth; // 2 columns for the new island
        int height = gridSize; // Keep the height the same

        // Split the tiles into two parts
        Tile[] originalTilesPart = new Tile[originalWidth * height];
        Tile[] newTilesPart = new Tile[newWidth * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < gridSize; x++) {
                int index = y * gridSize + x;
                if (x < originalWidth) {
                    originalTilesPart[y * originalWidth + x] = originalTiles[index];
                } else {
                    newTilesPart[y * newWidth + (x - originalWidth)] = originalTiles[index];
                }
            }
        }

        // Update the original island with the remaining tiles
        island.setTiles(originalTilesPart);
        island.setGridSize(originalWidth);

        // Find a valid position for the new island
        int newIslandXOffset = xOffset + originalWidth; // Place it to the right of the original island
        int newIslandYOffset = yOffset; // Keep the same vertical position
        while (isOverlapping(newIslandXOffset, newIslandYOffset, newWidth)) {
            newIslandXOffset++;
            if (newIslandXOffset + newWidth >= gp.maxScreenCol) { // Wrap to the next row if out of bounds
                newIslandXOffset = 0;
                newIslandYOffset++;
            }
            if (newIslandYOffset + height >= gp.maxScreenRow) { // Stop if no valid position is found
                System.out.println("No valid position found for the new island.");
                return;
            }
        }

        // Create a new island for the broken part
        TektonComponent newIsland = new TektonComponent(newTilesPart, newIslandXOffset, newIslandYOffset, newWidth, tileSize);

        // Add the new island to the islands list
        islands.add(newIsland);

        // Refresh the GamePanel to reflect the changes
        gp.repaint();
    }
}