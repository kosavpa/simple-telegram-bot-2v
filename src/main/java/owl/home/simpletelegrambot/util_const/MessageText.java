package owl.home.simpletelegrambot.util_const;

public enum MessageText {
    ASSEMBLE("Avengers, assemble!"),
    INFO("""
            1. Перейдите по ссылке (https://t.me/GeneralGathering_bot) и запустите меня.
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
