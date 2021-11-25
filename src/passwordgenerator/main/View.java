package passwordgenerator.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import passwordgenerator.components.Button;
import passwordgenerator.theme.Colors;
import passwordgenerator.theme.Fonts;

public class View extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel panel;
	private JPanel topPanel;
	private JPasswordField passwordField;
	private Button viewButton;
	private JLabel hashLabel;
	private JPanel bottomPanel;
	private Button hashButton;
	private Button copyButton;
	private Button clearButton;
	
	private final int WIDTH = 300;
	private final int HEIGHT = 150;
	private final int SCALE = 1;
	private final int ICON_SIZE = 16;
	private final int DEFAULT_COMPONENT_HEIGHT = 30;
	private final int DEFAULT_PASSWORD_WIDTH = 235;
	
	private final String EMPTY_STRING = "";
	
	private Controller _controller;
	private boolean _passwordVisible = false;
	
	public View() {
		System.out.println("[View] Creating");
		
		_controller = new Controller();
	}
	
	public void Initialize() {
		System.out.println("[View] Initializing");
		
		this.setTitle("PasswordGenerator");
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = createMainPanel();
		
		this.getContentPane().add(panel);
		
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	// ==== Main panel ====
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.setBounds(new Rectangle(0, 0, WIDTH*SCALE, HEIGHT*SCALE));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setBackground(Colors.PRIMARY_BACKGROUND_COLOR);

		topPanel = createTopPanel();
		hashLabel = createHashLabel();
		bottomPanel = createBottomPanel();

		panel.add(topPanel, BorderLayout.NORTH);
		panel.add(hashLabel, BorderLayout.CENTER);
		panel.add(bottomPanel, BorderLayout.SOUTH);
		
		return panel;
	}
	
	// ==== Top panel ====
	private JPanel createTopPanel() {
		BorderLayout layout = new BorderLayout();
	    
		JPanel panel = new JPanel(layout);
		panel.setBackground(Colors.PRIMARY_BACKGROUND_COLOR);
		
		passwordField = createPasswordField();
		viewButton = createViewButton();

		panel.add(passwordField, BorderLayout.LINE_START);
		panel.add(viewButton, BorderLayout.LINE_END);
		
		return panel;
	}
	
	private JPasswordField createPasswordField() {
		JPasswordField field = new JPasswordField();
		field.setBackground(Colors.SECONDARY_BACKGROUND_COLOR);
		field.setForeground(Colors.PRIMARY_FOREGROUND_COLOR);
		field.setCaretColor(Colors.PRIMARY_FOREGROUND_COLOR);
		field.setSelectionColor(Colors.PRIMARY_FOREGROUND_COLOR);
		
		field.setFont(Fonts.TERTIARY_FONT);
		
		field.setBorder(BorderFactory.createEmptyBorder(0, 7, 0, 7));
		
		field.setEchoChar((char)UIManager.get("PasswordField.echoChar"));
		
		field.setPreferredSize(new Dimension(DEFAULT_PASSWORD_WIDTH, DEFAULT_COMPONENT_HEIGHT));
		
		return field;
	}
	
	private Button createViewButton() {
		Button button = new Button(getViewButtonImageIcon(_passwordVisible));

		button.setPreferredSize(new Dimension(DEFAULT_COMPONENT_HEIGHT, DEFAULT_COMPONENT_HEIGHT));
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				_passwordVisible = !_passwordVisible;
				passwordField.setEchoChar(_passwordVisible ? (char)0 : (char)UIManager.get("PasswordField.echoChar"));
				button.setIcon(getViewButtonImageIcon(_passwordVisible));
			}
		});
		
		return button;
	}
	
	// ==== Middle label ====
	private JLabel createHashLabel() {
		JLabel label = new JLabel();
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setForeground(Colors.PRIMARY_FOREGROUND_COLOR);
		label.setFont(Fonts.PRIMARY_FONT);
		
		return label;
	}
	
	// ==== Bottom panel ====
	private JPanel createBottomPanel() {
		GridLayout layout = new GridLayout();
	    layout.setHgap(10);
	    
		JPanel panel = new JPanel(layout);
		panel.setBackground(Colors.PRIMARY_BACKGROUND_COLOR);
		
		hashButton = createHashButton();
		copyButton = createCopyButton();
		clearButton = createClearButton();

		panel.add(hashButton);
		panel.add(copyButton);
		panel.add(clearButton);
		
		return panel;
	}
	
	private Button createHashButton() {
		Button button = new Button("MD5");
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String text = new String(passwordField.getPassword());
				button.setOk();
				
				if (text.isEmpty()) {
					System.out.println("[View] Empty password");
					button.setWarning();
				}
				
				System.out.println("[View] Hash button pressed: " + text);
				
				String hash = _controller.Execute(text);
				
				hashLabel.setText(hash);
			}
		});
		
		return button;
	}
	
	private Button createCopyButton() {
		Button button = new Button("Copy");
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("[View] Copy button pressed");
				button.setOk();
				
				String text = hashLabel.getText();
				
				if (text.isEmpty()) {
					System.out.println("[View] Empty hash");
					button.setWarning();
				}
				
				_controller.CopyHashToClipboard(text);
			}
		});
		
		return button;
	}
	
	private Button createClearButton() {
		Button button = new Button("Clear");
		
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				System.out.println("[View] Clear button pressed");
				resetPanelToDefault();
			}
		});
		
		return button;
	}

	// ==== Helper methods ====
	private void resetPanelToDefault() {
		passwordField.setText(EMPTY_STRING);
		passwordField.setEchoChar((char)UIManager.get("PasswordField.echoChar"));
		hashLabel.setText(EMPTY_STRING);
		_controller.ClearClipboard();
		_passwordVisible = false;
		viewButton.setIcon(getViewButtonImageIcon(_passwordVisible));
		hashButton.setOk();
		copyButton.setOk();
	}

	private ImageIcon getViewButtonImageIcon(boolean isOpen) {
		ImageIcon image = isOpen
						? new ImageIcon("src//resources//eye_open_light.png")
						: new ImageIcon("src//resources//eye_close_light.png");
		
		return new ImageIcon(image.getImage().getScaledInstance( ICON_SIZE, ICON_SIZE,  java.awt.Image.SCALE_SMOOTH ));
	}

}
