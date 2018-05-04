import javax.swing.*;
import java.awt.*;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * Each download task is an instance of this class
 * For adding a new download we should add it to a queue
 * and then add JPanel of download to the MainForm place for downloads.
 * @author  Mohamad Chaman-Motlah
 * @version 1
 */
public class Download {
    private JPanel downloadPanel;
    private JProgressBar progressBar;
    private String name;
    private String address;
    private long volume;
    private long downloadedVolume;
    private double percentDownload;
    private long downloadRate;
    private String link;
    private LocalDateTime startTime;
    private DownloadInfoForm downloadInfoForm;
    public Download(String name, String address, long volume, String link)
    {
        this.name = name;
        this.address = address;
        this.volume = volume;
        this.link = link;
        downloadPanel = new JPanel(new GridLayout(2,3,10,10));
        JLabel label1 = new JLabel("Name: " + name);
        JLabel label2 = new JLabel("              Download rate:  " + downloadRate);
        JLabel label3 = new JLabel("Size: " + volume + "MB");
        JLabel label4 = new JLabel("         \t                  " + percentDownload + "% Downloaded");
        JLabel label5 = new JLabel("              " + downloadedVolume + "MB Downloaded");
        progressBar = new JProgressBar();
        downloadPanel.add(label1);
        downloadPanel.add(progressBar);
        downloadPanel.add(label2);
        downloadPanel.add(label3);
        downloadPanel.add(label4);
        downloadPanel.add(label5);
        downloadPanel.setPreferredSize(new Dimension(1000,100));
        startTime = LocalDateTime.now();
        downloadInfoForm = new DownloadInfoForm(name, address, link , volume, downloadedVolume, percentDownload, downloadRate, startTime);
    }

    /**
     * @return a panel in order to add it to main form
     */
    public JPanel getDownloadPanel()
    {
        return downloadPanel;
    }

    /**
     * Shows DonloadInfoForm of current download
     */
    public void showDownloadInfoForm()
    {
        downloadInfoForm.showForm();
    }
//    public void updateStatus()

}
