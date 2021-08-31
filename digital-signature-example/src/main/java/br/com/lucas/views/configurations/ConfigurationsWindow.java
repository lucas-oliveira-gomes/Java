package br.com.lucas.views.configurations;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import br.com.lucas.system.configurations.Configurations;

public class ConfigurationsWindow extends JDialog {
	private static final Logger logger = LogManager.getLogger(ConfigurationsWindow.class);

	private static final long serialVersionUID = 3271258300543291991L;
	private JTabbedPane tabbedPane;
	private JPanel pkcs11OptionsPanel;
	private JPanel pkcs12OptionsPanel;
	private JLabel lblNewLabel;
	private JTextField pkcs11ProvidersFolderTxtField;
	private JButton btnChangePkcs11Folder;
	private JTextField pkcs12ProviderFolderTxtField;
	private JButton btnChangePkcs12Folder;
	Configurations configs;

	/**
	 * Create the dialog.
	 */
	public ConfigurationsWindow() {
		initComponents();
	}

	public ConfigurationsWindow(Frame owner, boolean modal) {
		super(owner, modal);
		initComponents();
	}

	public ConfigurationsWindow(Frame owner, String title, boolean modal) {
		this(owner, modal);
		setTitle(title);
	}

	private void initComponents() {
		configs = new Configurations();
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("SAVE");
				okButton.addActionListener(e -> {
					saveConfigurations(e);
					setVisible(false);
					dispose();
				});
				buttonPane.add(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(e -> {
					setVisible(false);
					dispose();
				});
				buttonPane.add(cancelButton);
			}
		}
		{
			tabbedPane = new JTabbedPane(JTabbedPane.TOP);
			getContentPane().add(tabbedPane, BorderLayout.CENTER);
			{
				pkcs11OptionsPanel = new JPanel();
				tabbedPane.addTab("PKCS#11 Options", null, pkcs11OptionsPanel, null);
				{
					lblNewLabel = new JLabel("PKCS#11 Providers Folder");
				}

				pkcs11ProvidersFolderTxtField = new JTextField();
				pkcs11ProvidersFolderTxtField.setColumns(10);

				btnChangePkcs11Folder = new JButton("Change...");
				GroupLayout gl_pkcs11OptionsPanel = new GroupLayout(pkcs11OptionsPanel);
				gl_pkcs11OptionsPanel.setHorizontalGroup(gl_pkcs11OptionsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pkcs11OptionsPanel.createSequentialGroup().addGroup(gl_pkcs11OptionsPanel
								.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_pkcs11OptionsPanel.createParallelGroup(Alignment.TRAILING)
										.addComponent(btnChangePkcs11Folder).addComponent(pkcs11ProvidersFolderTxtField,
												GroupLayout.PREFERRED_SIZE, 410, GroupLayout.PREFERRED_SIZE)))
								.addContainerGap(19, Short.MAX_VALUE)));
				gl_pkcs11OptionsPanel.setVerticalGroup(gl_pkcs11OptionsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pkcs11OptionsPanel.createSequentialGroup().addComponent(lblNewLabel)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(pkcs11ProvidersFolderTxtField, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(btnChangePkcs11Folder)
								.addContainerGap(126, Short.MAX_VALUE)));
				pkcs11OptionsPanel.setLayout(gl_pkcs11OptionsPanel);
			}
			{
				pkcs12OptionsPanel = new JPanel();
				tabbedPane.addTab("PKCS#12 Options", null, pkcs12OptionsPanel, null);

				JLabel lblNewLabel_1 = new JLabel("PKCS#12 Folder");

				pkcs12ProviderFolderTxtField = new JTextField();
				pkcs12ProviderFolderTxtField.setColumns(10);

				btnChangePkcs12Folder = new JButton("Change...");
				GroupLayout gl_pkcs12OptionsPanel = new GroupLayout(pkcs12OptionsPanel);
				gl_pkcs12OptionsPanel.setHorizontalGroup(gl_pkcs12OptionsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pkcs12OptionsPanel.createSequentialGroup().addContainerGap()
								.addGroup(gl_pkcs12OptionsPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(pkcs12ProviderFolderTxtField, GroupLayout.DEFAULT_SIZE, 409,
												Short.MAX_VALUE)
										.addComponent(lblNewLabel_1)
										.addComponent(btnChangePkcs12Folder, Alignment.TRAILING))
								.addContainerGap()));
				gl_pkcs12OptionsPanel.setVerticalGroup(gl_pkcs12OptionsPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pkcs12OptionsPanel.createSequentialGroup().addContainerGap()
								.addComponent(lblNewLabel_1).addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(pkcs12ProviderFolderTxtField, GroupLayout.PREFERRED_SIZE,
										GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(18).addComponent(btnChangePkcs12Folder).addContainerGap(103, Short.MAX_VALUE)));
				pkcs12OptionsPanel.setLayout(gl_pkcs12OptionsPanel);
			}
		}
		loadConfigurations();
	}

	private void saveConfigurations(ActionEvent e) {
		boolean configFileExists = shouldCreateConfigFile();
		if (configFileExists) {
			try {
				configs.setConfiguration("pkcs11.provider.folder", pkcs11ProvidersFolderTxtField.getText());
				configs.setConfiguration("pkcs12.provider.folder", pkcs12ProviderFolderTxtField.getText());
				configs.save();
			} catch (IOException ieo) {
				logger.error("Unable to save configurations", ieo);
				JOptionPane.showMessageDialog(this, "Unable to save configurations!\nCheck logs for mor information.",
						"Unable to save configurations", JOptionPane.ERROR_MESSAGE);
			}
			logger.info("Configurations saved");
			JOptionPane.showMessageDialog(this, "Configurations saved!", "Configurations saved",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private boolean shouldCreateConfigFile() {
		boolean toReturn = false;
		if (configs.configFileExists()) {
			toReturn = true;
		} else {
			int selectedOption = JOptionPane.showConfirmDialog(this,
					"Configuration file missing!\nDo you want to create new one?", "Configuration file missing",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			switch (selectedOption) {
			case JOptionPane.YES_OPTION:
				try {
					return configs.createConfigFile();
				} catch (IOException ioe) {
					logger.error("Unable to create new file", ioe);
					JOptionPane.showMessageDialog(this, "Unable to create new file!\nCheck logs for mor information.",
							"Unable to save new file", JOptionPane.ERROR_MESSAGE);
					toReturn = false;
				}
				break;
			case JOptionPane.NO_OPTION:
				toReturn = false;
				break;
			}
		}
		return toReturn;
	}

	private void loadConfigurations() {
		pkcs11ProvidersFolderTxtField.setText(configs.getConfiguration("pkcs11.provider.folder", "./pkcs11"));
		pkcs12ProviderFolderTxtField.setText(configs.getConfiguration("pkcs12.provider.folder", "./pkcs12"));
	}
}
