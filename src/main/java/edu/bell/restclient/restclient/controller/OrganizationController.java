package edu.bell.restclient.restclient.controller;

import edu.bell.restclient.restclient.dto.request.OrganisationDtoRequest;
import edu.bell.restclient.restclient.dto.request.OrganizationSaveInDto;
import edu.bell.restclient.restclient.dto.request.OrganizationUpdateInDto;
import edu.bell.restclient.restclient.dto.response.ResponseDto;
import edu.bell.restclient.restclient.service.RestOrganizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/organization/")
public class OrganizationController {

    private final RestOrganizationService restService;

    private final RestTemplate template;

    public OrganizationController(RestOrganizationService restService, RestTemplate template) {
        this.restService = restService;
        this.template = template;
    }

    @GetMapping("{id}")
    public ResponseDto getOrganizationById(@PathVariable Integer id) {
        return restService.getOrganizationById(id);
    }

    @PostMapping("list")
    public ResponseDto getListOrganization(@RequestBody OrganisationDtoRequest organisationDTO) {
        return restService.getOrganizationByRequest(organisationDTO);
    }

    @PostMapping("save")
    public ResponseDto saveOrganization(@Valid @RequestBody OrganizationSaveInDto organizationSaveInDto) {
        return restService.saveOrganization(organizationSaveInDto);
    }

    @PostMapping("update")
    public ResponseDto updateOrganization(@Valid @RequestBody OrganizationUpdateInDto organizationUpdateInDto) {
        return restService.updateOrganization(organizationUpdateInDto);
    }
}
