import React, { useState } from "react";

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
        <div>
            <textarea
              rows={1}
              placeholder="Type your message..."
              value={message}
              onChange={(e) => setMessage(e.target.value)}
              onKeyDown={handleKeyDown}
            />
            <button
                onClick={handleSend}
            >
                Send
            </button>
        </div>
    );
};

export default MessageInput;
