# Image Processing Application

Welcome to the **Image Processing Application** project, developed as part of the [**Web Application Development
**](https://github.com/heig-vd-dai-course) course. This CLI application allows users to modify image formats, convert
images to black and white, and apply a blur effect. It is designed to be user-friendly while offering powerful image
processing features.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Examples](#examples)
- [Contributing](#contributing)

## Features

- **Image Ratio Modification**: Modifies the format ratio of your image. For example, if you insert a squared image, it
  can crop it to 4/5.
- **Black and White Conversion**: Transform your image to grayscale.
- **Blur Effect**: Apply a blur effect on the whole image or part of it. Blur strength can be adjusted.

## Installation

### Prerequisites

- **Java JDK 21**
- **Maven** for dependency management and building the project
- **Git** for cloning the repository
- **IntelliJ IDEA** (optional) for easier setup

### Clone the Repository

Open your terminal and run the following command to clone the repository:

```bash
git clone https://github.com/HEIG-VD-DAI-Iseni-Jacobs/pictures-cli-editor.git
```

### Build and run

<details>
<summary>Using the Terminal</summary>
<br>

#### Build the Project

Navigate to the cloned project directory and use Maven to build the application:

```bash
cd pictures-cli-editor
mvn spotless:apply dependency:go-offline clean compile package
```

#### Usage

Once the project is built, you can run the application using the following command:

```bash
java -jar target/pictures-cli-editor-1.0-SNAPSHOT.jar <inputPath> <outputPath> <command>
```

The application provides a command-line interface where you can choose the desired image processing options.

#### Required parameter

1. **input path** specifies which picture you want to modify

#### Optional parameter

1. **output path** specifies where you want to save the modified image. If not provided, the input path will be used
   and "_edited" will be appended to the file name before the extension. The output file will always be in .bmp format.

#### Available Commands

1. **grey**
2. **blur**
3. **crop**

Select the desired option by entering the corresponding name.
</details>

<details open>
<summary>Using IntelliJ IDEA</summary>
<br>

#### Build

In the "Run" tab, select `Package application as JAR file` and run it.

#### Run

In the "Run" tab, select `Run the application (needs parameters)` or other configs and run it.
By default, no parameters are given so the help message will display.
Feel free to update the run config or launch the app from the terminal to add your parameters !
For more details on the commands see Available Commands.

</details>

### Converting pictures to .bmp

The pictures were taken by ourselves. We had to convert them to bmp and reduce their size as bmp is not optimised and
takes
much space. For this purpose, two options are possible:

- Use online converters such as [convertio](https://convertio.co/fr/download/)
  and [reduceimages](https://www.reduceimages.com/)
- Use [magick](https://imagemagick.org/index.php), a dedicated CLI software. Here's the command used :  
  `magick mogrify -resize 1080x1620 -depth 8 -format bmp *.jpg`

## Examples

### Cropping

| **Before:**                                              | **After:**                                           |
|----------------------------------------------------------|------------------------------------------------------|
| ![Before Conversion](src/resources/pictures/golf_7r.bmp) | ![After Conversion](output_examples/golf_7r_5_4.bmp) |

**Used command**

````shell
java -jar target/pictures-cli-editor-1.0-SNAPSHOT.jar src/resources/pictures/golf_7r.bmp output_examples/golf_7r_5_4.bmp crop -f 5/4
````

### Black and White Conversion

| **Before:**                                                    | **After:**                                                  |
|----------------------------------------------------------------|-------------------------------------------------------------|
| ![Before Black and White](src/resources/pictures/rosa_mir.bmp) | ![After Black and White](output_examples/rosa_mir_grey.bmp) |

**Used command**

````shell
java -jar target/pictures-cli-editor-1.0-SNAPSHOT.jar src/resources/pictures/rosa_mir.bmp output_examples/rosa_mir_grey.bmp grey

````

### Image Blurring

| **Before:**                                     | **After:**                                        |
|-------------------------------------------------|---------------------------------------------------|
| ![Before Blur](src/resources/pictures/bird.bmp) | ![After Blur](output_examples/bird_blurred.bmp) |

**Used command**

````shell
java -jar target/pictures-cli-editor-1.0-SNAPSHOT.jar ./src/resources/pictures/bird.bmp ./output_examples.bmp blur -r 10 --x1 550 --y1 700 --x2 750 --y2 900
````

## Contributing

Contributions are welcome! To contribute:

1. Create an issue describing the feature you want to implement
2. Fork the project and clone it
3. Create your feature branch

````shell
git checkout -b feature/my-feature
````

4. Commit your changes

````shell
git commit -m "Add my feature"
````

5. Push the branch

```shell
git push
```

6. Open a Pull Request

If you have any questions or suggestions, feel free to open
an [issue](https://github.com/HEIG-VD-DAI-Iseni-Jacobs/pictures-cli-editor/issues) on GitHub.
