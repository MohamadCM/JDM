import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class is used to keep and save
 * Download info of each Download
 * to use them to read and write Downloads to File
 * @author Mohamad Chaman-Motlagh
 * @version 1
 */
public class DownloadInfo implements Serializable {
    private String name;
    private String address;
    private long volume;
    private long downloadedVolume;
    private double percentDownload;
    private long downloadRate;
    private String link;
    private LocalDateTime addTime;
    private LocalDateTime startTime;

    public DownloadInfo(String name, String address, long volume, long downloadedVolume, double percentDownload, long downloadRate, String link, LocalDateTime addTime, LocalDateTime startTime) {
        this.name = name;
        this.address = address;
        this.volume = volume;
        this.downloadedVolume = downloadedVolume;
        this.percentDownload = percentDownload;
        this.downloadRate = downloadRate;
        this.link = link;
        this.addTime = addTime;
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public long getDownloadedVolume() {
        return downloadedVolume;
    }

    public void setDownloadedVolume(long downloadedVolume) {
        this.downloadedVolume = downloadedVolume;
    }

    public double getPercentDownload() {
        return percentDownload;
    }

    public void setPercentDownload(double percentDownload) {
        this.percentDownload = percentDownload;
    }

    public long getDownloadRate() {
        return downloadRate;
    }

    public void setDownloadRate(long downloadRate) {
        this.downloadRate = downloadRate;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    /**
     * @return starting time of Download
     */
    public LocalDateTime getStartTime()
    {
       return startTime;
    }


    /**
     * Sets start time for download
     * @param startTime is given startTime
     */
    public void setStartTime(LocalDateTime startTime){
        this.startTime = startTime;
    }

}
