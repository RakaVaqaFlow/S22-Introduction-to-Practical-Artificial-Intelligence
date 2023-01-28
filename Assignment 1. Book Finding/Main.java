import java.util.*;
import java.math.*;

public class VagifKhalilov {

    public static class Cell {
        public int x;
        public int y;

        public Cell() {
            x = (int) (Math.random() * 8);
            y = (int) (Math.random() * 8);
        }

        public Cell(int x_, int y_) {
            x = x_;
            y = y_;
        }
    }

    public static class Library {
        String[][] map;
        int size;

        public Library() {
            size = 9;
            map = new String[size][size];
            // Harry
            map[0][0] = "H";
            // Argus and Norris
            int xArgus = 0;
            int yArgus = 0;
            while (0 >= xArgus - 2 && 0 <= xArgus + 2 && 0 >= yArgus - 2 && 0 <= yArgus + 2) {
                xArgus = (int) (Math.random() * 9);
                yArgus = (int) (Math.random() * 9);
            }
            for (int i = Math.max(0, xArgus - 2); i < Math.min(size, xArgus + 3); i++) {
                for (int j = Math.max(0, yArgus - 2); j < Math.min(size, yArgus + 3); j++) {
                    map[i][j] = "X";
                }
            }
            int xNorris = 0;
            int yNorris = 0;
            while (0 >= xNorris - 2 && 0 <= xNorris + 2 && 0 >= yNorris - 2 && 0 <= yNorris + 2) {
                xNorris = (int) (Math.random() * 9);
                yNorris = (int) (Math.random() * 9);
            }
            for (int i = Math.max(0, xNorris - 1); i < Math.min(size, xNorris + 2); i++) {
                for (int j = Math.max(0, yNorris - 1); j < Math.min(size, yNorris + 2); j++) {
                    map[i][j] = "X";
                }
            }
            map[xArgus][yArgus] = "A";
            if (map[xNorris][yNorris] == "A")
                map[xNorris][yNorris] += "N";
            else
                map[xNorris][yNorris] = "N";
            // Book
            int xBook = (int) (Math.random() * 9);
            int yBook = (int) (Math.random() * 9);
            while (isInInspecorZone(xBook, yBook, xArgus, yArgus, xNorris, yNorris)) {
                xBook = (int) (Math.random() * 9);
                yBook = (int) (Math.random() * 9);
            }
            if (map[xBook][yBook] != null)
                map[xBook][yBook] += "B";
            else
                map[xBook][yBook] = "B";

            // Invisibility Cloak
            int xCloak = (int) (Math.random() * 9);
            int yCloak = (int) (Math.random() * 9);
            while (isInInspecorZone(xCloak, yCloak, xArgus, yArgus, xNorris, yNorris)) {
                xCloak = (int) (Math.random() * 9);
                yCloak = (int) (Math.random() * 9);
            }
            if (map[xCloak][yCloak] != null)
                map[xCloak][yCloak] += "C";
            else
                map[xCloak][yCloak] = "C";

            // Exit
            int xExit = (int) (Math.random() * 9);
            int yExit = (int) (Math.random() * 9);
            while (isInInspecorZone(xExit, yExit, xArgus, yArgus, xNorris, yNorris)
                    || (xExit == xBook && yExit == yBook)) {
                xExit = (int) (Math.random() * 9);
                yExit = (int) (Math.random() * 9);
            }
            if (map[xExit][yExit] != null)
                map[xExit][yExit] += "E";
            else
                map[xExit][yExit] = "E";
            // other cells
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (map[i][j] == null)
                        map[i][j] = "-";
                }
            }

        }

        public Library(int data[]) {
            size = 9;
            map = new String[size][size];
            // Harry
            map[data[0]][data[1]] = "H";
            // Argus and Norris
            for (int i = Math.max(0, data[2] - 2); i < Math.min(size, data[2] + 3); i++) {
                for (int j = Math.max(0, data[3] - 2); j < Math.min(size, data[3] + 3); j++) {
                    map[i][j] = "X";
                }
            }
            for (int i = Math.max(0, data[4] - 1); i < Math.min(size, data[4] + 2); i++) {
                for (int j = Math.max(0, data[5] - 1); j < Math.min(size, data[5] + 2); j++) {
                    map[i][j] = "X";
                }
            }
            map[data[2]][data[3]] = "A";
            if (map[data[4]][data[5]] == "A")
                map[data[4]][data[5]] += "N";
            else
                map[data[4]][data[5]] = "N";

            // Book
            if (map[data[6]][data[7]] != null)
                map[data[6]][data[7]] += "B";
            else
                map[data[6]][data[7]] = "B";

            // Cloak
            if (map[data[8]][data[9]] != null)
                map[data[8]][data[9]] += "C";
            else
                map[data[8]][data[9]] = "C";

            // Exit
            if (map[data[10]][data[11]] != null)
                map[data[10]][data[11]] += "E";
            else
                map[data[10]][data[11]] = "E";
            // Other cells
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (map[i][j] == null)
                        map[i][j] = "-";
                }
            }
        }

        public void print() {
            int maxSizeOfCell = 0;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    maxSizeOfCell = Math.max(maxSizeOfCell, map[i][j].length());
                }
            }
            for (int i = size - 1; i >= 0; i--) {
                for (int j = 0; j < size; j++) {
                    if (maxSizeOfCell - map[j][i].length() == 2)
                        System.out.print(" ");
                    System.out.print(map[j][i]);
                    if (maxSizeOfCell - map[j][i].length() >= 1)
                        System.out.print(" ");
                    System.out.print(" ");
                }
                System.out.print("\n");
            }
        }

        public String[][] getMap() {
            return map;
        }

        public boolean isExit(int x, int y) {
            for (int i = 0; i < map[x][y].length(); i++) {
                if (map[x][y].charAt(i) == 'E')
                    return true;
            }
            return false;
        }

        public boolean isBook(int x, int y) {
            for (int i = 0; i < map[x][y].length(); i++) {
                if (map[x][y].charAt(i) == 'B')
                    return true;
            }
            return false;
        }

        public boolean isCloak(int x, int y) {
            for (int i = 0; i < map[x][y].length(); i++) {
                if (map[x][y].charAt(i) == 'C')
                    return true;
            }
            return false;
        }

        public boolean isInspectorZone(int x, int y) {
            for (int i = 0; i < map[x][y].length(); i++) {
                if (map[x][y].charAt(i) == 'X')
                    return true;
            }
            return false;
        }

        public boolean isInspector(int x, int y) {
            for (int i = 0; i < map[x][y].length(); i++) {
                if (map[x][y].charAt(i) == 'A' || map[x][y].charAt(i) == 'N')
                    return true;
            }
            return false;
        }

        public boolean isTarget(String target, int x, int y) {
            return target.equals("Book") && isBook(x, y) || target.equals("Exit") && isExit(x, y)
                    || target.equals("Cloak") && isCloak(x, y) || target.equals("Inspector") && isInspector(x, y)
                    || target.equals("InspectorZone") && isInspectorZone(x, y);
        }
    }

    public static class Harry {
        int visionMode;
        public Library lib;
        char[][] map;
        int[][] used;
        Vector<Cell> path;
        Vector<Cell> totalPath;
        Cell exit;
        int hasBook;
        int hasCloak;
        private int INF = 10000000;

        public Harry() {
            this.map = new char[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    map[i][j] = '·';
                }
            }
            this.used = new int[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    used[i][j] = 0;
                }
            }
            this.visionMode = 1;
            this.lib = new Library();
            this.path = new Vector<Cell>();
            for (int i = 0; i < lib.size; i++) {
                for (int j = 0; j < lib.size; j++) {
                    if (lib.isExit(i, j))
                        this.exit = new Cell(i, j);
                }
            }
            this.hasBook = 0;
            this.hasCloak = 0;
            this.totalPath = new Vector<Cell>();
        }

        public Harry(int VisionMode, Library lib) {
            this.visionMode = VisionMode;
            this.lib = lib;
            this.map = new char[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    map[i][j] = '·';
                }
            }
            this.used = new int[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    used[i][j] = 0;
                }
            }
            for (int i = 0; i < lib.size; i++) {
                for (int j = 0; j < lib.size; j++) {
                    if (lib.isExit(i, j))
                        this.exit = new Cell(i, j);
                }
            }
            this.hasBook = 0;
            this.hasCloak = 0;
            this.totalPath = new Vector<Cell>();
            this.path = new Vector<Cell>();
        }

        private int dfs(int xNode, int yNode, String target) {
            if (!(xNode >= 0 && xNode <= 8 && yNode >= 0 && yNode <= 8) || used[xNode][yNode] == 1)
                return 0;
            if (lib.isInspectorZone(xNode, yNode) && hasCloak == 0 || lib.isInspector(xNode, yNode)) {
                return 0;
            }
            if (lib.isCloak(xNode, yNode)) {
                hasCloak = 1;
            }
            if (lib.isTarget(target, xNode, yNode)) {
                map[xNode][yNode] = target.charAt(0);
                used[xNode][yNode] = 1;
                return 1;
            }
            map[xNode][yNode] = target.charAt(0);
            used[xNode][yNode] = 1;
            if (visionMode == 1) {
                for (int i = xNode - 1; i <= xNode + 1; i++) {
                    for (int j = yNode - 1; j <= yNode + 1; j++) {
                        if (!(i >= 0 && i <= 8 && j >= 0 && j <= 8) || i == xNode && j == yNode)
                            continue;
                        if (lib.isInspectorZone(i, j))
                            map[i][j] = 'X';
                        if (lib.isInspector(i, j))
                            map[i][j] = 'I';
                    }
                }
            } else if (visionMode == 2) {
                for (int i = xNode - 2; i <= xNode + 2; i++) {
                    for (int j = yNode - 2; j <= yNode + 2; j++) {
                        if (!(i >= 0 && i <= 8 && j >= 0 && j <= 8)
                                || (i >= xNode - 1 && i <= xNode + 1 && j >= yNode - 1 && j <= yNode + 1)
                                || Math.abs(xNode - i) == 2 && Math.abs(yNode - j) == 2)
                            continue;
                        if (lib.isInspectorZone(i, j))
                            map[i][j] = 'X';
                        if (lib.isInspector(i, j))
                            map[i][j] = 'I';
                    }
                }
            }
            for (int i = xNode - 1; i <= xNode + 1; i++) {
                for (int j = yNode - 1; j <= yNode + 1; j++) {
                    if (!(i >= 0 && i <= 8 && j >= 0 && j <= 8) || map[i][j] == 'X' && hasCloak == 0
                            || map[i][j] == 'I')
                        continue;
                    if (dfs(i, j, target) == 1) {
                        path.add(new Cell(i, j));
                        return 1;
                    }
                }
            }
            if (lib.isCloak(xNode, yNode)) {
                hasCloak = 0;
            }
            return 0;
        }

        private void clearMemory() {
            path.clear();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    used[i][j] = 0;
                    map[i][j] = '·';
                }
            }
        }

        public int backtrackingSearch() {
            clearMemory();
            totalPath.clear();
            if (lib.isBook(0, 0)) {
                return 1;
            }
            if (lib.isCloak(0, 0))
                this.hasCloak = 1;
            if (dfs(0, 0, "Book") == 0) {
                path.clear();
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        used[i][j] = 0;
                    }
                }
                if (dfs(0, 0, "Cloak") == 0)
                    return 0;
                for (int i = path.size() - 1; i >= 0; i--) {
                    totalPath.add(path.get(i));
                }
                path.clear();
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        used[i][j] = 0;
                    }
                }
                if (dfs(totalPath.lastElement().x, totalPath.lastElement().y, "Book") == 0)
                    return 0;
                for (int i = path.size() - 1; i >= 0; i--) {
                    totalPath.add(path.get(i));
                }
                path.clear();
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        used[i][j] = 0;
                    }
                }
                if (dfs(totalPath.lastElement().x, totalPath.lastElement().y, "Exit") == 0)
                    return 0;
                for (int i = path.size() - 1; i >= 0; i--) {
                    totalPath.add(path.get(i));
                }
                return 1;
            } else {
                for (int i = path.size() - 1; i >= 0; i--) {
                    totalPath.add(path.get(i));
                }
                path.clear();
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        used[i][j] = 0;
                    }
                }
                if (dfs(totalPath.lastElement().x, totalPath.lastElement().y, "Exit") == 0) {
                    if (hasCloak == 1)
                        return 0;
                    path.clear();
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            used[i][j] = 0;
                        }
                    }
                    if (dfs(totalPath.lastElement().x, totalPath.lastElement().y, "Cloak") == 0)
                        return 0;
                    for (int i = path.size() - 1; i >= 0; i--) {
                        totalPath.add(path.get(i));
                    }
                    path.clear();
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            used[i][j] = 0;
                        }
                    }
                    if (dfs(totalPath.lastElement().x, totalPath.lastElement().y, "Exit") == 0)
                        return 0;
                    for (int i = path.size() - 1; i >= 0; i--) {
                        totalPath.add(path.get(i));
                    }
                } else {
                    for (int i = path.size() - 1; i >= 0; i--) {
                        totalPath.add(path.get(i));
                    }
                    return 1;
                }
            }
            return 0;
        }

        private Vector<Cell> aStart(Cell start, String target) {
            clearMemory();
            LinkedList<Cell> queue = new LinkedList<Cell>();

            int[][] g = new int[9][9];
            int[][] f = new int[9][9];
            Cell goal = new Cell(8, 8);
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    g[i][j] = INF;
                    f[i][j] = INF;
                }
            }
            g[start.x][start.y] = 0;
            f[start.x][start.y] = g[0][0];
            queue.add(start);
            int[][] parentX = new int[9][9];
            int[][] parentY = new int[9][9];
            parentX[start.x][start.y] = 0;
            parentY[start.x][start.y] = 0;
            Cell current = start;
            while (!queue.isEmpty()) {
                int currentF = INF + 1;
                int currentId = 0;
                for (int i = 0; i < queue.size(); i++) {
                    if (currentF >= f[queue.get(i).x][queue.get(i).y]) {
                        current = queue.get(i);
                        currentF = f[queue.get(i).x][queue.get(i).y];
                        currentId = i;
                    }
                }
                if (lib.isTarget(target, current.x, current.y)) {
                    goal = current;
                    break;
                }
                queue.remove(currentId);
                used[current.x][current.y] = 1;
                for (int i = current.x - 1; i <= current.x + 1; i++) {
                    for (int j = current.y - 1; j <= current.y + 1; j++) {
                        if (!(i >= 0 && i <= 8 && j >= 0 && j <= 8) || (lib.isInspectorZone(i, j) && hasCloak == 0)
                                || lib.isInspector(i, j) || (i == current.x && j == current.y))
                            continue;
                        int tentativeScore = g[current.x][current.y] + 1;
                        if (used[i][j] == 1 && tentativeScore >= g[i][j])
                            continue;
                        if (used[i][j] != 0 || tentativeScore < g[i][j]) {
                            parentX[i][j] = current.x;
                            parentY[i][j] = current.y;
                            g[i][j] = tentativeScore;
                            f[i][j] = tentativeScore;
                            int push = 1;
                            for (int h = 0; h < queue.size(); h++) {
                                if (queue.get(h).x == i && queue.get(h).y == j) {
                                    push = 0;
                                    break;
                                }
                            }
                            if (push == 1) {
                                queue.add(new Cell(i, j));
                            }
                        }
                    }
                }
            }
            if (g[goal.x][goal.y] == INF) {
                return new Vector<Cell>();
            }
            int x1 = goal.x;
            int y1 = goal.y;
            while (!(x1 == start.x && y1 == start.y)) {
                path.add(new Cell(x1, y1));
                x1 = parentX[x1][y1];
                y1 = parentY[x1][y1];

            }
            Collections.reverse(path);
            return path;
        }

        private void addPath(Vector<Cell> pathToTarget) {
            for (int i = 0; i < pathToTarget.size(); i++) {
                totalPath.add(pathToTarget.elementAt(i));
            }
        }

        public Vector<Cell> aStarSearch() {
            totalPath.clear();
            Vector<Cell> pathToTarget = new Vector<Cell>();
            int minSize = INF;
            pathToTarget = aStart(new Cell(0, 0), "Book");
            minSize = Math.min(minSize, pathToTarget.size());
            addPath(pathToTarget);
            pathToTarget = aStart(totalPath.lastElement(), "Exit");
            minSize = Math.min(minSize, pathToTarget.size());
            addPath(pathToTarget);
            if (minSize != 0)
                return totalPath;
            totalPath.clear();
            pathToTarget = aStart(new Cell(0, 0), "Cloak");
            minSize = Math.min(minSize, pathToTarget.size());
            addPath(pathToTarget);
            pathToTarget = aStart(totalPath.lastElement(), "Book");
            minSize = Math.min(minSize, pathToTarget.size());
            addPath(pathToTarget);
            pathToTarget = aStart(totalPath.lastElement(), "Exit");
            minSize = Math.min(minSize, pathToTarget.size());
            addPath(pathToTarget);
            if (minSize != 0)
                return totalPath;
            totalPath.clear();
            pathToTarget = aStart(new Cell(0, 0), "Book");
            minSize = Math.min(minSize, pathToTarget.size());
            addPath(pathToTarget);
            pathToTarget = aStart(totalPath.lastElement(), "Cloak");
            minSize = Math.min(minSize, pathToTarget.size());
            addPath(pathToTarget);
            pathToTarget = aStart(totalPath.lastElement(), "Exit");
            minSize = Math.min(minSize, pathToTarget.size());
            addPath(pathToTarget);
            if (minSize != 0)
                return totalPath;
            else
                return new Vector<Cell>();
        }

        public void printUsed() {
            for (int i = 8; i >= 0; i--) {
                for (int j = 0; j < 9; j++) {
                    System.out.print(map[j][i]);
                }
                System.out.print("\n");
            }
        }

        public void printPath() {
            if (totalPath.size() == 0) {
                System.out.print("0");
                return;
            }
            for (int i = 0; i < totalPath.size(); i++) {
                System.out.print("[" + totalPath.elementAt(i).x + "," + totalPath.elementAt(i).y + "] ");
            }
        }
    }

    public static boolean isInInspecorZone(int xObject, int yObject, int xArgus, int yArgus, int xNorris, int yNorris) {
        return ((xObject >= xArgus - 2 && xObject <= xArgus + 2 && yObject >= yArgus - 2 && yObject <= yArgus + 2)
                || (xObject >= xNorris - 1 && xObject <= xNorris + 1 && yObject >= yNorris - 1
                        && yObject <= yNorris + 1));
    }

    public static int[] inputScanner() throws Exception {
        int data[] = new int[13];
        Scanner console = new Scanner(System.in);
        String input;
        input = console.nextLine();
        int currentNumber = 0;
        for (int i = 0; i < input.length(); i++) {
            if (i % 2 == 1 && input.charAt(i) != ' ') {
                if (!Character.isDigit(input.charAt(i)))
                    throw new Exception("Invalid input. Please enter numbers from 0 to 8. Try again.\n");
                else {
                    data[currentNumber] = input.charAt(i) - '0';
                    currentNumber++;
                }
            }
        }
        // Harry in [0,0]
        if (data[0] != 0 || data[1] != 0)
            throw new Exception("Harry must have coordinates [0,0]. Try again.\n");
        if (isInInspecorZone(data[0], data[1], data[2], data[3], data[4], data[5]))
            throw new Exception(
                    "Harry should not be in the inspection cells. Try again.\n");
        // Exit not in inspectors cells and not in Book cell
        if ((data[10] == data[6] && data[11] == data[7])
                || isInInspecorZone(data[10], data[11], data[2], data[3], data[4], data[5]))
            throw new Exception(
                    "Exit should not be in the inspection cells and in the cell where the book is located. Try again.\n");

        // Book not in inspectors cells
        if (isInInspecorZone(data[6], data[7], data[2], data[3], data[4], data[5]))
            throw new Exception(
                    "Book should not be in the inspection cells. Try again.\n");

        // Invisibility Cloak not in inspectors cells
        if (isInInspecorZone(data[8], data[9], data[2], data[3], data[4], data[5]))
            throw new Exception(
                    "Invisibility Cloak should not be in the inspection cells. Try again.\n");
        return data;
    }

    public static void main(String[] args) {

        boolean invalidInput = true;
        System.out.print("Do you want to generate a map automatically (enter 'a') or enter data manually (enter 'm').\n");
        Scanner console = new Scanner(System.in);
        Library lib = new Library();
        int data[] = new int[13];
        String input = new String();
        while (invalidInput) {
            if (invalidInput)
                input = console.nextLine();
            if (input.equals("a")) {
                invalidInput = false;
                lib = new Library();
            } else if (input.equals("m")) {
                System.out.print(
                        "Please enter the coordinates of the objects in the following form:\n[xHarry,yHarry] [xArgus,yArgus] [xNorris,yNorris] [xBook,yBook] [xCloak,yCloak] [xExit,yExit]\nСoordinates of objects must be between 0 and 8.\n");
                while (invalidInput) {
                    try {
                        data = inputScanner();
                        lib = new Library(data);
                        invalidInput = false;
                    } catch (Exception e) {
                        System.out.print(e.getMessage());
                    }
                }
            } else {
                System.out.print("Invalid input. Enter 'a' or 'm'.\n");
            }
        }
        System.out.print(
                "Please select a Perception Scenario.\nEnter '1' if you want Harry to see inspector zones with a 1 cell radius.\nEnter '2' if you want Harry to see inspector zones with a 2 cell radius.\n");
        invalidInput = true;
        int visionMode = 0;
        while (invalidInput) {
            if (invalidInput)
                input = console.nextLine();
            invalidInput = false;
            if (input.equals("1")) {
                visionMode = 1;
            } else if (input.equals("2")) {
                visionMode = 2;
            } else {
                System.out.print("Invalid input. Enter '1' or '2'.\n");
                invalidInput = true;
            }
        }
        Harry harry = new Harry(visionMode, lib);
        System.out.print("Your map:\n");
        lib.print();
        System.out.print("Result of backtracking Search:\n");
        if(harry.backtrackingSearch()==0) System.out.print(" Unsuccesfull:(\n");
        else{
            System.out.print("  Succesfull!\n");
            System.out.print("  Total length: " + harry.totalPath.size()+"\n");
            System.out.print("  Total path: ");
            harry.printPath();
            System.out.print("\n");
        }
        System.out.print("Result of A Start Search:\n");
        if(harry.aStarSearch().size()==0){
            System.out.print(" Unsuccesfull:(\n");
        }
        else{
            System.out.print("  Succesfull!\n");
            System.out.print("  Total length: " + harry.totalPath.size()+"\n");
            System.out.print("  Total path: ");
            harry.printPath();
            System.out.print("\n");
        }
        console.close();

    }
}