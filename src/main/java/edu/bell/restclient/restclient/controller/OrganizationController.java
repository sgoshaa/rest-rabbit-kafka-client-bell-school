package edu.bell.restclient.restclient.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bell.restclient.restclient.config.Message;
import edu.bell.restclient.restclient.config.RabbitMQConfig;
import edu.bell.restclient.restclient.dto.request.OrganisationDtoRequest;
import edu.bell.restclient.restclient.dto.request.OrganizationSaveInDto;
import edu.bell.restclient.restclient.dto.request.OrganizationUpdateInDto;
import edu.bell.restclient.restclient.dto.response.OrganizationOutDto;
import edu.bell.restclient.restclient.dto.response.ResponseDto;
import edu.bell.restclient.restclient.dto.response.SuccessDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/organization")
public class OrganizationController {

    private final String URL = "http://localhost:8080/api/organization/";

    private final RestTemplate template;

    private final RabbitTemplate rabbitTemplate;

    private final ObjectMapper objectMapper;

    private int messageId;

    private HashMap<Integer, Object> storage;

    private Logger logger = Logger.getLogger(OrganizationController.class.getName());

    public OrganizationController(RestTemplate template, RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.template = template;
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        storage = new HashMap<>();
    }

    @GetMapping("{id}")
    public ResponseDto getOrganizationById(@PathVariable int id) {
        ResponseDto forObject = template.getForObject(URL + id, ResponseDto.class);
        return forObject;
    }

    @GetMapping("/queue/{id}")
    public ResponseDto getOrganizationByIdQueue(@PathVariable int id) throws JsonProcessingException {
        ResponseDto responseDto = new ResponseDto();
        if (storage.containsKey(id) && storage.get(id) != null) {
            responseDto.setData(storage.get(id));
            return responseDto;
        }else if (storage.containsKey(id) && storage.get(id) == null){
            SuccessDto successDto = getSuccessDto(id);
            responseDto.setData(successDto);
            return responseDto;
        }

        messageId++;
        storage.put(messageId, null);
        Message message = new Message();
        message.setId(messageId);
        message.setMethod("get");
        message.setBody(id);

        rabbitTemplate.convertAndSend(RabbitMQConfig.NAME_QUEUE_GET_ORGANIZATION, objectMapper.writeValueAsString(message));

        SuccessDto successDto = getSuccessDto(messageId);
        responseDto.setData(successDto);
        return responseDto;
    }

    @PostMapping("list")
    public ResponseDto getListOrganization(@RequestBody OrganisationDtoRequest organisationDTO) {
        ResponseEntity<ResponseDto> responseDto
                = template.postForEntity(URL + "list", organisationDTO, ResponseDto.class);
        return responseDto.getBody();
    }

    @PostMapping("queue/list")
    public ResponseDto getListOrganizationQueue(@RequestBody OrganisationDtoRequest organisationDTO) throws JsonProcessingException {
        messageId++;
        storage.put(messageId,null);
        Message message = new Message();
        message.setId(messageId);
        message.setMethod("list");
        message.setBody(organisationDTO);

        rabbitTemplate.convertAndSend(RabbitMQConfig.NAME_QUEUE_GET_ORGANIZATION, objectMapper.writeValueAsString(message));

        ResponseDto responseDto = new ResponseDto();
        SuccessDto successDto = getSuccessDto(messageId);
        responseDto.setData(successDto);
        return responseDto;
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

    @PostMapping("save/queue")
    public ResponseDto saveOrganizationQueue(@Valid @RequestBody OrganizationSaveInDto organizationSaveInDto) throws JsonProcessingException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.NAME_QUEUE_SAVE_ORGANIZATION, objectMapper.writeValueAsString(organizationSaveInDto));
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(new SuccessDto());
        return responseDto;
    }

    @RabbitListener(queues = RabbitMQConfig.NAME_QUEUE_RETURN_ORGANIZATION)
    public void getOrganizationFromQueue(String message) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(message);
        Object organizationOutDto = objectMapper.treeToValue(jsonNode.get("body"), Object.class);
        storage.put(jsonNode.get("id").asInt(), organizationOutDto);
        logger.log(Level.INFO, "Из очереди добавлен объект: " + organizationOutDto.toString());
    }

    private SuccessDto getSuccessDto(int messageId) {
        SuccessDto successDto = new SuccessDto();
        successDto.setResult("Жди ответ по запросу /api/organization/queue/" + messageId);
        return successDto;
    }
}
