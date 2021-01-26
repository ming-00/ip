public class Ui {
    public void printHello() {
        String logo = ".------..------..------..------.\n"
                + "|D.--. ||U.--. ||K.--. ||E.--. |\n"
                + "| :/\\: || (\\/) || :/\\: || (\\/) |\n"
                + "| (__) || :\\/: || :\\/: || :\\/: |\n"
                + "| '--'D|| '--'U|| '--'K|| '--'E|\n"
                + "`------'`------'`------'`------'";

        System.out.println("Hello, this is\n" + logo + "\n What can I do for you today?\n");
    }

    public static void sayGoodbye() {
        System.out.println("Bye, see you soon! Don't miss me too much.");
    }

    /**
     * Prints a horizontal line to format text.
     */
    public static void formatText() {
        System.out.println("******************************************************");
    }
}
