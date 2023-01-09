package owl.home.simpletelegrambot.util;

public enum MessageText {
    ASSEMBLE("Avengers, assemble!"),
    BOT_URL("https://t.me/GeneralGathering_bot"),
    INFO("""
            1. Перейдите по ссылке и запустите меня.
            2. Введите команду /register.
            3. Все готово, для общего сбора вводите команду /assemble.
            """),
    REGISTERED("User successfully registered!");

    private final String text;

    MessageText(String text) {
        this.text = text;
    }

    public String getText(){
        return text;
    }
}
