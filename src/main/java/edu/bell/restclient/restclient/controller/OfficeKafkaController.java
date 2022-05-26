package edu.bell.restclient.restclient.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bell.restclient.restclient.config.KafkaTopicConfig;
import edu.bell.restclient.restclient.config.MessageDto;
import edu.bell.restclient.restclient.dto.response.ResponseDto;
import edu.bell.restclient.restclient.dto.response.SuccessDto;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RestController
@RequestMapping("/api/kafka/office/")
@Log4j2
public class OfficeKafkaController {

    private final String URL = "http://localhost:8080/api/office/";
    private final RestTemplate template;
    private final KafkaTemplate<String, MessageDto> kafkaTemplate;
    private int messageId = 0;
    private final ObjectMapper objectMapper;
    private HashMap<Integer, Object> storage = new HashMap<>();

    public OfficeKafkaController(RestTemplate template, KafkaTemplate<String, MessageDto> kafkaTemplate, ObjectMapper objectMapper) {
        this.template = template;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @GetMapping("{id}")
    public ResponseDto getOfficeById(@PathVariable Integer id) {
        ResponseDto responseDto = new ResponseDto();
        if (storage.containsKey(id)) {
            Object o = storage.get(id);
            if (o == null) {
                responseDto.setData(getSuccessDto(id));
                return responseDto;
            }
            responseDto.setData(o);
            return responseDto;
        }
        messageId++;
        MessageDto messageDto = new MessageDto();
        messageDto.setId(messageId);
        messageDto.setBody(id);
        messageDto.setMethod("get");

        kafkaTemplate.send(KafkaTopicConfig.QUEUE_OFFICE, messageDto);
        log.log(Level.INFO, "В очередь отправлен объект: " + messageDto);
        storage.put(messageId, null);
        responseDto.setData(getSuccessDto(messageId));
        return responseDto;
    }

    private SuccessDto getSuccessDto(int messageId) {
        SuccessDto successDto = new SuccessDto();
        successDto.setResult("Ожидай результат по запросу:http://localhost:8081/api/kafka/office/" + messageId);
        return successDto;
    }

    @KafkaListener(groupId = "groupId", topics = KafkaTopicConfig.QUEUE_RETURN_OFFICE)
    public void getOfficeFromQueue(String msg) throws JsonProcessingException {
        log.log(Level.INFO, "Из очереди получен объект:" + msg);
        MessageDto messageFromQueue = objectMapper.readValue(msg, MessageDto.class);
        Object body = messageFromQueue.getBody();
        storage.put(messageFromQueue.getId(), body);
        log.log(Level.INFO, "В хранилище добавлен объект: " + body);
    }


}
