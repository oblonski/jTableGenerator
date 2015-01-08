package latex.example;

import com.google.common.base.Splitter;
import latex.FluentLatexTableBuilder;

import java.util.Arrays;

/**
 * Created by schroeder on 08/01/15.
 */
public class LatexTable {

    public static void main(String[] args) {
        String columns = "scenario,time,distance";
        String row1 = "ref,1000,12";
        FluentLatexTableBuilder b = new FluentLatexTableBuilder();
        String table = b.withCaption("this is a latex table")
                .withFormat("| c | c | c |")
                .withLabel("tab:latex table")
                .withColumns(Splitter.on(',').trimResults().split(columns))
                .withRow(Splitter.on(',').trimResults().split(row1))
                .withRow(Arrays.asList("cordon", "2000", "123"))
                .build();
        System.out.println(table);
    }

}
