package com.eteration.simplebanking.util;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.domain.model.account.Transaction;
import com.github.curiousoddman.rgxgen.RgxGen;
import org.apache.commons.lang.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

public class BankAccountTestDataBuilder {
    public static BankAccount bankAccountWithoutTransaction() {
        RgxGen rgxGen = new RgxGen(AccountNumber.VALID_REGEX_FOR_VALUE);
        return BankAccount.builder()
                .accountNumber(AccountNumber.of(rgxGen.generate()))
                .owner(RandomStringUtils.randomAlphabetic(20))
                .createdDate(LocalDateTime.now())
                .build();
    }

    public static BankAccount bankAccountWithTransaction(
            Amount balance,
            Transaction... transactions
    ) {
        RgxGen rgxGen = new RgxGen(AccountNumber.VALID_REGEX_FOR_VALUE);
        return BankAccount.builder()
                .accountNumber(AccountNumber.of(rgxGen.generate()))
                .owner(RandomStringUtils.randomAlphabetic(20))
                .balance(balance)
                .transactions(new ArrayList<>(Arrays.asList(transactions)))
                .createdDate(LocalDateTime.now())
                .build();
    }
}
