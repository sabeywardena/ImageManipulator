import javax.swing.UIManager;
import javax.swing.JFrame;
import javax.swing.UnsupportedLookAndFeelException;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.io.FileReader;
import java.io.InputStreamReader;


import controller.JPanelController;
import controller.IMEController;
import view.IMEFrame;

/**
 * RunIME is the main class for this program, with the main method
 * and invokes the controller.
 */
public class RunIME {
  /**
   * The main method for the program.
   *
   * @param args it's the arguments for the main method.
   */
  public static void main(String[] args) {
    if (args.length > 0) {
      if ("-text".equals(args[0])) {
        Readable r = new InputStreamReader(System.in);
        IMEController c = new IMEController(r);
        try {
          c.runCommand();
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
      else if ("-file".equals(args[0])) {
        try {
          Readable r = new FileReader(args[1]);
          IMEController c = new IMEController(r);
          c.runCommand();
        }
        catch (FileNotFoundException e) {
          throw new IllegalArgumentException("file not found at " + args[1]);
        }
        catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    }
    else {
      IMEFrame.setDefaultLookAndFeelDecorated(false);
      IMEFrame frame = new IMEFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);

      JPanelController c = new JPanelController(frame);

      try {
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

      } catch (UnsupportedLookAndFeelException e) {
        // handle exception
      } catch (ClassNotFoundException e) {
        // handle exception
      } catch (InstantiationException e) {
        // handle exception
      } catch (IllegalAccessException e) {
        // handle exception
      } catch (Exception e) {
        // handles exception
      }
    }
  }
}

