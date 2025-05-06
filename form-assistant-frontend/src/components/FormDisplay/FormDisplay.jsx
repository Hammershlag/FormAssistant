import React from "react";

const FormDisplay = ({ formData }) => {
    const renderField = (value) => {
        if (value === null) {
            return <span>No data</span>;
        }

        return <span>{value}</span>;
    };

    return (
        <div className="form-display">
            <div>
                {Object.keys(formData).map((key) => {
                    const value = formData[key];
                    return (
                        <div key={key} className="form-field">
                            <div>
                                {key.charAt(0).toUpperCase() + key.slice(1)}
                            </div>
                            <div>
                                {renderField(value)}
                            </div>
                        </div>
                    );
                })}
            </div>
        </div>
    );
};

export default FormDisplay;
