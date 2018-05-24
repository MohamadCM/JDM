import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * this class creates Frame of download queue
 * @author Mohamad Chaman-Motlagh
 * @version 1
 */
public class QueueFrame extends JFrame{
    private JPanel downloadsPanel;
    private JToolBar toolBar;
    private JButton newDownloadButton;
    private JButton pauseButton;
    private JButton resumeButton;
    private JButton cancelButton;
    private JButton removeButton;
    private MouseAdapter mouseListener;
    private Queue queue;
    public QueueFrame(){
        super("Queue");
        setSize(900,500);
        setLocationRelativeTo(MainForm.getDownloadManager());

        downloadsPanel = new JPanel(new GridLayout(20,1,1,1));
        downloadsPanel.setBorder(new EmptyBorder(5,5,5,5));

        JScrollPane scrollPane = new JScrollPane(downloadsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(50, 30, 300, 50);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);

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

        add(toolBar, BorderLayout.NORTH);
        queue = new Queue("Queue");
    }

    /**
     * This methods shows this form GUI
     */
    public void showGUI()
    {
        setVisible(true);
    }

    /**
     * Adds download panel to frame
     * @param d is given download panel
     */
    public void addDownloadPanel(JPanel d)
    {
        downloadsPanel.add(d);
    }

    private class MyMouseListener extends MouseAdapter
    {

        @Override
        public void mousePressed(MouseEvent mouseEvent) {

        }
    }
}
