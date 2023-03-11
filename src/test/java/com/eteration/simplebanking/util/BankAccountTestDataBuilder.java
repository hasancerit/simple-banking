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
        RgxGen rgxGen = new RgxGen(AccountNumber.VALID_REGEX);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(AccountNumber.of(rgxGen.generate()));
        bankAccount.setOwner(RandomStringUtils.randomAlphabetic(20));
        bankAccount.setCreatedDate(LocalDateTime.now());
        return bankAccount;
    }

    public static BankAccount bankAccountWithTransaction(
            Amount balance,
            Transaction... transactions
    ) {
        RgxGen rgxGen = new RgxGen(AccountNumber.VALID_REGEX);

        BankAccount bankAccount = new BankAccount();
        bankAccount.setAccountNumber(AccountNumber.of(rgxGen.generate()));
        bankAccount.setOwner(RandomStringUtils.randomAlphabetic(20));
        bankAccount.setBalance(balance);
        bankAccount.setTransactions(new ArrayList<>(Arrays.asList(transactions)));
        bankAccount.setCreatedDate(LocalDateTime.now());
        return bankAccount;
    }
}
