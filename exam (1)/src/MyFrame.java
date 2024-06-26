import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MyFrame extends JFrame {
    private int W, H; // Width and height of the frame

    // Default constructor: Sets default screen/window size (800x500)
    public MyFrame() {
        super(); // Call JFrame constructor
        H = 800;
        W = 500;
        setWindowSize(W, H);
    }

    // Parameterized constructor: Allows setting custom width and height
    public MyFrame(int width, int height) {
        super();
        setWindowSize(width, height);
    }

    // Parameterized constructor: Allows setting title, width, and height
    public MyFrame(String title, int width, int height) {
        super(title);
        setWindowSize(width, height);
    }

    // Parameterized constructor: Allows setting title, width, height, and visibility
    public MyFrame(String title, int width, int height, boolean visible) {
        super(title);
        setWindowSize(width, height);
        setVisible(visible);
    }

    // Set window size method
    public void setWindowSize(int width, int height) {
        H = height;
        W = width;
        setSize(width, height);
    }

    // Set frame properties (title, width, height)
    public void setMyFrame(String title, int width, int height) {
        setTitle(title);
        setWindowSize(width, height);
    }

    // Set frame properties (title, width, height, visibility)
    public void setMyFrame(String title, int width, int height, boolean visible) {
        setMyFrame(title, width, height);
        setVisible(visible);
    }

    // Set frame properties (title, width, height, visibility, close operation)
    public void setMyFrame(String title, int width, int height, boolean visible, int closeOperation) {
        setMyFrame(title, width, height, visible);
        setDefaultCloseOperation(closeOperation);
    }

    // Set frame properties (title, width, height, visibility, close operation, resizable)
    public void setMyFrame(String title, int width, int height, boolean visible, int closeOperation, boolean resizable) {
        setMyFrame(title, width, height, visible, closeOperation);
        setResizable(resizable);
    }

    // Set background color with transparency
    public void setBackgroundColor(int red, int green, int blue, int opacity) {
        getContentPane().setBackground(new Color(red, green, blue, opacity));
    }

    // Set background image
    public JPanel setBackgroundImage(String file) {
        JPanel panelBG = new JPanel();
        JLabel img = new JLabel(new ImageIcon(file));
        panelBG.add(img);
        return panelBG;
    }
}
