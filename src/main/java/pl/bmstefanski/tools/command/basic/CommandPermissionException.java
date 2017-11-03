package pl.bmstefanski.tools.command.basic;

public class CommandPermissionException extends CommandException {

    public CommandPermissionException() {
        super();
    }

    public CommandPermissionException(String permission) {
        super(permission);
    }

    public String getPermission() {
        return this.getMessage();
    }
}
