package latex;

import java.util.Arrays;
import java.util.List;

/**
 * Created by schroeder on 08/01/15.
 */
class BaseTableGenerator {

    public static final String TABLE = "table";

    public static final String TABULAR = "tabular";

    public static final String CENTER = "center";

    public static final String LEFT = "l";

    public static final String RIGHT = "r";

    public static final String BORDER = "|";

    private int countIndent = 0;

    public String begin(List<String> params){
        String s = indent_();
        s += "\\begin";
        for(String p : params){
            s += "{" + p + "}";
        }
        s += "\n";
        return s;
    }

    private String indent_() {
        String intend = "";
        for(int i=0;i<countIndent;i++){
            intend += "\t";
        }
        return intend;
    }

    public String begin(String param){
        return begin(Arrays.asList(param));
    }

    public String end(String itemToEnd){
        return indent_() + "\\end{"+itemToEnd+"}\n";
    }

    public String caption(String caption){
        return indent_() + "\\caption{"+caption+"}\n";
    }


    public String label(String label) {
        return indent_() + "\\label{"+label+"}\n";
    }

    public String hline() {
        return indent_() + "\\hline\n";
    }

    public String tableHead(Iterable<String> columnNames) {
        String head = getRow(columnNames);
        head += lineBreak();
        return head;
    }

    private String getRow(Iterable<String> columnNames) {
        String head = indent_();
        boolean first = true;
        for(String s : columnNames){
            if(first){
                head += s;
                first = false;
            }
            else{
                head += " & " + s;
            }
        }
        return head;
    }

    public String tableBody(List<Iterable<String>> rows) {
        String body = "";
        for(Iterable<String> row : rows){
            body += getRow(row);
            body += lineBreak();
        }
        return body;
    }

    private String lineBreak() {
        return " \\\\\n";
    }

    public void indent() {
        countIndent++;
    }

    public void revIndent() {
        countIndent--;
        if(countIndent<0) countIndent=0;
    }
}
