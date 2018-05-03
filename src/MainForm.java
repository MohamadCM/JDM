import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    private JMenu help;
    private JMenu exitMenu;
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
    private JMenuItem aboutMe;
    private JMenuItem exit;
    private JPanel downloadPanel;
    private SettingForm settingForm;
    private MouseListener mouseListener;
    /**
     * Each mainForm needs a title to get created
     * @param title
     */
    public MainForm(String title)
    {
        downloadManager = new JFrame(title);
        downloadManager.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        downloadManager.setSize(1200,900);
        downloadManager.setLocationRelativeTo(null);
        settingForm = new SettingForm();
        mainPanel = new JPanel(new BorderLayout());
        leftPanel = new JPanel(new GridLayout(6,1,5,5));
        downloadManager.setContentPane(mainPanel);
        newDownloadButton = new JButton("", new ImageIcon("Images/add.png"));
        newDownloadButton.setFocusable(false);
        pauseButton = new JButton("", new ImageIcon("Images/pause.png"));
        newDownloadButton.setToolTipText("Start a new download!");
        pauseButton.setFocusable(false);
        resumeButton = new JButton("", new ImageIcon("Images/resume.png"));
        resumeButton.setToolTipText("Resume a paused download");
        resumeButton.setFocusable(false);
        cancelButton = new JButton("", new ImageIcon("Images/cancel.png"));
        cancelButton.setToolTipText("Cancel a selected download");
        cancelButton.setFocusable(false);
        removeButton = new JButton("", new ImageIcon("Images/remove.png"));
        removeButton.setToolTipText("Remove selected download");
        removeButton.setFocusable(false);
        settingButton = new JButton("", new ImageIcon("Images/setting.png"));
        settingButton.setToolTipText("App preference");
        settingButton.setFocusable(false);
        leftPanel.add(newDownloadButton);
        leftPanel.add(pauseButton);
        leftPanel.add(resumeButton);
        leftPanel.add(cancelButton);
        leftPanel.add(removeButton);
        leftPanel.add(settingButton);
        downloadManager.add(leftPanel, BorderLayout.WEST);
        menuBar = new JMenuBar();
        menuBar.setFocusable(false);
        downloadMenu = new JMenu("                  Download                         ");
        downloadMenu.setFocusable(false);
        newDownload = new JMenuItem("New Download");
        newDownload.setFocusable(false);
        newDownload.setMnemonic('+');
        pause = new JMenuItem("Pause");
        pause.setFocusable(false);
        pause.setMnemonic('|');
        resume = new JMenuItem("Resume");
        resume.setFocusable(false);
        resume.setMnemonic(')');
        cancel = new JMenuItem("Cancel");
        cancel.setFocusable(false);
        cancel.setMnemonic('*');
        remove = new JMenuItem("Remove");
        remove.setFocusable(false);
        remove.setMnemonic('c');
        setting = new JMenuItem("Setting");
        setting.setFocusable(false);
        setting.setMnemonic('s');
        downloadMenu.add(newDownload);
        downloadMenu.add(pause);
        downloadMenu.add(resume);
        downloadMenu.add(cancel);
        downloadMenu.add(remove);
        downloadMenu.add(setting);
        help = new JMenu("                                   Help                                   ");
        help.setFocusable(false);
        aboutMe = new JMenuItem("                              About me:)                              ");
        aboutMe.setFocusable(false);
        aboutMe.setMnemonic('i');
        help.add(aboutMe);
        help.setFocusable(false);
        exitMenu = new JMenu("                                   Exit                                   ");
        exitMenu.setFocusable(false);
        exit = new JMenuItem("                                   Exit:(                                 ");
        exit.setFocusable(false);
        exit.setMnemonic('x');
        exitMenu.add(exit);
        menuBar.add(downloadMenu);
        menuBar.add(help);
        menuBar.add(exitMenu);
        downloadManager.setJMenuBar(menuBar);
        mouseListener = new MyMouseListener();
        settingButton.addMouseListener(mouseListener);
    }

    /**
     * Makes the mainForm visible
     */
    public void showGUI()
    {
        downloadManager.setVisible(true);
    }

    private class MyMouseListener implements MouseListener
    {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
            if(mouseEvent.getSource().equals(settingButton))
                settingForm.showSetting();
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseReleased(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseEntered(MouseEvent mouseEvent) {

        }

        @Override
        public void mouseExited(MouseEvent mouseEvent) {

        }
    }
    private void showAboutMe()
    {
        JOptionPane.showMessageDialog(null, "Programmer:       Mohamad Chaman-Motlagh\n" +
                "Student number:      9631018\n" +
                "Start date:      2/5/2018\n" +
                "End date:      -\n" +
                "This programme is a simple download manager,\n" +
                "you can use start a new download by using + button\n" +
                "and or remove it using specified buttons\n");
    }
}
