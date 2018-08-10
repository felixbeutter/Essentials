package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public class RoundPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private Dimension arcs = new Dimension(0, 0);
	private Color borderColor = null;

	public RoundPanel(Dimension arcs) {

		this.arcs = arcs;
		setOpaque(false);
	}

	public RoundPanel(Dimension arcs, Color borderColor) {

		this.arcs = arcs;
		this.borderColor = borderColor;
		setOpaque(false);
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D graphics = (Graphics2D) g;

		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (borderColor != null) {

			graphics.setColor(borderColor);
			graphics.fillRoundRect(0, 0, getWidth(), getHeight(), arcs.width, arcs.height);
			
			graphics.setColor(getBackground());
			graphics.fillRoundRect(1, 1, getWidth() - 2, getHeight() - 2, arcs.width, arcs.height);
			
		} else {
			
			graphics.setColor(getBackground());
			graphics.fillRoundRect(0, 0, getWidth(), getHeight(), arcs.width, arcs.height);
		}
	}
}