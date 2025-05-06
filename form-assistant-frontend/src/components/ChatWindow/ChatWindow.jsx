import React, { useEffect, useRef } from "react";
import "./ChatWindow.css";

const ChatWindow = ({ messages }) => {
    const endOfMessagesRef = useRef(null);

    useEffect(() => {
        if (endOfMessagesRef.current) {
            endOfMessagesRef.current.scrollIntoView({ behavior: "smooth" });
        }
    }, [messages]);

    return (
        <div className="chat-window">
            {messages.map((msg, index) => {
                const isUser = msg.sender === "user";
                const userClasses = "user";
                const systemClasses = "system";

                return (
                    <div
                        key={index}
                        className={`${isUser ? userClasses : systemClasses}`}
                    >
                        {msg.text}
                    </div>
                );
            })}
            <div ref={endOfMessagesRef} />
        </div>
    );
};

export default ChatWindow;
