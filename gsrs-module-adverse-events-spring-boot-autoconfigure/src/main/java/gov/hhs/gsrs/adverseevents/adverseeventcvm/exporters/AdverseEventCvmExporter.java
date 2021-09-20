package gov.hhs.gsrs.adverseevents.adverseeventcvm.exporters;

import gov.hhs.gsrs.adverseevents.adverseeventcvm.models.AdverseEventCvm;

import ix.ginas.exporters.*;

import java.io.IOException;
import java.util.*;

enum CvmefaultColumns implements Column {
    SUBSTANCE_NAME,
    SUBSTANCE_KEY,
    SPECIES
}

public class AdverseEventCvmExporter implements Exporter<AdverseEventCvm> {

    private final Spreadsheet spreadsheet;

    private int row=1;

    private final List<ColumnValueRecipe<AdverseEventCvm>> recipeMap;

    private AdverseEventCvmExporter(Builder builder){

        this.spreadsheet = builder.spreadsheet;
        this.recipeMap = builder.columns;

        int j=0;
        Spreadsheet.SpreadsheetRow header = spreadsheet.getRow(0);
        for(ColumnValueRecipe<AdverseEventCvm> col : recipeMap){
            j+= col.writeHeaderValues(header, j);
        }
    }

    @Override
    public void export(AdverseEventCvm s) throws IOException {
        Spreadsheet.SpreadsheetRow row = spreadsheet.getRow( this.row++);

        int j=0;
        for(ColumnValueRecipe<AdverseEventCvm> recipe : recipeMap){
            j+= recipe.writeValuesFor(row, j, s);
        }
    }

    @Override
    public void close() throws IOException {
        spreadsheet.close();
    }

    private static Map<Column, ColumnValueRecipe<AdverseEventCvm>> DEFAULT_RECIPE_MAP;

    static{

        DEFAULT_RECIPE_MAP = new LinkedHashMap<>();

        DEFAULT_RECIPE_MAP.put(CvmefaultColumns.SUBSTANCE_NAME, SingleColumnValueRecipe.create( CvmefaultColumns.SUBSTANCE_NAME ,(s, cell) -> cell.writeString(s.name)));
        DEFAULT_RECIPE_MAP.put(CvmefaultColumns.SUBSTANCE_KEY, SingleColumnValueRecipe.create( CvmefaultColumns.SUBSTANCE_KEY ,(s, cell) -> cell.writeString(s.substanceKey)));
        DEFAULT_RECIPE_MAP.put(CvmefaultColumns.SPECIES, SingleColumnValueRecipe.create( CvmefaultColumns.SPECIES ,(s, cell) -> cell.writeString(s.species)));

    }

    /**
     * Builder class that makes a SpreadsheetExporter.  By default, the default columns are used
     * but these may be modified using the add/remove column methods.
     *
     */
    public static class Builder{
        private final List<ColumnValueRecipe<AdverseEventCvm>> columns = new ArrayList<>();
        private final Spreadsheet spreadsheet;

        private boolean publicOnly = false;

        /**
         * Create a new Builder that uses the given Spreadsheet to write to.
         * @param spreadSheet the {@link Spreadsheet} object that will be written to by this exporter. can not be null.
         *
         * @throws NullPointerException if spreadsheet is null.
         */
        public Builder(Spreadsheet spreadSheet){
            Objects.requireNonNull(spreadSheet);
            this.spreadsheet = spreadSheet;

            for(Map.Entry<Column, ColumnValueRecipe<AdverseEventCvm>> entry : DEFAULT_RECIPE_MAP.entrySet()){
            	columns.add(entry.getValue());
            }
        }

        public Builder addColumn(Column column, ColumnValueRecipe<AdverseEventCvm> recipe){
        	return addColumn(column.name(), recipe);
        }

        public Builder addColumn(String columnName, ColumnValueRecipe<AdverseEventCvm> recipe){
        	Objects.requireNonNull(columnName);
            Objects.requireNonNull(recipe);
            columns.add(recipe);

            return this;
        }

        public Builder renameColumn(Column oldColumn, String newName){
            return renameColumn(oldColumn.name(), newName);
        }
        
        public Builder renameColumn(String oldName, String newName){
            //use iterator to preserve order
            ListIterator<ColumnValueRecipe<AdverseEventCvm>> iter = columns.listIterator();
            while(iter.hasNext()){

                ColumnValueRecipe<AdverseEventCvm> oldValue = iter.next();
                ColumnValueRecipe<AdverseEventCvm> newValue = oldValue.replaceColumnName(oldName, newName);
                if(oldValue != newValue){
                   iter.set(newValue);
                }
            }
            return this;
        }

        public AdverseEventCvmExporter build(){
            return new AdverseEventCvmExporter(this);
        }

        public Builder includePublicDataOnly(boolean publicOnly){
            this.publicOnly = publicOnly;
            return this;
        }

    }
}