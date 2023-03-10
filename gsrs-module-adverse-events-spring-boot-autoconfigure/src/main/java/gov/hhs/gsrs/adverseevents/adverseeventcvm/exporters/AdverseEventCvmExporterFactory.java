package gov.hhs.gsrs.adverseevents.adverseeventcvm.exporters;

import gov.hhs.gsrs.adverseevents.adverseeventcvm.exporters.*;

import ix.ginas.exporters.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;;
import java.util.*;

public class AdverseEventCvmExporterFactory implements ExporterFactory {

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
	public AdverseEventCvmExporter createNewExporter(OutputStream out, Parameters params) throws IOException {

		SpreadsheetFormat format = SpreadsheetFormat.XLSX;
		Spreadsheet spreadsheet = format.createSpreadsheet(out);

		AdverseEventCvmExporter.Builder builder = new AdverseEventCvmExporter.Builder(spreadsheet);
		configure(builder, params);
		
		return builder.build();
	}

	protected void configure(AdverseEventCvmExporter.Builder builder, Parameters params) {
		builder.includePublicDataOnly(params.publicOnly());
	}

}