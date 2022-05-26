package edu.bell.restclient.restclient.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bell.restclient.restclient.dto.response.OfficeOutDto;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.logging.log4j.Level;

import java.util.Map;

@Log4j2
public class CustomDeserializer implements Deserializer<MessageDto> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public MessageDto deserialize(String topic, byte[] data) {
        try {
            if (data == null) {
                log.log(Level.INFO, "Null received at deserializing");
                return null;
            }
            log.log(Level.INFO, "Deserializing...");
            return objectMapper.readValue(new String(data, "UTF-8"), MessageDto.class);
        } catch (Exception e) {
            throw new SerializationException("Error when deserializing byte[] to MessageDto");
        }
    }

    @Override
    public MessageDto deserialize(String topic, Headers headers, byte[] data) {
        return Deserializer.super.deserialize(topic, headers, data);
    }

    @Override
    public void close() {
        Deserializer.super.close();
    }
}
