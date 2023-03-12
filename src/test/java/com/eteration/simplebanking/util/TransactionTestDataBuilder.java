package com.eteration.simplebanking.util;

import com.eteration.simplebanking.domain.model.Amount;
import com.eteration.simplebanking.domain.model.account.transaction.DepositTransaction;
import com.eteration.simplebanking.domain.model.account.transaction.WithdrawTransaction;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TransactionTestDataBuilder {
    public static DepositTransaction approvedAndPersistedDepositTransaction(Amount amount) {
        DepositTransaction depositTransaction = new DepositTransaction(amount);
        depositTransaction.setId(new Random().nextLong(50));
        depositTransaction.setApprovalCode(UUID.randomUUID().toString());
        depositTransaction.setCreatedDate(LocalDateTime.now());
        return depositTransaction;
    }

    public static WithdrawTransaction approvedAndPersistedWithdrawTransaction(Amount amount) {
        WithdrawTransaction withdrawTransaction = new WithdrawTransaction(amount);
        withdrawTransaction.setId(new Random().nextLong(50));
        withdrawTransaction.setApprovalCode(UUID.randomUUID().toString());
        withdrawTransaction.setCreatedDate(LocalDateTime.now());
        return withdrawTransaction;
    }

    public static DepositTransaction approvedDepositTransaction(Amount amount) {
        DepositTransaction depositTransaction = new DepositTransaction(amount);
        depositTransaction.setApprovalCode(UUID.randomUUID().toString());
        return depositTransaction;
    }

    public static WithdrawTransaction approvedWithdrawTransaction(Amount amount) {
        WithdrawTransaction withdrawTransaction = new WithdrawTransaction(amount);
        withdrawTransaction.setApprovalCode(UUID.randomUUID().toString());
        return withdrawTransaction;
    }
}
