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
        QueueFrame.repaintForm();
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
        int size = contentLenth;
        long startTime = System.nanoTime();
        download.setVolume(size);
        while((bytesRead = bufferedInputStream.read(buffer) ) > 0) {
            if(Thread.interrupted() || bytesRead <= 0)
                break;
            outputStream.write(buffer, 0 , bytesRead);
            if(bytesRead != 0)
                totalRead += bytesRead;
            download.setDownloadedVolume(totalRead);
            download.setPercentDownload((totalRead * 100 / size));
            download.getProgressBar().setValue((int) Math.abs(download.getPercentDownload()));
            download.setDownloadRate((totalRead) / ((System.nanoTime() - startTime) / 1000000));
            download.setDownloadedVolume(totalRead / 1000);
            download.getDownloadInfoForm().updateForm((totalRead * 100 / size) , size , totalRead/1000 , Math.toIntExact((totalRead) / ((System.nanoTime() - startTime) / 1000000)));
            download.getDownloadInfo().update(size,totalRead,(totalRead * 100 / size),(totalRead) / ((System.nanoTime() - startTime) / 1000000), size == totalRead);
            MainForm.repaintForm();
            QueueFrame.repaintForm();
            publish(getProgress());
        }
        download.getDownloadInfo().update(size,totalRead,(totalRead * 100 / size),(totalRead) / ((System.nanoTime() - startTime) / 1000000), true);
        return null;
    }

    @Override
    protected void done() {
        super.done();
    }
}
