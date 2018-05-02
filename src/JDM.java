import javax.swing.*;

/**
 * This class is used to run the DownloadManger
 * @author Mohamad Chaman-Motlagh
 * @version 1
 */
public class JDM {
    public static void main(String[] args) throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        MainForm mainForm = new MainForm("Download Manager");
        mainForm.showGUI();
    }
}
