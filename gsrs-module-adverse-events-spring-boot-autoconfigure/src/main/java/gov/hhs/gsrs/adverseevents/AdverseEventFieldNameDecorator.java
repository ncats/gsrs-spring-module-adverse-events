package gov.hhs.gsrs.adverseevents;

import ix.core.FieldNameDecorator;

import java.util.HashMap;
import java.util.Map;

public class AdverseEventFieldNameDecorator implements FieldNameDecorator {

    private static final Map<String, String> displayNames;

    static {
        Map<String, String> m = new HashMap<>();

        m.put("root_name", "Ingredient Name");
        m.put("root_adverseEvent", "Adverse Event");
        m.put("root_routeOfAdmin", "Route of Administration");
        m.put("root_ptTermMeddra", "PT Term Meddra");
        m.put("root_dmeReactions", "DME Reactions");
        m.put("root_ptTerm", "PT Term");
		m.put("root_species", "Species");
		m.put("root_primSoc", "Prim SOC");

        displayNames = m;
    }

    @Override
    public String getDisplayName(String field) {
        String fdisp = displayNames.get(field);
        if (fdisp == null) {
            return field;
        }
        return fdisp;
    }

}
