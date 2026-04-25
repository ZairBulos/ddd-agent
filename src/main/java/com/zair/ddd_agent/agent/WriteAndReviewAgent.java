package com.zair.ddd_agent.agent;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.Ai;
import com.embabel.agent.domain.io.UserInput;
import com.zair.ddd_agent.model.LinkedInDraftPost;
import com.zair.ddd_agent.model.LinkedInReviewedPost;
import com.zair.ddd_agent.util.FileUtil;

@Agent(description = "Write and review a LinkedIn post about a Domain-Driven Design (DDD) topic")
class WriteAndReviewAgent {

    private final AgentProperties agentProperties;
    private final FileUtil fileUtil;

    public WriteAndReviewAgent(AgentProperties agentProperties, FileUtil fileUtil) {
        this.agentProperties = agentProperties;
        this.fileUtil = fileUtil;
    }

    @Action(description = "Write an initial draft for a LinkedIn post")
    LinkedInDraftPost writeDraft(UserInput input, Ai ai) {
        return ai
                .withDefaultLlm()
                .withId("post-draft-writer")
                .withPromptContributor(Personas.WRITER)
                .creating(LinkedInDraftPost.class)
                .fromPrompt("""
                        Write a LinkedIn post about the following DDD topic:
                        %s
                        
                        Requirements:
                        - Start with a strong hook that captures attention in the first line
                        - Keep it between 150 and 300 words
                        - Use short paragraphs (2-3 lines max) for readability on mobile
                        - Include a concrete, real-world example or analogy
                        - End with a clear takeaway or a thought-provoking question
                        - Do NOT use generic filler phrases like "In today's world" or "Game changer"
                        - Write in English
                        """.formatted(input.getContent()));
    }

    @AchievesGoal(description = "A polished and publication-ready LinkedIn post")
    @Action(description = "Review, refine, and improve the LinkedIn post draft")
    LinkedInReviewedPost reviewPost(LinkedInDraftPost draft, Ai ai) {
        var reviewed = ai
                .withLlmByRole("reviewer")
                .withId("post-reviewer")
                .withPromptContributor(Personas.REVIEWER)
                .creating(LinkedInReviewedPost.class)
                .fromPrompt("""
                        Review and improve the following LinkedIn post draft about a DDD topic.
                        
                        Draft:
                        %s
                        
                        Your task:
                        - Fix any technical inaccuracies in DDD terminology or concepts
                        - Strengthen the opening hook if it is weak
                        - Improve flow, clarity, and conciseness
                        - Ensure the post ends with a strong takeaway or call to action
                        - Keep the final post between 150 and 300 words
                        
                        In the 'feedback' field, briefly explain the key changes you made and why.
                        """.formatted(draft.content()));

        fileUtil.writeToFile(reviewed.content(), agentProperties.outputDir());

        return reviewed;
    }

}
