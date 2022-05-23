package edu.bell.restclient.restclient.controller;

import edu.bell.restclient.restclient.dto.request.OrganisationDtoRequest;
import edu.bell.restclient.restclient.dto.request.OrganizationSaveInDto;
import edu.bell.restclient.restclient.dto.request.OrganizationUpdateInDto;
import edu.bell.restclient.restclient.dto.response.OrganizationListOut;
import edu.bell.restclient.restclient.dto.response.OrganizationOutDto;
import edu.bell.restclient.restclient.dto.response.ResponseDto;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/organization")
public class OrganizationController {

    private final String URL = "http://localhost:8080/api/organization/";

    private final RestTemplate template;

    public OrganizationController(RestTemplate template) {
        this.template = template;
    }

    @GetMapping("{id}")
    public ResponseDto getOrganizationById(@PathVariable int id) {
        ResponseDto forObject = template.getForObject(URL + id, ResponseDto.class);
        return forObject;
    }

    @PostMapping("list")
    public ResponseDto getListOrganization(@RequestBody OrganisationDtoRequest organisationDTO) {
        ResponseEntity<ResponseDto> responseDto
                = template.postForEntity(URL + "list", organisationDTO, ResponseDto.class);
        return responseDto.getBody();
    }

    @PostMapping("save")
    public ResponseDto saveOrganization(@Valid @RequestBody OrganizationSaveInDto organizationSaveInDto) {
        ResponseEntity<ResponseDto> responseDtoResponseEntity
                = template.postForEntity(URL + "save", organizationSaveInDto, ResponseDto.class);
        return responseDtoResponseEntity.getBody();
    }

    @PostMapping("update")
    public ResponseDto updateOrganization(@Valid @RequestBody OrganizationUpdateInDto organizationUpdateInDto) {
        ResponseEntity<ResponseDto> responseDtoResponseEntity
                = template.postForEntity(URL + "update", organizationUpdateInDto, ResponseDto.class);
        return responseDtoResponseEntity.getBody();
    }

}
