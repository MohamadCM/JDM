import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * This class creates setting form
 *
 * @author Mohamad Chaman-Motlagh
 * @version 1
 */
public class SettingForm {
    private static int numberOfSimDowns;
    private JFrame mainFrame;
    private JFileChooser fileChooser;
    private JSpinner numberofDownsSpinner;
    private JComboBox<String> lookAndFeelInfoBox;
    private JButton cancelButton;
    private JButton okButton;
    private JButton chooseAdress;
    private String saveAdress;
    private Defaults defaults;

    private JTextArea blockedLinks;

    public SettingForm() {

        mainFrame = new JFrame("Setting");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLocation(600, 300);
        mainFrame.setSize(500, 400);


        if (FileUtils.readDefaults() != null)
            defaults = FileUtils.readDefaults();


        numberOfSimDowns = 1;
        if (defaults != null)
            numberOfSimDowns = defaults.getNumberOfSimDownloads();


        JPanel upPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        upPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel borderPanel = new JPanel();
        borderPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainFrame.setContentPane(borderPanel);

        mainFrame.setLayout(new BorderLayout());

        JLabel label1 = new JLabel("Number of simultaneous downloads:");
        label1.setFocusable(false);
        JLabel label2 = new JLabel("Default download location:");
        label2.setFocusable(false);
        JLabel label3 = new JLabel("Select programme's look and feel:");
        label3.setFocusable(false);

        numberofDownsSpinner = new JSpinner(new SpinnerNumberModel());
        //numberofDownsSpinner.setFocusable(false);

        numberofDownsSpinner.addKeyListener(new MyKeyboardListener());
        numberofDownsSpinner.setValue((Integer) numberOfSimDowns);
        numberofDownsSpinner.addKeyListener(new MyKeyboardListener());
        lookAndFeelInfoBox = new JComboBox<String>();
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
                lookAndFeelInfoBox.addItem(info.getClassName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        fileChooser = new JFileChooser();

        saveAdress = "./" + System.getProperty("user.dir");
        if (defaults != null)
            saveAdress = defaults.getLocation();

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setDialogTitle("Choose place to save your files");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        chooseAdress = new JButton("Choose address");
        //chooseAdress.setFocusable(false);
        //chooseAdress.addKeyListener(new MyKeyboardListener());
        chooseAdress.addMouseListener(new MyMouseListener());
        fileChooser.setFocusable(false);

        upPanel.add(label1);
        upPanel.add(numberofDownsSpinner);
        upPanel.add(label2);
        upPanel.add(chooseAdress);
        upPanel.add(label3);
        upPanel.add(lookAndFeelInfoBox);

        mainFrame.add(upPanel, BorderLayout.NORTH);

        cancelButton = new JButton("Cancel");
        cancelButton.addMouseListener(new MyMouseListener());
        cancelButton.setFocusable(false);

        okButton = new JButton("OK");
        okButton.addMouseListener(new MyMouseListener());
        okButton.addKeyListener(new MyKeyboardListener());
        //okButton.setFocusable(false);

        okButton.requestFocus();

        JPanel okAndCancelPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        okAndCancelPanel.add(cancelButton);
        okAndCancelPanel.add(okButton);
        mainFrame.add(okAndCancelPanel, BorderLayout.SOUTH);
        try {

            lookAndFeelInfoBox.setSelectedItem(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        if (defaults != null) {
            lookAndFeelInfoBox.setSelectedItem(defaults.getLookAndFeelInfo());
            try {
                UIManager.setLookAndFeel(defaults.getLookAndFeelInfo());
                SwingUtilities.updateComponentTreeUI(MainForm.getDownloadManager());
                SwingUtilities.updateComponentTreeUI(mainFrame);
                SwingUtilities.updateComponentTreeUI(QueueFrame.getMainFrame());
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (InstantiationException e) {
                System.out.println(e.getMessage());
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
            } catch (UnsupportedLookAndFeelException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        blockedLinks = new JTextArea();
        JScrollPane blockedListScrollPane = new JScrollPane(blockedLinks);
        blockedListScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        blockedListScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel blockListPanel = new JPanel(new BorderLayout());
        blockListPanel.add(new JLabel("Blocked links:"), BorderLayout.NORTH);
        blockListPanel.add(blockedListScrollPane, BorderLayout.CENTER);

        mainFrame.add(blockListPanel, BorderLayout.CENTER);

        if (FileUtils.readBlockedLinks() != null)
            for (String string : FileUtils.readBlockedLinks())
                blockedLinks.setText(blockedLinks.getText() + string + "\n");
        //mainFrame.requestFocus();
    }

    /**
     * @return number of set simultaneous downloads
     */
    public static int getSimultaneousDownloads() {
        return numberOfSimDowns;
    }

    /**
     * Shows setting form
     */
    public void showSetting() {
        mainFrame.setVisible(true);
    }

    private void hidesetting() {
        mainFrame.setVisible(false);
    }

    /**
     * @return location of saving a file
     */
    public String getSaveAdress() {
        return saveAdress;
    }

    private class MyMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            if (mouseEvent.getSource().equals(cancelButton)) {
                mainFrame.dispose();
                hidesetting();
            } else if (mouseEvent.getSource().equals(okButton)) {
                numberOfSimDowns = (Integer) numberofDownsSpinner.getValue();
                try {
                    UIManager.setLookAndFeel(lookAndFeelInfoBox.getSelectedItem().toString());
                    SwingUtilities.updateComponentTreeUI(MainForm.getDownloadManager());
                    SwingUtilities.updateComponentTreeUI(mainFrame);

                    SwingUtilities.updateComponentTreeUI(QueueFrame.getMainFrame());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                if (fileChooser != null && fileChooser.getSelectedFile() != null)
                    saveAdress = fileChooser.getSelectedFile().toString();

                mainFrame.dispose();
                MainForm.repaintForm();

                defaults = new Defaults(saveAdress, (Integer) numberofDownsSpinner.getValue(), lookAndFeelInfoBox.getSelectedItem().toString());

                FileUtils.writeDefaults(defaults);
                FileUtils.writeBlockedLinks(blockedLinks.getText());
            }

            if (mouseEvent.getSource().equals(chooseAdress) && chooseAdress != null)
                fileChooser.showDialog(null, "Confirm this path");
            if (fileChooser.getSelectedFile() != null)
                saveAdress = fileChooser.getSelectedFile().toString();
        }
    }

    private class MyKeyboardListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent keyEvent) {
            if (keyEvent.getKeyChar() == KeyEvent.VK_ENTER) {
                numberOfSimDowns = (Integer) numberofDownsSpinner.getValue();
                try {
                    UIManager.setLookAndFeel(lookAndFeelInfoBox.getSelectedItem().toString());
                    SwingUtilities.updateComponentTreeUI(MainForm.getDownloadManager());
                    SwingUtilities.updateComponentTreeUI(mainFrame);

                    SwingUtilities.updateComponentTreeUI(QueueFrame.getMainFrame());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                if (fileChooser.getSelectedFile() != null)
                    saveAdress = fileChooser.getSelectedFile().toString();

                mainFrame.dispose();
                MainForm.repaintForm();
                hidesetting();

                defaults = new Defaults(saveAdress, (Integer) numberofDownsSpinner.getValue(), lookAndFeelInfoBox.getSelectedItem().toString());
                FileUtils.writeDefaults(defaults);
                FileUtils.writeBlockedLinks(blockedLinks.getText());
            }

        }
    }


}