import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * This class creates main form of the programme
 * and implements it's functions
 *
 * @author Mohamad Chaman-Motlagh
 * @version 1
 */
public class MainForm {
    private static MyJFrame downloadManager;
    private static JPanel downloadPanel;
    private static Queue queue;
    private static JScrollPane scrollPane;
    private static DPanelMouseLister downloadPanelMouseLister;
    private static QueueFrame queueFrame;
    private final int MaxNumberOfDownloads = 20;
    private JMenuBar menuBar;
    private JMenu downloadMenu;
    private JMenu help;
    private JMenu exitMenu;
    private JPanel mainPanel;
    private JToolBar upToolbar;
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
    private JMenuItem accessRemoved;
    private JMenuItem exportToZip;
    private SettingForm settingForm;
    private MouseListener mouseListener;
    private JTextField searchField;
    private JButton searchButton;
    private JButton sortButton;
    private JPopupMenu sortMenu;
    private JButton queueButton;
    private Queue mainQueue;

    /**
     * Each mainForm needs a title to get created
     *
     * @param title
     */
    public MainForm(String title) {
        mouseListener = new MyMouseListener();
        downloadManager = new MyJFrame(title);
        downloadManager.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        downloadManager.setSize(1140, 920);
        downloadManager.setLocationRelativeTo(null);
        downloadManager.setLayout(new BorderLayout());
        settingForm = new SettingForm();
        SwingUtilities.updateComponentTreeUI(downloadManager);
        mainPanel = new JPanel(new BorderLayout());
        upToolbar = new JToolBar();
        //downloadManager.setContentPane(mainPanel);

        newDownloadButton = new JButton("", new ImageIcon("Images/add.png"));
        newDownloadButton.setFocusable(false);


        newDownloadButton.setToolTipText("Start a new download!");
        newDownloadButton.addMouseListener(mouseListener);

        pauseButton = new JButton("", new ImageIcon("Images/pause.png"));
        pauseButton.setToolTipText("Pause a download");
        pauseButton.setFocusable(false);
        pauseButton.addMouseListener(mouseListener);

        resumeButton = new JButton("", new ImageIcon("Images/resume.png"));
        resumeButton.setToolTipText("Resume a paused download");
        resumeButton.setFocusable(false);
        resumeButton.addMouseListener(mouseListener);

        cancelButton = new JButton("", new ImageIcon("Images/cancel.png"));
        cancelButton.setToolTipText("Cancel a selected download");
        cancelButton.setFocusable(false);
        cancelButton.addMouseListener(mouseListener);

        removeButton = new JButton("", new ImageIcon("Images/remove.png"));
        removeButton.setToolTipText("Remove selected download");
        removeButton.setFocusable(false);
        removeButton.addMouseListener(mouseListener);

        settingButton = new JButton("", new ImageIcon("Images/setting.png"));
        settingButton.setToolTipText("App preference");
        settingButton.setFocusable(false);

        sortButton = new JButton("", new ImageIcon("Images/sort.png"));
        sortButton.setToolTipText("Sort by options");
        sortButton.setFocusable(false);

        upToolbar.add(new JLabel(new ImageIcon("Images/ICON.png")));
        upToolbar.add(Box.createHorizontalStrut(40));
        upToolbar.add(newDownloadButton);
        upToolbar.add(Box.createHorizontalStrut(10));
        upToolbar.addSeparator();
        upToolbar.add(pauseButton);
        upToolbar.add(resumeButton);
        upToolbar.add(cancelButton);
        upToolbar.add(removeButton);
        upToolbar.add(Box.createHorizontalStrut(10));
        upToolbar.addSeparator();
        upToolbar.add(Box.createHorizontalStrut(10));
        upToolbar.add(settingButton);
        upToolbar.add(Box.createHorizontalStrut(10));
        upToolbar.addSeparator();
        upToolbar.add(Box.createHorizontalStrut(10));
        upToolbar.add(sortButton);
        upToolbar.addSeparator();
        upToolbar.add(Box.createHorizontalStrut(10));
        mainPanel.add(upToolbar, BorderLayout.NORTH);

        menuBar = new JMenuBar();
        menuBar.setFocusable(false);

        downloadMenu = new JMenu("Download     ");
        downloadMenu.addActionListener(new MyActionListener());
        downloadMenu.setMnemonic(KeyEvent.VK_D);
        downloadMenu.setFocusable(false);

        newDownload = new JMenuItem("New Download");
        newDownload.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK));
        newDownload.setMnemonic(KeyEvent.VK_N);
        newDownload.addActionListener(new MyActionListener());
        newDownload.setFocusable(false);

        pause = new JMenuItem("Pause");
        pause.setMnemonic(KeyEvent.VK_P);
        pause.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.ALT_MASK));
        pause.addActionListener(new MyActionListener());
        pause.setFocusable(false);

        resume = new JMenuItem("Resume");
        resume.setMnemonic(KeyEvent.VK_R);
        resume.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
        resume.addActionListener(new MyActionListener());
        resume.setFocusable(false);

        cancel = new JMenuItem("Cancel");
        cancel.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.ALT_MASK));
        cancel.addActionListener(new MyActionListener());
        cancel.setFocusable(false);
        cancel.setMnemonic(KeyEvent.VK_C);

        remove = new JMenuItem("Remove");
        remove.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.ALT_MASK));
        remove.addActionListener(new MyActionListener());
        remove.setFocusable(false);
        remove.setMnemonic(KeyEvent.VK_D);

        setting = new JMenuItem("Setting");
        setting.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.ALT_MASK));
        setting.addActionListener(new MyActionListener());
        setting.setFocusable(false);
        setting.setMnemonic(KeyEvent.VK_S);

        exportToZip = new JMenuItem("Export to zip");
        exportToZip.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.ALT_MASK));
        exportToZip.addActionListener(new MyActionListener());
        exportToZip.setFocusable(false);
        exportToZip.setMnemonic(KeyEvent.VK_E);

        downloadMenu.add(newDownload);
        downloadMenu.add(pause);
        downloadMenu.add(resume);
        downloadMenu.add(cancel);
        downloadMenu.add(remove);
        downloadMenu.add(setting);
        downloadMenu.add(exportToZip);

        help = new JMenu("Help     ");
        help.setFocusable(false);
        aboutMe = new JMenuItem("About me:)                              ");
        aboutMe.setFocusable(false);
        aboutMe.setMnemonic(KeyEvent.VK_H);
        aboutMe.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.ALT_MASK));
        aboutMe.addActionListener(new MyActionListener());

        accessRemoved = new JMenuItem("Recover removed downloads");
        accessRemoved.setFocusable(false);
        accessRemoved.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.ALT_MASK));
        accessRemoved.setMnemonic(KeyEvent.VK_R);
        accessRemoved.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                FileUtils.openRemovedList();
            }
        });

        help.add(aboutMe);
        help.add(accessRemoved);

        exitMenu = new JMenu("Exit");
        exitMenu.setFocusable(false);

        exit = new JMenuItem("Exit:(");
        exit.setFocusable(false);
        exit.setMnemonic(KeyEvent.VK_X);

        exitMenu.add(exit);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.ALT_MASK));
        exit.addActionListener(new MyActionListener());
        menuBar.add(downloadMenu);
        menuBar.add(help);
        menuBar.add(exitMenu);
        downloadManager.setJMenuBar(menuBar);
        settingButton.addMouseListener(mouseListener);

        /*
        Need a better solution here :((
         */

        downloadPanel = new JPanel(new GridLayout(MaxNumberOfDownloads, 1, 1, 1));
        queue = new Queue("Default");
        scrollPane = new JScrollPane(downloadPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 30, 300, 50);

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        downloadManager.add(mainPanel, BorderLayout.CENTER);

        JPanel leftBlackPanel = new JPanel(new BorderLayout());
        leftBlackPanel.setBackground(Color.decode("#32363F"));
        //leftBlackPanel.add(new JLabel(new ImageIcon("Images/logo.png")),BorderLayout.NORTH);
        queueButton = new JButton("?????????Queue");
        queueButton.setFont(new Font("Arial", Font.BOLD, 20));
        queueButton.setFocusable(false);
        queueButton.setHorizontalAlignment(SwingConstants.LEFT);
        queueButton.setForeground(Color.WHITE);
        queueButton.setOpaque(false);
        queueButton.setContentAreaFilled(false);
        queueButton.setBorderPainted(false);
        queueButton.addMouseListener(mouseListener);
        JPanel leftUpPanel = new JPanel(new BorderLayout());
        leftUpPanel.setBackground(Color.decode("#32363F"));
        leftUpPanel.add(new JLabel(new ImageIcon("Images/logo.png")), BorderLayout.NORTH);
        leftUpPanel.add(queueButton, BorderLayout.CENTER);
        leftBlackPanel.add(leftUpPanel, BorderLayout.NORTH);
        downloadManager.add(leftBlackPanel, BorderLayout.WEST);

        JLabel credit1 = new JLabel("         Created by \n ");
        credit1.setFont(new Font("Arial", Font.BOLD, 20));
        credit1.setForeground(Color.WHITE);
        JLabel credit2 = new JLabel("       Mohamad CM");
        credit2.setFont(new Font("Arial", Font.BOLD, 20));
        credit2.setForeground(Color.WHITE);
        JPanel creditPanel = new JPanel(new GridLayout(2, 1, 1, 1));
        creditPanel.setBackground(Color.decode("#32363F"));
        creditPanel.add(credit1);
        creditPanel.add(credit2);
        leftBlackPanel.add(creditPanel, BorderLayout.SOUTH);

        searchField = new JTextField("Search");
        searchField.setColumns(10);
        searchButton = new JButton("", new ImageIcon("./Images/search.png"));
        searchButton.setFocusable(false);
        searchButton.setToolTipText("Find in downloads!");
        searchButton.addMouseListener(mouseListener);
        searchField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {
            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyChar() == KeyEvent.VK_ENTER)
                    findAndMark();
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {
            }
        });

        upToolbar.add(searchField);
        upToolbar.add(searchButton);


        if (FileUtils.readDownload(queue) != null)
            queue.setDownloads(FileUtils.readDownload(queue));


        downloadPanelMouseLister = new DPanelMouseLister();


        sortMenu = new JPopupMenu();
        sortMenu.add(new JMenuItem(new AbstractAction("Sort by name   " + "???") {
            public void actionPerformed(ActionEvent e) {
                queue.sortBy("name", true);
                updateDownloadList();
            }
        }));
        sortMenu.add(new JMenuItem(new AbstractAction("Sort by size  " + "???") {
            public void actionPerformed(ActionEvent e) {
                queue.sortBy("size", true);
                updateDownloadList();
            }
        }));
        sortMenu.add(new JMenuItem(new AbstractAction("Sort by start time   " + "???") {
            public void actionPerformed(ActionEvent e) {
                queue.sortBy("time", true);
                updateDownloadList();
            }
        }));


        sortButton.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                sortMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });


        mainQueue = new Queue("Main queue");

        queueFrame = new QueueFrame(mainQueue, settingForm);
        queue.sortBy("time", true);

        for (Download d : queue.getDownloads())
            d.getProgressBar().setValue((int) d.getDownloadInfo().getPercentDownload());
        updateDownloadList();

    }

    /**
     * This class is used to update the download list and show them
     */
    public static void updateDownloadList() {
        downloadPanel.removeAll();
        for (Download d : queue.getDownloads()) {
            downloadPanel.add(d.getDownloadPanel());
            d.getDownloadPanel().setBackground(Color.WHITE);
            if (d.getDownloadPanel().getMouseListeners().length == 0)
                d.getDownloadPanel().addMouseListener(downloadPanelMouseLister);
            d.setIndexInDownloads(queue.getIndex(d));
            if (!d.isStarted() && !d.isFinished() && !d.isCancelled() && countSimulationsDownloads() < SettingForm.getSimultaneousDownloads()) {
                d.getDownloadUtil().execute();
                d.setIsStarted(true);
            }
        }
        FileUtils.writeDownload(queue);
        downloadPanel.revalidate();
        downloadPanel.repaint();
    }

    public static void repaintForm() {
        SwingUtilities.invokeLater(() -> {
            downloadPanel.revalidate();
            downloadPanel.repaint();
        });
    }

    /**
     * @return main frame as a JFrame
     */
    public static JFrame getDownloadManager() {
        return downloadManager;
    }

    /**
     * @return main queue as Queue object
     */
    public static Queue getQueue() {
        return queue;
    }

    private static int countSimulationsDownloads() {
        int result = 0;
        for (Download d : queue.getDownloads()) {
            if (d.isStarted() && (!d.isCancelled() && !d.isFinished() && !d.isPaused()))
                result++;
        }
        return result;
    }

    /**
     * Makes the mainForm visible
     */
    public void showGUI() {
        downloadManager.setVisible(true);
    }

    private void addDownload() {
        NewDownloadForm downloadForm = new NewDownloadForm(settingForm.getSaveAdress(), queue);
        downloadForm.showForm();
    }

    private void showAboutMe() {
        JOptionPane.showMessageDialog(null, "Programmer:       Mohamad Chaman-Motlagh\n" +
                "Student number:      9631018\n" +
                "Start date:      2/5/2018\n" +
                "End date:      -\n" +
                "This programme is a simple download manager,\n" +
                "you can use start a new download by using + button\n" +
                "and or remove it using specified buttons\n" +
                "Mnemonics keys are the same as accelerator keys (without ALT)\n" +
                "Close button wont work if your OS won't allow system tray\n" +
                "Thanks for using MY DownloadManager");
    }

    private void delete() {
        if (queue.getDownloads().size() == 0)
            return;
        int i = 0;
        for (i = 0; i < queue.getDownloads().size(); i++)
            if (queue.getDownloads().get(i).getIsSelected())
                break;
        if (i == queue.getDownloads().size())
            return;
        queue.removeDownload(i);
        updateDownloadList();
    }

    private void findAndMark() {
        for (Download d : queue.getDownloads())
            d.getDownloadPanel().setBackground(Color.WHITE);
        for (Download d : queue.getDownloads())
            if (d.getName().contains(searchField.getText()) || d.getLink().contains(searchField.getText()))
                d.getDownloadPanel().setBackground(Color.decode("#51ff54"));
    }

    private void pause() {
        for (Download d : queue.getDownloads())
            if (d.getIsSelected())
                d.getDownloadUtil().pause();
        updateDownloadList();
    }

    private void resume() {
        for (Download d : queue.getDownloads())
            if (d.getIsSelected())
                d.getDownloadUtil().resume();
        updateDownloadList();
    }

    private void cancel() {
        for (Download d : queue.getDownloads())
            if (d.getIsSelected()) {
                d.cancel();
                d.getDownloadUtil().cancel(true);
                updateDownloadList();
            }
    }

    /*
    Listener for downloads
     */
    private static class DPanelMouseLister implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent mouseEvent) {
        }

        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            for (Download d : queue.getDownloads()) {
                if (mouseEvent.getClickCount() == 2 && !mouseEvent.isConsumed() && mouseEvent.getSource().equals(d.getDownloadPanel())) {
                    if (d.getIsSelected() && d.isFinished()) {

                        File file = new File(d.getAddress() + "/" + d.getName());
                        if (!file.exists())
                            return;
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            desktop.open(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                if (mouseEvent.getSource().equals(d.getDownloadPanel()) && mouseEvent.getButton() == MouseEvent.BUTTON3)
                    d.showDownloadInfoForm();
                if (mouseEvent.getSource().equals(d.getDownloadPanel()) && mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    for (Download tmpDownload : queue.getDownloads()) {
                        if (tmpDownload.getDownloadPanel().getBackground().equals(Color.decode("#ffaa00"))) {
                            tmpDownload.getDownloadPanel().setBackground(Color.WHITE);
                            tmpDownload.setIsSelected(false);
                            tmpDownload.getDownloadPanel().revalidate();
                            tmpDownload.getDownloadPanel().repaint();
                        }
                    }
                    d.getDownloadPanel().setBackground(Color.decode("#ffaa00"));
                    d.setIsSelected(true);
                    d.getDownloadPanel().revalidate();
                    d.getDownloadPanel().repaint();
                }
            }
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

    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            if (mouseEvent.getSource().equals(settingButton))
                settingForm.showSetting();
            if (mouseEvent.getSource().equals(newDownloadButton))
                addDownload();
            if (mouseEvent.getSource().equals(removeButton))
                delete();
            if (mouseEvent.getSource().equals(resumeButton))
                resume();
            if (mouseEvent.getSource().equals(pauseButton))
                pause();
            if (mouseEvent.getSource().equals(cancelButton))
                cancel();
            if (mouseEvent.getSource().equals(newDownload))
                addDownload();
            if (mouseEvent.getSource().equals(remove)) {
                cancel();
                delete();
            }
            if (mouseEvent.getSource().equals(searchButton))
                findAndMark();
            if (mouseEvent.getSource().equals(queueButton))
                queueFrame.showGUI();
        }
    }

    private class MyActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getActionCommand().equals("New Download"))
                addDownload();
            if (actionEvent.getActionCommand().equals("Setting"))
                settingForm.showSetting();
            if (actionEvent.getActionCommand().equals("Resume"))
                resume();
            if (actionEvent.getActionCommand().equals("Pause"))
                pause();
            if (actionEvent.getActionCommand().equals("Cancel"))
                cancel();
            if (actionEvent.getActionCommand().equals("Remove")) {
                cancel();
                delete();
            }
            if (actionEvent.getActionCommand().equals("About me:)                              "))
                showAboutMe();
            if (actionEvent.getActionCommand().equals("Exit:(")) {
                FileUtils.writeDownload(queue);
                FileUtils.writeQueue(mainQueue);
                System.exit(0);
            }
            if (actionEvent.getActionCommand().equals("Export to zip"))
                FileUtils.exportToZip();
        }
    }
}