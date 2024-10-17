package ch.heigvd.dai.commands;

import picocli.CommandLine;

@CommandLine.Command(
    description = "A CLI tool to edit BMP pictures.",
    version = "1.0.0",
    subcommands = {Grey.class, Blur.class, Crop.class},
    scope = CommandLine.ScopeType.INHERIT,
    mixinStandardHelpOptions = true)
public class Root {

  @CommandLine.Parameters(index = "0", description = "The path to the input file.")
  protected String inputPath;

  // Make outputPath optional with arity = "0..1"
  @CommandLine.Parameters(
      index = "1",
      description = "The desired path for the output file.",
      arity = "0..1")
  protected String outputPath;

  public String getInputPath() {
    return inputPath;
  }

  public String getOutputPath() {
    // If outputPath is null, we add "bis" to inputPath
    if (outputPath == null) {
      int dotIndex = inputPath.lastIndexOf('.');
      // Insert "bis" before file extension
      outputPath = inputPath.substring(0, dotIndex) + "bis" + inputPath.substring(dotIndex);
    }
    return outputPath;
  }
}
