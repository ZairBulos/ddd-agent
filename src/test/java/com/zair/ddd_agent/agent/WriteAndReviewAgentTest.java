package com.zair.ddd_agent.agent;

import com.embabel.agent.domain.io.UserInput;
import com.embabel.agent.test.unit.FakeOperationContext;
import com.embabel.agent.test.unit.FakePromptRunner;
import com.zair.ddd_agent.model.LinkedInDraftPost;
import com.zair.ddd_agent.model.LinkedInReviewedPost;
import com.zair.ddd_agent.util.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class WriteAndReviewAgentTest {

    private WriteAndReviewAgent agent;

    @BeforeEach
    void setUp() {
        var agentProperties = new AgentProperties("test-output");
        var fileUtil = mock(FileUtil.class);

        agent = new WriteAndReviewAgent(agentProperties, fileUtil);
    }

    @Test
    void testWriteDraft() {
        var context = FakeOperationContext.create();
        var promptRunner = (FakePromptRunner) context.promptRunner();
        context.expectResponse(new LinkedInDraftPost("Aggregates are not just clusters of objects."));

        var draft = agent.writeDraft(new UserInput("Aggregate design"), context.ai());

        var prompt = promptRunner.getLlmInvocations().getFirst().getMessages().getFirst().getContent();
        assertTrue(prompt.contains("Aggregate design"), "Expected prompt to contain the DDD topic");
    }

    @Test
    void testReviewPost() {
        var context = FakeOperationContext.create();
        var draft = new LinkedInDraftPost("Aggregates protect invariants at their boundary.");
        context.expectResponse(new LinkedInReviewedPost("Polished post.", "Improved the hook."));

        var reviewed = agent.reviewPost(draft, context.ai());

        var prompt = context.getLlmInvocations().getFirst().getMessages().getFirst().getContent();
        assertTrue(prompt.contains("Aggregates protect invariants"), "Expected prompt to contain the draft content");
        assertTrue(prompt.contains("feedback"), "Expected prompt to ask for feedback");
    }

}
