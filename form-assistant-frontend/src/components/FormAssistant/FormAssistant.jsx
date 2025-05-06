import React, { useState } from "react";
import ChatWindow from "../ChatWindow/ChatWindow";
import MessageInput from "../MessageInput/MessageInput";
import FormDisplay from "../FormDisplay/FormDisplay";
import "./FormAssistant.css";

const SupportFormAssistant = () => {
    const [messages, setMessages] = useState([
        {
            text: "👋 Welcome! How can I assist you with filling out your form today?",
            sender: "system",
        }
    ]);

    const [formData, setFormData] = useState({});

    // Example handler to simulate sending a message
    const handleSend = (text) => {
        const newMsg = { text, sender: "user" };
        setMessages([...messages, newMsg]);

        // Simulate system response and update
        setTimeout(() => {
            const systemMsg = {
                text: "This is a simulated response.",
                sender: "system",
            };
            setMessages(prev => [...prev, systemMsg]);

            setFormData({
                firstName: "John",
                lastName: "Doe",
                email: null,
                reasonOfContact: null,
                urgency: 1,
            });
        }, 500);
    };

    return (
        <div className="assistant-container">
            <div className="chat-section">
                <ChatWindow messages={messages} />
                <MessageInput onSend={handleSend} />
            </div>
            <div className="form-section">
                <FormDisplay formData={formData} />
            </div>
        </div>
    );
};

export default SupportFormAssistant;
