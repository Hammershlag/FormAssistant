import React from "react";
import { render, screen } from "@testing-library/react";
import ChatWindow from "./ChatWindow";

describe("ChatWindow component", () => {
    const messages = [
        { sender: "user", text: "Hi there!" },
        { sender: "system", text: "Hello, how can I assist you?" },
    ];

    it("renders without crashing", () => {
        const { container } = render(<ChatWindow messages={[]} />);
        const chatWindow = container.querySelector(".chat-window");
        expect(chatWindow).toBeInTheDocument();
    });

    it("renders all messages", () => {
        render(<ChatWindow messages={messages} />);
        messages.forEach((msg) => {
            expect(screen.getByText(msg.text)).toBeInTheDocument();
        });
    });

    it("applies the correct class to user messages", () => {
        render(<ChatWindow messages={messages} />);
        const userMessage = screen.getByText("Hi there!");
        expect(userMessage).toHaveClass("user");
    });

    it("applies the correct class to system messages", () => {
        render(<ChatWindow messages={messages} />);
        const systemMessage = screen.getByText("Hello, how can I assist you?");
        expect(systemMessage).toHaveClass("system");
    });
});
