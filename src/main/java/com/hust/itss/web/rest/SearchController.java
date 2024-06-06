package com.hust.itss.web.rest;

import com.hust.itss.repository.DocumentRepository;
import com.hust.itss.service.DocumentService;
import com.hust.itss.service.MajorService;
import com.hust.itss.service.SubjectService;
import com.hust.itss.service.dto.DocumentDTO;
import com.hust.itss.service.dto.SubjectDTO;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class SearchController {

    private final Logger log = LoggerFactory.getLogger(SearchController.class);
    private MajorService majorService;
    private final DocumentService documentService;
    private final SubjectService subjectService;

    public SearchController(MajorService majorService, DocumentService documentService, SubjectService subjectService) {
        this.majorService = majorService;
        this.documentService = documentService;
        this.subjectService = subjectService;
    }

    @GetMapping("/search/subject")
    public ResponseEntity<List<SubjectDTO>> searchSubject(@RequestParam String query) {
        log.debug("REST request to search Subjects for query {}", query);
        List<SubjectDTO> result = subjectService.search(query);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/search/major/{majorId}")
    public ResponseEntity<List<DocumentDTO>> getDocumentByMajorId(@PathVariable("majorId") Long majorId) {
        log.debug("REST request to get Document by major id : {}", majorId);
        List<DocumentDTO> documentDTO = documentService.getDocumentByMajorId(majorId);
        return ResponseEntity.ok().body(documentDTO);
    }

    @GetMapping("/search/keyword/{searchTerm}")
    public ResponseEntity<List<DocumentDTO>> getDocumentBySearchTerm(@PathVariable("searchTerm") String searchTerm) {
        log.debug("REST request to get Document by search term : {}", searchTerm);
        List<DocumentDTO> documentDTO = documentService.getDocumentBySearchTerm(searchTerm);
        return ResponseEntity.ok().body(documentDTO);
    }
}
