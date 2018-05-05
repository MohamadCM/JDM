import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

/**
 * This class creates setting form
 * @author Mohamad Chaman-Motlagh
 * @version 1
 */
public class SettingForm {
    private JFrame mainFrame;
    private JFileChooser fileChooser;
    private JSpinner numberofDownsSpinner;
    private JComboBox<String> lookAndFeelInfoBox;
    private JButton cancelButton;
    private JButton okButton;
    private JButton chooseAdress;
    private String saveAdress;
    private int numberOfSimDowns;
    public SettingForm()
    {
        mainFrame = new JFrame("Setting");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setLocation(600,300);
        mainFrame.setSize(700,500);
        numberOfSimDowns = 1;
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        mainFrame.setContentPane(panel);
        mainFrame.setLayout(new GridLayout(4,2,10,10));
        JLabel label1 = new JLabel("Number of simultaneous downloads:");
        label1.setFocusable(false);
        JLabel label2 = new JLabel("Default download location:");
        label2.setFocusable(false);
        JLabel label3 = new JLabel("Select programme's look and feel:");
        label3.setFocusable(false);
        numberofDownsSpinner = new JSpinner(new SpinnerNumberModel());
        //numberofDownsSpinner.setFocusable(false);
        numberofDownsSpinner.addKeyListener(new MyKeyboardListener());
        numberofDownsSpinner.setValue((Integer)1);
        numberofDownsSpinner.addKeyListener(new MyKeyboardListener());
        lookAndFeelInfoBox = new JComboBox<String>();
        for(UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels())
            lookAndFeelInfoBox.addItem(info.getClassName());
        fileChooser = new JFileChooser();
        saveAdress = ".";
        fileChooser.setCurrentDirectory(new File(".."));
        fileChooser.setDialogTitle("Choose place to save your files");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(false);
        chooseAdress = new JButton("Choose address");
        //chooseAdress.setFocusable(false);
        chooseAdress.addKeyListener(new MyKeyboardListener());
        chooseAdress.addMouseListener(new MyMouseListener());
        fileChooser.setFocusable(false);
        mainFrame.add(label1);
        mainFrame.add(numberofDownsSpinner);
        mainFrame.add(label2);
        mainFrame.add(chooseAdress);
        mainFrame.add(label3);
        mainFrame.add(lookAndFeelInfoBox);
        cancelButton = new JButton("Cancel");
        cancelButton.addMouseListener(new MyMouseListener());
        cancelButton.setFocusable(false);
        okButton = new JButton("OK");
        okButton.addMouseListener(new MyMouseListener());
        okButton.addKeyListener(new MyKeyboardListener());
        //okButton.setFocusable(false);
        okButton.requestFocus();
        mainFrame.add(cancelButton);
        mainFrame.add(okButton);
        //mainFrame.requestFocus();
    }
    /**
     * Shows setting form
     */
    public void showSetting()
    {
        mainFrame.setVisible(true);
    }
    private void hidesetting()
    {
        mainFrame.setVisible(false);
    }
    private class MyMouseListener extends MouseAdapter
    {
        @Override
        public void mousePressed(MouseEvent mouseEvent) {
            if(mouseEvent.getSource().equals(cancelButton))
                hidesetting();
            else if(mouseEvent.getSource().equals(okButton))
            {
                numberOfSimDowns = (Integer)numberofDownsSpinner.getValue();
                try {
                    UIManager.setLookAndFeel(lookAndFeelInfoBox.getSelectedItem().toString());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
            }
            if (mouseEvent.getSource().equals(chooseAdress))
                fileChooser.showDialog(null,"Confirm this path");
            saveAdress = fileChooser.getCurrentDirectory().toString();
            mainFrame.dispose();
            hidesetting();

        }
    }
    private class MyKeyboardListener extends KeyAdapter
    {
        @Override
        public void keyTyped(KeyEvent keyEvent)
        {
            if(keyEvent.getKeyChar() == KeyEvent.VK_ENTER){
                numberOfSimDowns = (Integer)numberofDownsSpinner.getValue();
                try {
                    UIManager.setLookAndFeel(lookAndFeelInfoBox.getSelectedItem().toString());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();
                }
                saveAdress = fileChooser.getCurrentDirectory().toString();
                mainFrame.dispose();
                hidesetting();
            }

        }
    }
    /**
     * @return location of saiving a file
     */
    public String getSaveAdress()
    {
        return saveAdress;
    }
}
