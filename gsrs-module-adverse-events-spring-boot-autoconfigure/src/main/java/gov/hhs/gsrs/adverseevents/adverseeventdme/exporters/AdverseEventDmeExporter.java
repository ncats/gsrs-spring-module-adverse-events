package gov.hhs.gsrs.adverseevents.adverseeventdme.exporters;

import gov.hhs.gsrs.adverseevents.adverseeventdme.models.*;

import ix.ginas.exporters.*;

import java.io.IOException;
import java.util.*;

enum DmeDefaultColumns implements Column {
    SUBSTANCE_NAME,
    APPROVAL_ID,
    SUBSTANCE_KEY,
    DME_REACTIONS,
    PTTERM_MEDDRA,
    CASE_COUNT,
    DME_COUNT,
    DME_COUNT_PERCENT,
    WEIGHTED_AVERAGE_PRR
}

public class AdverseEventDmeExporter implements Exporter<AdverseEventDme> {

    private final Spreadsheet spreadsheet;

    private int row=1;

    private final List<ColumnValueRecipe<AdverseEventDme>> recipeMap;

    private AdverseEventDmeExporter(Builder builder){

        this.spreadsheet = builder.spreadsheet;
        this.recipeMap = builder.columns;

        int j=0;
        Spreadsheet.SpreadsheetRow header = spreadsheet.getRow(0);
        for(ColumnValueRecipe<AdverseEventDme> col : recipeMap){
            j+= col.writeHeaderValues(header, j);
        }
    }

    @Override
    public void export(AdverseEventDme s) throws IOException {
        Spreadsheet.SpreadsheetRow row = spreadsheet.getRow( this.row++);

        int j=0;
        for(ColumnValueRecipe<AdverseEventDme> recipe : recipeMap){
            j+= recipe.writeValuesFor(row, j, s);
        }
    }

    @Override
    public void close() throws IOException {
        spreadsheet.close();
    }

    private static Map<Column, ColumnValueRecipe<AdverseEventDme>> DEFAULT_RECIPE_MAP;

    static{

        DEFAULT_RECIPE_MAP = new LinkedHashMap<>();

        DEFAULT_RECIPE_MAP.put(DmeDefaultColumns.SUBSTANCE_NAME, SingleColumnValueRecipe.create( DmeDefaultColumns.SUBSTANCE_NAME ,(s, cell) -> cell.writeString(s.name)));
        DEFAULT_RECIPE_MAP.put(DmeDefaultColumns.APPROVAL_ID, SingleColumnValueRecipe.create( DmeDefaultColumns.APPROVAL_ID ,(s, cell) -> cell.writeString(s.unii)));
        DEFAULT_RECIPE_MAP.put(DmeDefaultColumns.DME_REACTIONS, SingleColumnValueRecipe.create( DmeDefaultColumns.DME_REACTIONS ,(s, cell) -> cell.writeString(s.dmeReactions)));
        DEFAULT_RECIPE_MAP.put(DmeDefaultColumns.PTTERM_MEDDRA, SingleColumnValueRecipe.create( DmeDefaultColumns.PTTERM_MEDDRA ,(s, cell) -> cell.writeString(s.ptTermMeddra)));
        DEFAULT_RECIPE_MAP.put(DmeDefaultColumns.CASE_COUNT, SingleColumnValueRecipe.create( DmeDefaultColumns.CASE_COUNT ,(s, cell) -> {
            String count = (s.caseCount != null) ? Integer.toString(s.caseCount) : null;
            cell.writeString(count);
        }));
        DEFAULT_RECIPE_MAP.put(DmeDefaultColumns.DME_COUNT, SingleColumnValueRecipe.create( DmeDefaultColumns.DME_COUNT ,(s, cell) -> {
            String count = (s.dmeCount != null) ? Integer.toString(s.dmeCount) : null;
            cell.writeString(count);
        }));
        DEFAULT_RECIPE_MAP.put(DmeDefaultColumns.DME_COUNT_PERCENT, SingleColumnValueRecipe.create( DmeDefaultColumns.DME_COUNT_PERCENT ,(s, cell) -> {
            String count = (s.dmeCountPercent != null) ? Double.toString(s.dmeCountPercent) : null;
            cell.write(count);
        }));
        DEFAULT_RECIPE_MAP.put(DmeDefaultColumns.WEIGHTED_AVERAGE_PRR, SingleColumnValueRecipe.create( DmeDefaultColumns.WEIGHTED_AVERAGE_PRR ,(s, cell) -> {
            String count = (s.weightedAvgPrr != null) ? Double.toString(s.weightedAvgPrr) : null;
            cell.write(count);
        }));
    }

    /**
     * Builder class that makes a SpreadsheetExporter.  By default, the default columns are used
     * but these may be modified using the add/remove column methods.
     *
     */
    public static class Builder{
        private final List<ColumnValueRecipe<AdverseEventDme>> columns = new ArrayList<>();
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

            for(Map.Entry<Column, ColumnValueRecipe<AdverseEventDme>> entry : DEFAULT_RECIPE_MAP.entrySet()){
            	columns.add(entry.getValue());
            }
        }

        public Builder addColumn(Column column, ColumnValueRecipe<AdverseEventDme> recipe){
        	return addColumn(column.name(), recipe);
        }

        public Builder addColumn(String columnName, ColumnValueRecipe<AdverseEventDme> recipe){
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
            ListIterator<ColumnValueRecipe<AdverseEventDme>> iter = columns.listIterator();
            while(iter.hasNext()){

                ColumnValueRecipe<AdverseEventDme> oldValue = iter.next();
                ColumnValueRecipe<AdverseEventDme> newValue = oldValue.replaceColumnName(oldName, newName);
                if(oldValue != newValue){
                   iter.set(newValue);
                }
            }
            return this;
        }

        public AdverseEventDmeExporter build(){
            return new AdverseEventDmeExporter(this);
        }

        public Builder includePublicDataOnly(boolean publicOnly){
            this.publicOnly = publicOnly;
            return this;
        }

    }
}