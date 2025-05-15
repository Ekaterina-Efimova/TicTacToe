package exercise.exercise.datasource.mapper;

import exercise.exercise.datasource.model.TicTacToeBoardEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Конвертер для преобразования сущности доски игры в крестики-нолики (TicTacToeBoardEntity)
 * в строку JSON и обратно. Используется для сохранения состояния доски игры в базе данных.
 */
@Converter
public class TicTacToeBoardMapperData implements AttributeConverter<TicTacToeBoardEntity, String> {
    /**
     * Преобразует сущность доски игры (TicTacToeBoardEntity) в строку JSON для сохранения в базе данных.
     *
     * @param ticTacToeBoardEntity сущность доски игры для преобразования
     * @return строка JSON, представляющая состояние доски игры
     * @throws RuntimeException если произошла ошибка при преобразовании в JSON
     */
    @Override
    public String convertToDatabaseColumn(TicTacToeBoardEntity ticTacToeBoardEntity) {
        try {
            return new ObjectMapper().writeValueAsString(ticTacToeBoardEntity.getBoard());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Преобразует строку JSON из базы данных обратно в сущность доски игры (TicTacToeBoardEntity).
     *
     * @param ticTacToeBoardEntity строка JSON, представляющая состояние доски игры
     * @return сущность доски игры
     * @throws RuntimeException если произошла ошибка при преобразовании из JSON
     */
    @Override
    public TicTacToeBoardEntity convertToEntityAttribute(String ticTacToeBoardEntity) {
        try {
            return new TicTacToeBoardEntity(
                    new ObjectMapper().readValue(ticTacToeBoardEntity, int[][].class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}