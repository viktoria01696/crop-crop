package mts.teta.resizer;

import mts.teta.resizer.imageprocessor.ImageProcessor;
import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(name = "resizer", mixinStandardHelpOptions = true, version = "resizer 0.0.1", description = "...")
public class ResizerApp extends ConsoleAttributes implements Callable<Integer> {
    public static void main(String... args) {
        int exitCode = runConsole(args);
        System.exit(exitCode);
    }

    protected static int runConsole(String[] args) {
        return new CommandLine(new ResizerApp()).execute(args);
    }

    @Override
    public Integer call() throws Exception {
        ImageProcessor imageProcessor = new ImageProcessor();
        imageProcessor.processImage(this);
        return 0;
    }
}
