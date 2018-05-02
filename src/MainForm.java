import javax.swing.*;
import java.awt.*;

/**
 * This class creates main form of the programme
 * and implements it's functions
 * @author Mohamad Chaman-Motlagh
 * @version 1
 */
public class MainForm {
    private JFrame downloadManager;
    private JMenuBar menuBar;
    private JMenu downloadMenu;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JButton newDownloadButton;
    private JButton pauseButton;
    private JButton resumeButton;
    private JButton cancelButton;
    private JButton removeButton;
    private JButton settingButton;
    private JMenuItem newDownload;
    private JMenuItem pause;
    private JMenuItem resume;
    private JMenuItem cancel;
    private JMenuItem remove;
    private JMenuItem setting;
    /**
     * Each mainForm needs a title to get created
     * @param title
     */
    public MainForm(String title)
    {
        downloadManager = new JFrame(title);
        downloadManager.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        downloadManager.setSize(1000,700);
        downloadManager.setLocationRelativeTo(null);
        mainPanel = new JPanel(new BorderLayout());
        leftPanel = new JPanel(new GridLayout(6,1,5,5));
        downloadManager.setContentPane(mainPanel);
        newDownloadButton = new JButton("", new ImageIcon("Images/"));
        newDownloadButton.setFocusable(false);
        pauseButton = new JButton();
        pauseButton.setFocusable(false);
        resumeButton = new JButton();
        resumeButton.setFocusable(false);
        cancelButton = new JButton();
        cancelButton.setFocusable(false);
        removeButton = new JButton();
        removeButton.setFocusable(false);
        settingButton = new JButton();
        settingButton.setFocusable(false);
        leftPanel.add(newDownloadButton);
        leftPanel.add(pauseButton);
        leftPanel.add(resumeButton);
        leftPanel.add(cancelButton);
        leftPanel.add(removeButton);
        leftPanel.add(settingButton);
        downloadManager.add(leftPanel, BorderLayout.WEST);
        menuBar = new JMenuBar();
        downloadMenu = new JMenu("Download");
        menuBar.add(downloadMenu);
        downloadManager.setJMenuBar(menuBar);
    }

    /**
     * Makes the mainForm visible
     */
    public void showGUI()
    {
        downloadManager.setVisible(true);
    }
}
