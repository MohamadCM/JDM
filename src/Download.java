import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Each download task is an instance of this class
 * For adding a new download we should add it to a queue
 * and then add JPanel of download to the MainForm place for downloads.
 *
 * @author Mohamad Chaman-Motlah
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
    private LocalDateTime addTime;
    private DownloadInfoForm downloadInfoForm;
    private boolean isSelected;
    private int indexInDownloads;
    private DownloadInfo downloadInfo;
    private Queue queue;
    private LocalDateTime startTime;

    private boolean isStarted;
    private boolean isFinished;
    private boolean isCancelled;
    private boolean isPaused;

    private JLabel nameLabel;
    private JLabel mbDownloaded;
    private JLabel downRateLabel;
    private JLabel sizeLabel;
    private JLabel percentCompeleted;
    private JLabel kbDownloadedLabel;

    private DownloadUtil downloadUtil;

    public Download(String name, String address, long volume, long downloadedVolume, double percentDownload, long downloadRate, String link, Queue queue, LocalDateTime startTime, boolean isFinished, boolean isCancelled) {
        this.name = name;
        this.address = address;
        this.volume = volume;
        this.downloadedVolume = downloadedVolume;
        this.percentDownload = percentDownload;
        this.downloadRate = downloadRate;
        this.link = link;
        this.startTime = startTime;
        isStarted = false;
        this.isFinished = isFinished;
        this.isCancelled = isCancelled;
        try {
            downloadUtil = new DownloadUtil(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

        downloadPanel = new JPanel(new GridLayout(2, 3, 10, 10));
        nameLabel = new JLabel("Name: " + name);
        downRateLabel = new JLabel("          Download rate:  " + this.downloadRate + "KB/s");
        sizeLabel = new JLabel("Size: " + volume + "KB");
        percentCompeleted = new JLabel("         \t                  " + this.percentDownload + "% Downloaded");
        kbDownloadedLabel = new JLabel("              " + this.downloadedVolume + "KB Downloaded");
        progressBar = new JProgressBar();
        downloadPanel.add(nameLabel);
        downloadPanel.add(progressBar);
        downloadPanel.add(downRateLabel);
        downloadPanel.add(sizeLabel);
        downloadPanel.add(percentCompeleted);
        downloadPanel.add(kbDownloadedLabel);
        downloadPanel.setPreferredSize(new Dimension(1000, 100));

        addTime = LocalDateTime.now();
        downloadInfoForm = new DownloadInfoForm(name, address, link, volume, this.downloadedVolume, this.percentDownload, this.downloadRate, addTime, queue, startTime);
        this.address = address;
        downloadPanel.setPreferredSize(new Dimension(970, 100));

        isSelected = false;
        this.queue = queue;
        downloadInfo = new DownloadInfo(name, address, volume, this.downloadedVolume, this.percentDownload, this.downloadRate, link, addTime, this.startTime, isFinished, isCancelled);

    }

    /**
     * @return a panel in order to add it to main form
     */
    public JPanel getDownloadPanel() {
        return downloadPanel;
    }

    /**
     * Shows DonloadInfoForm of current download
     */
    public void showDownloadInfoForm() {
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
     *
     * @param selected
     */
    public void setIsSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * sets number of this download in queue
     *
     * @param indexInDownloads
     */
    public void setIndexInDownloads(int indexInDownloads) {
        this.indexInDownloads = indexInDownloads;
        downloadInfoForm.setIndexInDownloads(indexInDownloads);
    }

    /**
     * @return DownloadInfo as DownloadInfo Object
     */
    public DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    /**
     * Set DownloadVolume fo a Download
     *
     * @param downloadedVolume is current download volume
     */
    public void setDownloadedVolume(long downloadedVolume) {
        this.downloadedVolume = downloadedVolume;
        kbDownloadedLabel.setText("              " + this.downloadedVolume + "KB Downloaded");
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
    public String getLink() {
        return link;
    }

    /**
     * @return size of downloading file
     */
    public long getVolume() {
        return volume;
    }

    /**
     * Sets Volume of this file
     *
     * @param volume
     */
    public void setVolume(long volume) {
        this.volume = volume;
        sizeLabel.setText("Size: " + volume / 1000 + "KB");
    }

    /**
     * @return start time of download
     */
    public LocalDateTime getAddTime() {
        return addTime;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    /**
     * @return save location of download as a String
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets download rate
     *
     * @param downloadRate is current download rate
     */
    public void setDownloadRate(long downloadRate) {
        this.downloadRate = downloadRate;
        downRateLabel.setText("          Download rate:  " + this.downloadRate + "KB/s");
    }

    /**
     * @return percent downloaded
     */
    public double getPercentDownload() {
        return percentDownload;
    }

    /**
     * Sets Percent completed for this download
     *
     * @param percentDownload
     */
    public void setPercentDownload(double percentDownload) {
        this.percentDownload = percentDownload;
        percentCompeleted.setText("         \t                  " + this.percentDownload + "% Downloaded");
    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setIsStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public DownloadInfoForm getDownloadInfoForm() {
        return downloadInfoForm;
    }

    /**
     * @return code {@code true} is the download has been finished
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Set finish stats of this download
     *
     * @param isFinished is current finishing stat of this download
     */
    public void setFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    /**
     * Cancel the download
     */
    public void cancel() {
        isCancelled = true;
        downloadInfo.cancel();
    }

    /**
     * Show whether the download is cancelled or not
     *
     * @return {@code true} if the download is cancelled, {@code false} otherwise
     */
    public boolean isCancelled() {
        return isCancelled;
    }

    /**
     * @return DownloadUtil for this download
     */
    public DownloadUtil getDownloadUtil() {
        return downloadUtil;
    }

    /**
     * @return {@code true} if the download is paused, {@code false} otherwise
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Set pause situation of a download
     *
     * @param paused is a boolean shows whether this download is paused or not
     */
    public void setPaused(boolean paused) {
        isPaused = paused;
    }
}
