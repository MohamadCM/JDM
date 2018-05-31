import java.io.Serializable;

/**
 * This class keeps and saves default preferences of programme
 *
 * @author Mohamad Chaman-Motalgh
 * @version 1
 */
public class Defaults implements Serializable {
    private String location;
    private int numberOfSimDownloads;
    private String lookAndFeelInfo;

    public Defaults(String location, int numberOfSimDownloads, String lookAndFeelInfo) {

        this.location = location;
        this.numberOfSimDownloads = numberOfSimDownloads;
        this.lookAndFeelInfo = lookAndFeelInfo;

    }

    /**
     * @return deafult location of download
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return default number of simultaneous downloads
     */
    public int getNumberOfSimDownloads() {
        return numberOfSimDownloads;
    }

    /**
     * @return lookAndFeel name
     */
    public String getLookAndFeelInfo() {
        return lookAndFeelInfo;
    }

}
