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
    SUBSTANCE_KEY,
    PT_TERM,
    PRIM_SOC
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

    static{

        DEFAULT_RECIPE_MAP = new LinkedHashMap<>();

        /*
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.SUBSTANCE_NAME, SingleColumnValueRecipe.create( AppDefaultColumns.SUBSTANCE_NAME ,(s, cell) ->{
        	StringBuilder sb = getIngredientDetails(s, AppDefaultColumns.SUBSTANCE_NAME);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.UNII, SingleColumnValueRecipe.create( AppDefaultColumns.UNII ,(s, cell) ->{
            StringBuilder sb = getIngredientDetails(s, AppDefaultColumns.UNII);
            cell.writeString(sb.toString());
        }));
        */

        /*
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.SUBSTANCE_NAME, SingleColumnValueRecipe.create(AppDefaultColumns.SUBSTANCE_NAME ,(s, cell) ->{
            StringBuilder sb = getIngredientName(s);
      //      StringBuilder sb1 = getIngredientName((Application) s);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.APPROVAL_ID, SingleColumnValueRecipe.create(AppDefaultColumns.APPROVAL_ID ,(s, cell) ->{
         cell.writeString(substanceApprovalIdSB.toString());
       }));

        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.SUBSTANCE_KEY, SingleColumnValueRecipe.create( AppDefaultColumns.SUBSTANCE_KEY ,(s, cell) ->{
            StringBuilder sb = getIngredientDetails(s, AppDefaultColumns.SUBSTANCE_KEY);
            cell.writeString(sb.toString());
        }));

        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.INGREDIENT_TYPE, SingleColumnValueRecipe.create( AppDefaultColumns.INGREDIENT_TYPE ,(s, cell) ->{
            StringBuilder sb = getIngredientDetails(s, AppDefaultColumns.INGREDIENT_TYPE);
            cell.writeString(sb.toString());
        }));
        */

        DEFAULT_RECIPE_MAP.put(PtDefaultColumns.SUBSTANCE_NAME, SingleColumnValueRecipe.create( PtDefaultColumns.SUBSTANCE_NAME ,(s, cell) -> cell.writeString(s.name)));
        DEFAULT_RECIPE_MAP.put(PtDefaultColumns.SUBSTANCE_KEY, SingleColumnValueRecipe.create( PtDefaultColumns.SUBSTANCE_KEY ,(s, cell) -> cell.writeString(s.substanceKey)));
        DEFAULT_RECIPE_MAP.put(PtDefaultColumns.PT_TERM, SingleColumnValueRecipe.create( PtDefaultColumns.PT_TERM ,(s, cell) -> cell.writeString(s.adverseEvent)));
        DEFAULT_RECIPE_MAP.put(PtDefaultColumns.PRIM_SOC, SingleColumnValueRecipe.create( PtDefaultColumns.PRIM_SOC ,(s, cell) -> cell.writeString(s.primSoc)));

        /*
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.APP_TYPE, SingleColumnValueRecipe.create( AppDefaultColumns.APP_TYPE ,(s, cell) -> cell.writeString(s.appType)));
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.APP_NUMBER, SingleColumnValueRecipe.create( AppDefaultColumns.APP_NUMBER ,(s, cell) -> cell.writeString(s.appNumber)));
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.PRODUCT_NAME, SingleColumnValueRecipe.create( AppDefaultColumns.PRODUCT_NAME ,(s, cell) ->{
            StringBuilder sb = getProductDetails(s, AppDefaultColumns.PRODUCT_NAME);
            cell.writeString(sb.toString());
        }));
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.SPONSOR_NAME, SingleColumnValueRecipe.create( AppDefaultColumns.SPONSOR_NAME ,(s, cell) -> cell.writeString(s.sponsorName)));
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.APP_STATUS, SingleColumnValueRecipe.create( AppDefaultColumns.APP_STATUS ,(s, cell) -> cell.writeString(s.status)));
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.APP_SUB_TYPE, SingleColumnValueRecipe.create( AppDefaultColumns.APP_SUB_TYPE ,(s, cell) -> cell.writeString(s.appSubType)));
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.DIVISION_CLASS_DESC, SingleColumnValueRecipe.create( AppDefaultColumns.DIVISION_CLASS_DESC ,(s, cell) -> cell.writeString(s.divisionClassDesc)));
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.PROVENANCE, SingleColumnValueRecipe.create( AppDefaultColumns.PROVENANCE ,(s, cell) -> cell.writeString(s.provenance)));
        DEFAULT_RECIPE_MAP.put(AppDefaultColumns.INDICATION, SingleColumnValueRecipe.create( AppDefaultColumns.INDICATION ,(s, cell) ->{
            StringBuilder sb = getIndicationDetails(s);
            cell.writeString(sb.toString());
        }));

         */
    }

    /*
    private static StringBuilder getProductDetails(AdverseEventPt s, AppDefaultColumns fieldName) {
        StringBuilder sb = new StringBuilder();

        if(s.applicationProductList.size() > 0){
            List<ApplicationProduct> prodList = s.applicationProductList;

            for(ApplicationProduct prod : prodList){

                for (ApplicationProductName prodName : prod.applicationProductNameList) {
                    if(sb.length()!=0){
                        sb.append("|");
                    }
                    switch (fieldName) {
                        case PRODUCT_NAME:
                            sb.append((prodName.productName != null) ? prodName.productName : "(No Product Name)");
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        return sb;
    }

    private static StringBuilder getIngredientName(AdverseEventPt s) {
        StringBuilder sb = new StringBuilder();
        substanceApprovalIdSB.setLength(0);
        String substanceName = null;
        String approvalId = null;

        try {
            if (s.applicationProductList.size() > 0) {
                List<ApplicationProduct> prodList = s.applicationProductList;

                for (ApplicationProduct prod : prodList) {

                    for (ApplicationIngredient ingred : prod.applicationIngredientList) {
                        if (sb.length() != 0) {
                            sb.append("|");
                        }

                        if (substanceApprovalIdSB.length() != 0) {
                            substanceApprovalIdSB.append("|");
                        }
                        if (substanceActiveMoietySB.length() != 0) {
                            substanceActiveMoietySB.append("|");
                        }


                        // Get Substance Details
                        if (ingred.substanceKey != null) {
                            if (adverseEventController != null) {
                                JsonNode subJson = adverseEventController.injectSubstanceBySubstanceKey(ingred.substanceKey);

                                if (subJson != null) {
                                    substanceName = subJson.path("_name").textValue();
                                    approvalId = subJson.path("approvalID").textValue();

                                    // Get Substance Name
                                    sb.append((ingred.substanceKey != null) ? substanceName : "(No Ingredient Name)");

                                    // Storing in static variable so do not have to call the same Substance API twice just to get
                                    // approval Id.
                                    substanceApprovalIdSB.append((ingred.substanceKey != null) ? approvalId : "(No Approval ID)");
                                }
                            }
                        } else {
                            sb.append("(No Ingredient Name)");
                            substanceApprovalIdSB.append("No Approval ID");
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb;
    }

    private static StringBuilder getIngredientDetails(AdverseEventPt s, AppDefaultColumns fieldName) {
        StringBuilder sb = new StringBuilder();

        try {
            if (s.applicationProductList.size() > 0) {
                List<ApplicationProduct> prodList = s.applicationProductList;

                for (ApplicationProduct prod : prodList) {

                    for (ApplicationIngredient ingred : prod.applicationIngredientList) {
                        if (sb.length() != 0) {
                            sb.append("|");
                        }

                        try {
                            switch (fieldName) {
                                case SUBSTANCE_KEY:
                                    sb.append((ingred.substanceKey != null) ? ingred.substanceKey : "(No Substance Key)");
                                    break;
                                case INGREDIENT_TYPE:
                                    sb.append((ingred.ingredientType != null) ? ingred.ingredientType : "(No Ingredient Type)");
                                    break;
                                default:
                                    break;
                            }

                        } catch (Exception ex) {
                            System.out.println("*** Error Occured in ApplicationExporter.java for Substance Code : " + ingred.substanceKey);
                            ex.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return sb;
    }
    */

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