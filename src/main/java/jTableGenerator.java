import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.common.base.Splitter;
import latex.FluentLatexTableBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by schroeder on 08/01/15.
 */
public class jTableGenerator {

    static class CmdArgs {

        @Parameter
        private List<String> parameters = new ArrayList<String>();

        @Parameter(names = { "-in" }, description = "input data file", required = true)
        private String inFile;

        @Parameter(names = { "-out" }, description = "out file containing latex table", required = true)
        private String outFile;

        @Parameter(names = {"-del","-delimiter"}, description = "delimiter to split infile. default is ';'")
        private String del = ";";

        @Parameter(names = {"-noHead"}, description = "determines that input data file has no head, i.e. no column names")
        private boolean noHead;

        @Parameter(names = {"-caption"}, description = "caption of table")
        private String caption;

        @Parameter(names = {"-label"}, description = "label of table")
        private String label;

        @Parameter(names = {"-help"}, description = "gives help")
        private boolean help = false;

    }

    static class Required implements IParameterValidator {

        @Override
        public void validate(String name, String value) throws ParameterException {
            if(value == null) throw new ParameterException("value of cmd line parameter " + name + " is required.");
        }
    }

    public static void main(String[] args) throws IOException {
        CmdArgs cmdArgs = new CmdArgs();
        JCommander jc = new JCommander(cmdArgs,args);
        if(cmdArgs.help) {
            jc.usage();
            return;
        }
        BufferedReader reader = new BufferedReader(new FileReader(new File(cmdArgs.inFile)));
        FluentLatexTableBuilder table = new FluentLatexTableBuilder();
        if(cmdArgs.caption != null) table.withCaption(cmdArgs.caption);
        if(cmdArgs.label != null) table.withLabel(cmdArgs.label);
        String line;
        boolean first = true;
        while((line = reader.readLine()) != null){
            if(first) {
                if (!cmdArgs.noHead) {
                    table.withColumns(Splitter.on(cmdArgs.del).trimResults().split(line));
                }
                first = false;
            }
            else{
                table.withRow(Splitter.on(cmdArgs.del).trimResults().split(line));
            }
        }
        String texTable = table.build();
        reader.close();
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(cmdArgs.outFile)));
        writer.write(texTable);
        writer.close();
    }

}
