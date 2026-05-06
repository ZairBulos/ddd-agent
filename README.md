# DDD Agent

An AI-powered agent that generates LinkedIn posts about Domain-Driven Design (DDD) topics, 
built with [Embabel Agent Framework](https://github.com/embabel/embabel-agent).

## Quick Start

```bash
export GEMINI_API_KEY=your-gemini-api-key-here
ollama pull llama3.2
mvn spring-boot:run
```

The app launches an interactive shell. Type `x "your topic"` and the agent will produce a reviewed, 
publication-ready LinkedIn post saved to `linkedin-posts/`.

## Prerequisites

- Java 25+
- Maven 3.9+
- [Ollama](https://ollama.com) running locally with a model pulled
- A Gemini API key → [aistudio.google.com](https://aistudio.google.com) (free tier works)

## How It Works

The agent runs a two-step pipeline powered by [Embabel](https://github.com/embabel/embabel-agent),
a JVM agent framework that uses **Goal-Oriented Action Planning (GOAP)** to resolve the execution
order automatically from input/output types — no orchestration code needed.

UserInput ──► writeDraft (Ollama) ──► reviewPost (Gemini) ──► LinkedInReviewedPost

1. **writeDraft** — A local Ollama model writes an opinionated draft from the perspective of a developer who has applied DDD in real systems.
2. **reviewPost** — Gemini verifies DDD terminology accuracy and LinkedIn writing quality (hook, mobile readability, takeaway), then saves the final post to `posts/`.

## Usage

Once the shell starts, use the `x` command with your DDD topic:

````shell
x "bounded contexts explained"
````
