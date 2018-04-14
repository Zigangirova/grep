import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Grep {

    public static boolean param_i = false;
    public static boolean param_r = false;
    public static boolean param_v = false;
    public static String word = "";

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Недостаточно аргументов");
            return;
        }

        config(args);

        if (!param_r)
            System.out.println("Выводим строки содержащие слово: " + word);
        else {
            System.out.println("Выводим строки содержащие регулярку: " + word);
            if (param_i)
                System.out.println("При работе с регулярными выражениями параметр '-i' не работает.");
        }

        try{
            FileInputStream stream = new FileInputStream(args[args.length - 1]);
            BufferedReader br = new BufferedReader(new InputStreamReader(stream));
            String strLine;
            while ((strLine = br.readLine()) != null){
                String s = filter(strLine, word);
                if (s != null) System.out.println(s);
            }
        }catch (IOException e){
            System.out.println("Файл не найден");
        }
    }

    public static void config(String[] args) {
        int index;
        for (index = 0; index < args.length - 1; index++) {
            char switcher = args[index].charAt(0);

            switch (switcher) {
                case '-':
                    switch (args[index].charAt(1)) {
                        case 'i':
                            param_i = true;
                            break;
                        case 'r':
                            param_r = true;
                            break;
                        case 'v':
                            param_v = true;
                            break;
                    }
                    break;
                default:
                    word = args[index];
                    break;
            }
        }
    }

    public static String filter(String strLine, String word) {
        String checker = strLine;
        String returnStr = null;
        if (!param_r) {
            if (param_i) {
                checker = strLine.toUpperCase();
                word = word.toUpperCase();
            }
            if (checker.contains(word) && !param_v) {
                returnStr = strLine;
            } else if (!checker.contains(word) && param_v) {
                returnStr = strLine;
            }
        } else {
            Pattern p = Pattern.compile(word);
            Matcher m = p.matcher(checker);
            if (m.matches() && !param_v) {
                returnStr = strLine;
            } else if (!m.matches() && param_v) {
                returnStr = strLine;
            }
        }
        return returnStr;
    }
}
