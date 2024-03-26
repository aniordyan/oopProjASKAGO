import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.security.spec.ECField;

public class MusicPlayerGUI extends JFrame {
    //color
    public static final Color FRAME_COLOR  = Color.GRAY;
    public static final Color TEXT_COLOR = Color.BLACK;
    public MusicPlayerGUI(){
        //calls JFrame constructor to configure out gui and set the title header to "Music Player".
        super("Music Player");
        //set the width and height
        setSize(400,700);
        //end process when app is closed
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //launch the app at the center of the screen
        setLocationRelativeTo(null);
        //prevent the app fom being resized
        setResizable(false);
        // set layout to null which allows us to control the (x,y) coordinates of our components
        // and also ste the height and width
        setLayout(null);
        //change the color of the frame
        getContentPane().setBackground(FRAME_COLOR);

        addGuiComponents();
    }
    private void addGuiComponents(){
        addToolBar();
        //load record image
        JLabel songImage = new JLabel(loadImage("src/assets/record.png"));
        songImage.setBounds(0,50,getWidth() - 20, 225);
        add(songImage);

        //song title
        JLabel songTitle = new JLabel("Song Title");
        songTitle.setBounds(0,285,getWidth() - 10,30);
        songTitle.setFont(new Font("Dialog", Font.BOLD, 24));
        songTitle.setForeground(TEXT_COLOR);
        songTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(songTitle);
    }
    private void addToolBar(){
        JToolBar toolBar = new JToolBar();
        toolBar.setBounds(0,0, getWidth(),20);

        //Prevent toolbar from being moved
        toolBar.setFloatable(false);

        //add drop down menu
        JMenuBar menuBar = new JMenuBar();
        toolBar.add(menuBar);

        //song manu showing loading song options
        JMenu songMenu = new JMenu("Song");
        menuBar.add(songMenu);

        //add "load song" item int the songMenu
        JMenuItem loadSong = new JMenuItem("Load Song");
        songMenu.add(loadSong);

        //playlist menu
        JMenu playlistMenu = new JMenu("Playlist Menu");
        menuBar.add(playlistMenu);

        //add items to the playlist menu
        JMenuItem makeAPlaylist = new JMenuItem("Make A Playlist");
        playlistMenu.add(makeAPlaylist);


        JMenuItem loadPlaylist = new JMenuItem("Load Playlist");
        playlistMenu.add(loadPlaylist);
        add(toolBar);
    }
    private ImageIcon loadImage(String imagePath){
        try{
            //read the image file from the path of the image
            BufferedImage image = ImageIO.read(new File(imagePath));
            //returns an image icon so that our components can render the image
            return new ImageIcon(image);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        //if could not find resource
        return null;

    }
}
