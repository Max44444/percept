package bsa.java.concurrency.util.hasher;

import bsa.java.concurrency.exeption.CannotCalculateCashException;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Component
public class DHasher implements Hasher {

    private static final int IMAGE_WIDTH = 9;
    private static final int IMAGE_HEIGHT = 9;

    public long calculateHash(byte[] imageByteArray) {
        try {
            var image = ImageIO.read(new ByteArrayInputStream(imageByteArray));
            return calculateHash(prepareImageForCalculateHash(image));
        } catch (IOException e) {
            throw new CannotCalculateCashException(e);
        }
    }

    private BufferedImage prepareImageForCalculateHash(BufferedImage image) {
        var scaledImage = image.getScaledInstance(IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_SMOOTH);
        var grayscaleImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_BYTE_GRAY);
        grayscaleImage.getGraphics().drawImage(scaledImage, 0, 0, null);

        return grayscaleImage;
    }

    private int calculateBrightnessScore(int rgb) {
        return rgb & 0b11111111;
    }

    private long calculateHash(BufferedImage image) {
        long hash = 0;
        for (var row = 1; row < image.getWidth(); row++) {
            for (var column = 1; column < image.getHeight(); column++) {
                var prev = calculateBrightnessScore(image.getRGB(column - 1, row - 1));
                var current = calculateBrightnessScore(image.getRGB(column, row));
                hash |= current > prev ? 1 : 0;
                hash <<= 1;
            }
        }

        return hash;
    }

}
