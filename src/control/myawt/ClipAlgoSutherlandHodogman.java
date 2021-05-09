package control.myawt;

import java.util.ArrayList;

public class ClipAlgoSutherlandHodogman {
    
    public static final int EDGE_COUNT = 4;
    
    private static class Edge {
        final MyPoint start;
        final MyPoint end;

        public Edge(MyPoint start, MyPoint end) {
            this.start = start;
            this.end = end;
        }
    }
    
    /**
     * 
     * @param input input points
     * @param clipPoints vertices of clipping polygon
     * @return clipped points
     */
    public ArrayList<MyPoint> process(ArrayList<MyPoint> input, double[][] clipPoints) {
        ArrayList<MyPoint> output = new ArrayList<>(input);
        
        for (MyPoint pt: output) {
            if (Double.isFinite(pt.getCoordY())) {
                pt.setCoordY((int) (Math.signum(pt.getCoordY()) * 1E6));
            }
        }
        
        for (int i = 0; i < EDGE_COUNT; i++) {
            output = clipWithEdge(createEdge(clipPoints, i), output);
        }
        
        return output;
    }
    
    private Edge createEdge(double[][] clipPoints, int i) {
        return new Edge(createPoint(clipPoints[(i + 3) % EDGE_COUNT]), createPoint(clipPoints[i]));
    }
    
    private MyPoint createPoint(double[] value) {
        return new MyPoint(value[0], value[1]);
    }
    
    private ArrayList<MyPoint> clipWithEdge(Edge edge, ArrayList<MyPoint> input) {
        ArrayList<MyPoint> output = new ArrayList<>();
        
        for (int i = 0; i < input.size(); i++) {
            MyPoint prev = input.get((i > 0 ? i : input.size()) - 1);
            MyPoint current = input.get(i);
            addClippedOutput(edge, prev, current, output);
        }
        
        return output;
    }
    
    private void addClippedOutput(Edge edge, MyPoint prev, MyPoint current, ArrayList<MyPoint> output) {
        if (isInside(edge, current)) {
            if (!isInside(edge, prev)) {
                MyPoint intersection = intersection(edge, prev, current);
                output.add(intersection);
            }
            output.add(current);
        } else if (isInside(edge, prev)) {
            output.add(intersection(edge, prev, current));
        }
    }
    
    /**
     * NOT SURE FOR CORRECTNESS.
     * Check other answers: https://stackoverflow.com/questions/328107/how-can-you-determine-a-point-is-between-two-other-points-on-a-line-segment
     * @param edge
     * @param p
     * @return 
     */
    private static boolean isInside(Edge edge, MyPoint p) {
        return (edge.start.coordX - p.coordX) * (edge.end.coordY - p.coordY)
                < (edge.start.coordY - p.coordY) * (edge.end.coordX - p.coordX);
    }
    
    private static MyPoint intersection(Edge edge, MyPoint point1, MyPoint point2) {
        double A1 = edge.end.coordY - edge.start.coordY;
        double B1 = edge.start.coordX - edge.end.coordX;
        double C1 = A1 * edge.start.coordX + B1 * edge.start.coordY;
        
        double A2 = point2.coordY - point1.coordY;
        double B2 = point1.coordX - point1.coordX;
        double C2 = A2 * point1.coordX + B2 * point1.coordY;
        
        double det = A1 * B2 - A2 * B1;
        
        double x = (B2 * C1 - B1 * C2) / det;
        double y = (A1 * C2 - A2 * C1) / det;
        
        // Add 0.0 to avoid -0.0 problem.
        return new MyPoint(x + 0.0, y + 0.0, point2.getPointDrawingType());
    }
}
