/**
 * This class is extended version of JFrame
 * That have icon on launch and show the app
 * on system tray by closing it (if supported by os)
 * @author Mohamad Chaman-Motlagh
 * @version 1
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyJFrame extends JFrame{
        TrayIcon trayIcon;
        SystemTray tray;
        MyJFrame(String title){
            super(title);
            if(SystemTray.isSupported()){
                tray=SystemTray.getSystemTray();
                Image image=Toolkit.getDefaultToolkit().getImage("Images/ICON.png");
                ActionListener exitListener=new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        System.exit(0);
                    }
                };
                PopupMenu popup=new PopupMenu();
                MenuItem defaultItem=new MenuItem("Exit");
                defaultItem.addActionListener(exitListener);
                popup.add(defaultItem);
                defaultItem=new MenuItem("Open");
                defaultItem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        setVisible(true);
                        setExtendedState(JFrame.NORMAL);
                    }
                });
                popup.add(defaultItem);
                trayIcon=new TrayIcon(image, "JDM", popup);
                trayIcon.setImageAutoSize(true);
            }else{
                System.out.println("system tray not supported");
            }
            addWindowListener(new WindowListener() {
                @Override
                public void windowOpened(WindowEvent e) {

                }

                public void windowClosing(WindowEvent e){
                    try {
                        tray.add(trayIcon);
                        setVisible(false);
                    } catch (AWTException ex) {
                        System.out.println(ex.getMessage());
                    } catch (NullPointerException ex){
                        System.out.println(ex.getMessage());
                    }
                }
                @Override
                public void windowClosed(WindowEvent e) {
                    try {
                        tray.add(trayIcon);
                        setVisible(false);
                    } catch (AWTException ex) {
                        System.out.println(ex.getMessage());
                    }
                }

                @Override
                public void windowIconified(WindowEvent e) { }

                @Override
                public void windowDeiconified(WindowEvent e) { }

                @Override
                public void windowActivated(WindowEvent e) { }

                @Override
                public void windowDeactivated(WindowEvent e) { }
            });
            setIconImage(Toolkit.getDefaultToolkit().getImage("Images/ICON.png"));

            setVisible(true);
            setSize(300, 200);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
}
