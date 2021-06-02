package mts.teta.resizer.imageprocessor;

import marvin.image.MarvinImage;
import mts.teta.resizer.ConsoleAttributes;
import net.coobird.thumbnailator.Thumbnails;
import org.marvinproject.image.blur.gaussianBlur.GaussianBlur;
import org.marvinproject.image.segmentation.crop.Crop;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ImageProcessor {

    private ConsoleAttributes attributes;

    public void processImage(ConsoleAttributes attrs) throws IOException, BadAttributesException {
        this.attributes = attrs;
        BufferedImage input = openImage();
        if (!validateAttributes(input))
            throw new BadAttributesException("Please check params!");
        BufferedImage output = crop(input);
        output = blur(output);
        output = resize(output);
        write(output);
    }

    private BufferedImage openImage() throws IIOException {
        try {
            return ImageIO.read(attributes.getInputFile());
        } catch (IOException ex) {
            throw new IIOException("Can't read input file!");
        }
    }

    private BufferedImage crop(BufferedImage image) {
        if (attributes.isCropSet()) {
            MarvinImage in = new MarvinImage(image);
            MarvinImage out = in.clone();
            Crop cropMethod = new Crop();
            cropMethod.load();
            cropMethod.setAttribute("width", attributes.getCropWidth());
            cropMethod.setAttribute("height", attributes.getCropHeight());
            cropMethod.setAttribute("x", attributes.getCropX());
            cropMethod.setAttribute("y", attributes.getCropY());
            cropMethod.process(in, out);
            return out.getBufferedImageNoAlpha();
        } else
            return image;
    }

    private BufferedImage resize(BufferedImage image) throws IOException {
        if (attributes.isResizeSet()) {
            try {
                return Thumbnails.of(image).size(attributes.getResizeWidth(), attributes.getResizeHeight()).keepAspectRatio(false).asBufferedImage();
            } catch (IOException ex) {
                throw new IOException("Error while trying to resize image to width: " + attributes.getResizeWidth() +
                        " and height: " + attributes.getResizeHeight());
            }
        } else
            return image;
    }

    private BufferedImage blur(BufferedImage image) {
        if (attributes.getBlurRadius() > 0) {
            GaussianBlur blurMethod = new GaussianBlur();
            MarvinImage in = new MarvinImage(image);
            blurMethod.load();
            blurMethod.setAttribute("radius", attributes.getBlurRadius());
            blurMethod.process(in, in);
            return in.getBufferedImageNoAlpha();
        } else
            return image;
    }

    private void write(BufferedImage image) throws IIOException {
        try {
            if (attributes.getFormat() != null) {
                Thumbnails.of(image).outputQuality(attributes.getQuality() / 100).scale(1).outputFormat(attributes.getFormat())
                        .toFile(attributes.getOutputFile());
            } else {
                Thumbnails.of(image).outputQuality(attributes.getQuality() / 100).scale(1).toFile(attributes.getOutputFile());
            }
        } catch (IOException ex) {
            throw new IIOException("Error while trying to write result in a file: "
                    + attributes.getOutputFile().getAbsolutePath());
        }
    }

    private boolean validateAttributes(BufferedImage image) throws BadAttributesException {
        if (attributes.getFormat() != null) {
            if (!Arrays.asList("jpg", "png").contains(attributes.getFormat()))
                return false;
            String outputFileName = attributes.getOutputFile().getAbsolutePath();
            attributes.setOutputFile(new File(outputFileName.substring(0, outputFileName.lastIndexOf('.'))));
        }
        if (attributes.isResizeSet() && (attributes.getResizeWidth() <= 0 || attributes.getResizeHeight() <= 0))
            return false;
        if (attributes.isCropSet() && (!isValidCropWidth(image) || !isValidCropHeight(image)
                || !isValidCropX(image) || !isValidCropY(image)))
            return false;
        if (attributes.getQuality() <= 0 || attributes.getQuality() > 100)
            return false;
        if (attributes.getBlurRadius() < 0)
            return false;
        return true;
    }

    private boolean isValidCropWidth(BufferedImage image) {
        return attributes.getCropWidth() > 0 && attributes.getCropWidth() <= image.getWidth();
    }

    private boolean isValidCropHeight(BufferedImage image) {
        return attributes.getCropHeight() > 0 && attributes.getCropHeight() <= image.getHeight();
    }

    private boolean isValidCropX(BufferedImage image) {
        return attributes.getCropX() >= 0 && attributes.getCropX() < image.getWidth();
    }

    private boolean isValidCropY(BufferedImage image) {
        return attributes.getCropY() >= 0 && attributes.getCropY() < image.getHeight();
    }

}
