import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

/**
 * Each download task is an instance of this class
 * For adding a new download we should add it to a queue
 * and then add JPanel of download to the MainForm place for downloads.
 * @author  Mohamad Chaman-Motlah
 * @version 1
 */
public class Download{
    private JPanel downloadPanel;
    private JProgressBar progressBar;
    private String name;
    private String address;
    private long volume;
    private long downloadedVolume;
    private double percentDownload;
    private long downloadRate;
    private String link;
    private LocalDateTime addTime;
    private DownloadInfoForm downloadInfoForm;
    private boolean isSelected;
    private int indexInDownloads;
    private DownloadInfo downloadInfo;
    private Queue queue;
    private LocalDateTime startTime;

    public Download(String name, String address, long volume, long downloadedVolume, double percentDownload, long downloadRate, String link, Queue queue, LocalDateTime startTime)
    {
        this.name = name;
        this.address = address;
        this.volume = volume;
        this.downloadedVolume = downloadedVolume;
        this.percentDownload = percentDownload;
        this.downloadRate = downloadRate;
        this.link = link;
        this.startTime = startTime;

        downloadPanel = new JPanel(new GridLayout(2,3,10,10));
        JLabel label1 = new JLabel("Name: " + name);
        JLabel label2 = new JLabel("              Download rate:  " + this.downloadRate);
        JLabel label3 = new JLabel("Size: " + volume + "MB");
        JLabel label4 = new JLabel("         \t                  " + this.percentDownload + "% Downloaded");
        JLabel label5 = new JLabel("              " + this.downloadedVolume + "MB Downloaded");
        progressBar = new JProgressBar();
        downloadPanel.add(label1);
        downloadPanel.add(progressBar);
        downloadPanel.add(label2);
        downloadPanel.add(label3);
        downloadPanel.add(label4);
        downloadPanel.add(label5);
        downloadPanel.setPreferredSize(new Dimension(1000,100));

        addTime = LocalDateTime.now();
        downloadInfoForm = new DownloadInfoForm(name, address, link , volume, this.downloadedVolume, this.percentDownload, this.downloadRate, addTime, queue, startTime);
        this.address = address;
        downloadPanel.setPreferredSize(new Dimension(970,100));

        isSelected = false;
        this.queue = queue;
        downloadInfo = new DownloadInfo(name,address,volume, this.downloadedVolume, this.percentDownload, this.downloadRate,link, addTime, this.startTime);

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

    /**
     * @return {@code true} if the download is selected, {@code false} otherwise
     */
    public boolean getIsSelected() {
        return isSelected;
    }

    /**
     * set selection status for the download
     * @param selected
     */
    public void setIsSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * sets number of this download in queue
     * @param indexInDownloads
     */
    public void setIndexInDownloads(int indexInDownloads) {
        this.indexInDownloads = indexInDownloads;
        downloadInfoForm.setIndexInDownloads(indexInDownloads);
    }

    /**
     * @return DownloadInfo as DownloadInfo Object
     */
    public DownloadInfo getDownloadInfo()
    {
        return downloadInfo;
    }
    /**
     * Set DownloadVolume fo a Download
     * @param downloadedVolume is current download volume
     */
    public void setDownloadedVolume(long downloadedVolume) {
        this.downloadedVolume = downloadedVolume;
    }

    /**
     * @return name of file as a string
     */
    public String getName() {
        return name;
    }

    /**
     * @return link of Download as a String
     */
    public String getLink(){
        return link;
    }

    /**
     * @return size of downloading file
     */
    public long getVolume() {
        return volume;
    }

    /**
     * @return start time of download
     */
    public LocalDateTime getAddTime() {
        return addTime;
    }

    public JProgressBar getProgressBar()
    {
        return progressBar;
    }
}
