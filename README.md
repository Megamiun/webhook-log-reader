# Log Reader

This project parses a log file and return some aggregated information about the frequency of urls and status codes inside the log content.

## Usage

### Library

As a library, you can use this project by importing it in the system, instantiating one of the possible `Parser` implementations(or creating yours) and passing a `InputStream` of your log to the created instance.

### CLI

As a `CLI`, you can use this project by specifying in the command line the method of retrieval of the results and the path for the file. The arguments are as follow:

```[-h] [-t] [FILEPATH] [--regex]```

- `-t` - Prints the execution time at the end of execution;
- `FILEPATH` - Path to the file to be parsed;
- `--regex` - Implementation to use, in this case, regex

### Sample File

There is a sample file in `src/main/resources/log.txt`. When no file is defined in the CLI, this file will be used.