package edu.bell.restclient.restclient.service;

import edu.bell.restclient.restclient.config.KafkaTopicConfig;
import edu.bell.restclient.restclient.dto.request.MessageDto;
import edu.bell.restclient.restclient.dto.request.OfficeInListDto;
import edu.bell.restclient.restclient.dto.request.OfficeInSaveDto;
import edu.bell.restclient.restclient.dto.request.OfficeInUpdateDto;
import edu.bell.restclient.restclient.dto.request.ResponseDto;
import edu.bell.restclient.restclient.dto.response.SuccessDto;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@Log4j2
public class KafkaOfficeService {

    private final KafkaTemplate<String, MessageDto> kafkaTemplate;
    private int messageId = 0;
    private HashMap<Integer, Object> storage = new HashMap<>();

    public KafkaOfficeService(KafkaTemplate<String, MessageDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public ResponseEntity<ResponseDto> getOfficeById(Integer id) {
        ResponseDto responseDto = new ResponseDto();
        if (storage.containsKey(id)) {
            Object o = storage.get(id);
            if (o == null) {
                responseDto.setData(getSuccessDto(id));
                return new ResponseEntity<>(responseDto, HttpStatus.OK);
            }
            responseDto.setData(o);
            return new ResponseEntity<>(responseDto,HttpStatus.OK);
        }
        MessageDto messageDto = getMessageDto(id, "get");
        kafkaTemplate.send(KafkaTopicConfig.QUEUE_OFFICE, messageDto);
        log.log(Level.INFO, "В очередь отправлен объект: " + messageDto);
        storage.put(messageId, null);
        responseDto.setData(getSuccessDto(messageDto.getId()));
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
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

    public ResponseEntity<ResponseDto> saveOffice(OfficeInSaveDto office) {
        MessageDto messageDto = getMessageDto(office, "save");
        kafkaTemplate.send(KafkaTopicConfig.QUEUE_OFFICE, messageDto);
        log.log(Level.INFO, "В очередь отправлен объект: " + messageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData("Офис успешно создан.");
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto> getListOfficeByRequest(OfficeInListDto office) {
        MessageDto messageDto = getMessageDto(office, "list");
        kafkaTemplate.send(KafkaTopicConfig.QUEUE_OFFICE, messageDto);
        storage.put(messageId, null);
        log.log(Level.INFO, "В очередь отправлен объект: " + messageDto);
        return new ResponseEntity<>(getResponseDto(messageId),HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto> updateOffice(OfficeInUpdateDto officeInUpdateDto) {
        MessageDto messageDto = getMessageDto(officeInUpdateDto, "update");
        kafkaTemplate.send(KafkaTopicConfig.QUEUE_OFFICE, messageDto);
        log.log(Level.INFO, "В очередь отправлен объект: " + messageDto);
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData("Офис успешно обновлен.");
        return new ResponseEntity<>(responseDto,HttpStatus.OK);
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

    private ResponseDto getResponseDto(int messageId) {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setData(getSuccessDto(messageId));
        return responseDto;
    }
}
