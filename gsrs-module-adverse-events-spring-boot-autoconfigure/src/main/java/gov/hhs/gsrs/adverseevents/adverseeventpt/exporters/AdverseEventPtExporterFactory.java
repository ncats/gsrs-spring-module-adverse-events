package gov.hhs.gsrs.adverseevents.adverseeventpt.exporters;

import gov.hhs.gsrs.adverseevents.adverseeventpt.models.*;
import gov.hhs.gsrs.adverseevents.adverseeventpt.controllers.*;

import gsrs.springUtils.AutowireHelper;
import ix.ginas.exporters.*;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;;
import java.util.*;

public class AdverseEventPtExporterFactory implements ExporterFactory {

	@Autowired
	public AdverseEventPtController adverseEventController;

//	@Autowired
//	public SubstanceModuleService substanceModuleService;

	private static final Set<OutputFormat> FORMATS;

	static {
		Set<OutputFormat> set = new LinkedHashSet<>();
		set.add(SpreadsheetFormat.TSV);
		set.add(SpreadsheetFormat.CSV);
		set.add(SpreadsheetFormat.XLSX);

		FORMATS = Collections.unmodifiableSet(set);
	}

	@Override
	public Set<OutputFormat> getSupportedFormats() {
		return FORMATS;
	}

	@Override
	public boolean supports(Parameters params) {
		return params.getFormat() instanceof SpreadsheetFormat;
	}

	@Override
	public AdverseEventPtExporter createNewExporter(OutputStream out, Parameters params) throws IOException {

		if(adverseEventController==null) {
			AutowireHelper.getInstance().autowire(this);
		}

		SpreadsheetFormat format = SpreadsheetFormat.XLSX;
		Spreadsheet spreadsheet = format.createSpeadsheet(out);

		AdverseEventPtExporter.Builder builder = new AdverseEventPtExporter.Builder(spreadsheet);
		configure(builder, params);
		
		return builder.build(adverseEventController);
	}

	protected void configure(AdverseEventPtExporter.Builder builder, Parameters params) {
		builder.includePublicDataOnly(params.publicOnly());
	}

}