package com.eteration.simplebanking.util;

import com.eteration.simplebanking.domain.model.account.Transaction;
import com.eteration.simplebanking.domain.model.account.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.model.account.transaction.TransactionType;
import com.eteration.simplebanking.domain.model.account.transaction.WithdrawTransaction;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CopyObjectUtil {
    private final static Gson gson = new GsonBuilder()
            .registerTypeAdapter(Transaction.class, new TransactionDeserializer())
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .create();

    public static <T> T deepCopy(T object, Class<T> tClass) {
        return gson.fromJson(gson.toJson(object), tClass);
    }

    private static final class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        @Override
        public void write(final JsonWriter jsonWriter, final LocalDateTime localDate) throws IOException {
            jsonWriter.value(localDate.toString());
        }

        @Override
        public LocalDateTime read(final JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString());
        }
    }

    private static final class TransactionDeserializer implements JsonDeserializer<Transaction> {
        @Override
        public Transaction deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if(((JsonObject) jsonElement).get("type").getAsString().equals(TransactionType.DEPOSIT.toString())) {
                return jsonDeserializationContext.deserialize(jsonElement, DepositTransaction.class);
            }

            if(((JsonObject) jsonElement).get("type").getAsString().equals(TransactionType.WITHDRAWAL.toString())) {
                return jsonDeserializationContext.deserialize(jsonElement, WithdrawTransaction.class);
            }

            return null;
        }
    }
}
