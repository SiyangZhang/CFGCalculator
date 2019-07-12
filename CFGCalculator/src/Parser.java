import java.util.Scanner;

public class Parser {

    int cursor = 0;

    String input;

    String buff;


    public Parser(String input){
        this.input = input+"$";
        cursor = 0;
        buff = "    ";
    }

    public double calculate(){
        return parseExpression("");
    }

    public double parseExpression(String buffer){
        System.out.println(buffer+"Expression");
        buffer += buff;
        double head = parseTerm(buffer);
        double tail = parseExpressionTail(buffer);
        return head + tail;
    }

    public double parseExpressionTail(String buffer){
        System.out.println(buffer+"Exp_Tail");
        buffer += buff;
        char c = peek();
        if(c == '+' || c == '-'){
            int sign = consumeAddOp(buffer);
            double head = parseTerm(buffer);
            double tail = parseExpressionTail(buffer);
            return sign * head + tail;
        }else{
            return 0;
        }

    }


    public double parseTerm(String buffer){
        System.out.println(buffer+"Term");
        buffer += buff;
        double head = parseFactor(buffer);
        double tail = parseTermTail(buffer);
        return head * tail;
    }

    public double parseTermTail(String buffer){
        System.out.println(buffer+"Term_Tail");
        buffer += buff;
        char c = peek();
        if(c == '*' || c == '/') {
            char sign = consumeMultiOp(buffer);
            double head = parseFactor(buffer);
            if (sign == '*') {

            } else if (sign == '/') {
                head = 1 / head;
            }

            double tail = parseTermTail(buffer);

            return head * tail;
        }else{
            return 1;
        }
    }

    public double parseFactor(String buffer){
        System.out.println(buffer+"Factor");
        buffer += buff;
        if(cursor >= input.length()){
            return 1;
        }

        char c = peek();

        if(c=='(') {
            cursor++;
            double val = parseExpression(buffer);
            cursor++;
            return val;

        }else if(isDigit(c)) {

            double head = parseNumber(buffer);

            return head;

        }else{
            return 1;
        }
    }

//    public double parseFactorTail(){
//        char cur = peek();
//        cursor++;
//
//
//        switch(cur){
//            case '*':
//        }
//        return 0;
//    }

    public int consumeAddOp(String buffer){
        System.out.println(buffer+"AddOP");

        char c = peek();
        cursor++;
        if(c=='+'){
            return 1;
        }else {
            return -1;
        }
    }

    public char consumeMultiOp(String buffer){
        System.out.println(buffer+"MulOP");

        char c = peek();
        cursor++;
        return c;
    }



    public double parseNumber(String buffer){
        System.out.println(buffer+"Number");

        String res = "";
        while(cursor < input.length()){
            char c = peek();
            if(isDigit(c)){
                res += c;
                cursor++;
            }else{
                break;
            }
        }
        return Double.parseDouble(res);
    }


    public char peek(){
        return input.charAt(cursor);
    }

    public static boolean isDigit(char c){
        int i = c;
        return (i >= 48) && (i <= 57);
    }
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter your expression:");
        String str = scan.nextLine();
        Parser parser = new Parser(str);
        parser.buff = "          ";
        try{
            System.out.println("\nLegal Expression, start to calculate:\n");
            double result = parser.calculate();

            System.out.println("\nThe result is: " + result);
        }catch(Exception e){
            System.out.println("\nIllegal Expression, exit.");
        }
        scan.close();
    }
}
