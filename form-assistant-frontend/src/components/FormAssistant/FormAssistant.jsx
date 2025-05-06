import React, { useEffect, useState } from "react";
import ChatWindow from "../ChatWindow/ChatWindow";
import MessageInput from "../MessageInput/MessageInput";
import FormDisplay from "../FormDisplay/FormDisplay";
import { fetchInitialForm, sendMessage } from "../../api/supportFormApi";
import "./FormAssistant.css";

const FormAssistant = () => {
    const [messages, setMessages] = useState([
        {
            text: "👋 Welcome! How can I assist you with filling out your form today?",
            sender: "system",
        }
    ]);
    const [formData, setFormData] = useState({});
    const [formId, setFormId] = useState(null);

    useEffect(() => {
        const loadInitialForm = async () => {
            try {
                const data = await fetchInitialForm();
                setFormData(data);
            } catch (error) {
                console.error("Error loading initial form:", error);
            }
        };
        loadInitialForm();
    }, []);

    const handleSend = async (text) => {
        const userMsg = { text, sender: "user" };
        setMessages(prev => [...prev, userMsg]);

        const result = await sendMessage(text, formId);

        if (result.error) {
            const errorMsg = {
                text: `⚠️ ${result.message}`,
                sender: "system",
            };
            setMessages(prev => [...prev, errorMsg]);
            return;
        }

        const { message, updatedForm, formId: newFormId } = result.data;
        const systemMsg = { text: message, sender: "system" };
        setMessages(prev => [...prev, systemMsg]);
        setFormData(updatedForm);
        if (!formId) setFormId(newFormId);
    };


    return (
        <>
            <div className="assistant-container" data-testid="form-assistant">
                <div className="chat-section">
                    <ChatWindow messages={messages} />
                    <MessageInput onSend={handleSend} />
                </div>
                <div className="form-section">
                    <FormDisplay formData={formData} />
                </div>
            </div>
        </>
    );
};

export default FormAssistant;
