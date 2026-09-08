import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAccount {
    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}

public class Task2 {
    @Test
    void testDepositDoesNotCheckNegativeAmount() {
        BankAccount ba = new BankAccount(0);
        assertDoesNotThrow(() -> ba.deposit(-100));
        assertEquals(-100, ba.getBalance());
    }

    @Test
    void testWithdrawDoesNotCheckNegativeAmount() {
        BankAccount ba = new BankAccount(0);
        assertDoesNotThrow(() -> ba.withdraw(-100));
        assertEquals(100, ba.getBalance());
    }

    @Test
    void testWithdrawCanProduceNegativeBalance() {
        BankAccount ba = new BankAccount(0);
        assertDoesNotThrow(() -> ba.withdraw(100));
        assertEquals(-100, ba.getBalance());
    }

    @Test
    void testBankAccount() {
        BankAccount ba = new BankAccount(0);
        ba.deposit(1000);
        ba.deposit(300);
        ba.withdraw(500);
        ba.withdraw(600);
        ba.deposit(100);
        assertEquals(300, ba.getBalance());
    }
}
