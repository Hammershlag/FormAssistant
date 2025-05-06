import React from "react";
import { render, fireEvent, screen } from "@testing-library/react";
import MessageInput from "./MessageInput";

describe("MessageInput component", () => {
    it("renders input and button", () => {
        render(<MessageInput onSend={() => {}} />);
        expect(screen.getByPlaceholderText("Type your message...")).toBeInTheDocument();
        expect(screen.getByText("Send")).toBeInTheDocument();
    });

    it("calls onSend with trimmed message on button click", () => {
        const mockSend = jest.fn();
        render(<MessageInput onSend={mockSend} />);
        const input = screen.getByPlaceholderText("Type your message...");
        const button = screen.getByText("Send");

        fireEvent.change(input, { target: { value: "  Hello world  " } });
        fireEvent.click(button);

        expect(mockSend).toHaveBeenCalledWith("Hello world");
    });

    it("clears input after sending", () => {
        const mockSend = jest.fn();
        render(<MessageInput onSend={mockSend} />);
        const input = screen.getByPlaceholderText("Type your message...");
        const button = screen.getByText("Send");

        fireEvent.change(input, { target: { value: "Test" } });
        fireEvent.click(button);

        expect(input.value).toBe("");
    });

    it("does not call onSend with empty input", () => {
        const mockSend = jest.fn();
        render(<MessageInput onSend={mockSend} />);
        const input = screen.getByPlaceholderText("Type your message...");
        const button = screen.getByText("Send");

        fireEvent.change(input, { target: { value: "   " } });
        fireEvent.click(button);

        expect(mockSend).not.toHaveBeenCalled();
    });

    it("sends message on Enter key press", () => {
        const mockSend = jest.fn();
        render(<MessageInput onSend={mockSend} />);
        const input = screen.getByPlaceholderText("Type your message...");

        fireEvent.change(input, { target: { value: "Enter key" } });
        fireEvent.keyDown(input, { key: "Enter", code: "Enter" });

        expect(mockSend).toHaveBeenCalledWith("Enter key");
    });
});
