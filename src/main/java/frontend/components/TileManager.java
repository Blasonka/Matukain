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

        // Create a grid marking blocked tiles (islands)
        boolean[][] grid = new boolean[rows][cols];
        for (TektonComponent island : islands) {
            int startX = island.getXOffset();
            int startY = island.getYOffset();
            int endX = startX + island.getGridWidth();
            int endY = startY + island.getGridHeight();
            for (int x = startX; x < endX; x++) {
                for (int y = startY; y < endY; y++) {
                    if (x >= 0 && x < cols && y >= 0 && y < rows) {
                        grid[y][x] = true;
                    }
                }
            }
        }

        // Find the closest walkable points near each island's edge
        int[] startPoint = findIslandEdgePoint(grid, island1, island2);
        int[] endPoint = findIslandEdgePoint(grid, island2, island1);

        if (startPoint == null || endPoint == null) {
            System.out.println("Could not find valid edge points for path.");
            return;
        }

        // Find the path between these points
        List<int[]> path = findPath(grid, startPoint[0], startPoint[1], endPoint[0], endPoint[1]);

        // Draw the path
        Graphics2D g2 = (Graphics2D) g;
        Stroke originalStroke = g2.getStroke();
        g2.setStroke(new BasicStroke(10));
        g2.setColor(new Color(144, 238, 144));

        if (path.isEmpty()) {
            System.out.println("No path found between points.");
            g2.setStroke(originalStroke);
            return;
        }

        // Draw from the island center to the first path point
        int island1CenterX = (island1.getXOffset() + island1.getGridWidth() / 2) * tileSize + tileSize / 2;
        int island1CenterY = (island1.getYOffset() + island1.getGridHeight() / 2) * tileSize + tileSize / 2;
        int firstPathX = path.get(0)[0] * tileSize + tileSize / 2;
        int firstPathY = path.get(0)[1] * tileSize + tileSize / 2;
        g2.drawLine(island1CenterX, island1CenterY, firstPathX, firstPathY);

        // Draw the main path
        int prevX = firstPathX;
        int prevY = firstPathY;
        for (int i = 1; i < path.size(); i++) {
            int currX = path.get(i)[0] * tileSize + tileSize / 2;
            int currY = path.get(i)[1] * tileSize + tileSize / 2;
            g2.drawLine(prevX, prevY, currX, currY);
            prevX = currX;
            prevY = currY;
        }

        // Draw from last path point to the second island center
        int island2CenterX = (island2.getXOffset() + island2.getGridWidth() / 2) * tileSize + tileSize / 2;
        int island2CenterY = (island2.getYOffset() + island2.getGridHeight() / 2) * tileSize + tileSize / 2;
        g2.drawLine(prevX, prevY, island2CenterX, island2CenterY);

        g2.setStroke(originalStroke);
    }

    private int[] findIslandEdgePoint(boolean[][] grid, TektonComponent island, TektonComponent targetIsland) {
        int islandLeft = island.getXOffset();
        int islandRight = island.getXOffset() + island.getGridWidth();
        int islandTop = island.getYOffset();
        int islandBottom = island.getYOffset() + island.getGridHeight();

        int targetX = targetIsland.getXOffset() + targetIsland.getGridWidth() / 2;
        int targetY = targetIsland.getYOffset() + targetIsland.getGridHeight() / 2;

        // Try to find a point just outside the island in the direction of the target
        int[] bestPoint = null;
        double minDistance = Double.MAX_VALUE;

        // Check all four sides of the island
        for (int x = islandLeft; x < islandRight; x++) {
            // Top side
            int y = islandTop - 1;
            if (y >= 0 && !grid[y][x]) {
                double distance = Math.sqrt(Math.pow(x - targetX, 2) + Math.pow(y - targetY, 2));
                if (distance < minDistance) {
                    minDistance = distance;
                    bestPoint = new int[]{x, y};
                }
            }
            // Bottom side
            y = islandBottom;
            if (y < grid.length && !grid[y][x]) {
                double distance = Math.sqrt(Math.pow(x - targetX, 2) + Math.pow(y - targetY, 2));
                if (distance < minDistance) {
                    minDistance = distance;
                    bestPoint = new int[]{x, y};
                }
            }
        }

        for (int y = islandTop; y < islandBottom; y++) {
            // Left side
            int x = islandLeft - 1;
            if (x >= 0 && !grid[y][x]) {
                double distance = Math.sqrt(Math.pow(x - targetX, 2) + Math.pow(y - targetY, 2));
                if (distance < minDistance) {
                    minDistance = distance;
                    bestPoint = new int[]{x, y};
                }
            }
            // Right side
            x = islandRight;
            if (x < grid[0].length && !grid[y][x]) {
                double distance = Math.sqrt(Math.pow(x - targetX, 2) + Math.pow(y - targetY, 2));
                if (distance < minDistance) {
                    minDistance = distance;
                    bestPoint = new int[]{x, y};
                }
            }
        }

        return bestPoint;
    }

    // Adjusts a point to the nearest walkable position if it's blocked
    private int[] adjustToWalkable(boolean[][] grid, int[] point) {
        if (!grid[point[1]][point[0]]) {
            return point; // Already walkable
        }
        return findNearestWalkablePoint(grid, point[0], point[1]);
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

