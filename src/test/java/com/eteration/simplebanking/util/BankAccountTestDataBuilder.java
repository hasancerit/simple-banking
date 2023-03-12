package com.eteration.simplebanking.util;

import com.eteration.simplebanking.domain.model.AccountNumber;
import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.BankAccount;
import com.eteration.simplebanking.domain.model.account.Transaction;
import com.github.curiousoddman.rgxgen.RgxGen;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BankAccountTestDataBuilder {
    private static final RgxGen rgxGen = new RgxGen(AccountNumber.VALID_REGEX);
    public static String generateValidAccountNumber() {
        return rgxGen.generate();
    }

    public static BankAccount bankAccountWithoutTransaction() {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(AccountNumber.of(generateValidAccountNumber()));
        bankAccount.setOwner(RandomStringUtils.randomAlphabetic(20));
        bankAccount.setCreatedDate(LocalDateTime.now());
        //balance -> 0
        //transactions -> Empty
        return bankAccount;
    }

    public static BankAccount bankAccountWithTransaction(
            Amount balance,
            Transaction... transactions
    ) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(AccountNumber.of(generateValidAccountNumber()));
        bankAccount.setOwner(RandomStringUtils.randomAlphabetic(20));
        bankAccount.setBalance(balance);
        bankAccount.setTransactions(new ArrayList<>(Arrays.asList(transactions)));
        bankAccount.setCreatedDate(LocalDateTime.now());
        return bankAccount;
    }
}
