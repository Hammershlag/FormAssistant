import React from "react";
import { render, screen } from "@testing-library/react";
import FormDisplay from "./FormDisplay";

describe("FormDisplay component", () => {
    const sampleData = {
        firstName: "John",
        lastName: "Doe",
        email: null,
        urgency: 1,
    };

    test("renders without crashing", () => {
        render(<FormDisplay formData={{}} />);
        const formDisplay = document.querySelector(".form-display");
        expect(formDisplay).toBeInTheDocument();
    });


    test("displays all field labels correctly", () => {
        render(<FormDisplay formData={sampleData} />);
        expect(screen.getByText("FirstName")).toBeInTheDocument();
        expect(screen.getByText("LastName")).toBeInTheDocument();
        expect(screen.getByText("Email")).toBeInTheDocument();
        expect(screen.getByText("Urgency")).toBeInTheDocument();
    });

    test("displays correct values", () => {
        render(<FormDisplay formData={sampleData} />);
        expect(screen.getByText("John")).toBeInTheDocument();
        expect(screen.getByText("Doe")).toBeInTheDocument();
        expect(screen.getByText("1")).toBeInTheDocument();
    });

    test('displays "No data" for null values', () => {
        render(<FormDisplay formData={sampleData} />);
        expect(screen.getByText("No data")).toBeInTheDocument();
    });
});
