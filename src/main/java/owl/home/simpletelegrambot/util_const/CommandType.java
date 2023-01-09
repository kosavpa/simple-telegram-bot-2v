package owl.home.simpletelegrambot.util_const;

public enum CommandType {
    REGISTER("/register"),
    ASSEMBLE("/assemble"),
    NEW_MEMBER("member"),
    UNKNOWN("");

    private final String command;

    CommandType(String command) {
        this.command = command;
    }

    public String getCommand(){
        return command;
    }
}
