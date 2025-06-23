import java.awt.Image;
import java.net.URL;
import javax.swing.ImageIcon;

public enum Seed {
    CROSS("X", "images/cross.png"),
    NOUGHT("O", "images/not.png"),
    NO_SEED(" ", null);

    private String displayName;
    private Image img = null;

    private Seed(String name, String imageFilename) {
        this.displayName = name;
        if (imageFilename != null) {
            URL imgURL = getClass().getClassLoader().getResource(imageFilename);
            if (imgURL != null) {
                img = new ImageIcon(imgURL).getImage();
            } else {
                System.err.println("Couldn't find file " + imageFilename);
            }
        }
    }

    public String getDisplayName() {
        return displayName;
    }

    public Image getImage() {
        return img;
    }
}
