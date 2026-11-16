package gov.hhs.gsrs.adverseevents.adverseeventdme.controllers;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import gov.hhs.gsrs.adverseevents.adverseeventdme.models.*;
import gov.hhs.gsrs.adverseevents.adverseeventdme.services.*;
import gov.hhs.gsrs.adverseevents.adverseeventdme.searcher.*;
import gov.hhs.gsrs.adverseevents.AdverseEventDataSourceConfig;

import gsrs.controller.*;
import gsrs.controller.hateoas.HttpRequestHolder;
import gov.nih.ncats.common.util.Unchecked;
import gsrs.autoconfigure.GsrsExportConfiguration;
import gsrs.controller.*;
import gsrs.legacy.LegacyGsrsSearchService;
import gsrs.repository.ETagRepository;
import gsrs.repository.EditRepository;
import gsrs.service.EtagExportGenerator;
import gsrs.service.ExportService;
import gsrs.service.GsrsEntityService;

import ix.core.models.ETag;
import ix.core.search.SearchResult;
import ix.ginas.exporters.ExportMetaData;
import ix.ginas.exporters.ExportProcess;
import ix.ginas.exporters.Exporter;
import ix.ginas.exporters.ExporterFactory;
import ix.ginas.models.v1.Substance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;

@ExposesResourceFor(AdverseEventDme.class)
@GsrsRestApiController(context = AdverseEventDmeEntityService.CONTEXT, idHelper = IdHelpers.STRING_NO_WHITESPACE)
public class AdverseEventDmeController extends EtagLegacySearchEntityController<AdverseEventDmeController, AdverseEventDme, String> {


    @Autowired
    private ETagRepository eTagRepository;

    @PersistenceContext(unitName =  AdverseEventDataSourceConfig.NAME_ENTITY_MANAGER)
    private EntityManager entityManager;

    @Autowired
    private GsrsControllerConfiguration gsrsControllerConfiguration;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private ExportService exportService;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private GsrsExportConfiguration gsrsExportConfiguration;


    @Autowired
    private AdverseEventDmeEntityService adverseEventEntityService;

    @Autowired
    private LegacyAdverseEventDmeSearcher legacyAdverseEventDmeSearcher;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public GsrsEntityService<AdverseEventDme, String> getEntityService() {
        return adverseEventEntityService;
    }

    @Override
    protected LegacyGsrsSearchService<AdverseEventDme> getlegacyGsrsSearchService() {
        return legacyAdverseEventDmeSearcher;
    }

    @Override
    protected Stream<AdverseEventDme> filterStream(Stream<AdverseEventDme> stream, boolean publicOnly, Map<String, String> parameters) {
        return stream;
    }

    public Optional<AdverseEventDme> injectSubstanceDetails(Optional<AdverseEventDme> application) {

        try {
            if (application.isPresent()) {
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return application;
    }

}
