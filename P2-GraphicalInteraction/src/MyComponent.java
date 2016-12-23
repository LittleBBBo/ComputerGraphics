import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

/**
 * Created by BodeNg on 2016/12/23.
 */
public class MyComponent extends JComponent implements MouseListener, MouseWheelListener, MouseMotionListener
{
    enum MouseMode {
        NONE,
        DRAG,
        ROTATE
    }

    public Color colorFill = Color.PINK;
    public Color colorBorder = Color.RED;

    private double x, y;
    private double width, height;
    private double radians;
    private MouseMode mouseMode = MouseMode.NONE;
    private boolean isFocus = false;

    private double originX, originY;
    private double originWidth, originHeight;
    private double originMouseX, originMouseY;
    private Point2D.Double originCenter;

    public MyComponent(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setBackground(Color.BLACK);
        addMouseListener(this);
        addMouseWheelListener(this);
        addMouseMotionListener(this);
    }

    private void saveOriginLocation(double x, double y, double width, double height, double mouseX, double mouseY) {
        originX = x;
        originY = y;
        originWidth = width;
        originHeight = height;
        originMouseX = mouseX;
        originMouseY = mouseY;
        originCenter = new Point2D.Double(x + width / 2, y + height / 2);
    }

    private void setNewLocation(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
//        g.setColor(Color.BLACK);
//        g.fillRect(getX(), getY(), getWidth(), getHeight());
        if (isFocus) {
            g.setColor(colorBorder);
            g.drawRect((int) x, (int) y, (int) width, (int) height);
        }
        g.setColor(colorFill);
        g.fillRect((int) x, (int) y, (int) width, (int) height);
        g.setColor(Color.BLACK);
        g.drawString("拖我!", (int)(x + width / 2 - 12), (int) (y + height / 2 + 5));
        g.drawString("滚我!", getWidth() / 2 - 12, 40);

//        Graphics2D g2d = (Graphics2D)g;
//        g2d.setColor(Color.CYAN);
//        Rectangle rect2 = new Rectangle((int) x, (int) y, (int) width, (int) height);
//
//        g2d.rotate(Math.toRadians(radians));
//        g2d.draw(rect2);
//        g2d.fill(rect2);
    }

    private boolean isInside(double x, double y) {
        return  (this. x <= x && x <= this.x + width &&
                this.y <= y && y <= this.y + height);
    }

    private double getAngle(Point2D.Double pSrc, Point2D.Double p1, Point2D.Double p2) {
        double angle = 0.0; // 夹角

        // 向量Vector a的(x, y)坐标
        double va_x = p1.x - pSrc.x;
        double va_y = p1.y - pSrc.y;

        // 向量b的(x, y)坐标
        double vb_x = p2.x - pSrc.x;
        double vb_y = p2.y - pSrc.y;

        double productValue = (va_x * vb_x) + (va_y * vb_y);  // 向量的乘积
        double va_val = Math.sqrt(va_x * va_x + va_y * va_y);  // 向量a的模
        double vb_val = Math.sqrt(vb_x * vb_x + vb_y * vb_y);  // 向量b的模
        double cosValue = productValue / (va_val * vb_val);      // 余弦公式

        // acos的输入参数范围必须在[-1, 1]之间，否则会"domain error"
        // 对输入参数作校验和处理
        if(cosValue < -1 && cosValue > -2)
            cosValue = -1;
        else if(cosValue > 1 && cosValue < 2)
            cosValue = 1;

        // acos返回的是弧度值，转换为角度值
        angle = Math.acos(cosValue) * 180 / Math.PI;
        return angle;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (isInside(e.getX(), e.getY())) {
            saveOriginLocation(x, y, width, height, e.getX(), e.getY());
            mouseMode = MouseMode.DRAG;
        } else {
//            saveOriginLocation(x, y, width, height, e.getX(), e.getY());
//            mouseMode = MouseMode.ROTATE;
        }
//        System.out.println("Mouse Mode: " + mouseMode);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseMode = MouseMode.NONE;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        // compute new width and height
        double rate = 0.1;
        double scale = e.getWheelRotation() * rate;
        if (Math.abs(scale) > 1)
            scale = scale / Math.abs(scale);
        double newWidth = width * (1 + scale);
        double newHeight = height * (1 + scale);

        // apply new location
        double newX = x + (width - newWidth) / 2;
        double newY = y + (height - newHeight) / 2 ;
        setNewLocation(newX, newY, newWidth, newHeight);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (mouseMode == MouseMode.DRAG) {
            setNewLocation(originX + e.getX() - originMouseX, originY + e.getY() - originMouseY,
                    originWidth, originHeight);
        } else if (mouseMode == MouseMode.ROTATE) {
            radians += getAngle(originCenter, new Point2D.Double(originMouseX, originMouseY), new Point2D.Double(e.getX(), e.getY()));
            repaint();
            System.out.println(radians);
        }
    }

    // need not to use
    @Override
    public void mouseMoved(MouseEvent e) {
        isFocus = isInside(e.getX(), e.getY());
        repaint();
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }
}
