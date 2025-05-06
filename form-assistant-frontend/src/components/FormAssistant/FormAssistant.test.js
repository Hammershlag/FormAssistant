import React from "react";
import { render, screen } from "@testing-library/react";
import FormAssistant from "./FormAssistant";

describe("FormAssistant", () => {
    test("renders without crashing", () => {
        render(<FormAssistant />);
    });

    test("renders ChatWindow with welcome message", () => {
        render(<FormAssistant />);
        const welcomeMessage = screen.getByText(/How can i assist you with filling out your form/i);
        expect(welcomeMessage).toBeInTheDocument();
    });

    test("renders MessageInput component", () => {
        render(<FormAssistant />);
        const input = screen.getByPlaceholderText(/Type your message/i);
        expect(input).toBeInTheDocument();
    });

    test("renders FormDisplay component", () => {
        render(<FormAssistant />);
        const formDisplay = document.querySelector(".form-display");
        expect(formDisplay).toBeInTheDocument();
    });
});
