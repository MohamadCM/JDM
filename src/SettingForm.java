import javax.swing.*;

/**
 * This class creates setting form
 * @author Mohamad Chaman-Motlagh
 * @version 1
 */
public class SettingForm {
    private JFrame mainFrame;
    private JFileChooser fileChooser;
    private JList<UIManager.LookAndFeelInfo> lookAndFeelInfoJList;
    public SettingForm()
    {
        mainFrame = new JFrame("Setting");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocation(600,300);
        mainFrame.setSize(700,500);
    }
    /**
     * Shows setting form
     */
    public void showSetting()
    {
        mainFrame.setVisible(true);
    }
}
