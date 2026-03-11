package lexerThingy;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        String html = "<div class=\"main\">Hello <b>world</b></div>";

        HtmlLexer lexer = new HtmlLexer(html);

        List<Token> tokens = lexer.tokenize();

        for (Token t : tokens)
            System.out.println(t);
    }
}
