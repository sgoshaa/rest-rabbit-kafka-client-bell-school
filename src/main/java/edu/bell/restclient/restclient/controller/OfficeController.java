package edu.bell.restclient.restclient.controller;

import edu.bell.restclient.restclient.dto.request.OfficeInListDto;
import edu.bell.restclient.restclient.dto.request.OfficeInSaveDto;
import edu.bell.restclient.restclient.dto.request.OfficeInUpdateDto;
import edu.bell.restclient.restclient.dto.response.ResponseDto;
import edu.bell.restclient.restclient.service.RestOfficeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/office/")
public class OfficeController {

    private final RestOfficeService restOfficeService;

    public OfficeController(RestOfficeService restOfficeService) {
        this.restOfficeService = restOfficeService;
    }

    @GetMapping("{id}")
    public ResponseDto getOfficeById(@PathVariable Integer id) {
        return restOfficeService.getOfficeById(id);
    }

    @PostMapping("save")
    public ResponseDto saveOffice(@Valid @RequestBody OfficeInSaveDto office) {
        return restOfficeService.saveOffice(office);
    }

    @PostMapping("list")
    public ResponseDto getListOfficeByRequest(@Valid @RequestBody OfficeInListDto office) {
        return restOfficeService.getListOfficeByRequest(office);
    }

    @PostMapping("update")
    public ResponseDto updateOffice(@Valid @RequestBody OfficeInUpdateDto officeInUpdateDto) {
       return restOfficeService.updateOffice(officeInUpdateDto);
    }
}
