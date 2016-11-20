package essentials;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;

public class Graphics {

	/**
	 * Adds a component to a container using the {@link GridBagLayout}.
	 * 
	 * @param container
	 *            Container to which the component will be added
	 * @param layout
	 *            Used GridBagLayout object
	 * @param component
	 *            Component which will be added to the container
	 * @param x
	 *            x coordinate of the component in the grid
	 * @param y
	 *            y coordinate of the component in the grid
	 * @param width
	 *            Width of the component
	 * @param height
	 *            Height of the component
	 * @param weightx
	 *            x weight of the component
	 * @param weighty
	 *            y weight of the component
	 * @param insets
	 *            Insets which defines the distances around the component
	 * @return boolean false if exception occurred
	 */
	public static boolean addComponent(Container container, GridBagLayout layout, Component component, int x, int y,
			int width, int height, double weightx, double weighty, Insets insets) {

		if (insets == null)
			insets = new Insets(0, 0, 0, 0);

		try {
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.fill = GridBagConstraints.BOTH;
			constraints.gridx = x;
			constraints.gridy = y;
			constraints.gridwidth = width;
			constraints.gridheight = height;
			constraints.weightx = weightx;
			constraints.weighty = weighty;
			constraints.insets = insets;
			layout.setConstraints(component, constraints);
			container.add(component);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Copies a BufferedImage
	 * 
	 * @param image
	 *            The image that should be copied
	 * @return The copied image
	 */
	public static BufferedImage copyBufferedImage(BufferedImage image) {

		ColorModel cm = image.getColorModel();
		boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
	}
}
