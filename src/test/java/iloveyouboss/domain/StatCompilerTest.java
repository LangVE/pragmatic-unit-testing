package iloveyouboss.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class StatCompilerTest {

    @Test
    public void responsesByQuestion() {
        // given
        StatCompiler stats = new StatCompiler();
        List<BooleanAnswer> answers = new ArrayList<>();
        answers.add(new BooleanAnswer(1, true));
        answers.add(new BooleanAnswer(1, true));
        answers.add(new BooleanAnswer(1, true));
        answers.add(new BooleanAnswer(1, false));
        answers.add(new BooleanAnswer(2, true));
        answers.add(new BooleanAnswer(2, true));
        Map<Integer, String> questions = new HashMap<>();
        questions.put(1, "Tuition reimbursement?");
        questions.put(2, "Relocation package?");

        // when
        Map<String, Map<Boolean, AtomicInteger>> responses = stats.responsesByQuestion(answers, questions);

        // then
        assertThat(responses.get("Tuition reimbursement?").get(Boolean.TRUE).get(), equalTo(3));
        assertThat(responses.get("Tuition reimbursement?").get(Boolean.FALSE).get(), equalTo(1));
        assertThat(responses.get("Relocation package?").get(Boolean.TRUE).get(), equalTo(2));
        assertThat(responses.get("Relocation package?").get(Boolean.FALSE).get(), equalTo(0));
    }
}