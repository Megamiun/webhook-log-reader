# Log Reader

This project parses a log file and return some aggregated information about the frequency of urls and status codes inside the log content.

## Building

I recommend packaging the program with the command `./gradlew shadow`, that will create a fat jar called `log-reader-1.0-SNAPSHOT-all.jar` in `build/libs`.

## Usage

### Library

As a library, you can use this project by importing it in the system, instantiating one of the possible `Parser` implementations(or creating yours) and passing a `InputStream` of your log to the created instance.

### CLI

As a `CLI`, you can use this project by specifying in the command line the method of retrieval of the results and the path for the file. The arguments are as follow:

```java -jar log-reader-1.0-SNAPSHOT-all.jar [-h] [-t] [-b] [FILEPATH] [--split|--regex]```

- `-t` - Prints the execution time at the end of execution;
- `-b` - Runs a benchmark between the implementations, running each a hundred times.
- `FILEPATH` - Path to the file to be parsed;
- `--regex` - Implementation to use, in this case, regex;
- `--split` - Implementation to use, in this case, split, is the default one.

### Gradle

To run this program directly from Gradle, you can use the command `./gradlew runProgram '-Parguments='` with the CLI arguments as a comma separated list of values after `-Parguments`.

### Sample File

There is a sample file in `src/main/resources/log.txt`. When no file is defined in the CLI, this file will be used.