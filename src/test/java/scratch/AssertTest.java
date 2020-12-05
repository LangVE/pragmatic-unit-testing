package scratch;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class AssertTest {
    private Account account;

    @Before
    public void createAccount() {
        account = new Account("my account");
    }

    @Test
    public void hasPositiveBalance() {
        // given

        // when
        account.deposit(50);

        // then
        assertTrue(account.hasPositiveBalance());
    }

    @Test
    public void depositIncreasesBalance() {
        // given
        int initialBalance = account.getBalance();

        // when
        account.deposit(100);

        // then
        assertTrue(account.getBalance() > initialBalance);
        assertThat(account.getBalance(), equalTo(100));
    }

    class InsufficientFundsException extends RuntimeException {
        private static final long serialVersionUID = 1L;

        public InsufficientFundsException(String message) {
            super(message);
        }
    }

    class Account {
        int balance;
        String name;

        Account(String name) {
            this.name = name;
        }

        void deposit(int dollars) {
            balance += dollars;
        }

        void withdraw(int dollars) {
            if (balance < dollars) {
                throw new InsufficientFundsException("balance only " + balance);
            }
            balance -= dollars;
        }

        public String getName() {
            return name;
        }

        public int getBalance() {
            return balance;
        }

        public boolean hasPositiveBalance() {
            return balance > 0;
        }
    }

    @Test
    public void depositIncreasesBalance_hamcrestAssertTrue() {
        // given

        // when
        account.deposit(50);

        // then
        assertThat(account.getBalance() > 0, is(true));
    }

    @Test
    public void getName_whenMatchersStartWith_thenFail() {
        try {
            // given

            // when

            // then
            assertThat(account.getName(), startsWith("xyz"));
        } catch (AssertionError e) {
            System.out.println("의도된 AssertError 발생");
            e.printStackTrace();
        }
    }


    @Test
    public void stringArray_whenMatchersEqualTo_thenFail() {
        try {
            // given

            // when

            // then
            assertThat(new String[]{"a", "b", "c"}, equalTo(new String[]{"a", "b"}));
        } catch (AssertionError e) {
            System.out.println("의도된 AssertError 발생");
            e.printStackTrace();
        }
    }

    @Test
    public void stringArray_whenMatchersEqualTo_thenPass() {
        // given

        // when

        // then
        assertThat(new String[]{"a", "b"}, equalTo(new String[]{"a", "b"}));
    }

    @Test
    public void stringList_whenMatchersEqualTo_thenFail() {
        try {
            // given

            // when

            // then
            assertThat(Arrays.asList(new String[]{"a"}), equalTo(Arrays.asList(new String[]{"a", "ab"})));
        } catch (AssertionError e) {
            System.out.println("의도된 AssertError 발생");
            e.printStackTrace();
        }
    }

    @Test
    public void stringList_whenMatchersEqualTo_thenPass() {
        // given

        // when

        // then
        assertThat(Arrays.asList(new String[]{"a"}), equalTo(Arrays.asList(new String[]{"a"})));
    }

    @Test
    public void getName_whenMatchersEqualTo_thenPass() {
        // given
        Account account = new Account("my big fat acct");

        // when
        String name = account.getName();

        // then
        assertThat(name, is(equalTo("my big fat acct")));
    }

    @Test
    public void getName_whenMatchersNotEqualTo_thenPass() {
        // given
        Account account = new Account("my big fat acct");

        // when
        String name = account.getName();

        // then
        assertThat(name, not(equalTo("plunderings")));
    }

    @Test
    public void getName_whenMatchersNotNullValue_thenPass() {
        // given
        Account account = new Account("my big fat acct");

        // when
        String name = account.getName();

        // then
        assertThat(name, is(not(nullValue())));
    }

    @Test
    public void getName_whenMatchersIsNotNullValue_thenPass() {
        // given
        Account account = new Account("my big fat acct");

        // when
        String name = account.getName();

        // then
        assertThat(name, is(notNullValue())); // 유용하지 않음
        assertThat(name, equalTo("my big fat acct"));
    }
}
