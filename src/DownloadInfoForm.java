import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class creates a form that is shown when
 * user clicks on download
 * @author Mohamad Chaman-Motlagh
 * @version 1
 */
public class DownloadInfoForm {
    private JFrame frame;
    private JButton okButton;
    private int indexInDownloads;
    private JSpinner numberInQueue;
    private Queue queue;
    private JProgressBar progressBar;
    private JLabel sizeLable;
    private JLabel xPercent;
    private JLabel yMB;
    private JLabel downRate;
    /**
     * Requires name of the download
     * @param name
     */
    public DownloadInfoForm(String name, String address,String link ,long volume, long downloadedVolume, double percentDownload,long downloadRate, LocalDateTime addTime, Queue queue, LocalDateTime startTime)
    {

        this.queue = queue;

        frame = new JFrame("Info of: " + name);
        frame.setSize(500,350);
        frame.setLocation(400,400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel borderPanel = new JPanel();
        borderPanel.setBorder(new EmptyBorder(5,5,5,5));
        frame.setContentPane(borderPanel);
        frame.setLayout(new GridLayout(9,1,5,5));

        JPanel upPanel = new JPanel(new GridLayout(1,2,10,10));
        JLabel nameLable = new JLabel("Name: " + name);
        JLabel timeLabe = new JLabel("Added at: " + addTime.toString());
        upPanel.add(nameLable);
        upPanel.add(timeLabe);
        frame.add(upPanel);
        progressBar = new JProgressBar();
        frame.add(progressBar);
        JPanel midPanel = new JPanel(new GridLayout(1,3,5,5));
        sizeLable = new JLabel("Size: " + volume + "MB");
        xPercent = new JLabel(percentDownload + "% Downloaded");
        yMB = new JLabel(downloadedVolume + "MB downloaded");

        midPanel.add(sizeLable);
        midPanel.add(xPercent);
        midPanel.add(yMB);

        numberInQueue = new JSpinner(new SpinnerNumberModel());
        numberInQueue.setFocusable(false);
        numberInQueue.setValue((Integer)indexInDownloads);
        numberInQueue.addKeyListener(new MyKeyboardListener());
        JLabel numberInQueueLabel = new JLabel("Number in queue: ");
        numberInQueueLabel.setFocusable(false);
        JPanel numberInQueuePanel = new JPanel();
        numberInQueuePanel.setFocusable(false);
        numberInQueuePanel.add(numberInQueueLabel);
        numberInQueuePanel.add(numberInQueue);

        downRate = new JLabel("Download speed: " + downloadRate);

        frame.add(midPanel);
        frame.add(new JLabel("Download link: " + link));
        frame.add(new JLabel("Download to: " + address));
        frame.add(downRate);
        frame.add(numberInQueuePanel);

        okButton = new JButton("OK");
        okButton.addKeyListener(new MyKeyboardListener());
        okButton.addMouseListener(new MyMouseListener());
        okButton.requestFocus();

        JPanel startTimePanel = new JPanel(new GridLayout(1,2,10,10));
        startTimePanel.add(new JLabel("Start Download at:"));
        if(startTime.isBefore(LocalDateTime.now()))
            startTime = LocalDateTime.now();
        startTimePanel.add(new JLabel(startTime.toString()));
        frame.add(startTimePanel);

        frame.add(okButton);
        frame.pack();
    }

    /**
     * Makes the form visible
     */
    public void showForm()
    {
        frame.setVisible(true);
    }

    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            if (mouseEvent.getSource().equals(okButton)) {
                queue.updateQueue(indexInDownloads, (Integer)numberInQueue.getValue());
                MainForm.updateDownloadList();
                frame.dispose();
            }
        }
    }

    private class MyKeyboardListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent keyEvent) {
            if(keyEvent.getKeyChar() == KeyEvent.VK_ENTER) {
                queue.updateQueue(indexInDownloads, (Integer)numberInQueue.getValue());
                MainForm.updateDownloadList();
                frame.dispose();
            }
        }
    }

    /**
     * sets number of this download in queue
     * @param indexInDownloads
     */
    public void setIndexInDownloads(int indexInDownloads) {
        this.indexInDownloads = indexInDownloads;
        numberInQueue.setValue((Integer)indexInDownloads);
    }

    /**
     * Updates status of this download in the form
     * @param percentDownloaded
     */
    public void updateForm(int percentDownloaded, long volume, long downloadedVolume, int downloadRate){
        progressBar.setValue(percentDownloaded);
        sizeLable.setText("Size: " + volume + "KB");
        xPercent.setText(percentDownloaded + "% Downloaded");
        yMB.setText(downloadedVolume + "KB downloaded");
        downRate.setText("Download speed: " + downloadRate + "KB/s");
        progressBar.setValue(percentDownloaded);
        frame.revalidate();
        frame.repaint();
    }
}
