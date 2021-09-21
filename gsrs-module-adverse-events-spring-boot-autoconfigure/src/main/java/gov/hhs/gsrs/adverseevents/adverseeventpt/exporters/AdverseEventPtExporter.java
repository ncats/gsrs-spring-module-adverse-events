package gov.hhs.gsrs.adverseevents.adverseeventpt.exporters;

import gov.hhs.gsrs.adverseevents.adverseeventpt.models.*;
import gov.hhs.gsrs.adverseevents.adverseeventpt.controllers.*;

import gsrs.module.substance.repository.SubstanceRepository;
import gsrs.springUtils.AutowireHelper;
import ix.ginas.exporters.*;
import ix.ginas.models.v1.Substance;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.*;

enum PtDefaultColumns implements Column {
    SUBSTANCE_NAME,
    APPROVAL_ID,
    SUBSTANCE_KEY,
    PT_TERM,
    PRIM_SOC,
    CASE_COUNT,
    PT_COUNT,
    PRR
}

public class AdverseEventPtExporter implements Exporter<AdverseEventPt> {

    private final Spreadsheet spreadsheet;

    private int row=1;

    private final List<ColumnValueRecipe<AdverseEventPt>> recipeMap;

    private static AdverseEventPtController adverseEventController;

    private static StringBuilder substanceApprovalIdSB;
    private static StringBuilder substanceActiveMoietySB;

    private AdverseEventPtExporter(Builder builder, AdverseEventPtController adverseEventController){

        this.adverseEventController = adverseEventController;
        substanceApprovalIdSB = new StringBuilder();
        substanceActiveMoietySB = new StringBuilder();

        this.spreadsheet = builder.spreadsheet;
        this.recipeMap = builder.columns;

        int j=0;
        Spreadsheet.SpreadsheetRow header = spreadsheet.getRow(0);
        for(ColumnValueRecipe<AdverseEventPt> col : recipeMap){
            j+= col.writeHeaderValues(header, j);
        }
    }

    @Override
    public void export(AdverseEventPt s) throws IOException {
        Spreadsheet.SpreadsheetRow row = spreadsheet.getRow( this.row++);

        int j=0;
        for(ColumnValueRecipe<AdverseEventPt> recipe : recipeMap){
            j+= recipe.writeValuesFor(row, j, s);
        }
    }

    @Override
    public void close() throws IOException {
        spreadsheet.close();
    }

    private static Map<Column, ColumnValueRecipe<AdverseEventPt>> DEFAULT_RECIPE_MAP;

    static {

        DEFAULT_RECIPE_MAP = new LinkedHashMap<>();

        DEFAULT_RECIPE_MAP.put(PtDefaultColumns.SUBSTANCE_NAME, SingleColumnValueRecipe.create(PtDefaultColumns.SUBSTANCE_NAME, (s, cell) -> cell.writeString(s.name)));
        DEFAULT_RECIPE_MAP.put(PtDefaultColumns.APPROVAL_ID, SingleColumnValueRecipe.create(PtDefaultColumns.APPROVAL_ID, (s, cell) -> cell.writeString(s.unii)));
        DEFAULT_RECIPE_MAP.put(PtDefaultColumns.SUBSTANCE_KEY, SingleColumnValueRecipe.create(PtDefaultColumns.SUBSTANCE_KEY, (s, cell) -> cell.writeString(s.substanceKey)));
        DEFAULT_RECIPE_MAP.put(PtDefaultColumns.PT_TERM, SingleColumnValueRecipe.create(PtDefaultColumns.PT_TERM, (s, cell) -> cell.writeString(s.adverseEvent)));
        DEFAULT_RECIPE_MAP.put(PtDefaultColumns.PRIM_SOC, SingleColumnValueRecipe.create(PtDefaultColumns.PRIM_SOC, (s, cell) -> cell.writeString(s.primSoc)));
        DEFAULT_RECIPE_MAP.put(PtDefaultColumns.CASE_COUNT, SingleColumnValueRecipe.create(PtDefaultColumns.CASE_COUNT, (s, cell) -> {
            String count = (s.caseCount != null) ? Integer.toString(s.caseCount) : null;
            cell.writeString(count);
        }));
        DEFAULT_RECIPE_MAP.put(PtDefaultColumns.PT_COUNT, SingleColumnValueRecipe.create(PtDefaultColumns.PT_COUNT, (s, cell) -> {
            String count = (s.ptCount != null) ? Integer.toString(s.ptCount) : null;
            cell.writeString(count);
        }));
        DEFAULT_RECIPE_MAP.put(PtDefaultColumns.PRR, SingleColumnValueRecipe.create(PtDefaultColumns.PRR, (s, cell) -> {
            String count = (s.prr != null) ? Double.toString(s.prr) : null;
            cell.write(count);
        }));
    }

    /**
     * Builder class that makes a SpreadsheetExporter.  By default, the default columns are used
     * but these may be modified using the add/remove column methods.
     *
     */
    public static class Builder{
        private final List<ColumnValueRecipe<AdverseEventPt>> columns = new ArrayList<>();
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

            for(Map.Entry<Column, ColumnValueRecipe<AdverseEventPt>> entry : DEFAULT_RECIPE_MAP.entrySet()){
            	columns.add(entry.getValue());
            }
        }

        public Builder addColumn(Column column, ColumnValueRecipe<AdverseEventPt> recipe){
        	return addColumn(column.name(), recipe);
        }

        public Builder addColumn(String columnName, ColumnValueRecipe<AdverseEventPt> recipe){
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
            ListIterator<ColumnValueRecipe<AdverseEventPt>> iter = columns.listIterator();
            while(iter.hasNext()){

                ColumnValueRecipe<AdverseEventPt> oldValue = iter.next();
                ColumnValueRecipe<AdverseEventPt> newValue = oldValue.replaceColumnName(oldName, newName);
                if(oldValue != newValue){
                   iter.set(newValue);
                }
            }
            return this;
        }

        public AdverseEventPtExporter build(AdverseEventPtController adverseEventController){
            return new AdverseEventPtExporter(this, adverseEventController);
        }

        public Builder includePublicDataOnly(boolean publicOnly){
            this.publicOnly = publicOnly;
            return this;
        }

    }
}