package view;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;


/**
 * Renders a visual representation of data collected in a histogram. References 4 arrays of data
 * to draw a histogram where each RGB channel along with overall intensity is represented.
 */
public class HistogramPanel extends JPanel {
  private int[] red;
  private int[] green;
  private int[] blue;
  private int[] intense;
  private static final Color redTrans = new Color(255, 0, 0, 150);
  private static final Color greenTrans = new Color(0, 255, 0, 120);
  private static final Color blueTrans = new Color(0, 0, 255, 100);
  private static final Color intenseTrans = new Color(150, 150, 150, 100);


  /**
   * Displays the histogram data by repainting the JPanel using the repaint method.
   *
   * @param red     the array of integers for the red channel data
   * @param green   the array of integers for the green channel data
   * @param blue    the array of integers for the blue channel data
   * @param intense the array of integers for the intensity data
   */
  public void showHistogram(int[] red, int[] green, int[] blue, int[] intense) {
    this.red = red;
    this.green = green;
    this.blue = blue;
    this.intense = intense;
    repaint();
  }

  @Override
  protected void paintComponent(Graphics g) {
    if (red == null || green == null || blue == null || intense == null) {
      return;
    } // No display if
    // any of the channel value frequency arrays are null

    super.paintComponent(g);

    int width = getWidth();
    int height = getHeight();
    int interval = width / 256;
    int individualWidth = (int) (interval * .8);

    int maxCount = 0;
    for (int i = 0; i < 256; i++) {
      if (maxCount < red[i]) {
        maxCount = red[i];
      }
      if (maxCount < green[i]) {
        maxCount = green[i];
      }
      if (maxCount < blue[i]) {
        maxCount = blue[i];
      }
      if (maxCount < intense[i]) {
        maxCount = intense[i];
      }
    }

    // x is the start position for the first bar in the histogram
    int x = 0;

    g.drawLine(10, height - 45, width - 10, height - 45);

    for (int i = 0; i < 256; i++) {
      int barHeight = (int) (((double) red[i] / (double) maxCount) * (height - 55));
      g.setColor(redTrans);
      g.drawRect(x, height - 45 - barHeight, individualWidth,
          barHeight);

      barHeight = (int) (((double) green[i] / (double) maxCount) * (height - 55));
      g.setColor(greenTrans);
      g.drawRect(x, height - 45 - barHeight, individualWidth,
          barHeight);

      barHeight = (int) (((double) blue[i] / (double) maxCount) * (height - 55));
      g.setColor(blueTrans);
      g.drawRect(x, height - 45 - barHeight, individualWidth,
          barHeight);

      barHeight = (int) (((double) intense[i] / (double) maxCount) * (height - 55));
      g.setColor(intenseTrans);
      g.drawRect(x, height - 45 - barHeight, individualWidth,
          barHeight);

      g.drawString("0", 0, height - 30);
      g.drawString("255", 255 * interval, height - 30);

      x += interval;
    }
  }
}
