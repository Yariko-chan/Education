import java.applet.Applet;
import java.awt.*;

/**
 * Created by Ganeeva Diana in Май, 2018
 * for Lab2
 */
public class AirCraft extends Applet implements Runnable {
    private static final int TOP_CLOUD_WIDTH = 200;
    private static final int BOTTOM_CLOUD_WIDTH = 140;
    private static final int TOP_CLOUD_HEIGHT = 80;
    private static final int BOTTOM_CLOUD_HEIGHT = 70;
    private Thread t ;

    private Cloud topCloud;
    private Cloud bottomCloud;
    private Plane plane;

    public void run () { // реализация метода run, точка входа в поток
        while(true) {
            try {
                repaint();
                Thread.sleep(100);
            }
            catch(InterruptedException e) {
            }
        }
    }

    public void init() {// метод инициализации апплета
        topCloud = new Cloud(getWidth(), TOP_CLOUD_WIDTH, 2);
        bottomCloud = new Cloud(getWidth(), BOTTOM_CLOUD_WIDTH, 10);
        plane = new Plane(140, 180);

        t = new Thread(this);
        t.start();
    }

    public void paint(Graphics g) { // метод прорисовки апплета

        Graphics2D g2 = (Graphics2D)g;
        g2.setRenderingHints(new RenderingHints(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
        g2.setRenderingHints(new RenderingHints(
                RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY));

        // draw background - sky + sun
        setBackground(Color.CYAN);
        g2.setColor(Color.ORANGE);
        int sunRadius = 50;
        g2.fillOval(getWidth() *3/4 - sunRadius, getHeight() / 4 - sunRadius, sunRadius * 2, sunRadius * 2);

        // draw upper cloud
        Rectangle rect = new Rectangle(topCloud.left(), 20, TOP_CLOUD_WIDTH, TOP_CLOUD_HEIGHT);
        drawCloud(rect, g2);

        // draw plane
        drawPlane(100, plane.bottom(), g2);

        // draw down cloud
        rect = new Rectangle(bottomCloud.left(), 140, BOTTOM_CLOUD_WIDTH, BOTTOM_CLOUD_HEIGHT);
        drawCloud(rect, g2);
    }

    private void drawPlane(int left, int bottom, Graphics g) {
        g.setColor(Color.BLUE);
        Polygon body = new Polygon();
        int pX = left + 5;
        int pY = bottom;
        body.addPoint(pX, pY - 10);
        body.addPoint(pX, pY);
        body.addPoint(pX += 100, pY);
        body.addPoint(pX += 5, pY -= 5);
        body.addPoint(pX -= 10, pY -= 10);

        Polygon wing = new Polygon();
        pX = left + 45;
        pY = bottom;
        wing.addPoint(pX, pY);
        wing.addPoint(pX += 40, pY);
        wing.addPoint(pX -= 40, pY -= 50);
        wing.addPoint(pX -= 15, pY);

        Polygon tale = new Polygon();
        pX = left + 5;
        pY = bottom;
        tale.addPoint(pX, pY);
        tale.addPoint(pX += 10, pY);
        tale.addPoint(pX -= 10, pY -= 20);
        tale.addPoint(pX -= 5, pY);

        g.fillPolygon(body);
        g.fillPolygon(wing);
        g.fillPolygon(tale);
    }

    private void drawCloud(Rectangle rect, Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(rect.x, rect.y + rect.height/2, rect.width, rect.height / 2);
        g.fillOval(rect.x + rect.width / 4, rect.y + rect.height / 4, rect.width / 2, rect.height / 2);
        g.fillOval(rect.x + rect.width * 5/8, rect.y + rect.height * 3/8, rect.width * 3/8, rect.height * 3/8);
    }

    private class Cloud implements Runnable {
        private Thread thread;
        private int left = 0;
        private int dif;
        private final int screenWidth;
        private final int objWidth;
        private final int pxPerSec;

        public Cloud(int screenWidth, int objWidth, int pxPerSec) {
            this.screenWidth = screenWidth + objWidth;
            this.objWidth = objWidth;
            this.pxPerSec = pxPerSec;
            dif = screenWidth;
            thread = new Thread(this);
            thread.start();
        }

        @Override
        public void run() {
            while(true) {
                dif = (dif += pxPerSec) % screenWidth;
                left = screenWidth - dif - objWidth;
                try {
                    thread.sleep(100);
                } catch (InterruptedException e) {

                }
            }
        }

        public int left() {
            return left;
        }
    }

    private class Plane implements Runnable {
        private final int topValue;
        private final int bottomValue;
        private Thread thread;
        private int bottom;

        public Plane(int topValue, int bottomValue) {
            this.topValue = topValue;
            this.bottomValue = bottomValue;
            this.bottom = bottomValue;
            thread = new Thread(this);
            thread.start();
        }

        @Override
        public void run() {
            while(true) {
                while ( bottom != topValue) {
                    bottom--;
                    try {
                        thread.sleep(100);
                    } catch (InterruptedException e) {

                    }
                }
                try {
                    thread.sleep(5000);
                } catch (InterruptedException e) {

                }
                while ( bottom != bottomValue) {
                    bottom++;
                    try {
                        thread.sleep(100);
                    } catch (InterruptedException e) {

                    }
                }
                try {
                    thread.sleep(5000);
                } catch (InterruptedException e) {

                }
            }
        }

        public int bottom() {
            return bottom;
        }
    }
}
