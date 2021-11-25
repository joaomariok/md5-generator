package passwordgenerator.components;

import java.awt.Dimension;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import passwordgenerator.theme.Colors;
import passwordgenerator.theme.Fonts;

public class Button extends JButton {

	private static final long serialVersionUID = 1L;

	public Button() {
		addStyle();
	}

	public Button(Icon icon) {
		super(icon);
		addStyle();
	}

	public Button(String text) {
		super(text);
		addStyle();
	}

	public Button(Action a) {
		super(a);
		addStyle();
	}

	public Button(String text, Icon icon) {
		super(text, icon);
		addStyle();
	}
	
	private void addStyle() {
	    setBorder(null);
		setFocusPainted(false);
		setBorderPainted(false);
		setPreferredSize(new Dimension(0, 30));
		setContentAreaFilled(false);
		setOpaque(true);
		
		setBackground(Colors.SECONDARY_BACKGROUND_COLOR);
		setForeground(Colors.PRIMARY_FOREGROUND_COLOR);
		
		setFont(Fonts.SECONDARY_FONT);
		
		addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent evt) {
                if (getModel().isPressed()) {
                	setBackground(Colors.PRESSED_BACKGROUND_COLOR);
                } else if (getModel().isRollover()) {
                    setBackground(Colors.ROLLOVER_BACKGROUND_COLOR);
                } else {
                    setBackground(Colors.SECONDARY_BACKGROUND_COLOR);
                }
            }
        });
	}
	
	public void setError() {
		setBorderPainted(true);
		setBorder(BorderFactory.createLineBorder(Colors.RED_COLOR.darker(), 2));
	}
	
	public void setWarning() {
		setBorderPainted(true);
		setBorder(BorderFactory.createLineBorder(Colors.YELLOW_COLOR.darker(), 2));
	}
	
	public void setOk() {
		setBorderPainted(true);
		setBorder(BorderFactory.createLineBorder(Colors.GREEN_COLOR.darker().darker(), 2));
	}
	
	public void setDefault() {
		setBorderPainted(false);
		setBorder(null);
	}

}
