import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * this class creates Frame of download queue
 *
 * @author Mohamad Chaman-Motlagh
 * @version 1
 */
public class QueueFrame {
    private static JFrame mainFrame;
    private static JPanel downloadsPanel;
    private static Queue queue;
    private JToolBar toolBar;
    private JButton newDownloadButton;
    private JButton pauseButton;
    private JButton resumeButton;
    private JButton cancelButton;
    private JButton removeButton;
    private JTextField searchField;
    private JButton searchButton;
    private JButton sortButton;
    private MouseAdapter mouseListener;
    private SettingForm settingForm;

    public QueueFrame(Queue queue, SettingForm settingForm) {
        mainFrame = new JFrame("Queue");
        mainFrame.setSize(900, 500);
        mainFrame.setLocationRelativeTo(MainForm.getDownloadManager());

        downloadsPanel = new JPanel(new GridLayout(20, 1, 1, 1));
        downloadsPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JScrollPane scrollPane = new JScrollPane(downloadsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 30, 300, 50);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(scrollPane, BorderLayout.CENTER);

        mouseListener = new MyMouseListener();

        toolBar = new JToolBar();

        newDownloadButton = new JButton("", new ImageIcon("Images/add.png"));
        newDownloadButton.setFocusable(false);

        pauseButton = new JButton("", new ImageIcon("Images/pause.png"));

        newDownloadButton.setToolTipText("Start a new download!");
        newDownloadButton.addMouseListener(mouseListener);

        pauseButton = new JButton("", new ImageIcon("Images/pause.png"));
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

        sortButton = new JButton("", new ImageIcon("Images/sort.png"));
        sortButton.setToolTipText("Sort by options");
        sortButton.setFocusable(false);
        sortButton.addMouseListener(mouseListener);

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

        toolBar.add(newDownloadButton);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.addSeparator();
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(pauseButton);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(resumeButton);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(cancelButton);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(removeButton);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.addSeparator();
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(sortButton);
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.addSeparator();
        toolBar.add(Box.createHorizontalStrut(15));
        toolBar.add(searchField);
        toolBar.add(searchButton);

        mainFrame.add(toolBar, BorderLayout.NORTH);
        mainFrame.add(scrollPane, BorderLayout.CENTER);

        this.settingForm = settingForm;
        this.queue = queue;

        JPopupMenu sortMenu = new JPopupMenu();
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

        if (FileUtils.readQueue(queue) != null)
            queue.setDownloads(FileUtils.readQueue(queue));
        updateDownloadList();

        queue.sortBy("time", true);


        for (Download d : queue.getDownloads())
            d.getProgressBar().setValue((int) d.getDownloadInfo().getPercentDownload());
    }

    /**
     * This class is used to update the download list and show them
     */
    public static void updateDownloadList() {
        downloadsPanel.removeAll();
        for (Download d : queue.getDownloads()) {
            downloadsPanel.add(d.getDownloadPanel());
            d.getDownloadPanel().setBackground(Color.WHITE);
            if (d.getDownloadPanel().getMouseListeners().length == 0)
                d.getDownloadPanel().addMouseListener(new DPanelMouseLister());
            d.setIndexInDownloads(queue.getIndex(d));
            if (!d.isStarted() && !d.isFinished() && !d.isCancelled() && countSimulationsDownloads() < 1) {
                d.getDownloadUtil().execute();
                d.setIsStarted(true);
            }

        }
        FileUtils.writeQueue(queue);
        downloadsPanel.revalidate();
        downloadsPanel.repaint();
    }

    /**
     * @return main queue as Queue object
     */
    public static Queue getQueue() {
        return queue;
    }

    public static void repaintForm() {
        mainFrame.revalidate();
        mainFrame.repaint();
        for (Download d : queue.getDownloads())
            d.getProgressBar().setValue((int) Math.abs(d.getPercentDownload()));
    }

    public static JFrame getMainFrame() {
        return mainFrame;
    }

    private static int countSimulationsDownloads() {
        int result = 0;
        for (Download d : queue.getDownloads())
            if (d.isStarted() && (!d.isFinished() && !d.isCancelled() && !d.isPaused()))
                result++;
        return result;
    }

    /**
     * This methods shows this form GUI
     */
    public void showGUI() {
        mainFrame.setVisible(true);
    }

    /**
     * Adds download panel to frame
     *
     * @param d is given download panel
     */
    public void addDownloadPanel(JPanel d) {
        downloadsPanel.add(d);
    }

    private void addDownload() {
        NewDownloadForm downloadForm = new NewDownloadForm(settingForm.getSaveAdress(), queue);
        downloadForm.showForm();
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
        System.out.print("");
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
                }
                if (mouseEvent.getSource().equals(d.getDownloadPanel()) && mouseEvent.getButton() == MouseEvent.BUTTON3)
                    d.showDownloadInfoForm();
                if (mouseEvent.getSource().equals(d.getDownloadPanel()) && mouseEvent.getButton() == MouseEvent.BUTTON1) {
                    for (Download tmpDownload : queue.getDownloads()) {
                        if (tmpDownload.getDownloadPanel().getBackground().equals(Color.decode("#ffaa00"))) {
                            System.out.print("");
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
            if (mouseEvent.getSource().equals(newDownloadButton))
                addDownload();
            if (mouseEvent.getSource().equals(removeButton)) {
                cancel();
                delete();
            }
            if (mouseEvent.getSource().equals(resumeButton))
                resume();
            if (mouseEvent.getSource().equals(pauseButton))
                pause();
            if (mouseEvent.getSource().equals(cancelButton))
                cancel();
            if (mouseEvent.getSource().equals(searchButton))
                findAndMark();
        }
    }

}
