package edu.bell.restclient.restclient.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bell.restclient.restclient.config.RabbitMQConfig;
import edu.bell.restclient.restclient.dto.request.MessageDto;
import edu.bell.restclient.restclient.dto.request.OrganisationDtoRequest;
import edu.bell.restclient.restclient.dto.request.OrganizationSaveInDto;
import edu.bell.restclient.restclient.dto.response.ResponseDto;
import edu.bell.restclient.restclient.dto.response.SuccessDto;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Log4j2
public class RabbitOrganizationService {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private int messageId;
    private HashMap<Integer, Object> storage = new HashMap<>();

    public RabbitOrganizationService(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public ResponseDto getOrganizationById(Integer id) throws JsonProcessingException {
        ResponseDto responseDto = new ResponseDto();
        if (storage.containsKey(id) && storage.get(id) != null) {
            responseDto.setData(storage.get(id));
            return responseDto;
        } else if (storage.containsKey(id) && storage.get(id) == null) {
            SuccessDto successDto = getSuccessDto(id);
            responseDto.setData(successDto);
            return responseDto;
        }

        MessageDto message = getMessageDto("get", id);
        storage.put(messageId, null);

        rabbitTemplate.convertAndSend(RabbitMQConfig.NAME_QUEUE_GET_ORGANIZATION
                , objectMapper.writeValueAsString(message));

        SuccessDto successDto = getSuccessDto(messageId);
        responseDto.setData(successDto);
        return responseDto;
    }

    private SuccessDto getSuccessDto(int messageId) {
        SuccessDto successDto = new SuccessDto();
        successDto.setResult("Жди ответ по запросу /api/organization/queue/" + messageId);
        return successDto;
    }

    private MessageDto getMessageDto(String method, Object body) {
        messageId++;
        MessageDto message = new MessageDto();
        message.setId(messageId);
        message.setMethod(method);
        message.setBody(body);
        return message;
    }

    public ResponseDto getListOrganizationByRequest(
            OrganisationDtoRequest organisationDTO) throws JsonProcessingException {
        MessageDto message = getMessageDto("list", organisationDTO);
        storage.put(messageId, null);

        rabbitTemplate.convertAndSend(RabbitMQConfig.NAME_QUEUE_GET_ORGANIZATION
                , objectMapper.writeValueAsString(message));

        ResponseDto responseDto = new ResponseDto();
        SuccessDto successDto = getSuccessDto(messageId);
        responseDto.setData(successDto);
        return responseDto;
    }

    public ResponseDto saveOrganization(OrganizationSaveInDto organizationSaveInDto) throws JsonProcessingException {
        rabbitTemplate.convertAndSend(RabbitMQConfig.NAME_QUEUE_SAVE_ORGANIZATION
                , objectMapper.writeValueAsString(organizationSaveInDto));
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(new SuccessDto());
        return responseDto;
    }

    /**
     * Метод слушает очередь Rabbit и загружает оттуда сообщения
     *
     * @param message сообщение
     * @throws JsonProcessingException выбрасываемое исключение
     */
    @RabbitListener(queues = RabbitMQConfig.NAME_QUEUE_RETURN_ORGANIZATION)
    public void getOrganizationFromQueue(String message) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(message);
        Object organizationOutDto = objectMapper.treeToValue(jsonNode.get("body"), Object.class);
        storage.put(jsonNode.get("id").asInt(), organizationOutDto);
        log.log(Level.INFO, "Из очереди добавлен объект: " + organizationOutDto.toString());
    }
}
