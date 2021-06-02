package mts.teta.resizer;

import mts.teta.resizer.imageprocessor.BadAttributesException;

import java.io.File;

import static picocli.CommandLine.Option;
import static picocli.CommandLine.Parameters;

public class ConsoleAttributes {

    @Parameters(index = "0", description = "input file")
    private File inputFile;
    @Parameters(index = "1", description = "output file")
    private File outputFile;
    private int resizeWidth;
    private int resizeHeight;

    private boolean isResizeSet = false;
    @Option(names = "--quality", defaultValue = "100", description = "JPEG/PNG compression level")
    private double quality;
    @Option(names = "--blur", defaultValue = "0", description = "reduce image noise detail levels")
    private int blurRadius;
    @Option(names = "--format", description = "the image format type")
    private String format;
    private int cropWidth;
    private int cropHeight;
    private int cropX;
    private int cropY;
    private boolean isCropSet = false;

    @Option(names = "--resize", arity = "2", description = "width x height to resize the image")
    public void setResize(int[] args) throws BadAttributesException {
        if (args.length == 2) {
            resizeWidth = args[0];
            resizeHeight = args[1];
            isResizeSet = true;
        } else {
            throw new BadAttributesException("Please check params!");
        }
    }

    @Option(names = "--crop", arity = "4", description = "width x height + x:y to cut out one rectangular area of the image")
    public void setCrop(int[] args) throws BadAttributesException {
        if (args.length == 4) {
            cropWidth = args[0];
            cropHeight = args[1];
            cropX = args[2];
            cropY = args[3];
            isCropSet = true;
        } else {
            throw new BadAttributesException("Please check params!");
        }
    }

    public int getResizeWidth() {
        return resizeWidth;
    }

    public void setResizeWidth(int resizeWidth) {
        this.resizeWidth = resizeWidth;
        this.isResizeSet = true;

    }

    public int getResizeHeight() {
        return resizeHeight;
    }

    public void setResizeHeight(int resizeHeight) {
        this.resizeHeight = resizeHeight;
        this.isResizeSet = true;
    }

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

    public int getBlurRadius() {
        return blurRadius;
    }

    public void setBlurRadius(int blurRadius) {
        this.blurRadius = blurRadius;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getCropWidth() {
        return cropWidth;
    }

    public void setCropWidth(int cropWidth) {
        this.cropWidth = cropWidth;
        this.isCropSet = true;
    }

    public int getCropHeight() {
        return cropHeight;
    }

    public void setCropHeight(int cropHeight) {
        this.cropHeight = cropHeight;
        this.isCropSet = true;
    }

    public int getCropX() {
        return cropX;
    }

    public void setCropX(int cropX) {
        this.cropX = cropX;
        this.isCropSet = true;
    }

    public int getCropY() {
        return cropY;
    }

    public void setCropY(int cropY) {
        this.cropY = cropY;
        this.isCropSet = true;
    }

    public boolean isResizeSet() {
        return isResizeSet;
    }

    public boolean isCropSet() {
        return isCropSet;
    }
}
