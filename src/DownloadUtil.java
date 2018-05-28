import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * This class is used to download a file
 * and fill the progress bar
 */

public class DownloadUtil extends SwingWorker<Void, Integer> {
    private Download download;
    private HttpURLConnection urlConnection;
    private URL url;
    private int contentLenth;
    private InputStream inputStream;
    /**
     * Each download task need a Download object to complete
     * @param download
     */
    public DownloadUtil(Download download) throws IOException {
        this.download = download;
        if(!download.getLink().startsWith("http://") && !download.getLink().startsWith("https://"))
             url = new URL("http://" + download.getLink());
        else
            url = new URL(download.getLink());
        System.out.println(url);
        urlConnection = (HttpURLConnection) url.openConnection();
        if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
            contentLenth = urlConnection.getContentLength();
            inputStream = urlConnection.getInputStream();
            System.out.println("Connected!");
        }
        else inputStream = null;
    }
    @Override
    protected void process(List<Integer> list) {
        super.process(list);
        MainForm.repaintForm();
//        QueueFrame.repaintForm();
    }

    @Override
    protected Void doInBackground() throws IOException {
        if (inputStream == null)
            throw new IOException();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        FileOutputStream outputStream = new FileOutputStream(new File(download.getAddress() + "/" + download.getName()));
        int bytesRead = 0;
        int totalRead = 0;
        byte[] buffer = new byte[2048];
        int size = urlConnection.getContentLength();
        while((bytesRead = bufferedInputStream.read(buffer) ) > 0) {
            if(Thread.interrupted())
                break;
            outputStream.write(buffer, 0 , bytesRead);
            totalRead += bytesRead;
            download.setDownloadedVolume((int) totalRead/1000);
            download.setPercentDownload(bytesRead / size);
            MainForm.repaintForm();
           // QueueFrame.repaintForm();
            publish(getProgress());
        }
        return null;
    }

    @Override
    protected void done() {
        super.done();
    }
}
