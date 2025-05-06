import React from "react";
import "./ChatWindow.css"

const ChatWindow = ({ messages }) => {
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
        </div>
    );
};

export default ChatWindow;
