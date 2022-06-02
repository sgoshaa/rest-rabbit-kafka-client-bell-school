package edu.bell.restclient.restclient.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.bell.restclient.restclient.dto.request.OrganisationDtoRequest;
import edu.bell.restclient.restclient.dto.request.OrganizationSaveInDto;
import edu.bell.restclient.restclient.dto.response.ResponseDto;
import edu.bell.restclient.restclient.service.RabbitOrganizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/organization")
public class OrganizationRabbitController {

    private final RabbitOrganizationService rabbitOrganizationService;

    public OrganizationRabbitController(RabbitOrganizationService rabbitOrganizationService) {
        this.rabbitOrganizationService = rabbitOrganizationService;
    }

    @GetMapping("/queue/{id}")
    public ResponseDto getOrganizationByIdQueue(@PathVariable Integer id) throws JsonProcessingException {
        return rabbitOrganizationService.getOrganizationById(id);
    }

    @PostMapping("queue/list")
    public ResponseDto getListOrganizationQueue(
            @RequestBody OrganisationDtoRequest organisationDTO) throws JsonProcessingException {
        return rabbitOrganizationService.getListOrganizationByRequest(organisationDTO);
    }

    @PostMapping("save/queue")
    public ResponseDto saveOrganizationQueue(
            @Valid @RequestBody OrganizationSaveInDto organizationSaveInDto) throws JsonProcessingException {
        return rabbitOrganizationService.saveOrganization(organizationSaveInDto);
    }
}
