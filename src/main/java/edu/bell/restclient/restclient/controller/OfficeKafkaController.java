package edu.bell.restclient.restclient.controller;

import edu.bell.restclient.restclient.config.KafkaTopicConfig;
import edu.bell.restclient.restclient.dto.request.MessageDto;
import edu.bell.restclient.restclient.dto.request.OfficeInListDto;
import edu.bell.restclient.restclient.dto.request.OfficeInSaveDto;
import edu.bell.restclient.restclient.dto.request.OfficeInUpdateDto;
import edu.bell.restclient.restclient.dto.response.ResponseDto;
import edu.bell.restclient.restclient.dto.response.SuccessDto;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/api/kafka/office/")
@Log4j2
public class OfficeKafkaController {

    private final KafkaTemplate<String, MessageDto> kafkaTemplate;
    private int messageId = 0;
    private HashMap<Integer, Object> storage = new HashMap<>();

    public OfficeKafkaController(KafkaTemplate<String, MessageDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
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
        MessageDto messageDto = getMessageDto(id, "get");
        kafkaTemplate.send(KafkaTopicConfig.QUEUE_OFFICE, messageDto);
        log.log(Level.INFO, "В очередь отправлен объект: " + messageDto);
        storage.put(messageId, null);
        responseDto.setData(getSuccessDto(messageDto.getId()));
        return responseDto;
    }

    @PostMapping("save")
    public ResponseDto saveOffice(@Valid @RequestBody OfficeInSaveDto office) {
        MessageDto messageDto = getMessageDto(office, "save");
        kafkaTemplate.send(KafkaTopicConfig.QUEUE_OFFICE, messageDto);
        log.log(Level.INFO, "В очередь отправлен объект: " + messageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData("Офис успешно создан.");
        return responseDto;
    }

    @PostMapping("list")
    public ResponseDto getListOfficesByRequest(@Valid @RequestBody OfficeInListDto office) {
        MessageDto messageDto = getMessageDto(office, "list");
        kafkaTemplate.send(KafkaTopicConfig.QUEUE_OFFICE, messageDto);
        storage.put(messageId, null);
        log.log(Level.INFO, "В очередь отправлен объект: " + messageDto);
        return getResponseDto(messageId);
    }

    @PostMapping("update")
    public ResponseDto updateOffice(@Valid @RequestBody OfficeInUpdateDto officeInUpdateDto) {
        MessageDto messageDto = getMessageDto(officeInUpdateDto, "update");
        kafkaTemplate.send(KafkaTopicConfig.QUEUE_OFFICE, messageDto);
        log.log(Level.INFO, "В очередь отправлен объект: " + messageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData("Офис успешно обновлен.");
        return responseDto;
    }

    private ResponseDto getResponseDto(int messageId) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(getSuccessDto(messageId));
        return responseDto;
    }

    /**
     * Метод слушает очередь в Kafka и загружает сообщения
     *
     * @param messageFromQueue сообщение из очереди
     */
    @KafkaListener(groupId = "groupId",
            topics = KafkaTopicConfig.QUEUE_RETURN_OFFICE,
            containerFactory = "listenerContainerFactory")
    public void getOfficeFromQueue(MessageDto messageFromQueue) {
        log.log(Level.INFO, "Из очереди получен объект:" + messageFromQueue);
        Object body = messageFromQueue.getBody();
        storage.put(messageFromQueue.getId(), body);
        log.log(Level.INFO, "В хранилище добавлен объект: " + body);
    }

    private SuccessDto getSuccessDto(int messageId) {
        SuccessDto successDto = new SuccessDto();
        successDto.setResult("Ожидай результат по запросу:http://localhost:8081/api/kafka/office/" + messageId);
        return successDto;
    }

    private MessageDto getMessageDto(Object body, String method) {
        messageId++;
        MessageDto messageDto = new MessageDto();
        messageDto.setId(messageId);
        messageDto.setBody(body);
        messageDto.setMethod(method);
        return messageDto;
    }
}
