package cube;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Cube3D extends JFrame implements MouseListener, MouseMotionListener,
        MouseWheelListener {
    public int width = 200;
    public int height = 200;
    public JPanel panel;
    public float distance;
    public float angle;
    Point prevMove = new Point();
    public Cube cube;

    public Cube3D() {
        super("Cube");
        angle = (float) Math.toRadians(40);
        distance = (width / 2) / (float) (Math.tan(angle / 2));
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                cube.project(g);
            }
        };
        panel.setPreferredSize(new Dimension(300, 300));
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        cube = new Cube();
    }
    class Cube {
        public int size = 50;
        Vector3D ulf, urf, llf, lrf;
        Vector3D ulb, urb, llb, lrb;
        public Cube() {
            int startx = width / 2 - size / 2;
            int starty = height / 2 - size / 2;
            ulf = new Vector3D(startx, starty, 0);
            urf = new Vector3D(startx + size, starty, 0);
            llf = new Vector3D(startx, starty+size, 0);
            lrf = new Vector3D(startx + size, starty + size, 0);
            ulb = new Vector3D(startx, starty, -size);
            urb = new Vector3D(startx + size, starty, -size);
            llb = new Vector3D(startx, starty+size, -size);
            lrb = new Vector3D(startx + size, starty + size, -size);
        }
        public void project(Graphics g) {
            Point pulf = ulf.to2D();
            Point purf = urf.to2D();
            Point pllf = llf.to2D();
            Point plrf = lrf.to2D();
            Point pulb= ulb.to2D();
            Point purb= urb.to2D();
            Point pllb= llb.to2D();
            Point plrb= lrb.to2D();
            g.setColor(Color.BLACK);
            g.drawLine(plrf.x, plrf.y, purf.x, purf.y);
            g.drawLine(plrf.x, plrf.y, pllf.x, pllf.y);
            g.drawLine(pulf.x, pulf.y, purf.x, purf.y);
            g.drawLine(pulf.x, pulf.y, pllf.x, pllf.y);
            g.setColor(Color.BLACK);
            g.drawLine(purb.x, purb.y, pulb.x, pulb.y);
            g.drawLine(pllb.x, pllb.y, pulb.x, pulb.y);
            g.drawLine(pllb.x, pllb.y, plrb.x, plrb.y);
            g.drawLine(purb.x,purb.y, plrb.x, plrb.y);
            g.setColor(Color.BLACK);
            g.drawLine(pllb.x, pllb.y, pllf.x, pllf.y);
            g.drawLine(pulf.x, pulf.y, pulb.x, pulb.y);
            g.drawLine(purb.x,purb.y, purf.x, purf.y);
            g.drawLine(plrf.x, plrf.y, plrb.x, plrb.y);
        }
        public void move(int dx, int dy)
        {
            ulf.x += dx;
            urf.x += dx;
            llf.x += dx;
            lrf.x += dx;
            ulb.x += dx;
            urb.x += dx;
            llb.x += dx;
            lrb.x += dx;

            ulf.y += dy;
            urf.y += dy;
            llf.y += dy;
            lrf.y += dy;
            ulb.y += dy;
            urb.y += dy;
            llb.y += dy;
            lrb.y += dy;

        }
    }
    private float rotX, rotY, rotZ = 0f;
    class Vector3D {
        public float x, y, z;
        public Vector3D(float x, float y, float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
        public String toString() {
            return "(" + x + "," + y + "," + z + ")";
        }
        public Point to2D() {
            Vector3D v = new Vector3D(x, y, z);
            rotateVector(v, rotY, -rotX, rotZ);
            Point p;
            float Z = distance + v.z;
            p = new Point((int) (distance * v.x / Z), (int) (distance * v.y / Z));
            p.x += width / 2;
            p.y += height / 2;
            return p;
        }
    }
    public void rotateVector(Vector3D p, float thetaX, float thetaY, float thetaZ) {
        float aX, aY, aZ;
        float camX = 0;
        float camY = 0;
        float camZ = 0;
        aY = p.y;
        aZ = p.z;
        p.y = (float) ((aY-camY)*Math.cos(thetaX)-(aZ-camZ)*Math.sin(thetaX));
        p.z = (float) ((aY-camY)*Math.sin(thetaX)+(aZ-camZ)*Math.cos(thetaX));
        aX = p.x;
        aZ = p.z;
        p.x = (float) ((aX-camX)*Math.cos(thetaY)+(aZ-camZ)*Math.sin(thetaY));
        p.z = (float) (-(aX-camX)*Math.sin(thetaY)+(aZ-camZ)*Math.cos(thetaY));
        aY = p.y;
        aX = p.x;
        p.x = (float) ((aX-camX)*Math.cos(thetaZ)-(aY-camY)*Math.sin(thetaZ));
        p.y = (float) ((aY-camY)*Math.cos(thetaZ)+(aX-camX)*Math.sin(thetaZ));
    }
    @Override
    public void mouseMoved(MouseEvent e) {
        prevMove = e.getPoint();
    }
    float ROT_FACTOR = 100;
    @Override
    public void mouseDragged(MouseEvent e) {
        int dx = e.getX()-prevMove.x;
        int dy = e.getY()-prevMove.y;
        if (e.isAltDown()) {
            if (e.isShiftDown()) {
                rotX += dx/ROT_FACTOR;
                rotY += 0;
                rotZ += dy/ROT_FACTOR;
            } else {
                rotX += dx/ROT_FACTOR;
                rotY += dy/ROT_FACTOR;
                rotZ += 0;
            }
        } else {
            cube.move(dx,dy);
        }
        panel.repaint();
        prevMove = e.getPoint();
    }
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
}
