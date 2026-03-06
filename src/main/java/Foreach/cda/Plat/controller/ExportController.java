package Foreach.cda.Plat.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import Foreach.cda.Plat.service.PdfGeneratorService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/api/recettes", produces = "application/json")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExportController {

    private final PdfGeneratorService pdfGeneratorService;

    @GetMapping("/{id}/export/pdf")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> exportRecetteToPdf(@PathVariable Long id) {
        byte[] pdfBytes = pdfGeneratorService.generateRecettePdf(id);

        if (pdfBytes == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "recette_" + id + ".pdf");
        headers.setContentLength(pdfBytes.length);

        return ResponseEntity.ok().headers(headers).body(pdfBytes);
    }
}
