// src/api.js
const API_BASE = "http://localhost:8080/support-form";

export const fetchInitialForm = async () => {
    const res = await fetch(`${API_BASE}/initial`);
    if (!res.ok) throw new Error("Failed to fetch initial form");
    return await res.json();
};

export const sendMessage = async (text, formId) => {
    const url = formId ? `${API_BASE}/${formId}` : API_BASE;
    const res = await fetch(url, {
        method: "PUT",
        headers: { "Content-Type": "text/plain" },
        body: text,
    });

    const data = await res.json();
    if (!res.ok) {
        return { error: true, message: data.errorMessage || "An error occurred." };
    }

    return {
        error: false,
        data,
    };
};

