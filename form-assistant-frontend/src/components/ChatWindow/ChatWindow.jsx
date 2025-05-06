import React from "react";

const ChatWindow = ({ messages }) => {
    return (
        <div>
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
