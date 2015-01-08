package latex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by schroeder on 08/01/15.
 */
public class FluentLatexTableBuilder {

    private String format;

    private String caption;

    private String label;

    private Iterable<String> columnNames;

    private List<Iterable<String>> rows = new ArrayList<Iterable<String>>();

    private Integer noColumns;

    public FluentLatexTableBuilder withFormat(String formatString){
        this.format = formatString;
        return this;
    }

    public FluentLatexTableBuilder withCaption(String caption){
        this.caption = caption;
        return this;
    }

    public FluentLatexTableBuilder withLabel(String label){
        this.label = label;
        return this;
    }

    public FluentLatexTableBuilder withColumns(Iterable<String> columnNames){
        this.columnNames = columnNames;
        return this;
    }

    public FluentLatexTableBuilder withRow(Iterable<String> row){
        this.rows.add(row);
        return this;
    }

    public String build(){
        determineNoColumnsAndCheckValidity();
        if(columnNames == null) generateColumns();
        BaseTableGenerator base = new BaseTableGenerator();
        String table = base.begin(BaseTableGenerator.TABLE);
        base.indent();
        table += base.begin(BaseTableGenerator.CENTER);
        base.indent();
        if(caption != null){
            table += base.caption(caption);
        }
        table += base.begin(Arrays.asList(BaseTableGenerator.TABULAR,getFormat()));
        base.indent();
        table += base.hline();
        table += base.tableHead(columnNames);
        table += base.hline();
        table += base.tableBody(rows);
        table += base.hline();
        base.revIndent();
        table += base.end(BaseTableGenerator.TABULAR);
        base.revIndent();
        table += base.end(BaseTableGenerator.CENTER);
        if(label != null) table += base.label(label);
        base.revIndent();
        table += base.end(BaseTableGenerator.TABLE);
        return table;
    }

    private void determineNoColumnsAndCheckValidity() {
        this.noColumns = null;
        if(columnNames != null){
            noColumns = getNoIterableItems(columnNames);
        }
        if(rows.isEmpty()) throw new IllegalStateException("table rows missing");
        for(Iterable<String> row : rows){
            Integer noIterableItems = getNoIterableItems(row);
            if(noColumns == null){
                noColumns = noIterableItems;
            }
            else{
                if(noColumns != noIterableItems){
                    throw new IllegalStateException("no columns must be unique");
                }
            }
        }
        if(format != null){

        }
    }

    private Integer getNoIterableItems(Iterable<String> columnNames) {
        int count = 0;
        for(String s : columnNames) count++;
        return count;
    }

    private void generateColumns() {
        List<String> names = new ArrayList<String>();
        for(int i=0;i<noColumns;i++){
            names.add("column"+(i+1));
        }
        columnNames = names;
    }

    private String getDefaultFormat() {
        String format = "l";
        for(int i=0;i<noColumns-1;i++){
            format += " | r";
        }
        return format;
    }

    private String getFormat() {
        if(format != null) return format;
        return getDefaultFormat();
    }
}
