package edu.bell.restclient.restclient.controller;

import edu.bell.restclient.restclient.dto.request.OfficeInListDto;
import edu.bell.restclient.restclient.dto.request.OfficeInSaveDto;
import edu.bell.restclient.restclient.dto.request.OfficeInUpdateDto;
import edu.bell.restclient.restclient.dto.response.ResponseDto;
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

    private final String URL = "http://localhost:8080/api/office/";
    private final RestTemplate template;

    public OfficeController(RestTemplate template) {
        this.template = template;
    }

    @GetMapping("{id}")
    public ResponseDto getOfficeById(@PathVariable Integer id) {
        return template.getForObject(URL + id, ResponseDto.class);
    }

    @PostMapping("save")
    public ResponseDto saveOffice(@Valid @RequestBody OfficeInSaveDto office) {
        ResponseEntity<ResponseDto> responseDtoResponseEntity = template
                .postForEntity(URL + "save", office, ResponseDto.class);
        return responseDtoResponseEntity.getBody();
    }

    @PostMapping("list")
    public ResponseDto getListOfficeByRequest(@Valid @RequestBody OfficeInListDto office) {
        ResponseEntity<ResponseDto> responseDtoResponseEntity = template
                .postForEntity(URL + "list", office, ResponseDto.class);
        return responseDtoResponseEntity.getBody();
    }

    @PostMapping("update")
    public ResponseDto updateOffice(@Valid @RequestBody OfficeInUpdateDto officeInUpdateDto) {
        ResponseEntity<ResponseDto> responseDtoResponseEntity = template
                .postForEntity(URL + "update", officeInUpdateDto, ResponseDto.class);
        return responseDtoResponseEntity.getBody();
    }
}
