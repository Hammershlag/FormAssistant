import React, { useState } from "react";
import "./MessageInput.css";

const MessageInput = ({ onSend }) => {
    const [message, setMessage] = useState("");

    const handleSend = () => {
        const trimmed = message.trim();
        if (trimmed) {
            onSend(trimmed);
            setMessage("");
        }
    };

    const handleKeyDown = (e) => {
        if (e.key === "Enter" && !e.shiftKey) {
            e.preventDefault();
            handleSend();
        }
    };

    return (
        <div className="message-input-container">
            <textarea
                className="message-input-textarea"
                rows={1}
                placeholder="Type your message..."
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                onKeyDown={handleKeyDown}
            />
            <button
                className="message-input-button"
                onClick={handleSend}
            >
                Send
            </button>
        </div>
    );
};

export default MessageInput;
