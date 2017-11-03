package pl.bmstefanski.tools.command.basic;

public class CommandUsageException extends CommandException {

    public CommandUsageException() {
        super();
    }

    public CommandUsageException(String message) {
        super(message);
    }
}
