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

  // TODO : make optional ? If omitted, original picture would be replaced ?
  @CommandLine.Parameters(index = "1", description = "The desired path for the output file.")
  protected String outputPath;

  public String getInputPath() {
    return inputPath;
  }

  public String getOutputPath() {
    return outputPath;
  }
}
