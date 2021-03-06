/**
 * AddSongUI.java
 */
package ra00528.music.catalogue.com1028;

import java.awt.Component;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import ra00528.music.catalogue.com1028.dao.DAOFactory;
import ra00528.music.catalogue.com1028.dao.SongDAO;

import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.awt.event.ActionEvent;

/**
 * Defines the properties and behaviour of the Add Song UI.
 * 
 * @author Rosabelle Armstead
 *
 */
public class AddSongUI {
	/** The UI components */
	private JFrame frame;
	private JTextField txtBoxTitle;
	private JTextField txtBoxAlbum;
	private JTextField txtBoxArtist;
	/** MP3 file of a song if stored */
	private File file = null;
	/** the song DAO */
	private SongDAO songDAO = null;

	/**
	 * Launch application
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddSongUI window = new AddSongUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Connect to database and initialise contents of frame
	 */
	public AddSongUI() {
		try {
			songDAO = DAOFactory.getSongDAO();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (songDAO != null) {
				songDAO.closeConnection();
			}
		}
		initialize();
	}

	/**
	 * Initialise contents of frame
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(1, 1, 1600, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblMusicCollectionCatalogue = new JLabel("Music Collection Catalogue");
		lblMusicCollectionCatalogue.setBounds(0, 30, 1578, 54);
		lblMusicCollectionCatalogue.setFont(new Font("Tahoma", Font.BOLD, 40));
		lblMusicCollectionCatalogue.setHorizontalAlignment(SwingConstants.CENTER);
		lblMusicCollectionCatalogue.setVerticalAlignment(SwingConstants.TOP);
		frame.getContentPane().add(lblMusicCollectionCatalogue);

		JLabel lblSongTitle = new JLabel("Title:");
		lblSongTitle.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblSongTitle.setBounds(478, 176, 114, 34);
		frame.getContentPane().add(lblSongTitle);

		JLabel lblAlbum = new JLabel("Album:");
		lblAlbum.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblAlbum.setBounds(478, 226, 114, 34);
		frame.getContentPane().add(lblAlbum);

		JLabel lblArtist = new JLabel("Artist:");
		lblArtist.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblArtist.setBounds(478, 276, 114, 34);
		frame.getContentPane().add(lblArtist);

		JLabel lblGenre = new JLabel("Genre:");
		lblGenre.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblGenre.setBounds(478, 326, 114, 34);
		frame.getContentPane().add(lblGenre);

		JLabel lblFormat = new JLabel("Format:");
		lblFormat.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblFormat.setBounds(478, 376, 114, 34);
		frame.getContentPane().add(lblFormat);

		txtBoxTitle = new JTextField();
		txtBoxTitle.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtBoxTitle.setBounds(578, 177, 534, 33);
		frame.getContentPane().add(txtBoxTitle);
		txtBoxTitle.setColumns(10);

		txtBoxAlbum = new JTextField();
		txtBoxAlbum.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtBoxAlbum.setColumns(10);
		txtBoxAlbum.setBounds(578, 227, 534, 33);
		frame.getContentPane().add(txtBoxAlbum);

		txtBoxArtist = new JTextField();
		txtBoxArtist.setFont(new Font("Tahoma", Font.PLAIN, 22));
		txtBoxArtist.setColumns(10);
		txtBoxArtist.setBounds(578, 277, 534, 33);
		frame.getContentPane().add(txtBoxArtist);

		JLabel lblAddSong = new JLabel("Add Song");
		lblAddSong.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddSong.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblAddSong.setBounds(673, 100, 249, 41);
		frame.getContentPane().add(lblAddSong);

		JComboBox comboBoxGenre = new JComboBox();
		comboBoxGenre.setModel(new DefaultComboBoxModel(new String[] { "Alternative", "Blues", "Country", "Electronic",
				"Folk", "Hip Hop", "Indie", "Jazz", "Latin", "Pop", "R&B", "Rock", "Other" }));
		comboBoxGenre.setBounds(578, 329, 534, 34);
		frame.getContentPane().add(comboBoxGenre);

		JComboBox comboBoxFormat = new JComboBox();
		comboBoxFormat.setModel(new DefaultComboBoxModel(new String[] { "Cassette", "CD", "Digital", "Vinyl" }));
		comboBoxFormat.setBounds(578, 379, 534, 34);
		frame.getContentPane().add(comboBoxFormat);

		JLabel lblFilepath = new JLabel("MP3:");
		lblFilepath.setFont(new Font("Tahoma", Font.PLAIN, 22));
		lblFilepath.setBounds(478, 426, 114, 34);
		frame.getContentPane().add(lblFilepath);

		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			/**
			 * Connect to database and run add song method using text in input
			 * fields and combo boxes as parameters
			 */
			public void actionPerformed(ActionEvent e) {
				songDAO.openConnection();

				String title = null;
				String album = null;
				String artist = null;
				String genre = null;
				String format = null;

				title = txtBoxTitle.getText();
				album = txtBoxAlbum.getText();
				artist = txtBoxArtist.getText();
				genre = (String) comboBoxGenre.getSelectedItem();
				format = (String) comboBoxFormat.getSelectedItem();

				Song newSong = null;
				// if the user has chosen to add .mp3 file to song, get the
				// filepath, otherwise set filepath as null
				if (file != null) {
					newSong = new Song(title, album, artist, genre, format, file.getAbsolutePath());
				} else {
					newSong = new Song(title, album, artist, genre, format, null);
				}
				// if the song added by the user doesn't already exist, add the
				// song to the database, otherwise tell user it already exists
				// and do not add to database
				if (songDAO.isExisting(title, album, artist, genre, format).isEmpty()) {
					songDAO.addSong(newSong);
					List<Song> songs = songDAO.getSongs();
					songDAO.closeConnection();
					// refresh table in Music Collection Catalogue UI with
					// updated list of songs
					MusicCollectionCatalogueUI.refreshTable(songs);
					frame.dispose();
				} else {
					Component frame = null;
					JOptionPane.showMessageDialog(frame, "Song already in database! Try again.");
				}
			}
		});
		btnConfirm.setBounds(919, 494, 193, 54);
		frame.getContentPane().add(btnConfirm);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			/**
			 * if cancel button clicked, close the Add Song UI window
			 */
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		btnCancel.setBounds(478, 494, 193, 54);
		frame.getContentPane().add(btnCancel);

		JButton btnChooseFile = new JButton("Choose file...");
		btnChooseFile.addActionListener(new ActionListener() {
			/**
			 * if user wants to add .mp3 file to song, file browser opens so
			 * they can choose the location of the .mp3 file and set the
			 * filepath
			 */
			public void actionPerformed(ActionEvent e) {

				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog(null);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					file = fileChooser.getSelectedFile();
					System.out.println(file.getName());
				}
			}
		});
		btnChooseFile.setBounds(578, 430, 193, 33);
		frame.getContentPane().add(btnChooseFile);
	}

	/**
	 * gets frame so that Add Song UI window can be opened from Music Collection
	 * Catalogue UI
	 * 
	 * @return frame
	 */
	public JFrame getFrame() {
		return frame;
	}
}
