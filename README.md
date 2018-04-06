# Log Reader

This project parses a log file and return some aggregated information about the log content.

## Usage

### Library

As a library, you can use this project by importing it in the system, instantiating one of the possible implementations and passing a `InputStream` of your log to the created instance.

### CLI

As a `CLI`, you can use this project by specifying in the command line the method of retrieval of the results and the path for the file. When no file is defined, the project will run with a sample file. Format still to be defined.

## Sample File

There is a sample file in `src/main/resources/log.txt`. When no file is defined in the CLI, this file will be used.