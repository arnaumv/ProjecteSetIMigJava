package sieteymedio;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class Menu {

    
    
	public static void main(String[] args) {

	}
	
	private static DefaultTableModel tableModel;
	private static int maxRounds;
	private static int gamePlayers;
	private static String cardDeck;
	
	JMenuItem startGameItem;
	JMenuItem addPlayerItem;
	JMenuItem showPlayersItem;
	JMenuItem removePlayerItem;
	JMenuItem setGamePlayersItem;
	JMenuItem setCardsDeckItem;
	JMenuItem setMaxRoundsItem;
	JMenuItem exitMenuItem;
	JFrame frame;
	
	public void initializeMenu() {
        // Crear la barra de menú
        JMenuBar menuBar = new JMenuBar();

        // Crear el primer menú
        JMenu playersMenu = new JMenu("Add/Remove/Show Players");
        addPlayerItem = new JMenuItem("Add Player");
        removePlayerItem = new JMenuItem("Remove Player");
        showPlayersItem = new JMenuItem("Show Players");
        playersMenu.add(addPlayerItem);
        playersMenu.add(removePlayerItem);
        playersMenu.add(showPlayersItem);
        menuBar.add(playersMenu);

        // Crear el segundo menú
        JMenu settingsMenu = new JMenu("Settings");
        setGamePlayersItem = new JMenuItem("Set Game Players");
        setCardsDeckItem = new JMenuItem("Set Card's Deck");
        setMaxRoundsItem = new JMenuItem("Set Max Rounds (Default 5 Rounds)");
        settingsMenu.add(setGamePlayersItem);
        settingsMenu.add(setCardsDeckItem);
        settingsMenu.add(setMaxRoundsItem);
        menuBar.add(settingsMenu);
        
        // Crear el tercer menú
        JMenu playGameMenu = new JMenu("Play Game");
        startGameItem = new JMenuItem("Start Game");

        playGameMenu.add(startGameItem);
        menuBar.add(playGameMenu);
        
        // Crear el cuarto menú
        JMenu rankingMenu = new JMenu("Ranking");
        JMenuItem earningsItem = new JMenuItem("Players With More Earnings");
        JMenuItem gamesPlayedItem = new JMenuItem("Players With More Games Played");
        JMenuItem minutesPlayedItem = new JMenuItem("Players With More Minutes Played");
        rankingMenu.add(earningsItem);
        rankingMenu.add(gamesPlayedItem);
        rankingMenu.add(minutesPlayedItem);
        menuBar.add(rankingMenu);

        // Crear el quinto menú
        JMenu reportsMenu = new JMenu("Reports");
        JMenuItem report1Item = new JMenuItem("Report 1");
        JMenuItem report2Item = new JMenuItem("Report 2");
        JMenuItem report3Item = new JMenuItem("Report 3");
        reportsMenu.add(report1Item);
        reportsMenu.add(report2Item);
        reportsMenu.add(report3Item);
        menuBar.add(reportsMenu);

        // Crear el sexto menú
        exitMenuItem = new JMenuItem("Exit");
        menuBar.add(exitMenuItem);

        // Crear el JFrame
        frame = new JFrame("Game Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setJMenuBar(menuBar);
        frame.setSize(500, 500);

        // Crear el panel de contenido
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Establecer la imagen de fondo
        try {
            BufferedImage backgroundImage = ImageIO.read(new File("imagenes/fond0.png"));
            JLabel backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
            contentPanel.add(backgroundLabel, BorderLayout.CENTER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Añadir el panel de contenido al JFrame
        frame.setContentPane(contentPanel);

        frame.setVisible(true);

        // ...
	}
	
	
	public JMenuItem getStartGameItem() {
		 return startGameItem;
	}
	
	public JMenuItem getAddPlayerItem() {
		 return addPlayerItem;
	}
	
	public JMenuItem getShowPlayersItem() {
		 return showPlayersItem;
	}
	
	public JMenuItem getRemovePlayerItem() {
		 return removePlayerItem;
	}
	
	public JMenuItem getSetGamePlayersItem() {
		 return setGamePlayersItem;
	}
	
	public JMenuItem getSetCardsDeckItemm() {
		 return setCardsDeckItem;
	}
	
	public JMenuItem getSetMaxRoundsItem() {
		 return setMaxRoundsItem;
	}
	
	public JMenuItem getExitMenuItem() {
		 return exitMenuItem;
	}
	
	
	public JFrame getFrame() {
		 return frame;
	}


	
}
