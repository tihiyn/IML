import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CorrectBankAccount {
    private double balance;

    public CorrectBankAccount(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма депозита должна быть положительной");
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Сумма снятия должна быть положительной");
        }
        if (amount > balance) {
            throw new IllegalStateException("На банковском счёте недостаточно средств");
        }
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}

public class Task3 {
    @Test
    void testDepositCheckNegativeAmount() {
        CorrectBankAccount ba = new CorrectBankAccount(0);
        assertThrows(IllegalArgumentException.class, () -> ba.deposit(-100));
        assertEquals(0, ba.getBalance());
    }

    @Test
    void testWithdrawCheckNegativeAmount() {
        CorrectBankAccount ba = new CorrectBankAccount(0);
        assertThrows(IllegalArgumentException.class, () -> ba.withdraw(-100));
        assertEquals(0, ba.getBalance());
    }

    @Test
    void testWithdrawCannotProduceNegativeBalance() {
        CorrectBankAccount ba = new CorrectBankAccount(0);
        assertThrows(IllegalStateException.class, () -> ba.withdraw(100));
        assertEquals(0, ba.getBalance());
    }
}
