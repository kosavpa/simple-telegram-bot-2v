package owl.home.simpletelegrambot.util;

public enum Action {
    REGISTER("/register"),
    ASSEMBLE("/assemble"),
    TEXT("Avengers, assemble!"),
    BOT_URL("https://t.me/GeneralGathering_bot"),
    NEW_MEMBER("member");

    private final String command;

    Action(String command) {
        this.command = command;
    }

    public String getCommand(){
        return command;
    }
}
