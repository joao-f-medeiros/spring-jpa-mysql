package com.javaee.springjpamysql.controllers.v1;

import com.github.pjfanning.xlsx.StreamingReader;
import com.javaee.springjpamysql.api.v1.model.GarageDTO;
import com.javaee.springjpamysql.services.GarageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(GarageController.BASE_URL)
public class GarageController {

	public static final String BASE_URL = "/api/v1/garages";

    private final GarageService garageService;

    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<GarageDTO> getAll(){
        return garageService.getAll();
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public GarageDTO getById(@PathVariable Long id){
        return garageService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GarageDTO createNew(@RequestBody GarageDTO garage){
        return garageService.createNew(garage);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public GarageDTO update(@PathVariable Long id, @RequestBody GarageDTO garage){
        return garageService.save(id, garage);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long id){
    	garageService.deleteById(id);
    }

    @PostMapping("/upload")
    public String handleFileUpload(final HttpServletRequest request) {

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        if (!isMultipart) {
            // consider raising an error here if desired
        }

        FileItemIterator iter;
        InputStream fileStream = null;
        try {
            ServletFileUpload upload = new ServletFileUpload();
            iter = upload.getItemIterator(request);

            while (iter.hasNext()) {
                FileItemStream item = iter.next();
                if (!item.isFormField()) {
                    try (InputStream is = item.openStream(); Workbook workbook = StreamingReader.builder().rowCacheSize(100).bufferSize(4096).open(is)) {
                        workbook.forEach(sheet -> sheet.forEach(row -> {
                            if (row.getRowNum() >= 2) {
                                String value = CellUtil.getCell(row, 1).getStringCellValue();
                                log.info(value);
                            }
                        }));
                    } catch (IOException e) {
                        log.error(e.getMessage());
                    }
                }
            }
        } catch (FileUploadException | IOException e) {
            e.printStackTrace();
        }

        if (fileStream != null) {
            // a file has been sent in the http request
            // pass the fileStream to a method on the storageService so it can be persisted
            // note the storageService will need to be modified to receive and process the fileStream
//            storageService.store(fileStream, name);
        }

        return "success";
    }
}
