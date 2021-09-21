package br.com.lucas.views.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.digitalsignature.PDSignature;

import br.com.lucas.bouncycastle.signatures.standards.SignatureStandards;
import br.com.lucas.bouncycastle.signers.impl.CAdESSigner;
import br.com.lucas.bouncycastle.signers.impl.PAdESSigner;
import br.com.lucas.keystore.loaders.impl.MSCAPIKeyStoreLoader;
import br.com.lucas.keystore.loaders.impl.PKCS12KeyStoreLoader;
import br.com.lucas.system.configurations.Configurations;
import br.com.lucas.views.components.CertificateTableModel;
import br.com.lucas.views.components.models.TableModelRow;
import br.com.lucas.views.configurations.ConfigurationsWindow;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 2241447537107638568L;

	private static final Logger logger = LogManager.getLogger(MainWindow.class);

	private JPanel panelMain;
	private JPanel panelLoadCertificate;
	private JScrollPane scrollPaneCertificateList;
	private JButton btnSign;
	private JButton btnLoadCertificates;
	private JTable tblCertificates;
	private JMenuBar menuBar;
	private JMenu FileMenu;
	private JMenuItem configurationMenuItem;
	private CertificateTableModel tableModel;
	private Configurations configs;
	private JLabel lblNewLabel_1;
	private JComboBox<SignatureStandards> signatureStandardComboBox;
	private JButton btnSelectFile;

	private File selectedFile;

	public MainWindow() {
		initialize();
	}

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			MainWindow mw = new MainWindow();
			SwingUtilities.invokeAndWait(() -> {
				mw.setVisible(true);
			});
		} catch (Exception e) {
			logger.error("Application error", e);
		}
	}

	private void initialize() {
		configs = new Configurations();
		setBounds(100, 100, 700, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panelMain = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(panelMain,
				GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addComponent(panelMain,
				Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 473, Short.MAX_VALUE));

		panelLoadCertificate = new JPanel();
		panelLoadCertificate.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Certificates",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		btnSign = new JButton("Sign");
		btnSign.addActionListener(e -> {
			startSignature(e);
		});

		signatureStandardComboBox = new JComboBox<>();
		signatureStandardComboBox.setModel(new DefaultComboBoxModel<SignatureStandards>(SignatureStandards.values()));

		lblNewLabel_1 = new JLabel("Signature Standard");

		JCheckBox chckbxNewCheckBox = new JCheckBox("ICP-Brasil");

		btnSelectFile = new JButton("Select file");
		btnSelectFile.addActionListener(e -> {
			selectFileToSign(e);
		});
		GroupLayout gl_panelMain = new GroupLayout(panelMain);
		gl_panelMain.setHorizontalGroup(gl_panelMain.createParallelGroup(Alignment.LEADING)
				.addComponent(panelLoadCertificate, GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
				.addGroup(gl_panelMain.createSequentialGroup().addContainerGap().addComponent(chckbxNewCheckBox)
						.addContainerGap(581, Short.MAX_VALUE))
				.addGroup(gl_panelMain.createSequentialGroup().addContainerGap().addComponent(lblNewLabel_1).addGap(18)
						.addComponent(signatureStandardComboBox, GroupLayout.PREFERRED_SIZE, 70,
								GroupLayout.PREFERRED_SIZE)
						.addGap(29).addComponent(btnSelectFile)
						.addPreferredGap(ComponentPlacement.RELATED, 312, Short.MAX_VALUE).addComponent(btnSign)
						.addContainerGap()));
		gl_panelMain.setVerticalGroup(gl_panelMain.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMain.createSequentialGroup()
						.addComponent(panelLoadCertificate, GroupLayout.PREFERRED_SIZE, 352, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelMain.createParallelGroup(Alignment.LEADING).addComponent(btnSign)
								.addGroup(gl_panelMain.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED).addGroup(gl_panelMain
												.createParallelGroup(Alignment.BASELINE).addComponent(lblNewLabel_1)
												.addComponent(signatureStandardComboBox, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addComponent(btnSelectFile))))
						.addGap(18).addComponent(chckbxNewCheckBox).addGap(18)));

		tblCertificates = new JTable();
		scrollPaneCertificateList = new JScrollPane(tblCertificates);

		btnLoadCertificates = new JButton("Load Certificates");
		btnLoadCertificates.addActionListener(e -> {
			loadCertificates(e);
		});
		GroupLayout gl_panelLoadCertificate = new GroupLayout(panelLoadCertificate);
		gl_panelLoadCertificate
				.setHorizontalGroup(gl_panelLoadCertificate.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelLoadCertificate.createSequentialGroup().addContainerGap()
								.addGroup(gl_panelLoadCertificate.createParallelGroup(Alignment.LEADING)
										.addComponent(scrollPaneCertificateList, GroupLayout.DEFAULT_SIZE, 654,
												Short.MAX_VALUE)
										.addComponent(btnLoadCertificates, Alignment.TRAILING))
								.addContainerGap()));
		gl_panelLoadCertificate.setVerticalGroup(gl_panelLoadCertificate.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelLoadCertificate.createSequentialGroup()
						.addComponent(scrollPaneCertificateList, GroupLayout.PREFERRED_SIZE, 266,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
						.addComponent(btnLoadCertificates).addContainerGap()));
		panelLoadCertificate.setLayout(gl_panelLoadCertificate);
		panelMain.setLayout(gl_panelMain);
		getContentPane().setLayout(groupLayout);

		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		FileMenu = new JMenu("File");
		menuBar.add(FileMenu);

		configurationMenuItem = new JMenuItem("Configuration");
		configurationMenuItem.addActionListener(e -> {
			ConfigurationsWindow configWindow = new ConfigurationsWindow(this, "Configurations", true);
			configWindow.setVisible(true);
		});
		FileMenu.add(configurationMenuItem);
		tableModel = new CertificateTableModel();
		tblCertificates.setModel(tableModel);
	}

	private void selectFileToSign(ActionEvent e) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		int option = fileChooser.showOpenDialog(this);
		switch (option) {
		case JFileChooser.APPROVE_OPTION:
			selectedFile = fileChooser.getSelectedFile();
			break;
		case JFileChooser.CANCEL_OPTION:
			selectedFile = null;
			break;
		}
	}

	private void startSignature(ActionEvent e) {
		int tableRowIndex = tblCertificates.getSelectedRow();
		if (tableRowIndex == -1) {
			JOptionPane.showMessageDialog(this, "No certificate selected!", "No certificate selected",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		} else {
			if (selectedFile != null) {
				TableModelRow selectedRow = tableModel.getRow(tableRowIndex);
				SignatureStandards signatureStandard = signatureStandardComboBox
						.getItemAt(signatureStandardComboBox.getSelectedIndex());
				switch (signatureStandard) {
				case CAdES:
					startCadesSignature(selectedRow);
					break;
				case PAdES:
					startPadesSignature(selectedRow);
					break;
				default:
					break;
				}
			} else {
				JOptionPane.showMessageDialog(this, "No file selected to sign", "No file selected",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}

		}

	}

	private void startPadesSignature(TableModelRow selectedRow) {
		if (!selectedFile.getName().toLowerCase().endsWith(".pdf")) {
			JOptionPane.showMessageDialog(this, "Only PDF files are supported", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		File destinationFile = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setSelectedFile(new File("signedDocument.pdf"));
		int option = fileChooser.showSaveDialog(this);
		switch (option) {
		case JFileChooser.APPROVE_OPTION:
			destinationFile = fileChooser.getSelectedFile();
			break;
		case JFileChooser.CANCEL_OPTION:
			return;
		}
		try {
			PDDocument pdDocument = PDDocument.load(selectedFile);
			PDSignature signature = new PDSignature();
			signature.setFilter(PDSignature.FILTER_ADOBE_PPKLITE);
			signature.setSubFilter(PDSignature.SUBFILTER_ADBE_PKCS7_DETACHED);
			signature.setName("Charlinho");
			signature.setLocation("Sao Paulo - Brazil");
			signature.setReason("Because i can");
			signature.setSignDate(Calendar.getInstance());
			pdDocument.addSignature(signature, new PAdESSigner(selectedRow));
			try (FileOutputStream dest = new FileOutputStream(destinationFile)) {
				pdDocument.saveIncremental(dest);
			}
			JOptionPane.showMessageDialog(this, "Document Signed", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error during the signature process!\nCheck logs for details", "Error",
					JOptionPane.ERROR_MESSAGE);
			logger.error("Error during the signin process.", e);
		}

	}

	private void startCadesSignature(TableModelRow selectedRow) {
		File destinationFile = null;
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setSelectedFile(new File("signature.p7b"));
		int option = fileChooser.showSaveDialog(this);
		switch (option) {
		case JFileChooser.APPROVE_OPTION:
			destinationFile = fileChooser.getSelectedFile();
			break;
		case JFileChooser.CANCEL_OPTION:
			return;
		}
		try {
			MessageDigest md = MessageDigest.getInstance("Sha-256");
			byte[] originalContentBytes = FileUtils.readFileToByteArray(selectedFile);
			byte[] hash = md.digest(originalContentBytes);
			CAdESSigner signer = new CAdESSigner(selectedRow);
			byte[] signedContent = signer.sign(hash);
			FileUtils.writeByteArrayToFile(destinationFile, signedContent);
			JOptionPane.showMessageDialog(this, "Document Signed", "Success", JOptionPane.INFORMATION_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error during the signature process!\nCheck logs for details", "Error",
					JOptionPane.ERROR_MESSAGE);
			logger.error("Error during the signin process.", e);
		}

	}

	private void loadCertificates(ActionEvent e) {
		loadPkcs11Certs(e);
		loadMsCapiCerts(e);
		loadPkcs12Certs(e);
	}

	private void loadPkcs12Certs(ActionEvent e) {
		List<TableModelRow> certs = findPkcs12Certificates(configs.getConfiguration("pkcs12.provider.folder", null));
		tableModel.addAll(certs);
	}

	private List<TableModelRow> findPkcs12Certificates(String folder) {
		List<TableModelRow> toReturn = new ArrayList<TableModelRow>();
		try {
			Files.list(Paths.get(folder)).filter(Files::isRegularFile)
					.filter(path -> (path.toString().endsWith(".p12") || path.toString().endsWith(".pfx")))
					.map(Path::toFile).collect(Collectors.toList()).forEach(file -> {
						List<TableModelRow> rows = openAndProcessPkcs12(file);
						toReturn.addAll(rows);
					});
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Failed to read contents!\nCheck logs for more details",
					"Failed to read contents", JOptionPane.ERROR_MESSAGE);
			logger.error("Failed to read contents", e);
		}
		return toReturn;
	}

	private List<TableModelRow> openAndProcessPkcs12(File file) {
		List<TableModelRow> toReturn = new ArrayList<TableModelRow>();
		String fileName = file.getName();
		JPanel panel = new JPanel();
		JPasswordField passwdField = new JPasswordField();
		panel.setLayout(new BorderLayout());
		panel.add(passwdField, BorderLayout.SOUTH);
		panel.setPreferredSize(new Dimension(250, 20));
		JOptionPane.showMessageDialog(this, panel, "Enter password for: " + fileName, JOptionPane.INFORMATION_MESSAGE);
		char[] passwd = passwdField.getPassword();
		PKCS12KeyStoreLoader pkcs12Loader = new PKCS12KeyStoreLoader(file);

		pkcs12Loader.setPasswd(passwd);
		try {
			pkcs12Loader.load();
			List<String> aliases = pkcs12Loader.listKeyAliases();
			for (String alias : aliases) {
				X509Certificate cert = pkcs12Loader.getCertificate(alias);
				TableModelRow row = new TableModelRow();
				row.setAlias(alias);
				row.setOwner(cert.getSubjectX500Principal().getName());
				row.setKeyStoreSource(pkcs12Loader);
				toReturn.add(row);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Failed to read contents!\nCheck logs for more details",
					"Failed to read contents", JOptionPane.ERROR_MESSAGE);
			logger.error("Failed to read contents", e);
		}
		return toReturn;
	}

	private void loadMsCapiCerts(ActionEvent e) {
		List<TableModelRow> toReturn = new ArrayList<TableModelRow>();
		MSCAPIKeyStoreLoader keyStoreLoader = new MSCAPIKeyStoreLoader();
		try {
			keyStoreLoader.load();
			List<String> aliases = keyStoreLoader.listKeyAliases();
			for (String alias : aliases) {
				X509Certificate cert = keyStoreLoader.getCertificate(alias);
				TableModelRow row = new TableModelRow();
				row.setAlias(alias);
				row.setOwner(cert.getSubjectX500Principal().getName());
				row.setKeyStoreSource(keyStoreLoader);
				toReturn.add(row);
			}			
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Failed to read contents!\nCheck logs for more details",
					"Failed to read contents", JOptionPane.ERROR_MESSAGE);
			logger.error("Failed to read contents", ex);
		}
		tableModel.addAll(toReturn);
	}

	private void loadPkcs11Certs(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
